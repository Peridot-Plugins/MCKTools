package me.mckoxu.mcktools.object;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Drop {

    public static List<Drop> drops = new ArrayList<Drop>();

    private Material material;
    private short data;
    private String name;
    private int durability;
    private ArrayList<String> lore;
    private ArrayList<String> enchantment;
    private int minamount;
    private int maxamount;
    private int minheight;
    private int maxheight;
    private double chance;
    private List<Material> tools;
    private boolean useTools;
    private boolean turbodrop;
    private boolean fortune;

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
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
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurability() {
        return this.durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public ArrayList<String> getLore() {
        return this.lore;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public ArrayList<String> getEnchantment() {
        return this.enchantment;
    }

    public void setEnchantment(ArrayList<String> enchantment) {
        this.enchantment = enchantment;
    }

    public int getMinAmount() {
        return this.minamount;
    }

    public void setMinAmount(int minamount) {
        this.minamount = minamount;
    }

    public int getMaxAmount() {
        return this.maxamount;
    }

    public void setMaxAmount(int maxamount) {
        this.maxamount = maxamount;
    }

    public int getMinHeight() {
        return this.minheight;
    }

    public void setMinHeight(int minheight) {
        this.minheight = minheight;
    }

    public int getMaxHeight() {
        return this.maxheight;
    }

    public void setMaxHeight(int maxheight) {
        this.maxheight = maxheight;
    }

    public double getChance() {
        return this.chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public boolean getTurbodrop() {
        return this.turbodrop;
    }

    public void setTurbodrop(boolean turbodrop) {
        this.turbodrop = turbodrop;
    }

    public boolean getFortune() {
        return this.fortune;
    }

    public void setFortune(boolean fortune) {
        this.fortune = fortune;
    }

    public List<Material> getTools() {
        return this.tools;
    }

    public void setTools(List<Material> tools) {
        this.tools = tools;
    }

    public boolean getUseTools() {
        return this.useTools;
    }

    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }

}
