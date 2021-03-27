package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class StatsInventory {
    public static HashMap<Player, Inventory> statsinv = new HashMap();

    public static Inventory getInventory(Player p, Player owner) {
        Inventory inv = Bukkit.createInventory(null, 9*FileManager.getConfig().getInt("config.stats.size"), ChatUtil.color(FileManager.getConfig().getString("config.stats.title").replace("{PLAYER}", owner.getName())));
        statsinv.put(p, inv);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.stats.background.material"))).setData(FileManager.getConfig().getInt("config.stats.background.data")).setAmount(1).setName(ChatUtil.color(FileManager.getConfig().getString("config.stats.background.name"))).toItemStack());
        }
        ConfigurationSection csk = FileManager.getConfig().getConfigurationSection("config.stats.items");
        for (String ss : csk.getKeys(false)) {
            ConfigurationSection cs = csk.getConfigurationSection(ss);
            ArrayList<String> lore = new ArrayList();
            for (String msg : cs.getStringList("lore")) {
                String slr = ChatUtil.color(VariablesUtil.returner(owner, msg));
                lore.add(slr);
            }
            inv.setItem(cs.getInt("slot"), new ItemBuilder(Material.matchMaterial(cs.getString("material"))).setData(FileManager.getConfig().getInt("data")).setAmount(1).setName(ChatUtil.color(VariablesUtil.returner(owner, cs.getString("name")))).setLore(lore).toItemStack());
        }
        return inv;
    }
}