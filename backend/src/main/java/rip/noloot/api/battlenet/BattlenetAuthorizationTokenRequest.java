package rip.noloot.api.battlenet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import rip.noloot.api.battlenet.response.TokenResponse;
import rip.noloot.api.battlenet.util.OauthApiUtil;
import rip.noloot.exception.ApiException;

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

    private RestTemplate restTemplate;
    private BattlenetClientSecrets battlenetClientSecrets;
    private String authorizationCode;
    private String redirectURI;

    public BattlenetAuthorizationTokenRequest(RestTemplate restTemplate,
                                              BattlenetClientSecrets battlenetClientSecrets,
                                              String authorizationCode,
                                              String redirectURI) {

        Objects.requireNonNull(restTemplate);
        Objects.requireNonNull(battlenetClientSecrets);
        Objects.requireNonNull(authorizationCode);
        Objects.requireNonNull(redirectURI);

        this.restTemplate = restTemplate;
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

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String battlenetCreds = this.battlenetClientSecrets.getClientId() + ":" + this.battlenetClientSecrets.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(battlenetCreds.getBytes(StandardCharsets.UTF_8));

        headers.setBasicAuth(encodedCredentials);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", BattlenetAuthorizationTokenRequest.GRANT_TYPE);
        map.add("code", this.authorizationCode);
        map.add("redirect_uri", this.redirectURI);
        map.add("client_id", this.battlenetClientSecrets.getClientId());
        map.add("client_secret", this.battlenetClientSecrets.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(OauthApiUtil.getTokenRequestUrlString(null), entity, TokenResponse.class);

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) throw new ApiException();

        return response.getBody();

    }

}
