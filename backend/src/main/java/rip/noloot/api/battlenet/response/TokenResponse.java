package rip.noloot.api.battlenet.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for reading in the oAuth2 access token response from https://_region_.battle.net/oauth/token
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    /**
     * The access token used on future requests to the API.
     */
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("scope")
    private String scope;

    /**
     * Seconds from when received that the token will expire.
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public String getScope() {
        return scope;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }

    @Override
    public String toString() {
        return "TokenResponse [accessToken=" + this.accessToken + ", tokenType=" + this.tokenType + ", expiresIn=" + this.expiresIn + "]";
    }

}
