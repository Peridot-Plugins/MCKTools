package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetLoreCmd implements CommandExecutor {

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setlore")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.setlore")) {
                    if (args.length >= 1) {
                        String lorea = "";
                        for (int i = 0; i < args.length; i++) {
                            lorea += " " + args[i];
                        }
                        if (p.getItemInHand().getType() != Material.AIR) {
                            ItemStack item = p.getItemInHand();
                            ItemMeta i = item.getItemMeta();
                            List<String> lore = new ArrayList<String>();
                            lore.add(ChatUtil.color(lorea.replaceFirst(" ", "")));
                            i.setLore(lore);
                            item.setItemMeta(i);
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.setlore.change").replace("{LORE}", lorea.replaceFirst(" ", ""))));
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.airhand")));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/setlore [kolory](nazwa)"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.setlore"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
