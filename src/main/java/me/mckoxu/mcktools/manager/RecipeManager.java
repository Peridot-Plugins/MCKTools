package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.Recipe;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RecipeManager {

    public static void loadRecipes() {
        ConfigurationSection csec = FileManager.getConfig().getConfigurationSection("config.recipes.recipes");
        for (String ss : csec.getKeys(false)) {
            ConfigurationSection cs = csec.getConfigurationSection(ss);
            ArrayList<String> ench = (ArrayList) cs.getList("result.enchantment");
            ItemStack result = new ItemBuilder(Material.matchMaterial(cs.getString("result.material"))).setData(cs.getInt("result.data")).setAmount(cs.getInt("result.amount")).setName(ChatUtil.color(cs.getString("result.name"))).setLore(ChatUtil.color(cs.getStringList("result.lore"))).setEnchantments(ench).toItemStack();
            ItemStack item1 = new ItemBuilder(Material.matchMaterial(cs.getString("items.1.material"))).setData(cs.getInt("items.1.material")).setAmount(cs.getInt("items.1.amount")).setName(ChatUtil.color(cs.getString("items.1.name"))).setLore(ChatUtil.color(cs.getStringList("items.1.lore"))).toItemStack();
            ItemStack item2 = new ItemBuilder(Material.matchMaterial(cs.getString("items.2.material"))).setData(cs.getInt("items.2.material")).setAmount(cs.getInt("items.2.amount")).setName(ChatUtil.color(cs.getString("items.2.name"))).setLore(ChatUtil.color(cs.getStringList("items.2.lore"))).toItemStack();
            ItemStack item3 = new ItemBuilder(Material.matchMaterial(cs.getString("items.3.material"))).setData(cs.getInt("items.3.material")).setAmount(cs.getInt("items.3.amount")).setName(ChatUtil.color(cs.getString("items.3.name"))).setLore(ChatUtil.color(cs.getStringList("items.3.lore"))).toItemStack();
            ItemStack item4 = new ItemBuilder(Material.matchMaterial(cs.getString("items.4.material"))).setData(cs.getInt("items.4.material")).setAmount(cs.getInt("items.4.amount")).setName(ChatUtil.color(cs.getString("items.4.name"))).setLore(ChatUtil.color(cs.getStringList("items.4.lore"))).toItemStack();
            ItemStack item5 = new ItemBuilder(Material.matchMaterial(cs.getString("items.5.material"))).setData(cs.getInt("items.5.material")).setAmount(cs.getInt("items.5.amount")).setName(ChatUtil.color(cs.getString("items.5.name"))).setLore(ChatUtil.color(cs.getStringList("items.5.lore"))).toItemStack();
            ItemStack item6 = new ItemBuilder(Material.matchMaterial(cs.getString("items.6.material"))).setData(cs.getInt("items.6.material")).setAmount(cs.getInt("items.6.amount")).setName(ChatUtil.color(cs.getString("items.6.name"))).setLore(ChatUtil.color(cs.getStringList("items.6.lore"))).toItemStack();
            ItemStack item7 = new ItemBuilder(Material.matchMaterial(cs.getString("items.7.material"))).setData(cs.getInt("items.7.material")).setAmount(cs.getInt("items.7.amount")).setName(ChatUtil.color(cs.getString("items.7.name"))).setLore(ChatUtil.color(cs.getStringList("items.7.lore"))).toItemStack();
            ItemStack item8 = new ItemBuilder(Material.matchMaterial(cs.getString("items.8.material"))).setData(cs.getInt("items.8.material")).setAmount(cs.getInt("items.8.amount")).setName(ChatUtil.color(cs.getString("items.8.name"))).setLore(ChatUtil.color(cs.getStringList("items.8.lore"))).toItemStack();
            ItemStack item9 = new ItemBuilder(Material.matchMaterial(cs.getString("items.9.material"))).setData(cs.getInt("items.9.material")).setAmount(cs.getInt("items.9.amount")).setName(ChatUtil.color(cs.getString("items.9.name"))).setLore(ChatUtil.color(cs.getStringList("items.9.lore"))).toItemStack();
            new Recipe(result, item1, item2, item3, item4, item5, item6, item7, item8, item9);
        }
    }
}
