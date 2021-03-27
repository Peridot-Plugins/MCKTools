package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.inventory.CaseInventory;
import me.mckoxu.mcktools.manager.CaseManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class BlockPlaceListener implements Listener {

    public static Inventory inv = Bukkit.createInventory(null, 27, CaseManager.inventorytitle);

    @EventHandler
    public void onPlace(BlockPlaceEvent e) throws IOException {
        Player p = e.getPlayer();
        User u = UserManager.createUser(p.getUniqueId());
        ItemStack item = e.getItemInHand();
        if (!e.isCancelled()) {
            u.addPlacedBlocks(1);
            if (e.getBlock().getType() == Material.OBSIDIAN) {
                u.addPlacedObsidian(1);
            }
            if (e.getBlock().getType() == Material.CHEST) {
                if (item == null || item.getType() != CaseManager.chest.getType()) return;
                if (!item.hasItemMeta() || item.getItemMeta().getDisplayName() == null) return;
                if (item.getItemMeta().getDisplayName().equals(CaseManager.chest.getItemMeta().getDisplayName()) && item.getItemMeta().getLore().equals(CaseManager.chest.getItemMeta().getLore())) {
                    if (Util.itemsAmountInInventory(p, CaseManager.chest) >= 1) {
                        e.setCancelled(true);
                        if (Util.itemsAmountInInventory(p, CaseManager.key) >= 1) {
                            if (CaseInventory.caseinv.get(p) == null) {
                                ItemStack key = CaseManager.key;
                                key.setAmount(1);
                                p.getInventory().removeItem(key);
                                item.setAmount(item.getAmount() - 1);
                                p.updateInventory();
                                p.openInventory(CaseInventory.getInventory(p));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getCases().getString("messages.nokey")));
                        }
                    }
                }
            }
        } else {
            return;
        }
    }
}
