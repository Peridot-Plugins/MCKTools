package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("helpop")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.helpop")) {
                    if (args.length >= 1) {
                        String msgs = "";
                        for (int i = 0; i < args.length; i++) {
                            msgs += " " + args[i];
                        }
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            if (pl.hasPermission("mckt.helpop.read")) {
                                pl.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.helpop.admin").replace("{PLAYER}", p.getName()).replace("{MSG}", msgs)));
                                Bukkit.getConsoleSender().sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.helpop.admin").replace("{PLAYER}", p.getName()).replace("{MSG}", msgs)));
                            }
                        }
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.helpop.player").replace("{MSG}", msgs)));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/helpop [wiadomosc]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.helpop"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
