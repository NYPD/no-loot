package rip.noloot.api.battlenet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpHeaders;

import rip.noloot.api.battlenet.response.TokenResponse;
import rip.noloot.api.battlenet.util.OauthApiUtil;
import rip.noloot.util.HttpRequestUtil;

/**
 * Battlenet specific implementation of the OAuth 2.0 request for an access token based on an authorization code.
 * 
 * A {@link TokenResponse} is returned by {@link #execute()} to be used for future access of protected resources.
 * 
 * @author NYPD
 *
 */
public class BattlenetAuthorizationTokenRequest {

    private static final String GRANT_TYPE = "authorization_code";

    private final BattlenetClientSecrets battlenetClientSecrets;
    private final String authorizationCode;
    private final String redirectURI;

    public BattlenetAuthorizationTokenRequest(BattlenetClientSecrets battlenetClientSecrets, String authorizationCode, String redirectURI) {

        Objects.requireNonNull(battlenetClientSecrets);
        Objects.requireNonNull(authorizationCode);
        Objects.requireNonNull(redirectURI);

        this.battlenetClientSecrets = battlenetClientSecrets;
        this.authorizationCode = authorizationCode;
        this.redirectURI = redirectURI;

    }

    /**
     * 
     * @return
     * @throws IOException
     */
    public TokenResponse execute() {

        Map<String, String> headers = new HashMap<>();

        String battlenetCreds = this.battlenetClientSecrets.getClientId() + ":" + this.battlenetClientSecrets.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(battlenetCreds.getBytes(StandardCharsets.UTF_8));

        headers.put(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials);

        Map<String, String> paramterMap = new HashMap<>();

        paramterMap.put("grant_type", BattlenetAuthorizationTokenRequest.GRANT_TYPE);
        paramterMap.put("code", this.authorizationCode);
        paramterMap.put("redirect_uri", this.redirectURI);
        paramterMap.put("client_id", this.battlenetClientSecrets.getClientId());
        paramterMap.put("client_secret", this.battlenetClientSecrets.getClientSecret());//TODO test without this

        return HttpRequestUtil.sendPost(OauthApiUtil.getTokenRequestUrlString(null), headers, paramterMap, TokenResponse.class);

    }

}
