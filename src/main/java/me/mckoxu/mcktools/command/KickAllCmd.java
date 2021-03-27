package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickAllCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kickall")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.kickall")) {
                    if (args.length >= 1) {
                        StringBuilder msgs = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            msgs.append(" ")
                                    .append(args[i]);
                        }
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .filter(player -> (!player.equals(p)))
                                .forEach(player -> {
                                    player.kickPlayer(ChatUtil.color(msgs.toString().replace("||", "\n")));
                                });
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.kickall.player")));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/kickall [powod]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.kickall"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
