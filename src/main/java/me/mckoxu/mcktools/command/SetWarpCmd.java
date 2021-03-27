package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetWarpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.setwarp")) {
                    if (args.length >= 1) {
                        Location loc = p.getLocation();
                        FileManager.getData().set("warps." + args[0] + ".locx", loc.getX());
                        FileManager.getData().set("warps." + args[0] + ".locy", loc.getY());
                        FileManager.getData().set("warps." + args[0] + ".locz", loc.getZ());
                        FileManager.getData().set("warps." + args[0] + ".locworld", loc.getWorld().getName());
                        try {
                            FileManager.getData().save(MCKTools.getInst().getDataFolder() + "/" + "data.yml");
                        } catch (IOException ex) {
                            MCKToolsLogger logger = new MCKToolsLogger();
                            logger.error(ErrorType.DATA_CANTSAVE.getMessage(), ex.getCause());
                        }
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.setwarp.setwarp").replace("{WARP}", args[0])));
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/setwarp (nazwa warpa)"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.setwarp"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
