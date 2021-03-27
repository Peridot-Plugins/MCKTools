package me.mckoxu.mcktools.listener;


import com.mojang.authlib.GameProfile;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.data.mysql.UserDatabase;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        User user = UserManager.createUser(player.getUniqueId());
        UserDatabase.create(user);
        UserDatabase.load(user);
        if (user.getTurboDrop().getTime() < 1) {
            user.setTurboDrop(null);
        }
        user.addJoinAmount(1);
        MCKTools.joinAmount++;
        for (String msgs : FileManager.getChat().getStringList("messages.motd")) {
            player.sendMessage(ChatUtil.color(VariablesUtil.serverreturner(VariablesUtil.returner(player, msgs))));
        }
        player.setDisplayName(user.getDisplayName());
        String group = MCKTools.perm.getPrimaryGroup(player);
        String format = "";
        if (FileManager.getChat().getConfigurationSection("chatformat.groups") != null && FileManager.getChat().getConfigurationSection("chatformat.groups").contains(group)) {
            format = FileManager.getChat().getString("chatformat.groups." + group + ".join");
        } else {
            format = FileManager.getChat().getString("chatformat.default_join");
        }
        if (format == null) {
            e.setJoinMessage(ChatUtil.color(VariablesUtil.returner(player, FileManager.getChat().getString("chatformat.default_join"))));
        }
        e.setJoinMessage(ChatUtil.color(VariablesUtil.returner(player, format)));
    }
}
