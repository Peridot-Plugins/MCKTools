package me.mckoxu.mcktools.object;

import me.mckoxu.mcktools.enums.BanType;

import java.util.ArrayList;
import java.util.List;

public class BanIP {
    public static List<BanIP> bans = new ArrayList<BanIP>();

    private String reason;
    private String admin;
    private BanType type;
    private final String ip;
    private long time;

    public BanIP(final String ip) {
        this.ip = ip;
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

    public String getIp() {
        return this.ip;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public static BanIP get(String ip) {
        for (BanIP b : bans) {
            if (b.getIp().equals(ip)) {
                return b;
            }
        }
        return null;
    }
}
