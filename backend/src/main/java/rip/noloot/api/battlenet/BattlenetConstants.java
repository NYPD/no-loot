package rip.noloot.api.battlenet;

/**
 * Battlenet constants to be used in various requests
 * 
 * @author NYPD
 *
 */
public class BattlenetConstants {

    private BattlenetConstants() throws IllegalAccessException {
        throw new IllegalAccessException("No BattlenetConstants instances for you!");
    }

    private static final String BATTLENET_API_BASE_URL = "https://%s.api.blizzard.com";

}
