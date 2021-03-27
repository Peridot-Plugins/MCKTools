package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.gamemode")) {
                    if (args.length >= 1) {
                        if (args.length >= 2) {
                            if (p.hasPermission("mckt.gamemode.other")) {
                                String pName = args[1];
                                if (Bukkit.getPlayer(pName) != null) {
                                    Player pa = Bukkit.getPlayer(pName);
                                    if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                                        pa.setGameMode(GameMode.SURVIVAL);
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegmother").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.survival")).replace("{PLAYER}", pa.getName())));
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.survival"))));
                                    } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                                        pa.setGameMode(GameMode.CREATIVE);
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegmother").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.creative")).replace("{PLAYER}", pa.getName())));
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.creative"))));
                                    } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                                        pa.setGameMode(GameMode.ADVENTURE);
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegmother").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.adventure")).replace("{PLAYER}", pa.getName())));
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.adventure"))));
                                    } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator")) {
                                        pa.setGameMode(GameMode.SPECTATOR);
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegmother").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.spectator")).replace("{PLAYER}", pa.getName())));
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.spectator"))));
                                    } else {
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/gamemode 0/1/2/3 [gracz]"));
                                    }
                                } else {
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                                }
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.gamemode.other"));
                            }
                        } else {
                            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                                p.setGameMode(GameMode.SURVIVAL);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.survival"))));
                            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                                p.setGameMode(GameMode.CREATIVE);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.creative"))));
                            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                                p.setGameMode(GameMode.ADVENTURE);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.adventure"))));
                            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator")) {
                                p.setGameMode(GameMode.SPECTATOR);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.gamemode.changegm").replace("{GM}", FileManager.getMsg().getString("messages.variables.gamemode.spectator"))));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/gamemode 0/1/2/3 [gracz]"));
                            }
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/gamemode 0/1/2/3 [gracz]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.gamemode"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
