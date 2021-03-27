package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.CaseManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CaseCmd implements CommandExecutor {

    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("case")) {
            if (s.hasPermission("mckt.case")) {
                if (args.length >= 1) {
                    String pName = args[0];
                    if (Bukkit.getPlayer(pName) != null) {
                        Player pa = Bukkit.getPlayer(pName);
                        ItemStack item = null;
                        String sitem = "";
                        int amount = 1;
                        if (args[1].equalsIgnoreCase("case")) {
                            sitem = FileManager.getMsg().getString("messages.case.case");
                            item = CaseManager.chest;
                        } else if (args[1].equalsIgnoreCase("key")) {
                            sitem = FileManager.getMsg().getString("messages.case.key");
                            item = CaseManager.key;
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/case [gracz]/all case/key [ilosc]"));
                            return true;
                        }
                        if (args.length >= 3) {
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception ex) {
                                amount = 1;
                            }
                            if (amount > 64) {
                                amount = 64;
                            }
                            if (amount < 1) {
                                amount = 1;
                            }
                            item.setAmount(amount);
                        }
                        pa.getInventory().addItem(item);
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.case.player.admin").replace("{PLAYER}", pa.getName()).replace("{AMOUNT}", String.valueOf(amount)).replace("{ITEM}", sitem)));
                        amount = 0;
                    } else {
                        if (args[0].equalsIgnoreCase("all")) {
                            if (args.length >= 2) {
                                ItemStack item = null;
                                String sitem = "";
                                int amount = 1;
                                if (args[1].equalsIgnoreCase("case")) {
                                    sitem = FileManager.getMsg().getString("messages.case.case");
                                    item = CaseManager.chest;
                                } else if (args[1].equalsIgnoreCase("key")) {
                                    sitem = FileManager.getMsg().getString("messages.case.key");
                                    item = CaseManager.key;
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/case [gracz]/all case/key [ilosc]"));
                                    return true;
                                }
                                if (args.length >= 3) {
                                    try {
                                        amount = Integer.parseInt(args[2]);
                                    } catch (Exception ex) {
                                        amount = 1;
                                    }
                                    if (amount > 64) {
                                        amount = 64;
                                    }
                                    if (amount < 1) {
                                        amount = 1;
                                    }
                                    item.setAmount(amount);
                                }
                                for (Player pa : Bukkit.getOnlinePlayers()) {
                                    pa.getInventory().addItem(item);
                                }
                                Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.case.all.broadcast")).replace("{ADMIN}", s.getName()).replace("{AMOUNT}", String.valueOf(amount)).replace("{ITEM}", sitem));
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.case.all.admin")).replace("{AMOUNT}", String.valueOf(amount)).replace("{ITEM}", sitem));
                                amount = 0;
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/case [gracz]/all case/key [ilosc]"));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/case [gracz]/all case/key [ilosc]"));
                        }
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/case [gracz]/all case/key [ilosc]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.case"));
            }
        }
        return true;
    }

}
