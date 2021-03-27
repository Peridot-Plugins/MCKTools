package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.feed")) {
                    if (args.length >= 1) {
                        if (p.hasPermission("mckt.feed.other")) {
                            String pName = args[0];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player pa = Bukkit.getPlayer(pName);
                                pa.setFoodLevel(25);
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.feed.addfood")));
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.feed.addfoodother").replace("{PLAYER}", pa.getName())));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.feed.other"));
                        }
                    } else {
                        p.setFoodLevel(25);
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.feed.addfood")));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.feed"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
