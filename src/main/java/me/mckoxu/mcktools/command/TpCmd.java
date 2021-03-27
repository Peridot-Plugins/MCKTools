package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("teleport")) {
            if (s.hasPermission("mckt.teleport")) {
                if (args.length >= 1) {
                    String pName = args[0];
                    if (Bukkit.getPlayer(pName) != null) {
                        Player pa = Bukkit.getPlayer(pName);
                        if (args.length >= 2) {
                            String pName2 = args[1];
                            if (Bukkit.getPlayer(pName2) != null) {
                                Player pa2 = Bukkit.getPlayer(pName2);
                                pa.teleport(pa2.getLocation());
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.teleport.tpplayertoplayer")).replace("{PLAYER}", pa.getName()).replace("{PLAYEROTHER}", pa2.getName()).replace("{ADMIN}", s.getName()));
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.teleport.tptoplayer")).replace("{PLAYER}", pa.getName()).replace("{PLAYEROTHER}", pa2.getName()).replace("{ADMIN}", s.getName()));
                            } else {
                                double x = 0D;
                                double y = 0D;
                                double z = 0D;
                                try {
                                    x = Double.parseDouble(args[1]);
                                } catch (Exception ex) {
                                }
                                try {
                                    y = Double.parseDouble(args[2]);
                                } catch (Exception ex) {
                                }
                                try {
                                    z = Double.parseDouble(args[3]);
                                } catch (Exception ex) {
                                }
                                Location l = new Location(pa.getWorld(), x, y, z);
                                pa.teleport(l);
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.teleport.tpplayertocord")).replace("{PLAYER}", pa.getName()).replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)).replace("{WORLD}", pa.getWorld().getName()).replace("{ADMIN}", s.getName()));
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.teleport.tptocord")).replace("{PLAYER}", pa.getName()).replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)).replace("{WORLD}", pa.getWorld().getName()).replace("{ADMIN}", s.getName()));
                            }
                        } else {
                            if (s instanceof Player) {
                                Player p = (Player) s;
                                p.teleport(pa.getLocation());
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.teleport.tptoplayer")).replace("{PLAYER}", p.getName()).replace("{PLAYEROTHER}", pa.getName()));
                            } else {
                                s.sendMessage("[MCKTools] This command can be used only by players !");
                            }
                        }
                    } else {
                        if (s instanceof Player) {
                            Player p = (Player) s;
                            double x = 0D;
                            double y = 0D;
                            double z = 0D;
                            try {
                                x = Double.parseDouble(args[0]);
                            } catch (Exception ex) {
                            }
                            try {
                                y = Double.parseDouble(args[1]);
                            } catch (Exception ex) {
                            }
                            try {
                                z = Double.parseDouble(args[2]);
                            } catch (Exception ex) {
                            }
                            Location l = new Location(p.getWorld(), x, y, z);
                            p.teleport(l);
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.teleport.tptocord")).replace("{PLAYER}", p.getName()).replace("{X}", String.valueOf(x)).replace("{Y}", String.valueOf(y)).replace("{Z}", String.valueOf(z)).replace("{WORLD}", p.getWorld().getName()));
                        } else {
                            s.sendMessage("[MCKTools] This command can be used only by players !");
                        }
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/tp [gracz] <gracz> lub /tp [gracz] [x] [y] [z]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.teleport"));
            }
        }
        return true;
    }
}
