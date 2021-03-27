package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCmd implements CommandExecutor {

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("enchant")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.enchant")) {
                    if (args.length >= 1) {
                        if (p.getItemInHand().getType() != Material.AIR) {
                            ItemStack item = p.getItemInHand();
                            int el = 1;
                            if (args.length >= 2) {
                                try {
                                    el = Integer.parseInt(args[1]);
                                } catch (Exception e) {
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.enchant.badlvl")));
                                    return true;
                                }
                            }
                            try {
                                item.addUnsafeEnchantment(Enchantment.getByName(args[0]), el);
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.enchant.addenchant").replace("{ENCHANT}", String.valueOf(args[0]))));
                            } catch (IllegalArgumentException e) {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.enchant.badid")));
                                return true;
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.airhand")));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/enchant [id enchantu] [lvl]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.enchant"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
