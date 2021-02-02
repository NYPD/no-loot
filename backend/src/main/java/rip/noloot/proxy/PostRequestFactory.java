package rip.noloot.proxy;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PostRequestFactory implements RequestFactory {

    private ObjectMapper objectMapper;
    private HttpHeaders httpHeaders;

    public PostRequestFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    public <T> T request(String url, Map<String, String> parameters, Class<T> valueType) {
        return null;
    }

}
