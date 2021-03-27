package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class WhitelistCmd implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("whitelist")) {
            if (s.hasPermission("mckt.whitelist")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args.length >= 2) {
                            OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
                            pa.setWhitelisted(true);
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.whitelist.addplayer").replace("{PLAYER}", pa.getName())));
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/whitelist add [gracz]"));
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (args.length >= 2) {
                            OfflinePlayer pa = Bukkit.getOfflinePlayer(args[1]);
                            pa.setWhitelisted(false);
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.whitelist.removeplayer").replace("{PLAYER}", pa.getName())));
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/whitelist remove [gracz]"));
                        }
                    } else if (args[0].equalsIgnoreCase("on")) {
                        Bukkit.getServer().setWhitelist(true);
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.whitelist.enable")));
                    } else if (args[0].equalsIgnoreCase("off")) {
                        Bukkit.getServer().setWhitelist(false);
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.whitelist.disable")));
                    } else if (args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("msg")) {
                        if (args.length >= 2) {
                            String msgs = "";
                            for (int i = 1; i < args.length; i++) {
                                msgs += " " + args[i];
                            }
                            FileManager.getData().set("whitelist.message", ChatUtil.color(msgs.replaceFirst(" ", "")));
                            try {
                                FileManager.getData().save(MCKTools.getInst().getDataFolder() + "/" + "data.yml");
                            } catch (IOException ex) {
                                MCKToolsLogger logger = new MCKToolsLogger();
                                logger.error(ErrorType.DATA_CANTSAVE.getMessage(), ex.getCause());
                            }
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.whitelist.message").replace("{MSG}", msgs.replaceFirst(" ", ""))));
                        } else {
                            s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/whitelist message [wiadomosc]"));
                        }
                    } else {
                        s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/whitelist add/remove/on/off/message [gracz]/[wiadomosc]"));
                    }
                } else {
                    s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/whitelist add/remove/on/off/message [gracz]/[wiadomosc]"));
                }
            } else {
                s.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.whitelist"));
            }
        }
        return true;
    }

}
