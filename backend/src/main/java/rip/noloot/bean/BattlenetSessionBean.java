package rip.noloot.bean;

import rip.noloot.api.battlenet.response.TokenResponse;
import rip.noloot.configuration.api.BattlenetConfiguration;

/**
 * Session bean containing all the relevant information specific to the Battlenet API and the current user.
 * 
 * This is set up in {@link BattlenetConfiguration}
 * 
 * @author NYPD
 */
public class BattlenetSessionBean {

    private String stateToken;
    private TokenResponse tokenResponse;

    public String getStateToken() {
        return stateToken;
    }

    public void setStateToken(String stateToken) {
        this.stateToken = stateToken;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

}
