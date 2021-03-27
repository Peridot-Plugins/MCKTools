package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.inventory.StatsInventory;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StatsCmd implements CommandExecutor {

    Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.color(FileManager.getConfig().getString("config.stats.title")));

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("stats")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.stats")) {
                    if (args.length >= 1) {
                        if (p.hasPermission("mckt.stats.other")) {
                            String pName = args[0];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player pa = Bukkit.getPlayer(pName);
                                p.openInventory(StatsInventory.getInventory(p, pa));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                                return true;
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.stats.other"));
                            return true;
                        }
                    } else {
                        p.openInventory(StatsInventory.getInventory(p, p));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.stats"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
