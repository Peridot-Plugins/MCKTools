package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.inventory.RecipesInventory;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class RecipesCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("recipes")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.recipes")) {
                    ConfigurationSection csk = FileManager.getConfig().getConfigurationSection("config.recipes.recipes");
                    if (csk != null) {
                        RecipesInventory.pagenum.put(p, 1);
                        p.openInventory(RecipesInventory.getInventory(p, RecipesInventory.pagenum.get(p)));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.recipes.norecipes")));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.recipes"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
