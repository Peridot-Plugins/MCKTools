package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCmd implements CommandExecutor {

    public static List<UUID> hidden = new ArrayList<UUID>();

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                User u = UserManager.createUser(p.getUniqueId());
                if (p.hasPermission("mckt.vanish")) {
                    u.setVanish(!u.getVanish());
                    if (u.getVanish()) {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.vanish.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable"))));
                        if (!hidden.contains(p.getUniqueId())) {
                            return true;
                        }
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            pl.showPlayer(p);
                        }
                        hidden.remove(p.getUniqueId());
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.vanish.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.enable"))));
                        if (hidden.contains(p.getUniqueId())) {
                            return true;
                        }
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            if (!pl.hasPermission("mckt.vanish")) {
                                pl.hidePlayer(p);
                            }
                        }
                        hidden.add(p.getUniqueId());
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.vanish"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
