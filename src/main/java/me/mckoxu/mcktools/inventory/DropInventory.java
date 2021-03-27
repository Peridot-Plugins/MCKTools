package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.manager.CaseManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.CaseItem;
import me.mckoxu.mcktools.object.Drop;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DropInventory {
    public static Map<Player, Inventory> basicinv = new HashMap();
    public static Map<Player, Inventory> dropinv = new HashMap();
    public static Map<Player, Inventory> caseinv = new HashMap();

    public static Inventory getBasicInv(Player p, boolean update) {
        Inventory inv;
        if ((basicinv.get(p) == null) || (update)) {
            inv = Bukkit.createInventory(null, 27, ChatUtil.color(FileManager.getDrops().getString("drop.gui.title")));
            ItemStack blank = new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.background.material")))
                    .setData(FileManager.getDrops().getInt("drop.gui.background.data"))
                    .setAmount(1)
                    .setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.background.name")))
                    .toItemStack();
            for (int i = 0; i < 27; i++) {
                inv.setItem(i, blank);
            }
            User u = UserManager.createUser(p.getUniqueId());
            inv.setItem(10, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.stone.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.stone.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.stone.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.stone.lore"))).toItemStack());
            ArrayList<String> lore = new ArrayList();
            String status = "";
            if (u.getCobblestone()) {
                status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.enable"));
            } else {
                status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.disable"));
            }
            for (String msg : FileManager.getDrops().getStringList("drop.gui.buttons.cobblestone.lore")) {
                lore.add(ChatUtil.color(msg.replace("{STATUS}", status)));
            }
            inv.setItem(13, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.cobblestone.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.cobblestone.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.cobblestone.name"))).setLore(lore).toItemStack());
            inv.setItem(16, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.case.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.case.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.case.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.case.lore"))).toItemStack());
            basicinv.put(p, inv);
        } else {
            inv = basicinv.get(p);
        }
        return inv;
    }

    public static Inventory getDropInv(Player p, boolean update) {
        Inventory inv;
        if ((dropinv.get(p) == null) || (update)) {
            inv = Bukkit.createInventory(null, 54, ChatUtil.color(FileManager.getDrops().getString("drop.gui.title")));
            for (int i = 0; i < 54; i++) {
                inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.background.material"))).setData(FileManager.getDrops().getInt("drop.gui.background.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.background.name"))).toItemStack());
            }
            User u = UserManager.createUser(p.getUniqueId());
            int count = 0;
            for (Drop drops : Drop.drops) {
                String status = "";
                if ((u.getDrops().get(drops) != null) && (((Boolean) u.getDrops().get(drops)).booleanValue())) {
                    status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.enable"));
                } else {
                    status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.disable"));
                }
                String turbodrop = "";
                if (drops.getTurbodrop()) {
                    turbodrop = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.yesvalue"));
                } else {
                    turbodrop = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.novalue"));
                }
                String fortune = "";
                if (drops.getTurbodrop()) {
                    fortune = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.yesvalue"));
                } else {
                    fortune = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.novalue"));
                }
                String tools = "";
                Material m;
                if (drops.getUseTools()) {
                    for (Iterator localIterator2 = drops.getTools().iterator(); localIterator2.hasNext(); ) {
                        m = (Material) localIterator2.next();
                        tools = tools + "," + m;
                    }
                    tools = tools.replaceFirst(",", "");
                } else {
                    tools = FileManager.getDrops().getString("drop.gui.status.anytool");
                }
                Object lore = new ArrayList();
                for (String msg : FileManager.getDrops().getStringList("drop.gui.lore")) {
                    ((ArrayList) lore).add(ChatUtil.color(msg.replace("{STATUS}", status).replace("{CHANCE}", String.valueOf(drops.getChance())).replace("{HEIGHT-MIN}", String.valueOf(drops.getMinHeight())).replace("{HEIGHT-MAX}", String.valueOf(drops.getMaxHeight())).replace("{AMOUNT-MIN}", String.valueOf(drops.getMinAmount()))).replace("{AMOUNT-MAX}", String.valueOf(drops.getMaxAmount())).replace("{TURBODROP}", turbodrop).replace("{FORTUNE}", fortune).replace("{TOOLS}", tools));
                }
                count++;
                inv.setItem(count + 8, new ItemBuilder(drops.getMaterial()).setData(drops.getData()).setAmount(1).setName(drops.getName()).setLore((ArrayList) lore).toItemStack());
            }
            for (int i = 36; i < 54; i++) {
                inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.background.material"))).setData(FileManager.getDrops().getInt("drop.gui.background.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.background.name"))).toItemStack());
            }

            int time = 0;
            if ((u.getTurboDrop() != null) && (u.getTurboDrop().getTime() > 0)) {
                time = u.getTurboDrop().getTime();
            }
            ArrayList<String> turbodrop = new ArrayList();
            for (String msg : FileManager.getDrops().getStringList("drop.gui.buttons.turbodrop.lore")) {
                turbodrop.add(ChatUtil.color(msg.replace("{TURBODROP}", Util.convertTime(time))));
            }
            inv.setItem(53, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.back.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.back.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.back.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.back.lore"))).toItemStack());
            inv.setItem(46, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.allon.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.allon.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.allon.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.allon.lore"))).toItemStack());
            inv.setItem(47, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.alloff.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.alloff.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.alloff.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.alloff.lore"))).toItemStack());
            inv.setItem(49, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.bonusdrop.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.bonusdrop.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.bonusdrop.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.bonusdrop.lore"))).toItemStack());
            inv.setItem(50, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.turbodrop.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.turbodrop.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.exp.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.exp.lore"))).toItemStack());
            inv.setItem(51, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.turbodrop.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.turbodrop.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.turbodrop.name"))).setLore(turbodrop).toItemStack());
            dropinv.put(p, inv);
        } else {
            inv = dropinv.get(p);
        }
        return inv;
    }

    public static Inventory getCaseInv(Player p, boolean update) {
        Inventory inv;
        if (caseinv.get(p) == null || (update)) {
            inv = Bukkit.createInventory(null, 45, ChatUtil.color(FileManager.getDrops().getString("drop.gui.title")));
            for (int i = 0; i < 45; i++) {
                inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.background.material")))
                        .setData(FileManager.getDrops().getInt("drop.gui.background.data"))
                        .setAmount(1)
                        .setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.background.name")))
                        .toItemStack());
            }
            int count = 0;
            for (CaseItem item : CaseManager.caseitems) {
                count++;
                inv.setItem(count + 8, item.getItem());
            }
            for (int i = 36; i < 45; i++) {
                inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.background.material")))
                        .setData(FileManager.getDrops().getInt("drop.gui.background.data"))
                        .setAmount(1)
                        .setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.background.name")))
                        .toItemStack());
            }
            inv.setItem(44, new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.back.material")))
                    .setData(FileManager.getDrops().getInt("drop.gui.buttons.back.data"))
                    .setAmount(1)
                    .setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.back.name")))
                    .setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.back.lore"))).toItemStack());
            caseinv.put(p, inv);
        } else {
            inv = caseinv.get(p);
        }
        return inv;
    }
}