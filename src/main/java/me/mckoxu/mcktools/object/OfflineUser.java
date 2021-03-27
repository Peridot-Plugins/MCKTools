package me.mckoxu.mcktools.object;

import java.util.ArrayList;
import java.util.List;

public class OfflineUser {

    public static List<OfflineUser> users = new ArrayList<>();

    private final String name;

    public OfflineUser(String name) {
        this.name = name;
        users.add(this);
    }

    public String getName() {
        return name;
    }

    public static OfflineUser get(String name) {
        for (OfflineUser user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
