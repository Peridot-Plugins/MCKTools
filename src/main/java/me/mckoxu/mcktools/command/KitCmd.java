package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.inventory.KitInventory;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class KitCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kit")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                ConfigurationSection csk = FileManager.getKits().getConfigurationSection("kits");
                int count = csk.getKeys(false).size();
                if (p.hasPermission("mckt.kits")) {
                    if (count >= 1) {
                        p.openInventory(KitInventory.getBasicInv(p));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.kits.nokits")));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.kits"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
