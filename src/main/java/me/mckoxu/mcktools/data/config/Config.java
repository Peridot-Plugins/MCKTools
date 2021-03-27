package me.mckoxu.mcktools.data.config;

import me.mckoxu.mcktools.manager.FileManager;

import java.util.List;

public class Config {

    public static int expMultiplayer;
    public static int minExp;
    public static int maxExp;
    public static String actionbarMining;
    public static List<String> blockedcmd;

    public static void loadConfig() {
        expMultiplayer = FileManager.getConfig().getInt("config.mining.expmultiplayer");
        minExp = FileManager.getConfig().getInt("config.mining.min");
        maxExp = FileManager.getConfig().getInt("config.mining.max");
        actionbarMining = FileManager.getConfig().getString("config.mining.actionbar");
        blockedcmd = FileManager.getConfig().getStringList("config.blockedcmd.list");
    }
}
