package me.mckoxu.mcktools.data.config;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;

public class Messages {
    public static String topNull;
    public static String topFormat;

    public static void loadMessages() {
        topNull = ChatUtil.color(FileManager.getMsg().getString("messages.top.none"));
        topFormat = ChatUtil.color(FileManager.getMsg().getString("messages.top.format"));
    }
}
