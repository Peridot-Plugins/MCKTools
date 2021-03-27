package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TimeInventory {
    public static Inventory inv;
    public static ItemStack day;
    public static ItemStack night;
    public static ItemStack custom;

    public TimeInventory(){
        inv = Bukkit.createInventory(null, FileManager.getConfig().getInt("config.time.size")*9, ChatUtil.color(FileManager.getConfig().getString("config.time.title")));
        ItemStack blank = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.time.background.material")))
                .setData(FileManager.getConfig().getInt("config.time.background.data"))
                .setAmount(1)
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.time.background.name")))
                .toItemStack();
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, blank);
        }
        day = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.time.buttons.day.material")))
                .setData(FileManager.getConfig().getInt("config.time.buttons.day.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.time.buttons.day.name")))
                .setLore(ChatUtil.color(FileManager.getConfig().getStringList("config.time.buttons.day.lore")))
                .toItemStack();
        night = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.time.buttons.night.material")))
                .setData(FileManager.getConfig().getInt("config.time.buttons.night.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.time.buttons.night.name")))
                .setLore(ChatUtil.color(FileManager.getConfig().getStringList("config.time.buttons.night.lore")))
                .toItemStack();
        custom = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.time.buttons.custom.material")))
                .setData(FileManager.getConfig().getInt("config.time.buttons.custom.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.time.buttons.custom.name")))
                .setLore(ChatUtil.color(FileManager.getConfig().getStringList("config.time.buttons.custom.lore")))
                .toItemStack();
        inv.setItem(FileManager.getConfig().getInt("config.time.buttons.day.slot"), day);
        inv.setItem(FileManager.getConfig().getInt("config.time.buttons.night.slot"), night);
        inv.setItem(FileManager.getConfig().getInt("config.time.buttons.custom.slot"), custom);
    }
}
