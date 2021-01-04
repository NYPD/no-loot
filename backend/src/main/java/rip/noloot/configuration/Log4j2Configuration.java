package rip.noloot.configuration;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import rip.noloot.util.AppConstants;

@Configuration
public class Log4j2Configuration {

    private static final String ENCODER_PATTERN = "%d{DEFAULT} [%-5level] \\(%F{0}:%M\\(\\):%L\\) - %msg%n";
    private static final String FILE_PATTERN = "/tomcat/logs/" + AppConstants.PROJECT_NAME + "/rip.%d{yyyy-MM-dd}.log";

    private final ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
    private final LayoutComponentBuilder standard = builder.newLayout("PatternLayout").addAttribute("pattern",
                                                                                                    ENCODER_PATTERN);

    @Autowired
    private Environment springEnvironment;

    public AppenderComponentBuilder getConsoleAppender() {

        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        console.add(standard);

        return console;
    }

    public AppenderComponentBuilder getFileAppender() {

        ComponentBuilder<?> timeBasedTrigegringPolicy = builder.newComponent("TimeBasedTriggeringPolicy");
        //1 is one day since it is the most specific time unit in the date pattern.
        timeBasedTrigegringPolicy.addAttribute("interval", "1");

        ComponentBuilder<?> rolloverStrategy = builder.newComponent("DirectWriteRolloverStrategy");
        rolloverStrategy.addAttribute("maxFiles", 60);

        ComponentBuilder<?> triggeringPolicy = builder.newComponent("Policies");
        triggeringPolicy.addComponent(timeBasedTrigegringPolicy);

        AppenderComponentBuilder fileAppender = builder.newAppender("LogToRollingFile", "RollingFile");
        fileAppender.addAttribute("filePattern", FILE_PATTERN);

        fileAppender.add(standard);
        fileAppender.addComponent(triggeringPolicy);
        fileAppender.addComponent(rolloverStrategy);

        return fileAppender;
    }

    @PostConstruct
    public void initializeLog4J2() throws IOException {

        String[] activeProfiles = springEnvironment.getActiveProfiles();
        boolean isDevelopment = Arrays.stream(activeProfiles).anyMatch(x -> AppConstants.DEVELOPMENT_PROFILE.equals(x));

        Level loggingLevel = isDevelopment ? Level.DEBUG : Level.INFO;

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(loggingLevel);
        rootLogger.addAttribute("additivity", true);

        if (isDevelopment) {

            AppenderComponentBuilder consoleAppender = this.getConsoleAppender();
            AppenderComponentBuilder fileAppender = this.getFileAppender();

            builder.add(consoleAppender);
            builder.add(fileAppender);

            rootLogger.add(builder.newAppenderRef(consoleAppender.getName()));
            rootLogger.add(builder.newAppenderRef(fileAppender.getName()));

        } else {

            AppenderComponentBuilder fileAppender = this.getFileAppender();

            builder.add(this.getFileAppender());
            rootLogger.add(builder.newAppenderRef(fileAppender.getName()));

        }

        builder.setStatusLevel(loggingLevel);
        builder.add(rootLogger);

        if (isDevelopment) builder.writeXmlConfiguration(System.out);

        Configurator.reconfigure(builder.build());

    }

}
