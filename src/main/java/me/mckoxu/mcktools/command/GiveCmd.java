package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GiveCmd implements CommandExecutor {

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("give")) {
            if (s.hasPermission("mckt.give")) {
                if (args.length >= 1) {
                    String pName = args[0];
                    if (Bukkit.getPlayer(pName) != null) {
                        Player p = Bukkit.getPlayer(pName);
                        if (args.length >= 2) {
                            ItemBuilder ib;
                            if (Material.matchMaterial(args[0]) != null) {
                                ib = new ItemBuilder(Material.matchMaterial(args[1]));
                            } else {
                                ib = new ItemBuilder(Material.matchMaterial("STONE"));
                            }
                            if (args.length >= 3) {
                                int amount = 1;
                                try {
                                    amount = Integer.parseInt(args[2]);
                                } catch (Exception ex) {
                                }
                                if (amount > 64) {
                                    amount = 64;
                                }
                                ib.setAmount(amount);
                            }
                            if (args.length >= 4) {
                                String name = "";
                                if (!args[3].equals("-")) {
                                    name = ChatUtil.color(args[3].replace("_", " "));
                                }
                                ib.setName(name);
                            }
                            if (args.length >= 5) {
                                String lorel = "";
                                if (!args[4].equals("-")) {
                                    lorel = ChatUtil.color(args[4].replace("_", " "));
                                }
                                ArrayList<String> lore = new ArrayList<String>();
                                lore.add(lorel);
                                ib.setLore(lore);
                            }
                            ItemStack is = ib.toItemStack();
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.give.admin"))
                                    .replace("{ITEM}", String.valueOf(is.getType()))
                                    .replace("{AMOUNT}", String.valueOf(is.getAmount()))
                                    .replace("{NAME}", is.getItemMeta().getDisplayName())
                                    .replace("{ADMIN}", s.getName())
                                    .replace("{PLAYER}", p.getName()));
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.give.player"))
                                    .replace("{ITEM}", String.valueOf(is.getType()))
                                    .replace("{AMOUNT}", String.valueOf(is.getAmount()))
                                    .replace("{NAME}", is.getItemMeta().getDisplayName())
                                    .replace("{ADMIN}", s.getName())
                                    .replace("{PLAYER}", p.getName()));
                            p.getInventory().addItem(is);
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/give [gracz] [item] [ilosc] [mazwa] [opis]"));
                        }
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/give [gracz] [item] [ilosc] [mazwa] [opis]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.give"));
            }
        }
        return true;
    }
}
