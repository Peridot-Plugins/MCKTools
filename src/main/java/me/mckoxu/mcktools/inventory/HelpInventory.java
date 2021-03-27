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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelpInventory {
    public static Map<Player, Inventory> helpinv = new HashMap<Player, Inventory>();

    public static Inventory getInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, FileManager.getConfig().getInt("config.help.size") * 9, ChatUtil.color(FileManager.getConfig().getString("config.help.title")));
        helpinv.put(p, inv);
        ItemStack blank = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.help.background.material")))
                .setData(FileManager.getConfig().getInt("config.help.background.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.help.background.name")))
                .toItemStack();
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, blank);
        }
        ConfigurationSection csk = FileManager.getConfig().getConfigurationSection("config.help.items");
        for (String ss : csk.getKeys(false)) {
            ConfigurationSection cs = csk.getConfigurationSection(ss);
            ArrayList<String> lore = new ArrayList<String>();
            for (String msg : cs.getStringList("lore")) {
                lore.add(ChatUtil.color(VariablesUtil.returner(p, VariablesUtil.serverreturner(VariablesUtil.returner(p, msg)))));
            }
            ArrayList<String> ench = (ArrayList<String>) cs.getList("enchantment");
            inv.setItem(cs.getInt("slot"), new ItemBuilder(Material.matchMaterial(cs.getString("material"))).setData(FileManager.getConfig().getInt("data")).setAmount(cs.getInt("amount")).setName(ChatUtil.color(VariablesUtil.serverreturner(VariablesUtil.returner(p, cs.getString("name"))))).setLore(lore).setEnchantments(ench).toItemStack());
        }
        return inv;
    }
}
