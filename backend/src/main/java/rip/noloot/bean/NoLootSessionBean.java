package rip.noloot.bean;

import rip.noloot.configuration.ApplicationConfiguration;
import rip.noloot.domain.NoLootUser;

/**
 * Session bean containing all the relevant information needed for the noloot.rip application and the currently logged in
 * user.
 * <p>
 * This is set up in {@link ApplicationConfiguration}
 * 
 * @author NYPD
 */
public class NoLootSessionBean {

    private NoLootUser noLootUser;
    private boolean rememberMe;
    private String prevPath;

    public NoLootUser getNoLootUser() {
        return this.noLootUser;
    }

    public void setNoLootUser(NoLootUser noLootUser) {
        this.noLootUser = noLootUser;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getPrevPath() {
        return this.prevPath;
    }

    public void setPrevPath(String prevPath) {
        this.prevPath = prevPath;
    }

    @Override
    public String toString() {
        return "NoLootSessionBean [noLootUser=" + this.noLootUser + ", rememberMe=" + this.rememberMe + ", prevPath=" + this.prevPath + "]";
    }

}
