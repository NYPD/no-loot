package rip.noloot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rip.noloot.controller.Controller;

/**
 * Spring configuration class for the Web / Server side of things. Imports the application specific configuration
 * 
 * @author NYPD
 *
 */
@Configuration
@EnableWebMvc
@Import({ApplicationConfiguration.class})
@ComponentScan(basePackageClasses = {Controller.class})
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("https://localhost:8443", "https://noloot.rip");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
