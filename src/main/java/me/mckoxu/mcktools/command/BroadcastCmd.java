package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import net.dzikoysk.funnyguilds.element.notification.NotificationUtil;
import net.dzikoysk.funnyguilds.util.nms.PacketSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCmd implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (s.hasPermission("mckt.broadcast")) {
                if (args.length >= 1) {
                    if (args.length < 2) {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/broadcast chat/actionbar/title/alert (wiadomosc)"));
                        return true;
                    }
                    String msgs = "";
                    for (int i = 1; i < args.length; i++) {
                        msgs += " " + args[i];
                    }
                    String msgsr = ChatUtil.color(FileManager.getMsg().getString("messages.broadcast.defaultcolor") + msgs);
                    if (args[0].equalsIgnoreCase("chat")) {
                        Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.broadcast.bc") + msgsr));
                    } else if (args[0].equalsIgnoreCase("actionbar")) {
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .forEach(player -> {
                                    PacketSender.sendPacket(player, NotificationUtil.createActionbarNotification(msgsr));
                                });
                    } else if (args[0].equalsIgnoreCase("title")) {
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .forEach(player -> {
                                    PacketSender.sendPacket(player, NotificationUtil.createTitleNotification(msgsr, " ", 10, 40, 10));
                                });
                    } else if (args[0].equalsIgnoreCase("alert")) {
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .forEach(player -> {
                                    PacketSender.sendPacket(player, NotificationUtil.createTitleNotification(ChatUtil.color(FileManager.getMsg().getString("messages.broadcast.alert")), msgsr, 10, 40, 10));
                                });
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/broadcast chat/actionbar/title/alert (wiadomosc)"));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/broadcast chat/actionbar/title/alert (wiadomosc)"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.broadcast"));
            }
        }
        return true;
    }
}
