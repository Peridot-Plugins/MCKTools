package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class HealCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.heal")) {
                    if (args.length >= 1) {
                        if (p.hasPermission("mckt.heal.other")) {
                            String pName = args[0];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player pa = Bukkit.getPlayer(pName);
                                pa.setFoodLevel(25);
                                pa.setHealth(20);
                                pa.setFireTicks(0);
                                for (PotionEffect effect : pa.getActivePotionEffects()) {
                                    pa.removePotionEffect(effect.getType());
                                }
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.heal.heal")));
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.heal.healother").replace("{PLAYER}", pa.getName())));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.feed.heal"));
                        }
                    } else {
                        p.setFoodLevel(25);
                        p.setHealth(20);
                        p.setFireTicks(0);
                        for (PotionEffect effect : p.getActivePotionEffects()) {
                            p.removePotionEffect(effect.getType());
                        }
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.heal.heal")));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.heal"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
