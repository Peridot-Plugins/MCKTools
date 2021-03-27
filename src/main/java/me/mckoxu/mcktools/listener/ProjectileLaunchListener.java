package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if (!e.isCancelled()) {
            if (e.getEntity().getShooter() instanceof Player) {
                if (e.getEntity() instanceof EnderPearl) {
                    Player player = ((Player) e.getEntity().getShooter()).getPlayer();
                    User u = UserManager.createUser(player.getUniqueId());
                    u.addThrowedEnderpearls(1);
                }
            }
        }
    }
}
