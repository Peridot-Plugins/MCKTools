package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class PlayerEatListener implements Listener {
    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) throws IOException {
        Player p = e.getPlayer();
        User u = UserManager.createUser(p.getUniqueId());
        ItemStack i = e.getItem();
        u.addEatenItems(1);
        if (i.getType() == Material.GOLDEN_APPLE && i.getDurability() == 0) {
            u.addEatenGoldApples(1);
        }
        if (i.getType() == Material.GOLDEN_APPLE && i.getDurability() == 1) {
            u.addEatenEnchantedGoldApples(1);
        }
        return;
    }
}
