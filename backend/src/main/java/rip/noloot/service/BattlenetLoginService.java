package rip.noloot.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rip.noloot.annotation.BattlenetLogin;
import rip.noloot.api.battlenet.BattlenetAuthorizationCodeRequestUrl;
import rip.noloot.api.battlenet.BattlenetAuthorizationTokenRequest;
import rip.noloot.api.battlenet.BattlenetClientSecrets;
import rip.noloot.api.battlenet.response.AuthorizationResponse;
import rip.noloot.api.battlenet.response.TokenResponse;
import rip.noloot.api.battlenet.response.UserInfoResponse;
import rip.noloot.api.battlenet.util.OauthApiUtil;
import rip.noloot.bean.BattlenetSessionBean;
import rip.noloot.domain.NoLootUser;
import rip.noloot.exception.InvalidStateTokenException;
import rip.noloot.repository.UsersRepository;
import rip.noloot.util.AppConstants;
import rip.noloot.util.HttpRequestUtil;

@Service
@BattlenetLogin
public class BattlenetLoginService implements Oauth2LoginService {

    public static final String BATTLENET_TOKEN_COOKIE_NAME = "battlenet-token";

    private static final Logger LOGGER = LogManager.getLogger(BattlenetLoginService.class);

    private static final String OPEN_ID_SCOPE = "openid";
    private static final List<String> SCOPES = Arrays.asList(BattlenetLoginService.OPEN_ID_SCOPE);

    private String redirectURI;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment springEnvironment;
    @Autowired
    private BattlenetClientSecrets battlenetClientSecrets;
    @Autowired
    private BattlenetSessionBean battlenetSessionBean;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String getAuthenticationRequestUrl() {

        BattlenetAuthorizationCodeRequestUrl battlenetAuthorizationCodeRequestUrl = new BattlenetAuthorizationCodeRequestUrl(this.battlenetClientSecrets,
                                                                                                                             this.redirectURI,
                                                                                                                             BattlenetLoginService.SCOPES);

        String stateToken = this.getStateToken();

        battlenetAuthorizationCodeRequestUrl.setState(stateToken);
        this.battlenetSessionBean.setStateToken(stateToken);

        return battlenetAuthorizationCodeRequestUrl.build();
    }

    @Override
    public void verifyAuthenticationResponseAndRetrieveToken(HttpServletRequest request) {

        AuthorizationResponse authorizationResponse = new AuthorizationResponse(request);

        String battlenetstateToken = authorizationResponse.getStateToken();
        String sessionStateToken = this.battlenetSessionBean.getStateToken();

        boolean notSameStateToken = !Objects.equals(battlenetstateToken, sessionStateToken);
        if (notSameStateToken) throw new InvalidStateTokenException(request);

        BattlenetAuthorizationTokenRequest battlenetAuthorizationTokenRequest = new BattlenetAuthorizationTokenRequest(this.battlenetClientSecrets,
                                                                                                                       authorizationResponse.getAuthorizationCode(),
                                                                                                                       this.redirectURI);

        TokenResponse tokenResponse = battlenetAuthorizationTokenRequest.execute();
        this.battlenetSessionBean.setTokenResponse(tokenResponse);

    }

    @Override
    public NoLootUser getNoLootUser() {

        String userInfoUrlString = OauthApiUtil.getUserInfoUrlString(null);
        TokenResponse token = this.battlenetSessionBean.getTokenResponse();

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());

        UserInfoResponse userInfo = HttpRequestUtil.sendGet(userInfoUrlString, headers, null, UserInfoResponse.class);

        NoLootUser noLootUser = this.usersRepository.findByBattleNetId(userInfo.getId());

        if (noLootUser == null) {
            noLootUser = new NoLootUser(userInfo);
            this.usersRepository.save(noLootUser);
        }

        return noLootUser;
    }

    @Override
    public void createUserCookies(HttpServletResponse response) {

        TokenResponse tokenResponse = this.battlenetSessionBean.getTokenResponse();

        byte[] tokenResponseBytes = null;
        try {
            tokenResponseBytes = this.objectMapper.writeValueAsBytes(tokenResponse);
        }
        catch (JsonProcessingException e) {
            BattlenetLoginService.LOGGER.error("Error trying to encode TokenResponse object", e);
        }

        String encodedTokenResponse = Base64.getEncoder().encodeToString(tokenResponseBytes);
        Cookie cookie = new Cookie(BATTLENET_TOKEN_COOKIE_NAME, encodedTokenResponse);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(AppConstants.FIVE_YEARS_IN_SECONDS);

        response.addCookie(cookie);

    }

    @Override
    public void reAuthenticateUser(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        Map<String, Cookie> cookieByName = new HashMap<>();
        for (Cookie cookie : cookies)
            cookieByName.put(cookie.getName(), cookie);

        String encodedTokenResponseJson = cookieByName.get(BATTLENET_TOKEN_COOKIE_NAME).getValue();
        byte[] tokenResponseJsonBytes = Base64.getDecoder().decode(encodedTokenResponseJson);
        String tokenResponseJson = new String(tokenResponseJsonBytes);

        TokenResponse tokenResponse = null;
        //TODO test the token response through bliizy api
        try {
            tokenResponse = this.objectMapper.readValue(tokenResponseJson, TokenResponse.class);
            this.battlenetSessionBean.setTokenResponse(tokenResponse);

            NoLootUser noLootUser = this.getNoLootUser();
            System.out.println(noLootUser);
        }
        catch (JsonProcessingException e) {
            LOGGER.error(e);
        }

    }

    @PostConstruct
    private void init() {

        // If it is a development profile, try and find a localhost redirect
        String[] activeProfiles = this.springEnvironment.getActiveProfiles();
        boolean isDevelopment = Arrays.stream(activeProfiles).filter(AppConstants.DEVELOPMENT_PROFILE::equals).findAny().orElse(null) != null;

        for (String uri : this.battlenetClientSecrets.getRedirectUris()) {
            boolean isLocalHost = uri.contains("localhost");

            if (isLocalHost && isDevelopment || !isLocalHost && !isDevelopment) {
                this.redirectURI = uri;
                break;
            }

        }

        if (this.redirectURI == null) throw new IllegalStateException("No suitable redirectURI found in battlenetClientSecrets");

    }

}
