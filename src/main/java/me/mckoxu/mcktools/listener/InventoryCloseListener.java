package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.inventory.CaseInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public static void onClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (e.getInventory() != null) {
                if (CaseInventory.caseinv.get(p) != null) {
                    if (e.getInventory().equals(CaseInventory.caseinv.get(p))) {
                        Bukkit.getServer().getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                            public void run() {
                                p.openInventory(CaseInventory.getInventory(p));
                            }
                        }, 2);
                    }
                }
            }
        }
        return;
    }
}
