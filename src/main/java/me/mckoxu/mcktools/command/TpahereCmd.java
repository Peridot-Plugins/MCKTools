package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpahereCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpahere")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("mckt.tpahere")) {
                    if (args.length >= 1) {
                        String pName = args[0];
                        if (Bukkit.getPlayer(pName) != null) {
                            Player pa = Bukkit.getPlayer(pName);
                            User u = UserManager.createUser(pa.getUniqueId());
                            u.setTpaPlayer(p);
                            ;
                            u.setTpaType(false);
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpahere.msgyou").replace("{PLAYER}", pa.getName())));
                            for (String msgs : FileManager.getMsg().getStringList("messages.tpahere.msgarg")) {
                                pa.sendMessage(ChatUtil.color(msgs.replace("{PLAYER}", p.getName())));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/tpahere [gracz]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.tpahere"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
