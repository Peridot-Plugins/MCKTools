package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DelwarpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.delwarp")) {
                    if (args.length >= 1) {
                        ConfigurationSection csk = FileManager.getData().getConfigurationSection("warps");
                        if (csk == null) {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.delwarp.nowarps")));
                            return true;
                        }
                        if (csk.contains(args[0])) {
                            FileManager.getData().set("warps." + args[0] + ".locx", null);
                            FileManager.getData().set("warps." + args[0] + ".locy", null);
                            FileManager.getData().set("warps." + args[0] + ".locz", null);
                            FileManager.getData().set("warps." + args[0] + ".locworld", null);
                            FileManager.getData().set("warps." + args[0], null);
                            try {
                                FileManager.getData().save(MCKTools.getInst().getDataFolder() + "/" + "data.yml");
                            } catch (IOException ex) {
                                MCKToolsLogger logger = new MCKToolsLogger();
                                logger.error(ErrorType.DATA_CANTSAVE.getMessage(), ex.getCause());
                            }
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.delwarp.delwarp").replace("{WARP}", args[0])));
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.delwarp.nowarp").replace("{WARP}", args[0])));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/delwarp (nazwa warpa)"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.delwarp"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
