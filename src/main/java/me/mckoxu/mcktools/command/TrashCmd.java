package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TrashCmd implements CommandExecutor {

    public static ConfigurationSection config = FileManager.getConfig();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("trash")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.trash")) {
                    Inventory inv = Bukkit.createInventory(null, 54, ChatUtil.color(FileManager.getConfig().getString("config.trash.title")));
                    p.openInventory(inv);
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.trash"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
