package me.mckoxu.mcktools.object;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    public static List<Kit> kits = new ArrayList<Kit>();

    private List<ItemStack> items = new ArrayList<>();
    private final String name;
    private String material;
    private short data;
    private String displayname;
    private String permission;
    private Long time;
    private List<String> lore;

    public Kit(final String name) {
        this.name = name;
        kits.add(this);
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void addItems(List<ItemStack> items) {
        for (ItemStack is : this.items) {
            this.items.add(is);
        }
    }

    public void removeItems(List<ItemStack> items) {
        for (ItemStack is : this.items) {
            this.items.remove(is);
        }
    }

    public void addItem(ItemStack is) {
        this.items.add(is);
    }

    public void removeItem(ItemStack is) {
        this.items.remove(is);
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setData(short data) {
        this.data = data;
    }

    public void setData(int data) {
        this.data = (short) data;
    }

    public short getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayname;
    }

    public void setDisplayName(String displayname) {
        this.displayname = displayname;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public void addLoreLine(String loreline) {
        this.lore.add(loreline);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Kit get(String name) {
        for (Kit k : Kit.kits) {
            if (k.getName().equals(name)) {
                return k;
            }
        }
        return null;
    }
}
