package me.mckoxu.mcktools.enums;

import org.bukkit.inventory.ItemStack;

public enum ItemRarity {
    LEGENDARY("legendary", 255, 0, 0),
    EPIC("epic", 255, 0, 255),
    RARE("rare", 0, 255, 255),
    COMMON("common", 140, 140, 140);

    final String id;
    int red;
    int green;
    int blue;
    ItemStack item;

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setRgb(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    ItemRarity(String id, int red, int green, int blue) {
        this.id = id;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

}
