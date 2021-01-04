package rip.noloot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Log4j2Configuration.class})
public class ApplicationConfiguration {

}
