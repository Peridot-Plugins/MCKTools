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

public class ReplyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reply")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                User u = UserManager.createUser(p.getUniqueId());
                if (p.hasPermission("mckt.reply")) {
                    if (args.length >= 1) {
                        String msgs = "";
                        for (int i = 0; i < args.length; i++) {
                            msgs += " " + args[i];
                        }
                        if (p.hasPermission("mckt.msg.color")) {
                            msgs = ChatUtil.color(msgs);
                        }
                        if (u.getMsgPlayer() != null) {
                            Player pa = u.getMsgPlayer();
                            if (p.hasPermission("mckt.msg.color")) {
                                msgs = ChatUtil.color(msgs);
                            }
                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                User ul = UserManager.createUser(pl.getUniqueId());
                                if (ul.getSocialspy()) {
                                    pl.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.socialspy.prefix").replace("{MSG}", ChatUtil.color(FileManager.getMsg().getString("messages.msg.msgyou").replace("{YOU}", p.getName()).replace("{ARG}", pa.getName()).replace("{MSG}", msgs)))));
                                }
                            }
                            if (!p.hasPermission("mckt.censorebypass")) {
                                msgs = ChatUtil.censore(msgs, FileManager.getChat().getStringList("censore.words"), FileManager.getChat().getString("censore.symbol"));
                            }
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.msg.msgyou").replace("{YOU}", p.getName()).replace("{ARG}", pa.getName()).replace("{MSG}", msgs)));
                            pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.msg.msgarg").replace("{YOU}", p.getName()).replace("{ARG}", pa.getName()).replace("{MSG}", msgs)));
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.msg.noreplyplayer")));
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.correctusage")).replace("{CORRECTUSAGE}", "/reply [wiadomosc]"));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.reply"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
