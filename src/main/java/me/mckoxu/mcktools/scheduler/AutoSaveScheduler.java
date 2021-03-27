package me.mckoxu.mcktools.scheduler;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.Bukkit;

public abstract class AutoSaveScheduler implements Runnable {

    public static int time = MCKTools.getInst().getConfig().getInt("config.autosave.time") * 20;

    public static void startTimer() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(MCKTools.getInst(), new Runnable() {
            public void run() {
                Util.saveData();
            }
        }, 0, time);
    }
}
