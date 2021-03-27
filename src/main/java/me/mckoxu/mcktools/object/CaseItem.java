package me.mckoxu.mcktools.object;

import me.mckoxu.mcktools.enums.ItemRarity;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;

public class CaseItem {
    private ItemStack item;
    private ItemRarity itemRarity;
    private double chance;
    private FireworkEffect fireworkEffect;

    public CaseItem(ItemRarity itemRarity, ItemStack item, double chance) {
        this.itemRarity = itemRarity;
        this.item = item;
        this.chance = chance;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemRarity getItemRarity() {
        return itemRarity;
    }

    public double getChance() {
        return chance;
    }

    public FireworkEffect getFireworkEffect() {
        return fireworkEffect;
    }

    public void setFireworkEffect(FireworkEffect fireworkEffect) {
        this.fireworkEffect = fireworkEffect;
    }

}
