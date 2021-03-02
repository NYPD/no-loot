package rip.noloot.api.battlenet.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponse {

    @JsonProperty("id")
    private int id;
    @JsonProperty("battletag")
    private String battletag;

    public int getId() {
        return this.id;
    }

    public String getBattletag() {
        return this.battletag;
    }

    @Override
    public String toString() {
        return "UserInfoResponse [id=" + this.id + ", battletag=" + this.battletag + "]";
    }

}
