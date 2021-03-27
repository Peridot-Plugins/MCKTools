package me.mckoxu.mcktools.inventory;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.Recipe;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.HeadUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipesInventory {
    public static Map<Player, Inventory> recipesinv = new HashMap<Player, Inventory>();
    public static Map<Player, Integer> pagenum = new HashMap<Player, Integer>();

    public static ItemStack next = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

    {
        ItemMeta im = HeadUtil.createCustomSkull(next, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE0ZjY4YzhmYjI3OWU1MGFiNzg2ZjlmYTU0Yzg4Y2E0ZWNmZTFlYjVmZDVmMGMzOGM1NGM5YjFjNzIwM2Q3YSJ9fX0=").getItemMeta();
        im.setDisplayName(ChatUtil.color(FileManager.getConfig().getString("config.recipes.buttons.next.name")));
        ArrayList<String> lore = new ArrayList<String>();
        for (String msg : FileManager.getConfig().getStringList("config.recipes.buttons.next.lore")) {
            lore.add(ChatUtil.color(msg));
        }
        im.setLore(lore);
        next.setItemMeta(im);
    }

    public static ItemStack previous = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

    {
        ItemMeta im = HeadUtil.createCustomSkull(previous, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM3NjQ4YWU3YTU2NGE1Mjg3NzkyYjA1ZmFjNzljNmI2YmQ0N2Y2MTZhNTU5Y2U4YjU0M2U2OTQ3MjM1YmNlIn19fQ==").getItemMeta();
        im.setDisplayName(ChatUtil.color(FileManager.getConfig().getString("config.recipes.buttons.previous.name")));
        ArrayList<String> lore = new ArrayList<String>();
        for (String msg : FileManager.getConfig().getStringList("config.recipes.buttons.previous.lore")) {
            lore.add(ChatUtil.color(msg));
        }
        im.setLore(lore);
        previous.setItemMeta(im);
    }

    public static Inventory getInventory(Player p, int pagenumber) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.color(FileManager.getConfig().getString("config.recipes.title")));
        recipesinv.put(p, inv);
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, new ItemBuilder(Material.matchMaterial(FileManager.getConfig().getString("config.recipes.background.material"))).setData(FileManager.getConfig().getInt("config.recipes.background.data")).setAmount(1).setName(ChatUtil.color(FileManager.getConfig().getString("config.recipes.background.name"))).toItemStack());
        }
        inv.setItem(2, Recipe.get(pagenumber).getItem(1));
        inv.setItem(3, Recipe.get(pagenumber).getItem(2));
        inv.setItem(4, Recipe.get(pagenumber).getItem(3));
        inv.setItem(11, Recipe.get(pagenumber).getItem(4));
        inv.setItem(12, Recipe.get(pagenumber).getItem(5));
        inv.setItem(13, Recipe.get(pagenumber).getItem(6));
        inv.setItem(20, Recipe.get(pagenumber).getItem(7));
        inv.setItem(21, Recipe.get(pagenumber).getItem(8));
        inv.setItem(22, Recipe.get(pagenumber).getItem(9));
        inv.setItem(15, Recipe.get(pagenumber).getResult());
        ItemStack page = new ItemStack(Material.matchMaterial(MCKTools.getInst().getConfig().getString("config.recipes.buttons.page.material")), 1);
        {
            ItemMeta im = page.getItemMeta();
            im.setDisplayName(ChatUtil.color(FileManager.getConfig().getString("config.recipes.buttons.page.name").replace("{PAGE}", String.valueOf(pagenumber).replace("{MAXPAGE}", String.valueOf(Recipe.recipes.size())))));
            ArrayList<String> lore = new ArrayList<String>();
            for (String msg : FileManager.getConfig().getStringList("config.recipes.buttons.page.lore")) {
                lore.add(ChatUtil.color(msg.replace("{PAGE}", String.valueOf(pagenumber)).replace("{MAXPAGE}", String.valueOf(Recipe.recipes.size()))));
            }
            im.setLore(lore);
            page.setItemMeta(im);
        }
        if (pagenumber >= 1 && pagenumber < Recipe.recipes.size() && Recipe.get(pagenumber) != null) {
            inv.setItem(26, next);
        }
        if (pagenumber > 1 && pagenumber <= Recipe.recipes.size() && Recipe.get(pagenumber) != null) {
            inv.setItem(8, previous);
        }
        inv.setItem(17, page);
        return inv;
    }
}
