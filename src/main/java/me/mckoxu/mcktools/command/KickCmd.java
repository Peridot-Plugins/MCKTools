package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kick")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.kick")) {
                    if (args.length >= 1) {
                        String pName = args[0];
                        if (Bukkit.getPlayer(pName) != null) {
                            Player pa = Bukkit.getPlayer(pName);
                            String msgs = "";
                            if (args.length >= 2) {
                                for (int i = 1; i < args.length; i++) {
                                    msgs += " " + args[i];
                                }
                                msgs = msgs.replaceFirst(" ", "");
                            } else {
                                msgs = FileManager.getConfig().getString("config.punishments.defaultreason");
                            }
                            pa.kickPlayer(ChatUtil.color(FileManager.getConfig().getString("config.punishments.kick").replace("{ADMIN}", p.getName()).replace("{REASON}", msgs).replace("||", "\n")));
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.kick.admin")).replace("{PLAYER}", pa.getName()).replace("{REASON}", msgs));
                            Bukkit.broadcastMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.kick.broadcast")).replace("{PLAYER}", pa.getName()).replace("{REASON}", msgs).replace("{ADMIN}", p.getName()));
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/kick [gracz] [powod]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.kick"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
