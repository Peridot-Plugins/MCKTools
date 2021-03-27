package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCmd implements CommandExecutor {

    public static Boolean schat = true;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chat")) {
            if (s.hasPermission("mckt.chat")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("clear")) {
                        for (int i = 0; i < 106; i++) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendMessage(" ");
                            }
                        }
                        for (String msg : FileManager.getChat().getStringList("messages.chatclear")) {
                            Bukkit.broadcastMessage(ChatUtil.color(msg).replace("{ADMIN}", s.getName()));
                        }
                    } else if (args[0].equalsIgnoreCase("on")) {
                        for (String msg : FileManager.getChat().getStringList("messages.chaton")) {
                            Bukkit.broadcastMessage(ChatUtil.color(msg).replace("{ADMIN}", s.getName()));
                        }
                        schat = true;
                    } else if (args[0].equalsIgnoreCase("off")) {
                        for (String msg : FileManager.getChat().getStringList("messages.chatoff")) {
                            Bukkit.broadcastMessage(ChatUtil.color(msg).replace("{ADMIN}", s.getName()));
                        }
                        schat = false;
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/chat on/off/clear"));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/chat on/off/clear"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.chat"));
            }
        }
        return true;
    }
}
