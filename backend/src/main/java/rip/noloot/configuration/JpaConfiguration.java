package rip.noloot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import rip.noloot.repository.Repository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {Repository.class})
@EnableTransactionManagement
public class JpaConfiguration {

}
