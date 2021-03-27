package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeopCmd implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deop")) {
            if (s.isOp()) {
                if (args.length >= 1) {
                    OfflinePlayer pa = Bukkit.getOfflinePlayer(args[0]);
                    pa.setOp(false);
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.op.deop.sender").replace("{PLAYER}", pa.getName())));
                    Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.op.deop.broadcast").replace("{PLAYER}", pa.getName())));
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/deop [gracz]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "minecraft.command.deop"));
            }
        }
        return true;
    }

}
