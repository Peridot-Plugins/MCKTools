package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.data.config.Config;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import org.spigotmc.SpigotConfig;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage();
        Player p = e.getPlayer();
        String cmdsplit = cmd.split(" ")[0];
        HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
        if (!p.hasPermission("mck.commandspy.bypass")) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                User u = UserManager.createUser(p.getUniqueId());
                if (u.getCommandspy()) {
                    pp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.commandspy.prefix").replace("{CMD}", cmd).replace("{PLAYER}", e.getPlayer().getName())));
                }
            }
        }
        if (e.isCancelled()) {
            return;
        }
        if (topic != null) {
            if (!p.hasPermission("mckt.blockedcmd.bypass")) {
                Config.blockedcmd
                        .stream()
                        .filter(blocked -> blocked.equalsIgnoreCase(cmd))
                        .forEach(blocked -> {
                            e.setCancelled(true);
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.blockedcmd.message")));
                        });
            }
            return;
        }
        SpigotConfig.unknownCommandMessage = (ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.unknowcommand").replace("{CMD}", cmdsplit)));
    }
}
