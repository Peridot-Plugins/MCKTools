package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCmd implements CommandExecutor {

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("whois")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.mute")) {
                    if (args.length >= 1) {
                        String pName = args[0];
                        if (Bukkit.getPlayer(pName) != null) {
                            Player pa = Bukkit.getPlayer(pName);
                            for (String msgs : FileManager.getMsg().getStringList("messages.whois.online")) {
                                p.sendMessage(ChatUtil.color(VariablesUtil.returner(pa.getPlayer(), msgs)));
                            }
                        } else {
                            OfflinePlayer op = Bukkit.getOfflinePlayer(pName);
                            for (String msgs : FileManager.getMsg().getStringList("messages.whois.offline")) {
                                p.sendMessage(ChatUtil.color(msgs.replace("{PLAYER}", op.getName()).replace("{UUID}", String.valueOf(op.getUniqueId()))));
                            }
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/whois [gracz]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.whois"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
