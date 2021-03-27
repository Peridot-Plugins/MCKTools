package me.mckoxu.mcktools.object;

import me.mckoxu.mcktools.enums.TopType;
import org.bukkit.OfflinePlayer;

public class Top {

    private final OfflinePlayer player;
    private final TopType topType;
    private int value;

    public Top(OfflinePlayer player, TopType topType, int value) {
        this.player = player;
        this.topType = topType;
        this.value = value;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public int getValue() {
        return value;
    }
}
