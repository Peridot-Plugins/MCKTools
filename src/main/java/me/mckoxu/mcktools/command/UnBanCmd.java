package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.BanManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnBanCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("unban")) {
            if (s.hasPermission("mckt.unban")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("player")) {
                        if (s.hasPermission("mckt.unban.player")) {
                            if (args.length >= 2) {
                                String pName = args[1];
                                OfflinePlayer pa = Bukkit.getOfflinePlayer(pName);
                                BanManager.remove(pa.getUniqueId());
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.unban.admin")).replace("{PLAYER}", pa.getName()));
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/unban player/ip [gracz]/[ip]"));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.ban.player"));
                        }
                    } else if (args[0].equalsIgnoreCase("ip")) {
                        if (s.hasPermission("mckt.unban.ip")) {
                            if (args.length >= 2) {
                                String ip;
                                try {
                                    ip = args[1];
                                } catch (Exception ex) {
                                    ip = null;
                                }
                                if (ip != null) {
                                    if (BanManager.checkIp(ip)) {
                                        String[] str = ip.split("\\.", 4);
                                        String ip1 = str[0];
                                        String ip2 = str[1];
                                        String ip3 = str[2];
                                        String ip4 = str[3];
                                        String clearip = FileManager.getMsg().getString("messages.punishments.clearip").replace("{IP-1}", ip1).replace("{IP-2}", ip2).replace("{IP-3}", ip3).replace("{IP-4}", ip4);
                                        BanManager.remove(ip);
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.unbanip.admin")).replace("{IP}", clearip));
                                    } else {
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.badip")).replace("{IP}", ip));
                                    }
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/unban player/ip [gracz]/[ip]"));
                                }
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/unban player/ip [gracz]/[ip]"));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.ban.ip"));
                        }
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.wrongtype")));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/unban player/ip [gracz]/[ip]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.ban"));
            }
        }
        return true;
    }
}
