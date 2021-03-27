package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.fly")) {
                    if (args.length >= 1) {
                        if (p.hasPermission("mckt.fly.other")) {
                            String pName = args[0];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player pa = Bukkit.getPlayer(pName);
                                pa.setAllowFlight(!pa.getAllowFlight());
                                if (pa.getAllowFlight() == true) {
                                    pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.fly.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.enable"))));
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.fly.changeother").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable")).replace("{PLAYER}", pa.getName())));
                                } else {
                                    pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.fly.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable"))));
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.fly.changeother").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable")).replace("{PLAYER}", pa.getName())));
                                }
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.fly.other"));
                        }
                    } else {
                        p.setAllowFlight(!p.getAllowFlight());
                        if (p.getAllowFlight() == true) {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.fly.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.enable"))));
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.fly.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable"))));
                        }
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.fly"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
