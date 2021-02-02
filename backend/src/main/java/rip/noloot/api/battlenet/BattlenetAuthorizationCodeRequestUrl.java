package rip.noloot.api.battlenet;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;

import rip.noloot.api.battlenet.enums.BattlenetRegion;
import rip.noloot.api.battlenet.util.OauthApiUtil;

/**
 * Battlenet specific implementation of the OAuth 2.0 URL builder for an authorization web page to allow the end user to
 * authorize the application to access their protected resources and that returns an authorization code.
 * 
 * @author NYPD
 *
 */
public class BattlenetAuthorizationCodeRequestUrl {

    private static final String RESPONSE_TYPE = "code";

    private BattlenetClientSecrets battlenetClientSecrets;
    private String redirectUri;
    private Collection<String> scopes;
    private String state;
    private BattlenetRegion region;

    public BattlenetAuthorizationCodeRequestUrl(BattlenetClientSecrets secrets,
                                                String redirectUri,
                                                Collection<String> scopes) {

        Objects.requireNonNull(secrets, "BattlenetClientSecrets must not be null");
        Objects.requireNonNull(redirectUri, "redirectUri must not be null");
        Objects.requireNonNull(redirectUri, "scopes must not be null");

        if (scopes.isEmpty()) throw new IllegalArgumentException("One scope must at least be provided");

        this.battlenetClientSecrets = secrets;
        this.redirectUri = redirectUri;
        this.scopes = scopes;
    }

    public BattlenetAuthorizationCodeRequestUrl setState(String state) {
        this.state = state;
        return this;
    }

    public BattlenetAuthorizationCodeRequestUrl setRegion(BattlenetRegion region) {
        this.region = region;
        return this;
    }

    public String build() {

        String oAuthAuthorizationUrlString = OauthApiUtil.getOAuthAuthorizationUrlString(region);

        StringBuilder builder = new StringBuilder(oAuthAuthorizationUrlString);
        builder.append("?");
        builder.append("response_type=" + RESPONSE_TYPE);
        builder.append("&");
        builder.append("client_id=" + battlenetClientSecrets.getClientId());
        builder.append("&");
        builder.append("redirect_uri=" + URLEncoder.encode(this.redirectUri, StandardCharsets.UTF_8));
        builder.append("&");
        builder.append("scope=" + String.join(" ", scopes));
        if (state != null) {
            builder.append("&");
            builder.append("state=" + this.state);
        }

        return builder.toString();
    }

}
