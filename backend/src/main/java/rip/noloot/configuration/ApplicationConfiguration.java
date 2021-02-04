package rip.noloot.configuration;

import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.annotation.SessionScope;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import rip.noloot.bean.NoLootSessionBean;
import rip.noloot.configuration.api.BattlenetConfiguration;
import rip.noloot.service.Service;

/**
 * Spring configuration class for application specific logic.
 * 
 * @author NYPD
 *
 */
@Configuration
@ComponentScan(basePackageClasses = {Service.class})
@Import({BattlenetConfiguration.class, Log4j2Configuration.class})
public class ApplicationConfiguration {

    @Bean
    @SessionScope
    public NoLootSessionBean soilAndPimpSessionBean() {
        return new NoLootSessionBean();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

}
