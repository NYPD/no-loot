package rip.noloot.configuration;

import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import rip.noloot.bean.NoLootSessionBean;
import rip.noloot.configuration.api.BattlenetConfiguration;
import rip.noloot.service.Service;

@Configuration
@ComponentScan(basePackageClasses = {Service.class})
@Import({BattlenetConfiguration.class, Log4j2Configuration.class})
public class ApplicationConfiguration {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public NoLootSessionBean soilAndPimpSessionBean() {
        return new NoLootSessionBean();
    }

    @Bean
    public ObjectMapper objectMapper() {
        PropertyNamingStrategy propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

}
