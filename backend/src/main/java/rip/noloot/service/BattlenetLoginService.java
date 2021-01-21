package rip.noloot.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import rip.noloot.annotation.BattlenetLogin;
import rip.noloot.api.battlenet.BattlenetAuthorizationCodeRequestUrl;
import rip.noloot.api.battlenet.BattlenetClientSecrets;
import rip.noloot.bean.BattlenetSessionBean;
import rip.noloot.exception.InvalidStateTokenException;
import rip.noloot.model.User;
import rip.noloot.util.AppConstants;

@Service
@BattlenetLogin
public class BattlenetLoginService implements Oauth2LoginService {

    private static final String OPEN_ID_SCOPE = "openid";
    private static final String AUTHERIZATION_CODE_PARAMETER = "code";
    private static final String STATE_TOKEN_PARAMTER = "state";

    private static final List<String> SCOPES = Arrays.asList(OPEN_ID_SCOPE);

    private String redirectURI;

    @Autowired
    private Environment springEnvironment;
    @Autowired
    private BattlenetClientSecrets battlenetClientSecrets;
    @Autowired
    private BattlenetSessionBean battlenetSessionBean;

    @Override
    public String getAuthenticationRequestUrl() {

        BattlenetAuthorizationCodeRequestUrl battlenetAuthorizationCodeRequestUrl = new BattlenetAuthorizationCodeRequestUrl(battlenetClientSecrets,
                                                                                                                             redirectURI, SCOPES);

        String stateToken = this.getStateToken();

        battlenetAuthorizationCodeRequestUrl.setState(stateToken);
        battlenetSessionBean.setStateToken(stateToken);

        return battlenetAuthorizationCodeRequestUrl.build();
    }

    @Override
    public void verifyAuthenticationResponseAndRetrieveToken(HttpServletRequest request) {

        String authorizationCode = request.getParameter(AUTHERIZATION_CODE_PARAMETER);

        String battlenetstateToken = request.getParameter(STATE_TOKEN_PARAMTER);
        String sessionStateToken = battlenetSessionBean.getStateToken();

        boolean notSameStateToken = !Objects.equals(battlenetstateToken, sessionStateToken);
        if (notSameStateToken) throw new InvalidStateTokenException(request);

        //TODO get token here

    }

    @Override
    public User getNoLootUser() {
        // TODO Auto-generated method stub
        return null;
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
