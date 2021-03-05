package rip.noloot.util;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    /**
     * Helper method to send a {@link HttpMethod#POST} request using the {@link RestTemplate} class from Spring. Constructs
     * the {@link HttpEntity} with the given (if any) headers.
     * <p>
     * See {@link RestTemplate#exchange(String, HttpMethod, HttpEntity, Class, Object...)} for the underlying method.
     * 
     * 
     * @param <T>
     * @param url the URL
     * @param headers additional headers to attach (can be null)
     * @param valueType the type of the return value
     * @return the response as entity
     */
    public static <T> T sendPost(String url, @Nullable Map<String, String> headers, Class<T> valueType) {
        return sendPost(url, headers, null, valueType);
    }

    /**
     * Helper method to send a {@link HttpMethod#POST} request using the {@link RestTemplate} class from Spring. Constructs
     * the {@link HttpEntity} with the given (if any) headers and parameters.
     * <p>
     * See {@link RestTemplate#exchange(String, HttpMethod, HttpEntity, Class, Object...)} for the underlying method.
     * 
     * 
     * @param <T>
     * @param url the URL
     * @param headers additional headers to attach (can be null)
     * @param parameters any parameters to attach (can be null)
     * @param valueType the type of the return value
     * @return the response as entity
     */
    public static <T> T sendPost(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> parameters, Class<T> valueType) {

        Objects.requireNonNull(url, "You must specify a request URL");
        Objects.requireNonNull(valueType, "Class type needs to be provided");

        HttpHeaders httpHeaders = HttpRequestUtil.constructHeaders(headers);
        MultiValueMap<String, String> parameterMap = HttpRequestUtil.constructParamterMap(parameters);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameterMap, httpHeaders);

        ResponseEntity<T> response = REST_TEMPLATE.exchange(url, HttpMethod.POST, entity, valueType);

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) throw new ApiException(statusCode, "Bad HttpStatus code for: " + url);

        return response.getBody();
    }

    /**
     * Helper method to send a {@link HttpMethod#GET} request using the {@link RestTemplate} class from Spring. Constructs
     * the {@link HttpEntity} with the given (if any) headers.
     * <p>
     * See {@link RestTemplate#exchange(String, HttpMethod, HttpEntity, Class, Object...)} for the underlying method.
     * 
     * 
     * @param <T>
     * @param url the URL
     * @param headers additional headers to attach (can be null)
     * @param valueType the type of the return value
     * @return the response as entity
     */
    public static <T> T sendGet(String url, @Nullable Map<String, String> headers, Class<T> valueType) {
        return sendGet(url, headers, null, valueType);
    }

    /**
     * Helper method to send a {@link HttpMethod#GET} request using the {@link RestTemplate} class from Spring. Constructs
     * the {@link HttpEntity} with the given (if any) headers and parameters.
     * <p>
     * See {@link RestTemplate#exchange(String, HttpMethod, HttpEntity, Class, Object...)} for the underlying method.
     * 
     * 
     * @param <T>
     * @param url the URL
     * @param headers additional headers to attach (can be null)
     * @param parameters any parameters to attach (can be null)
     * @param valueType the type of the return value
     * @return the response as entity
     */
    public static <T> T sendGet(String url, @Nullable Map<String, String> headers, @Nullable Map<String, String> parameters, Class<T> valueType) {

        Objects.requireNonNull(url, "You must specify a request URL");
        Objects.requireNonNull(valueType, "Class type needs to be provided");

        HttpHeaders httpHeaders = HttpRequestUtil.constructHeaders(headers);
        MultiValueMap<String, String> parameterMap = HttpRequestUtil.constructParamterMap(parameters);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameterMap, httpHeaders);

        ResponseEntity<T> response = REST_TEMPLATE.exchange(url, HttpMethod.GET, entity, valueType);

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) throw new ApiException(statusCode, "Bad HttpStatus code for: " + url);

        return response.getBody();
    }

    private static HttpHeaders constructHeaders(Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders(); //Don't instantiate with HTTP_HEADERS because it uses the same backing map and gets polluted

        httpHeaders.addAll(HTTP_HEADERS); // Add the standard default headers
        if (headers != null) { //Add any header passed in
            for (Entry<String, String> header : headers.entrySet()) {
                httpHeaders.add(header.getKey(), header.getValue());
            }
        }

        return httpHeaders;
    }

    private static MultiValueMap<String, String> constructParamterMap(Map<String, String> parameters) {

        if (parameters == null) return null;

        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();

        for (Entry<String, String> parameterEntry : parameters.entrySet())
            parameterMap.add(parameterEntry.getKey(), parameterEntry.getValue());

        return parameterMap;
    }

}
