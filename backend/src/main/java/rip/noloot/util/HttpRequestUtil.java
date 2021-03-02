package rip.noloot.util;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import rip.noloot.api.battlenet.util.OauthApiUtil;
import rip.noloot.exception.ApiException;

/**
 * Utility class to help send HTTP requests
 * 
 * @author NYPD
 *
 */
public class HttpRequestUtil {

    private static final HttpHeaders HTTP_HEADERS;
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    static {
        HTTP_HEADERS = new HttpHeaders();
        HTTP_HEADERS.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HTTP_HEADERS.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    private HttpRequestUtil() throws IllegalAccessException {
        throw new IllegalAccessException("No HttpRequestUtil instances for you!");
    }

    public static <T> T sendPost(String url, Map<String, String> headers, Class<T> valueType) {
        return sendPost(url, headers, null, valueType);
    }

    public static <T> T sendPost(String url, Map<String, String> headers, Map<String, String> parameters, Class<T> valueType) {

        Objects.requireNonNull(url, "You must specify a request URL");
        Objects.requireNonNull(valueType, "Class type needs to be provided");

        HttpHeaders httpHeaders = new HttpHeaders(HTTP_HEADERS);

        if (headers != null) {

            for (Entry<String, String> parameterEntry : headers.entrySet())
                httpHeaders.add(parameterEntry.getKey(), parameterEntry.getValue());

        }

        MultiValueMap<String, String> parameterMap = null;

        if (parameters != null) {
            parameterMap = new LinkedMultiValueMap<>();

            for (Entry<String, String> parameterEntry : parameters.entrySet())
                parameterMap.add(parameterEntry.getKey(), parameterEntry.getValue());

        }

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameterMap, HTTP_HEADERS);

        ResponseEntity<T> response = REST_TEMPLATE.postForEntity(OauthApiUtil.getTokenRequestUrlString(null), entity, valueType);

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) throw new ApiException(statusCode, "Bad HttpStatus code for: " + url);

        return response.getBody();
    }

}
