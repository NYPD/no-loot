package rip.noloot.api.battlenet;

/**
 * POJO for reading in the oAuth2 access token response from https://_region_.battle.net/oauth/token .
 */
public class TokenResponse {

    /**
     * The access token used on future requests to the API.
     */
    private String accessToken;
    private String tokenType;
    /**
     * Seconds from when received that the token will expire.
     */
    private Long expiresIn;

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }

    @Override
    public String toString() {
        return "TokenResponse [accessToken=" + this.accessToken + ", tokenType=" + this.tokenType + ", expiresIn=" + this.expiresIn + "]";
    }

}
