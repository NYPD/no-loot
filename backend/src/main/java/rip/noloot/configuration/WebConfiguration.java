package rip.noloot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rip.noloot.bean.BattlenetSessionBean;
import rip.noloot.controller.Controller;
import rip.noloot.interceptor.SecurityInterceptor;
import rip.noloot.service.BattlenetLoginService;

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

    private static final String[] LOGIN_INTERCEPTOR_PATH = {"/api/**"};
    private static final String[] LOGIN_EXCLUDED_PATH = {"/", "/login/**"};

    @Autowired
    private BattlenetSessionBean battlenetSessionBean;
    @Autowired
    private BattlenetLoginService battlenetLoginService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("https://localhost:8443", "https://noloot.rip");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SecurityInterceptor securityInterceptor = new SecurityInterceptor(this.battlenetSessionBean, this.battlenetLoginService);
        registry.addInterceptor(securityInterceptor).addPathPatterns(LOGIN_INTERCEPTOR_PATH).excludePathPatterns(LOGIN_EXCLUDED_PATH);
    }

}
