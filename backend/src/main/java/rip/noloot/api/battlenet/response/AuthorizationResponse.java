package rip.noloot.api.battlenet.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationResponse {

    @JsonProperty("code")
    private String authorizationCode;
    @JsonProperty("state")
    private String stateToken;

    private AuthorizationResponse() {}

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
