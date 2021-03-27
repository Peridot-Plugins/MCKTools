package me.mckoxu.mcktools.listener;


import me.mckoxu.mcktools.object.Recipe;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent e) {
        CraftingInventory inv = e.getInventory();
        if (inv.getMatrix().length < 9) {
            return;
        }
        for (Recipe r : Recipe.recipes) {
            Util.createRecipe(inv, r);
        }
    }
}
