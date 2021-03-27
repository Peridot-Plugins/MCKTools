package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("commandspy")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                User u = UserManager.createUser(p.getUniqueId());
                if (p.hasPermission("mckt.commandspy")) {
                    u.setCommandspy(!u.getCommandspy());
                    if (u.getCommandspy()) {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.commandspy.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.enable"))));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.commandspy.change").replace("{STATUS}", FileManager.getMsg().getString("messages.variables.status.disable"))));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.commandspy"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
