package rip.noloot.api.battlenet;

public class BattlenetClientSecrets {

    private String clientId;
    private String clientSecret;

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public String toString() {
        return "BattlenetClientSecrets [clientId=" + this.clientId + ", clientSecret=" + this.clientSecret + "]";
    }
}
