package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("speed")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.speed")) {
                    if (args.length >= 1) {
                        float speed = 1;
                        try {
                            speed = Float.parseFloat(args[0]);
                        } catch (Exception e) {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.badspeed")));
                            return true;
                        }
                        if (speed > 10) {
                            speed = 10;
                        }
                        if (speed < 1) {
                            speed = 1;
                        }
                        if (args.length >= 2) {
                            if (p.hasPermission("mckt.speed.other")) {
                                String pName = args[1];
                                if (Bukkit.getPlayer(pName) != null) {
                                    Player pa = Bukkit.getPlayer(pName);
                                    if (p.isOnGround()) {
                                        pa.setWalkSpeed(speed * 0.1F);
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.changespeed").replace("{SPEED}", String.valueOf(speed)).replace("{TYPE}", FileManager.getMsg().getString("messages.speed.walking"))));
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.changespeedother").replace("{SPEED}", String.valueOf(speed)).replace("{TYPE}", FileManager.getMsg().getString("messages.speed.walking")).replace("{PLAYER}", pa.getName())));
                                    } else {
                                        pa.setFlySpeed(speed * 0.1F);
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.changespeed").replace("{SPEED}", String.valueOf(speed)).replace("{TYPE}", FileManager.getMsg().getString("messages.speed.flying"))));
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.changespeedother").replace("{SPEED}", String.valueOf(speed)).replace("{TYPE}", FileManager.getMsg().getString("messages.speed.flying")).replace("{PLAYER}", pa.getName())));

                                    }
                                }
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.speed.other"));
                            }
                        } else {
                            if (p.isOnGround()) {
                                p.setWalkSpeed(speed * 0.1F);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.changespeed").replace("{SPEED}", String.valueOf(speed)).replace("{TYPE}", FileManager.getMsg().getString("messages.speed.walking"))));
                            } else {
                                p.setFlySpeed(speed * 0.1F);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.speed.changespeed").replace("{SPEED}", String.valueOf(speed)).replace("{TYPE}", FileManager.getMsg().getString("messages.speed.flying"))));
                            }
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/speed (predkosc) [gracz]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.speed"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
