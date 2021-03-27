package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.BanType;
import me.mckoxu.mcktools.manager.BanManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.Ban;
import me.mckoxu.mcktools.object.BanIP;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (s.hasPermission("mckt.ban")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("player")) {
                        if (s.hasPermission("mckt.ban.player")) {
                            if (args.length >= 2) {
                                String pName = args[1];
                                if (Bukkit.getOfflinePlayer(pName) != null) {
                                    OfflinePlayer pa = Bukkit.getOfflinePlayer(pName);
                                    if (args.length >= 3) {
                                        Long l = 0L;
                                        boolean never;
                                        if (args[2].equalsIgnoreCase("never") || args[1].equalsIgnoreCase("nigdy")) {
                                            never = true;
                                        } else {
                                            try {
                                                l = Long.parseLong(args[2]);
                                                never = false;
                                            } catch (Exception e) {
                                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.wrongtime")));
                                                return true;
                                            }
                                        }
                                        String msgs = "";
                                        if (args.length >= 4) {
                                            for (int i = 3; i < args.length; i++) {
                                                msgs += " " + args[i];
                                            }
                                            msgs = msgs.replaceFirst(" ", "");
                                        } else {
                                            msgs = FileManager.getConfig().getString("config.punishments.defaultreason");
                                        }
                                        String time = "";
                                        Ban ban = new Ban(pa.getUniqueId());
                                        if (never) {
                                            time = FileManager.getMsg().getString("messages.punishments.never");
                                            ban.setTime(-1L);
                                            ban.setType(BanType.PERM_BAN);
                                        } else {
                                            time = MCKTools.getInst().df.format(System.currentTimeMillis() + (l * 1000));
                                            ban.setTime(System.currentTimeMillis() + (l * 1000));
                                            ban.setType(BanType.TEMP_BAN);
                                        }
                                        ban.setReason(msgs);
                                        ban.setAdmin(s.getName());
                                        BanManager.saveForUuid(pa.getUniqueId(), ban);
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            if (p.getName().equals(pa.getName())) {
                                                p.kickPlayer(ChatUtil.color(FileManager.getConfig().getString("config.punishments.ban").replace("{ADMIN}", s.getName()).replace("{REASON}", msgs).replace("{TIME}", time).replace("||", "\n")));
                                            }
                                        }
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.ban.admin")).replace("{PLAYER}", pa.getName()).replace("{REASON}", msgs).replace("{TIME}", time));
                                        Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.ban.broadcast")).replace("{PLAYER}", pa.getName()).replace("{REASON}", msgs).replace("{ADMIN}", s.getName()).replace("{TIME}", time));
                                    } else {
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/ban player/ip [gracz]/[ip] [czas w sekundach]/[never] [powod]"));
                                    }
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                                }
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/ban player/ip [gracz]/[ip] [czas w sekundach]/[never] [powod]"));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.ban.player"));
                        }
                    } else if (args[0].equalsIgnoreCase("ip")) {
                        if (s.hasPermission("mckt.ban.ip")) {
                            if (args.length >= 2) {
                                String ip;
                                try {
                                    ip = args[1];
                                } catch (Exception ex) {
                                    ip = null;
                                }
                                if (ip != null) {
                                    if (BanManager.checkIp(ip)) {
                                        if (args.length >= 3) {
                                            String[] str = ip.split("\\.", 4);
                                            String ip1 = str[0];
                                            String ip2 = str[1];
                                            String ip3 = str[2];
                                            String ip4 = str[3];
                                            String clearip = FileManager.getMsg().getString("messages.punishments.clearip").replace("{IP-1}", ip1).replace("{IP-2}", ip2).replace("{IP-3}", ip3).replace("{IP-4}", ip4);
                                            Long l = 0L;
                                            boolean never;
                                            if (args[2].equalsIgnoreCase("never") || args[1].equalsIgnoreCase("nigdy")) {
                                                never = true;
                                            } else {
                                                try {
                                                    l = Long.parseLong(args[2]);
                                                    never = false;
                                                } catch (Exception e) {
                                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.wrongtime")));
                                                    return true;
                                                }
                                            }
                                            String msgs = "";
                                            if (args.length >= 4) {
                                                for (int i = 3; i < args.length; i++) {
                                                    msgs += " " + args[i];
                                                }
                                                msgs = msgs.replaceFirst(" ", "");
                                            } else {
                                                msgs = FileManager.getConfig().getString("config.punishments.defaultreason");
                                            }
                                            String time = "";
                                            BanIP ban = new BanIP(ip);
                                            if (never) {
                                                time = FileManager.getMsg().getString("messages.punishments.never");
                                                ban.setTime(-1L);
                                                ban.setType(BanType.PERM_BAN);
                                            } else {
                                                time = MCKTools.getInst().df.format(System.currentTimeMillis() + (l * 1000));
                                                ban.setTime(System.currentTimeMillis() + (l * 1000));
                                                ban.setType(BanType.TEMP_BAN);
                                            }
                                            ban.setReason(msgs);
                                            ban.setAdmin(s.getName());
                                            BanManager.saveForIp(ip, ban);
                                            for (Player p : Bukkit.getOnlinePlayers()) {
                                                if (p.getAddress().getAddress().toString().replace("/", "").equals(ip)) {
                                                    p.kickPlayer(ChatUtil.color(FileManager.getConfig().getString("config.punishments.banip").replace("{ADMIN}", s.getName()).replace("{REASON}", msgs).replace("{TIME}", time).replace("||", "\n")));
                                                }
                                            }
                                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.banip.admin")).replace("{IP}", clearip).replace("{REASON}", msgs).replace("{TIME}", time));
                                            Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.banip.broadcast")).replace("{IP}", clearip).replace("{REASON}", msgs).replace("{ADMIN}", s.getName()).replace("{TIME}", time));
                                        } else {
                                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/ban player/ip [gracz]/[ip] [czas w sekundach]/[never] [powod]"));
                                        }
                                    } else {
                                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.badip")).replace("{IP}", ip));
                                    }
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/ban player/ip [gracz]/[ip] [czas w sekundach]/[never] [powod]"));
                                }
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/ban player/ip [gracz]/[ip] [czas w sekundach]/[never] [powod]"));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.ban.ip"));
                        }
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.wrongtype")));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/ban player/ip [gracz]/[ip] [czas w sekundach]/[never] [powod]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.ban"));
            }
        }
        return true;
    }
}
