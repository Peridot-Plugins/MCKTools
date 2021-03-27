package me.mckoxu.mcktools.scheduler;

import me.mckoxu.mcktools.MCKTools;
import org.bukkit.Bukkit;

public abstract class SpiralScheduler implements Runnable{

    public static int degree;

    public static void startTimer() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(MCKTools.getInst(), new Runnable() {
            public void run() {
                if(degree > 360){
                    degree = 0;
                } else{
                    degree += 36;
                }
            }
        }, 0, 2);
    }
}
