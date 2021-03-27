package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCmd implements CommandExecutor {
    Boolean never;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mute")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.mute")) {
                    if (args.length >= 1) {
                        String pName = args[0];
                        if (Bukkit.getPlayer(pName) != null) {
                            Player pa = Bukkit.getPlayer(pName);
                            User u = UserManager.createUser(pa.getUniqueId());
                            if (args.length >= 2) {
                                String msgs = "";
                                Long l = 0L;
                                if (args[1].equalsIgnoreCase("never") || args[1].equalsIgnoreCase("nigdy")) {
                                    never = true;
                                } else {
                                    try {
                                        l = Long.parseLong(args[1]);
                                        never = false;
                                    } catch (Exception e) {
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.wrongtime")));
                                        return true;
                                    }
                                }
                                if (args.length >= 3) {
                                    for (int i = 2; i < args.length; i++) {
                                        msgs += " " + args[i];
                                    }
                                    msgs = msgs.replaceFirst(" ", "");
                                } else {
                                    msgs = FileManager.getConfig().getString("config.punishments.defaultreason");
                                }
                                String time = "";
                                if (never) {
                                    time = FileManager.getMsg().getString("messages.punishments.never");
                                    u.setMute(-1L);
                                } else {
                                    time = MCKTools.getInst().df.format(System.currentTimeMillis() + l * 1000);
                                    u.setMute(System.currentTimeMillis() + l * 1000);
                                }
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.mute.admin")).replace("{PLAYER}", pa.getName()).replace("{REASON}", msgs).replace("{TIME}", time));
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.mute.player")).replace("{ADMIN}", p.getName()).replace("{REASON}", msgs).replace("{TIME}", time));
                                Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.mute.broadcast")).replace("{PLAYER}", pa.getName()).replace("{REASON}", msgs).replace("{ADMIN}", p.getName()).replace("{TIME}", time));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/mute [gracz] [czas]/nigdy [powod] (silent - bez broadcastu)"));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/mute [gracz] [czas]/[never] [powod]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.mute"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
