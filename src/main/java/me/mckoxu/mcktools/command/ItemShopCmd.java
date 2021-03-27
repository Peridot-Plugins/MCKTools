package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemShopCmd implements CommandExecutor {

    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("itemshop")) {
            if (s.hasPermission("mckt.itemshop")) {
                if (args.length >= 1) {
                    String pName = args[0];
                    if (Bukkit.getPlayer(pName) != null) {
                        Player pa = Bukkit.getPlayer(pName);
                        ConfigurationSection csk = FileManager.getConfig().getConfigurationSection("config.itemshop");
                        List<String> list = new ArrayList<String>();
                        for (String ss : csk.getKeys(false)) {
                            ConfigurationSection cs = csk.getConfigurationSection(ss);
                            list.add(cs.getName());
                        }
                        if (list != null) {
                            String liste = "";
                            for (String m : list) {
                                liste += "," + m;
                            }
                            if (args.length >= 2) {
                                if (list.contains(args[1])) {
                                    for (String msgc : csk.getStringList(args[1] + ".message")) {
                                        Bukkit.broadcastMessage(ChatUtil.color(msgc.replace("{PLAYER}", pa.getName())));
                                    }
                                    for (String cmds : csk.getStringList(args[1] + ".commands")) {
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmds.replace("{PLAYER}", pa.getName()));
                                    }
                                } else {
                                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.itemshop.noservice")).replace("{SERVICE}", args[1]));
                                }
                            } else {
                                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.itemshop.servicelist")).replace("{SERVICES}", liste.replaceFirst(",", "")));
                            }
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noservices")));
                        }
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/itemshop [gracz] [usluga]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.itemshop"));
            }
        }
        return true;
    }

}
