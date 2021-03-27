package me.mckoxu.mcktools.object;

import me.mckoxu.mcktools.enums.TurbodropType;

import java.util.ArrayList;
import java.util.List;

public class TurboDrop {
    public static List<TurboDrop> turbodrops = new ArrayList<TurboDrop>();

    private int time;
    private User user;
    private TurbodropType type;

    public TurboDrop(User user, TurbodropType type) {
        this.user = user;
        this.type = type;
        this.time = 0;
        turbodrops.add(this);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TurbodropType getType() {
        return type;
    }

    public void setType(TurbodropType type) {
        this.type = type;
    }

    public static TurboDrop get(User user) {
        for (TurboDrop td : turbodrops) {
            if (td.getUser().equals(user)) {
                return td;
            }
        }
        return null;
    }
}
