package rip.noloot.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

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

    private static final String OPEN_ID_SCOPE = "openid";
    private static final List<String> SCOPES = Arrays.asList(OPEN_ID_SCOPE);

    private String redirectURI;

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

        BattlenetAuthorizationCodeRequestUrl battlenetAuthorizationCodeRequestUrl = new BattlenetAuthorizationCodeRequestUrl(battlenetClientSecrets,
                                                                                                                             this.redirectURI,
                                                                                                                             SCOPES);

        String stateToken = this.getStateToken();

        battlenetAuthorizationCodeRequestUrl.setState(stateToken);
        battlenetSessionBean.setStateToken(stateToken);

        return battlenetAuthorizationCodeRequestUrl.build();
    }

    @Override
    public void verifyAuthenticationResponseAndRetrieveToken(HttpServletRequest request) {

        AuthorizationResponse authorizationResponse = new AuthorizationResponse(request);

        String battlenetstateToken = authorizationResponse.getStateToken();
        String sessionStateToken = battlenetSessionBean.getStateToken();

        boolean notSameStateToken = !Objects.equals(battlenetstateToken, sessionStateToken);
        if (notSameStateToken) throw new InvalidStateTokenException(request);

        BattlenetAuthorizationTokenRequest battlenetAuthorizationTokenRequest = new BattlenetAuthorizationTokenRequest(battlenetClientSecrets,
                                                                                                                       authorizationResponse.getAuthorizationCode(),
                                                                                                                       this.redirectURI);

        TokenResponse tokenResponse = battlenetAuthorizationTokenRequest.execute();
        battlenetSessionBean.setToken(tokenResponse);

    }

    @Override
    public NoLootUser getNoLootUser() {

        String userInfoUrlString = OauthApiUtil.getUserInfoUrlString(null);
        TokenResponse token = battlenetSessionBean.getToken();

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());

        UserInfoResponse userInfo = HttpRequestUtil.sendGet(userInfoUrlString, headers, null, UserInfoResponse.class);

        NoLootUser noLootUser = usersRepository.findByBattleNetId(userInfo.getId());

        if (noLootUser == null) {
            noLootUser = new NoLootUser(userInfo);
            usersRepository.save(noLootUser);
        }

        return noLootUser;
    }

    @Override
    public void createUserCookies(HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void reAuthenticateUser(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    @PostConstruct
    private void init() {

        // If it is a development profile, try and find a localhost redirect
        String[] activeProfiles = springEnvironment.getActiveProfiles();
        boolean isDevelopment = Arrays.stream(activeProfiles).filter(AppConstants.DEVELOPMENT_PROFILE::equals).findAny().orElse(null) != null;

        for (String uri : battlenetClientSecrets.getRedirectUris()) {
            boolean isLocalHost = uri.contains("localhost");

            if (isLocalHost && isDevelopment || !isLocalHost && !isDevelopment) {
                this.redirectURI = uri;
                break;
            }

        }

        if (this.redirectURI == null) throw new IllegalStateException("No suitable redirectURI found in battlenetClientSecrets");

    }

}
