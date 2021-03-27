package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WeatherInventory {
    public static Inventory inv;
    public static ItemStack sun;
    public static ItemStack rain;
    public static ItemStack storm;

    public WeatherInventory(){
        inv = Bukkit.createInventory(null, FileManager.getConfig().getInt("config.weather.size")*9, ChatUtil.color(FileManager.getConfig().getString("config.weather.title")));
        ItemStack blank = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.weather.background.material")))
                .setData(FileManager.getConfig().getInt("config.weather.background.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.weather.background.name")))
                .toItemStack();
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, blank);
        }
        sun = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.weather.buttons.sun.material")))
                .setData(FileManager.getConfig().getInt("config.weather.buttons.sun.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.weather.buttons.sun.name")))
                .setLore(ChatUtil.color(FileManager.getConfig().getStringList("config.weather.buttons.sun.lore")))
                .toItemStack();
        rain = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.weather.buttons.rain.material")))
                .setData(FileManager.getConfig().getInt("config.weather.buttons.rain.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.weather.buttons.rain.name")))
                .setLore(ChatUtil.color(FileManager.getConfig().getStringList("config.weather.buttons.rain.lore")))
                .toItemStack();
        storm = new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.weather.buttons.storm.material")))
                .setData(FileManager.getConfig().getInt("config.weather.buttons.storm.data"))
                .setName(ChatUtil.color(FileManager.getConfig().getString("config.weather.buttons.storm.name")))
                .setLore(ChatUtil.color(FileManager.getConfig().getStringList("config.weather.buttons.storm.lore")))
                .toItemStack();
        inv.setItem(FileManager.getConfig().getInt("config.weather.buttons.sun.slot"), sun);
        inv.setItem(FileManager.getConfig().getInt("config.weather.buttons.rain.slot"), rain);
        inv.setItem(FileManager.getConfig().getInt("config.weather.buttons.storm.slot"), storm);
    }
}
