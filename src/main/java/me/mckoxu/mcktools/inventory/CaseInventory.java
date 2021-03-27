package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.Sounds;
import me.mckoxu.mcktools.manager.CaseManager;
import me.mckoxu.mcktools.object.CaseItem;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.*;

public class CaseInventory {
    public static Map<Player, Inventory> caseinv = new HashMap();

    public static Inventory getInventory(Player p) {
        Inventory inv;
        if (caseinv.get(p) == null) {
            inv = Bukkit.createInventory(null, 27, CaseManager.inventorytitle);
            caseinv.put(p, inv);
            final List<CaseItem> items = new ArrayList();
            while (items.size() < 50) {
                CaseManager.caseitems.stream()
                        .filter(item -> Util.getChance(item.getChance()))
                        .forEach(items::add);
            }
            Collections.shuffle(items);
            for (int i = 0; i < 27; i++) {
                inv.setItem(i, CaseManager.background);
            }
            for (int i = 0; i < 8; ) {
                inv.setItem(0 + i, items.get(i).getItemRarity().getItem());
                inv.setItem(9 + i, items.get(i).getItem());
                inv.setItem(18 + i, items.get(i).getItemRarity().getItem());
                i++;
            }
            inv.setItem(4, new ItemBuilder(Material.matchMaterial("HOPPER")).setName(CaseManager.droptitle).toItemStack());
            int ii = 9;
            int delay = 5;
            for (int i = 0; i < 41; i++) {
                if ((i > 15) && (i < 30)) {
                    delay++;
                }
                if (i >= 30) {
                    delay += 4;
                }
                if (i >= 36) {
                    delay += 5;
                }
                if (i >= 40) {
                    delay += 10;
                }
                final int ia = ii;
                Bukkit.getServer().getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                    public void run() {
                        try {
                            p.playSound(p.getLocation(), Sounds.CLICK.bukkitSound(), 1, 1);
                            for (int i = 0; i < 9; ) {
                                inv.setItem(0 + i, items.get(ia - i).getItemRarity().getItem());
                                inv.setItem(9 + i, items.get(ia - i).getItem());
                                inv.setItem(18 + i, items.get(ia - i).getItemRarity().getItem());
                                i++;
                            }
                            inv.setItem(4, new ItemBuilder(Material.matchMaterial("HOPPER")).setName(CaseManager.droptitle).toItemStack());
                        } catch (Exception ex) {
                        }
                    }
                }, delay);

                ii++;
            }
            Bukkit.getServer().getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                public void run() {
                    CaseInventory.caseinv.put(p, null);
                    p.closeInventory();
                    CaseItem item = items.get(45);
                    Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();
                    fireworkMeta.addEffect(item.getFireworkEffect());
                    fireworkMeta.setPower(1);
                    firework.setFireworkMeta(fireworkMeta);
                    p.playSound(p.getLocation(), Sounds.GLASS.bukkitSound(), 1, 1);
                    if (Util.inventoryIsFull(p, item.getItem())) {
                        p.getWorld().dropItem(p.getLocation(), item.getItem());
                    } else {
                        p.getInventory().addItem(item.getItem());
                    }
                }
            }, 125);
        } else {
            inv = caseinv.get(p);
        }
        return inv;
    }
}