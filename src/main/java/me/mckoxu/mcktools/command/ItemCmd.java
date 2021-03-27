package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemCmd implements CommandExecutor {

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("item")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.item")) {
                    if (args.length >= 1) {
                        ItemBuilder ib;
                        if (Material.matchMaterial(args[0]) != null) {
                            ib = new ItemBuilder(Material.matchMaterial(args[0]));
                        } else {
                            ib = new ItemBuilder(Material.matchMaterial("STONE"));
                        }
                        if (args.length >= 2) {
                            int amount = 1;
                            try {
                                amount = Integer.parseInt(args[1]);
                            } catch (Exception ex) {
                            }
                            if (amount > 64) {
                                amount = 64;
                            }
                            ib.setAmount(amount);
                        }
                        if (args.length >= 3) {
                            String name = "";
                            if (!args[2].equals("-")) {
                                name = ChatUtil.color(args[2].replace("_", " "));
                            }
                            ib.setName(name);
                        }
                        if (args.length >= 4) {
                            String lorel = "";
                            if (!args[3].equals("-")) {
                                lorel = ChatUtil.color(args[3].replace("_", " "));
                            }
                            ArrayList<String> lore = new ArrayList<String>();
                            lore.add(lorel);
                            ib.setLore(lore);
                        }
                        ItemStack is = ib.toItemStack();
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.give.player"))
                                .replace("{ITEM}", String.valueOf(is.getType()))
                                .replace("{AMOUNT}", String.valueOf(is.getAmount()))
                                .replace("{NAME}", is.getItemMeta().getDisplayName())
                                .replace("{PLAYER}", p.getName()));
                        p.getInventory().addItem(is);
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/item [item] [ilosc] [mazwa] [opis]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.item"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}

