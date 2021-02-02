package rip.noloot.api.battlenet.response;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for reading in the oAuth2 authorization response from https://_region_.battle.net/oauth/authorize
 * 
 * Which seems to be just a get request
 */
public class AuthorizationResponse {

    private static final String AUTHERIZATION_CODE_PARAMETER = "code";
    private static final String STATE_TOKEN_PARAMTER = "state";

    @JsonProperty("code")
    private String authorizationCode;
    @JsonProperty("state")
    private String stateToken;

    public AuthorizationResponse(HttpServletRequest request) {

        this.authorizationCode = request.getParameter(AUTHERIZATION_CODE_PARAMETER);
        this.stateToken = request.getParameter(STATE_TOKEN_PARAMTER);

        Objects.requireNonNull(authorizationCode, AUTHERIZATION_CODE_PARAMETER + " is not found in request: " + request.getRequestURI());
        Objects.requireNonNull(stateToken, STATE_TOKEN_PARAMTER + " is not found in request: " + request.getRequestURI());
    }

    public String getAuthorizationCode() {
        return this.authorizationCode;
    }

    public String getStateToken() {
        return this.stateToken;
    }

    @Override
    public String toString() {
        return "AuthorizationResponse [authorizationCode=" + this.authorizationCode + ", stateToken=" + this.stateToken + "]";
    }

}
