package rip.noloot.util;

/**
 * Application constants to be used throughout the codebase
 * 
 * @author NYPD
 *
 */
public class AppConstants {

    private AppConstants() throws IllegalAccessException {
        throw new IllegalAccessException("No AppConstants instances for you!");
    }

    public static final String PROJECT_NAME = "no-loot";
    public static final String APPLICATION_NAME = "No Loot";
    public static final String DEVELOPMENT_PROFILE = "DEVELOPMENT";
    public static final String TEST_PROFILE = "TEST";
    public static final String PRODUCTION_PROFILE = "PRODUCTION";

    public static final String DEV_URL = "http://localhost:8080";
    public static final String PROD_URL = "https://noloot.rip";
}
