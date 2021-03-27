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

public class SetNameCmd implements CommandExecutor {

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setname")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.setname")) {
                    if (args.length >= 1) {
                        String name = "";
                        for (int i = 0; i < args.length; i++) {
                            name += " " + args[i];
                        }
                        if (p.getItemInHand().getType() != Material.AIR) {
                            ItemStack item = p.getItemInHand();
                            ItemMeta i = item.getItemMeta();
                            i.setDisplayName(ChatUtil.color(name.replaceFirst(" ", "")));
                            item.setItemMeta(i);
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.setname.change").replace("{NAME}", name.replaceFirst(" ", ""))));
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.airhand")));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/setname [kolory](nazwa)"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.setname"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
