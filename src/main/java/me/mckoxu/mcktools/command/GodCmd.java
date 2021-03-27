package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("god")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.god")) {
                    User u = UserManager.createUser(p.getUniqueId());
                    u.setGod(!u.getGod());
                    if (u.getGod()) {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.god.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.enable"))));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.god.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable"))));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.god"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
