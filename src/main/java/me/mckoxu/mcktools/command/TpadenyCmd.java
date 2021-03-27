package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpadenyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpadeny")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                User u = UserManager.createUser(p.getUniqueId());
                if (p.hasPermission("mckt.tpadeny")) {
                    if (u.getTpaPlayer() != null) {
                        Player pa = u.getTpaPlayer();
                        if (pa.isOnline()) {
                            pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpadeny.msgyou").replace("{PLAYER}", p.getName())));
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpadeny.msgarg").replace("{PLAYER}", pa.getName())));
                            u.setTpaPlayer(null);
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpadeny.noplayer")));
                            u.setTpaPlayer(null);
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpadeny.noplayer")));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.tpadeny"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
