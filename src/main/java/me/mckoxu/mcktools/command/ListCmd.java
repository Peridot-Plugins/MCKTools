package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.list")) {
                    String list = "";
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        list += "," + pl.getName();
                    }
                    for (String msgc : FileManager.getMsg().getStringList("messages.list.list")) {
                        p.sendMessage(ChatUtil.color(msgc.replace("{PLAYERS}", list.replaceFirst(",", "")).replace("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{MAXONLINE}", String.valueOf(Bukkit.getMaxPlayers()))));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.list"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
