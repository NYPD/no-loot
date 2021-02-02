package rip.noloot.proxy;

import java.util.Map;

public interface RequestFactory {

    <T> T request(String url, Map<String, String> parameters, Class<T> valueType);
}
