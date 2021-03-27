package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) throws IOException {
        Player d = e.getEntity().getPlayer();
        User ud = UserManager.createUser(d.getUniqueId());
        ud.addDeaths(1);
        if (e.getEntity().getKiller() != null) {
            Player k = e.getEntity().getKiller();
            if (!k.equals(d)) {
                User uk = UserManager.createUser(k.getUniqueId());
                uk.addKills(1);
            }
        }
    }
}