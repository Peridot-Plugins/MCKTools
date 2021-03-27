package me.mckoxu.mcktools.scheduler;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.TurbodropType;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.TurboDrop;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class TurboDropScheduler implements Runnable {
    public static void startTimer() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(MCKTools.getInst(), new Runnable() {
            public void run() {
                if (!TurboDrop.turbodrops.isEmpty()) {
                    try {
                        for (TurboDrop td : TurboDrop.turbodrops) {
                            if (td.getType() == TurbodropType.TURBODROP_ALL) {
                                if (td.getTime() <= 0) {
                                    td.getUser().getPlayer().sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.timeleft.turboall")));
                                    td.getUser().setTurboDrop(null);
                                    TurboDrop.turbodrops.remove(td);
                                } else {
                                    td.getUser().getTurboDrop().setTime(td.getUser().getTurboDrop().getTime() - 1);
                                }
                            }
                        }
                    } catch (Exception ex) {
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        User u = UserManager.createUser(p.getUniqueId());
                        try {
                            if (u.getTurboDrop() != null) {
                                if (u.getTurboDrop().getType() == TurbodropType.TURBODROP_PLAYER) {
                                    if (u.getTurboDrop().getTime() <= 0) {
                                        u.getPlayer().sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.timeleft.turboplayer").replace("{PLAYER}", u.getTurboDrop().getUser().getName())));
                                        TurboDrop.turbodrops.remove(u.getTurboDrop());
                                        u.setTurboDrop(null);
                                    } else {
                                        u.getTurboDrop().setTime(u.getTurboDrop().getTime() - 1);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            return;
                        }
                    }
                }
            }
        }, 0, 20);
    }
}
