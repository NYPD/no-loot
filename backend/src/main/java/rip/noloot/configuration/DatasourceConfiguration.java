package rip.noloot.configuration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rip.noloot.annotation.DevelopmentProfile;
import rip.noloot.annotation.NoLootDataSource;
import rip.noloot.annotation.ProductionProfile;

@Configuration
@DevelopmentProfile
@ProductionProfile
public class DatasourceConfiguration {

    @Bean(/* destroyMethod = "" */)
    @NoLootDataSource
    public DataSource dataSource() throws NamingException {
        Context context = new InitialContext();
        return (DataSource) context.lookup("java:comp/env/jdbc/ds_no_loot");
    }
}
