package rip.noloot.api.battlenet.util;

import rip.noloot.api.battlenet.enums.BattlenetRegion;

/**
 * Battlenet utility class for all OAUTH API calls
 * 
 * @author NYPD
 *
 */
public class OauthApiUtil {

    private static final String BATTLENET_OAUTH_BASE_URL = "https://%s.battle.net/oauth";
    private static final String BATTLENET_AUTHORIZE = "/authorize";
    private static final String BATTLENET_TOKEN = "/token";
    private static final String BATTLENET_USER_INFO = "/userinfo";

    private OauthApiUtil() {
        throw new AssertionError("No BattlenetApiUtil instances for you");
    }

    /**
     * Using the {@link BattlenetRegion} passed in, constructs a url for battlenet's authorize request. If
     * {@link BattlenetRegion} is null, it defaults to {@link BattlenetRegion.USA}
     * 
     * @param region
     * @return
     */
    public static String getOAuthAuthorizationUrlString(BattlenetRegion region) {
        if (region == null) region = BattlenetRegion.USA;
        return String.format(BATTLENET_OAUTH_BASE_URL + BATTLENET_AUTHORIZE, region.getCode());
    }

    /**
     * 
     * Using the {@link BattlenetRegion} passed in, constructs a url for battlenet's token request. If
     * {@link BattlenetRegion} is null, it defaults to {@link BattlenetRegion.USA}
     * 
     * @param region
     * @return A String tokenRequest URL for battlenet
     */
    public static String getTokenRequestUrlString(BattlenetRegion region) {
        if (region == null) region = BattlenetRegion.USA;
        return String.format(BATTLENET_OAUTH_BASE_URL + BATTLENET_TOKEN, region.getCode());
    }

    /**
     * 
     * Using the {@link BattlenetRegion} passed in, constructs a url for battlenet's oauth user info request. If
     * {@link BattlenetRegion} is null, it defaults to {@link BattlenetRegion.USA}
     * 
     * @param region
     * @return A String tokenRequest URL for battlenet
     */
    public static String getUserInfoUrlString(BattlenetRegion region) {
        if (region == null) region = BattlenetRegion.USA;
        return String.format(BATTLENET_OAUTH_BASE_URL + BATTLENET_USER_INFO, region.getCode());
    }
}
