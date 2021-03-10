package rip.noloot.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import rip.noloot.api.battlenet.response.UserInfoResponse;

/**
 * Represents a user of the noloot.rip web application.
 */
@Entity
@Table(name = "users")
public class NoLootUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "battlenet_id")
    private int battlenetId;
    @Column(name = "battlenet_tag")
    private String battlenetTag;
    private boolean active;
    @Column(name = "date_registered")
    private LocalDateTime dateRegistered;

    // JPA constructor
    protected NoLootUser() {}

    public NoLootUser(UserInfoResponse userInfoResponse) {
        Objects.requireNonNull(userInfoResponse, "userInfoResponse must not be null for NoLootUser creation");

        this.battlenetId = userInfoResponse.getId();
        this.battlenetTag = userInfoResponse.getBattletag();

    }

    public int getId() {
        return this.id;
    }

    public int getBattlenetId() {
        return this.battlenetId;
    }

    public String getBattlenetTag() {
        return this.battlenetTag;
    }

    public boolean isActive() {
        return this.active;
    }

    public LocalDateTime getDateRegistered() {
        return this.dateRegistered;
    }

    @Override
    public String toString() {
        return "NoLootUser [id=" + this.id + ", battlenetId=" + this.battlenetId + ", battlenetTag=" + this.battlenetTag + ", active=" + this.active
               + ", dateRegistered=" + this.dateRegistered + "]";
    };

}
