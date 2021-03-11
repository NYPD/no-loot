package rip.noloot.api.battlenet.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for reading in the oAuth2 access token response from https://_region_.battle.net/oauth/token
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String BATTLENET_SCOPE = "scope";
    public static final String EXPIRATION = "expires_in";

    /**
     * The access token used on future requests to the API.
     */
    @JsonProperty(TokenResponse.ACCESS_TOKEN)
    private String accessToken;
    @JsonProperty(TokenResponse.TOKEN_TYPE)
    private String tokenType;
    @JsonProperty(TokenResponse.BATTLENET_SCOPE)
    private String scope;

    /**
     * Seconds from when received that the token will expire.
     */
    @JsonProperty(TokenResponse.EXPIRATION)
    private Long expiresIn;

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public String getScope() {
        return this.scope;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }

    @Override
    public String toString() {
        return "TokenResponse [accessToken=" + this.accessToken + ", tokenType=" + this.tokenType + ", expiresIn=" + this.expiresIn + "]";
    }

}
