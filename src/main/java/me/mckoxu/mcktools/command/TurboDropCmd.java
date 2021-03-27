package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.enums.TurbodropType;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.TurboDrop;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TurboDropCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("turbodrop")) {
            if (s.hasPermission("mckt.turbodrop")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("all")) {
                        if (args.length >= 2) {
                            int i = 0;
                            try {
                                i = Integer.parseInt(args[1]);
                            } catch (Exception e) {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.wrongtime")));
                                return true;
                            }
                            if (i <= 86400) {
                                for (Player pl : Bukkit.getOnlinePlayers()) {
                                    User u = UserManager.createUser(pl.getUniqueId());
                                    TurboDrop tb = new TurboDrop(u, TurbodropType.TURBODROP_ALL);
                                    if (i < 1) {
                                        i = 1;
                                    }
                                    tb.setTime(i);
                                    if (u.getTurboDrop() != null && u.getTurboDrop().getTime() < tb.getTime()) {
                                        TurboDrop.turbodrops.remove(u.getTurboDrop());
                                    }
                                    u.setTurboDrop(tb);
                                }
                                Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.turboall.broadcast").replace("{ADMIN}", s.getName()).replace("{TIME}", Util.convertTime(i))));
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.turboall.player").replace("{TIME}", Util.convertTime(i))));
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.timelimit")));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/turbodrop all/give [gracz]/[czas w sekundach] [czas w sekundach]"));
                        }
                    } else if (args[0].equalsIgnoreCase("give")) {
                        if (args.length >= 2) {
                            String pName = args[1];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player pa = Bukkit.getPlayer(pName);
                                User u = UserManager.createUser(pa.getUniqueId());
                                if (args.length >= 3) {
                                    int i = 0;
                                    try {
                                        i = Integer.parseInt(args[2]);
                                    } catch (Exception e) {
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.wrongtime")));
                                        return true;
                                    }
                                    if (i <= 86400) {
                                        TurboDrop tb = new TurboDrop(u, TurbodropType.TURBODROP_PLAYER);
                                        if (i < 1) {
                                            i = 1;
                                        }
                                        tb.setTime(i);
                                        u.setTurboDrop(tb);
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.turboplayer.playerarg").replace("{ADMIN}", s.getName()).replace("{TIME}", Util.convertTime(i))));
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.turboplayer.player").replace("{PLAYER}", pa.getName()).replace("{TIME}", Util.convertTime(i))));
                                    } else {
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.turbodrop.timelimit")));
                                    }
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/turbodrop all/give [gracz]/[czas w sekundach] [czas w sekundach]"));
                                }
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/turbodrop all/give [gracz]/[czas w sekundach] [czas w sekundach]"));
                        }
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/turbodrop all/give [gracz]/[czas w sekundach] [czas w sekundach]"));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/turbodrop all/give [gracz]/[czas w sekundach] [czas w sekundach]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.turbodrop"));
            }
        }
        return true;
    }

}
