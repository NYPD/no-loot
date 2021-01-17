package rip.noloot.api.battlenet;

/**
 * Battlenet constants to be used in various requests
 * 
 * @author NYPD
 *
 */
public class Battlenet {

    private Battlenet() throws IllegalAccessException {
        throw new IllegalAccessException("No Battlenet instances for you!");
    }

    public static final String BATTLENET_OAUTH_TOKEN_URL = "https://us.battle.net/oauth/token";
    public static final String BATTLENET_API_BASE_URL = "https://us.api.blizzard.com";
}
