package rip.noloot.api.battlenet;

import java.util.Collection;

/**
 * POJO object to hold Battlnet API client id and secret
 * 
 * @author NYPD
 *
 */
public class BattlenetClientSecrets {

    private String clientId;
    private String clientSecret;
    private Collection<String> redirectUris;

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public Collection<String> getRedirectUris() {
        return redirectUris;
    }

    @Override
    public String toString() {
        return "BattlenetClientSecrets [clientId=" + this.clientId + ", clientSecret=" + this.clientSecret + ", redirectUris=[" + String.join(",",
                                                                                                                                              this.redirectUris)
               + "]]";
    }

}
