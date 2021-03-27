package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.command.VanishCmd;
import me.mckoxu.mcktools.data.mysql.UserDatabase;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.TurboDrop;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        User user = UserManager.createUser(player.getUniqueId());
        UserDatabase.save(user);
        if (user.getMsgPlayer() != null) {
            User msgUser = UserManager.createUser(user.getMsgPlayer().getUniqueId());
            msgUser.setMsgPlayer(null);
        }
        if (VanishCmd.hidden.contains(e.getPlayer().getUniqueId())) {
            VanishCmd.hidden.remove(e.getPlayer().getUniqueId());
        }
        String group = MCKTools.perm.getPrimaryGroup(player);
        String format = "";
        if (FileManager.getChat().getConfigurationSection("chatformat.groups") != null && FileManager.getChat().getConfigurationSection("chatformat.groups").contains(group)) {
            format = FileManager.getChat().getString("chatformat.groups." + group + ".quit");
        } else {
            format = FileManager.getChat().getString("chatformat.default_quit");
        }
        if (format == null) {
            e.setQuitMessage(ChatUtil.color(VariablesUtil.returner(player, FileManager.getChat().getString("chatformat.default_quit"))));
        }
        e.setQuitMessage(ChatUtil.color(VariablesUtil.returner(player, format)));
        user.setMsgPlayer(null);
        user.setTpaPlayer(null);
        user.setDisplayName(player.getDisplayName());
        UserDatabase.save(user);
        for (TurboDrop td : TurboDrop.turbodrops) {
            if (td.getUser().equals(user)) {
                TurboDrop.turbodrops.remove(user);
            }
        }
        UserManager.removeUser(player.getUniqueId());
    }
}
