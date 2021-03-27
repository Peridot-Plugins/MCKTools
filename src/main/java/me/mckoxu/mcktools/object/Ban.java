package me.mckoxu.mcktools.object;

import me.mckoxu.mcktools.enums.BanType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ban {

    public static List<Ban> bans = new ArrayList<Ban>();

    private String reason;
    private String admin;
    private BanType type;
    private final UUID uuid;
    private long time;

    public Ban(final UUID uuid) {
        this.uuid = uuid;
        bans.add(this);
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdmin() {
        return this.admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public BanType getType() {
        return this.type;
    }

    public void setType(BanType type) {
        this.type = type;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static Ban get(UUID uuid) {
        for (Ban b : bans) {
            if (b.getUuid().equals(uuid)) {
                return b;
            }
        }
        return null;
    }
}
