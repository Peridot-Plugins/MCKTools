package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.HeadUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdminInventory {
    public static Inventory inv = Bukkit.createInventory(null, FileManager.getConfig().getInt("config.admin.size") * 9, ChatUtil.color(FileManager.getConfig().getString("config.admin.title")));

    public AdminInventory(){
        inv = Bukkit.createInventory(null, FileManager.getConfig().getInt("config.admin.size")*9, ChatUtil.color(FileManager.getConfig().getString("config.admin.title")));
        ItemStack blank = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.admin.background.material")))
                .setData(FileManager.getConfig().getInt("config.admin.background.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.admin.background.name")))
                .toItemStack();
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, blank);
        }
        ConfigurationSection csk = FileManager.getConfig().getConfigurationSection("config.admin.admins");
        for (String ss : csk.getKeys(false)) {
            ConfigurationSection cs = csk.getConfigurationSection(ss);
            inv.setItem(cs.getInt("slot"), HeadUtil.createSkull(ChatUtil.color(cs.getString("name")), ChatUtil.color(cs.getStringList("lore")), cs.getString("nick")));
        }
    }
}
