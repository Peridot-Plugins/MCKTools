package me.mckoxu.mcktools.scheduler;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class AutoMsgScheduler implements Runnable {

    public static int amt;
    public static int messagesAmount = FileManager.getChat().getConfigurationSection("automsg.msgs").getKeys(false).size() + 1;
    public static int time = FileManager.getChat().getInt("automsg.time") * 1200;

    public static void startTimer() {
        amt = 1;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MCKTools.getInst(), new Runnable() {
            public void run() {
                for (String s : FileManager.getChat().getStringList("automsg.msgs." + amt)) {
                    if (FileManager.getChat().getString("automsg.type").equalsIgnoreCase("player")) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(ChatUtil.color(s));
                        }
                    } else if (FileManager.getChat().getString("automsg.type").equalsIgnoreCase("console")) {
                        Bukkit.broadcastMessage(ChatUtil.color(s));
                    } else {
                        return;
                    }
                }
                amt++;
                if (amt == messagesAmount) {
                    amt = 1;
                }
            }
        }, 0, time);
    }
}
