package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("nick")) {
            if (s.hasPermission("mckt.nick")) {
                if (args.length >= 1) {
                    if (args.length >= 2) {
                        if (s.hasPermission("mckt.nick.other")) {
                            String pName = args[0];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player p = Bukkit.getPlayer(pName);
                                String oldnick = p.getDisplayName();
                                String nick = args[1];
                                nick = ChatUtil.color(nick);
                                String nicknc = ChatColor.stripColor(nick);
                                if (nicknc.contains(p.getName()) || p.hasPermission("mckt.nick.other.fake")) {
                                    p.setDisplayName(nick);
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.nick.changeother").replace("{PLAYER}", p.getName()).replace("{OLD}", oldnick).replace("{NEW}", nick)));
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.nick.change").replace("{OLD}", oldnick).replace("{NEW}", nick)));
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.nick.badnick")));
                                }
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.nick.other"));
                        }
                    } else {
                        if (s instanceof Player) {
                            Player p = (Player) s;
                            String oldnick = p.getDisplayName();
                            String nick = args[0];
                            nick = ChatUtil.color(nick);
                            String nicknc = ChatColor.stripColor(nick);
                            if (nicknc.contains(p.getName()) || p.hasPermission("mckt.nick.fake")) {
                                p.setDisplayName(nick);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.nick.change").replace("{OLD}", oldnick).replace("{NEW}", nick)));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.nick.badnick")));
                            }
                        } else {
                            s.sendMessage("[MCKTools] This command can be used only by players !");
                        }
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/nick [nowy nick]/[nick gracza] [nowy nick]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.nick"));
            }
        }
        return true;
    }

}
