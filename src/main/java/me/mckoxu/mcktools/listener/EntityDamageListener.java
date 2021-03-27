package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            User u = UserManager.createUser(p.getUniqueId());
            if (u.getGod()) {
                e.setCancelled(true);
            }
        }
    }

}
