package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkbenchCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("workbench")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.workbench")) {
                    p.openWorkbench(p.getLocation(), true);
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.workbench"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
