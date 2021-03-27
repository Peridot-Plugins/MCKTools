package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.Kit;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class KitInventory {
    public static HashMap<Player, Inventory> basicinv = new HashMap();
    public static HashMap<Player, Inventory> kitContent = new HashMap();

    public static Inventory getBasicInv(Player p) {
        File f = FileManager.getPFile(p);
        YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
        int count = Kit.kits.size();
        if (count > 18) {
            count = 18;
        }
        int rows = 1;
        if (count > 5) {
            rows++;
        }
        if (count > 9) {
            rows++;
        }
        if (count > 14) {
            rows++;
        }
        Inventory inv = Bukkit.createInventory(null, 18 + 9 * rows, ChatUtil.color(FileManager.getKits().getString("guiname")));
        for (int i = 0; i < 18 + 9 * rows; i++) {
            inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getKits().getString("background.material")))
                    .setData(FileManager.getKits().getInt("background.data"))
                    .setAmount(1)
                    .setName(ChatUtil.color(FileManager.getKits().getString("background.name")))
                    .toItemStack());
        }
        basicinv.put(p, inv);
        int i = 0;
        int aLine = 9;
        int bLine = 19;
        int cLine = 27;
        int dLine = 37;
        for (Kit k : Kit.kits) {
            i++;
            String returntime = "";
            if ((fYml.getLong("kits." + k.getName()) <= System.currentTimeMillis()) || (fYml.getLong("kits." + k.getName()) == 0) || (p.hasPermission("mckt.kits.bypass"))) {
                returntime = FileManager.getKits().getString("item.now");
            } else {
                returntime = MCKTools.getInst().df.format(Long.valueOf(fYml.getLong("kits." + k.getName())));
            }
            ArrayList<String> lore = new ArrayList();
            for (String msg : FileManager.getKits().getStringList("item.lore")) {
                lore.add(ChatUtil.color(msg).replace("{TIME}", returntime));
            }
            if (i >= 1 && i <= 5) {
                inv.setItem(aLine, new ItemBuilder(Material.matchMaterial(k.getMaterial())).setData(k.getData()).setAmount(1).setName(ChatUtil.color(FileManager.getKits().getString("item.name").replace("{KITNAME}", k.getDisplayName()))).setLore(lore).toItemStack());
                aLine += 2;
            } else if (i >= 6 && i <= 9) {
                inv.setItem(bLine, new ItemBuilder(Material.matchMaterial(k.getMaterial())).setData(k.getData()).setAmount(1).setName(ChatUtil.color(FileManager.getKits().getString("item.name").replace("{KITNAME}", k.getDisplayName()))).setLore(lore).toItemStack());
                bLine += 2;
            } else if (i >= 10 && i <= 14) {
                inv.setItem(cLine, new ItemBuilder(Material.matchMaterial(k.getMaterial())).setData(k.getData()).setAmount(1).setName(ChatUtil.color(FileManager.getKits().getString("item.name").replace("{KITNAME}", k.getDisplayName()))).setLore(lore).toItemStack());
                cLine += 2;
            } else if (i >= 15 && i <= 18) {
                inv.setItem(dLine, new ItemBuilder(Material.matchMaterial(k.getMaterial())).setData(k.getData()).setAmount(1).setName(ChatUtil.color(FileManager.getKits().getString("item.name").replace("{KITNAME}", k.getDisplayName()))).setLore(lore).toItemStack());
                dLine += 2;
            }
        }
        return inv;
    }

    public static Inventory getKitContent(Player p, Kit kit) {
        int count = kit.getItems().size();
        Inventory inv = Bukkit.createInventory(null, (count - 1) / 9 * 9 + 9, ChatUtil.color(kit.getDisplayName()));
        kitContent.put(p, inv);
        for (ItemStack item : kit.getItems()) {
            inv.addItem(item);
        }
        return inv;
    }
}