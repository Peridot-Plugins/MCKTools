package me.mckoxu.mcktools.object;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {

    public static List<Recipe> recipes = new ArrayList<>();

    private Map<Integer, ItemStack> item = new HashMap<>();
    private ItemStack result;
    private final int id;

    public Recipe(ItemStack result, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack item4, ItemStack item5, ItemStack item6, ItemStack item7, ItemStack item8, ItemStack item9) {
        item.put(1, item1);
        item.put(2, item2);
        item.put(3, item3);
        item.put(4, item4);
        item.put(5, item5);
        item.put(6, item6);
        item.put(7, item7);
        item.put(8, item8);
        item.put(9, item9);
        this.result = result;
        this.id = recipes.size() + 1;
        recipes.add(this);
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public Map<Integer, ItemStack> getItemHashMap() {
        return item;
    }

    public ItemStack getItem(int i) {
        if (i < 0 || i > 9) {
            return null;
        }
        return item.get(i);
    }

    public void setItem(int i, ItemStack item) {
        if (i < 0 || i > 9) {
            return;
        }
        this.item.put(i, item);
    }

    public int getId() {
        return id;
    }

    public static Recipe get(int id) {
        for (Recipe r : recipes) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }
}
