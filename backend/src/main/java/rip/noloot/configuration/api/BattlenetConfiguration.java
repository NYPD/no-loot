package rip.noloot.configuration.api;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.context.annotation.SessionScope;

import com.fasterxml.jackson.databind.ObjectMapper;

import rip.noloot.api.battlenet.BattlenetClientSecrets;
import rip.noloot.bean.BattlenetSessionBean;
import rip.noloot.proxy.PostRequestFactory;

/**
 * Configuration class for everything Battlenet.
 * 
 * @author NYPD
 *
 */
@Configuration
public class BattlenetConfiguration {

    @Value("classpath:api/battlenet/battlenet_secret.json")
    private Resource clientSecretsResource;

    @Bean
    public BattlenetClientSecrets battlenetClientSecrets(ObjectMapper objectMapper) throws IOException {
        InputStream inputStream = clientSecretsResource.getInputStream();
        return objectMapper.readValue(inputStream, BattlenetClientSecrets.class);
    }

    @Bean
    @SessionScope
    public BattlenetSessionBean battlenetSessionBean() {
        return new BattlenetSessionBean();
    }

    @Bean
    public PostRequestFactory postRequestFactory(ObjectMapper objectMapper) {
        return new PostRequestFactory(objectMapper);
    }

}
