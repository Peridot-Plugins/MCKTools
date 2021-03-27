package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.enums.ItemRarity;
import me.mckoxu.mcktools.object.CaseItem;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CaseManager {

    public static ItemStack chest;
    public static ItemStack key;
    public static ItemStack background;
    public static List<CaseItem> caseitems;
    public static String inventorytitle = ChatUtil.color(FileManager.getCases().getString("gui.title"));
    public static String droptitle = ChatUtil.color(FileManager.getCases().getString("gui.droptitle"));

    public static void loadChests() {
        ConfigurationSection cska = FileManager.getCases().getConfigurationSection("rarity");
        for (String ss : cska.getKeys(false)) {
            ConfigurationSection cs = cska.getConfigurationSection(ss);
            if (!cs.getName().equalsIgnoreCase("item")) {
                ItemRarity itemRarity = ItemRarity.valueOf(cs.getName().toUpperCase());
                itemRarity.setRgb(cs.getInt("firework.red"), cs.getInt("firework.green"), cs.getInt("firework.blue"));
                if (cska.getBoolean("item.enabled")) {
                    ItemStack item = new ItemBuilder(Material.matchMaterial(cs.getString("item.material")))
                            .setData(cs.getInt("item.data"))
                            .setName(ChatUtil.color(cs.getString("item.name")))
                            .toItemStack();
                    itemRarity.setItem(item);
                }
            }
        }
        caseitems = new ArrayList<>();
        ConfigurationSection cskb = FileManager.getCases().getConfigurationSection("drop.items");
        for (String ss : cskb.getKeys(false)) {
            ConfigurationSection cs = cskb.getConfigurationSection(ss);
            ItemBuilder ib = new ItemBuilder(Material.matchMaterial(cs.getString("material")));
            ib.setAmount(cs.getInt("amount"));
            ib.setName(ChatUtil.color(cs.getString("name")));
            ib.setLore(ChatUtil.color(cs.getStringList("lore")));
            ib.setEnchantments((ArrayList<String>) cs.getStringList("enchantment"));
            ib.setData(cs.getInt("data"));
            ItemRarity itemRarity = ItemRarity.valueOf(cs.getString("rarity").toUpperCase());
            CaseItem item = new CaseItem(itemRarity, ib.toItemStack(), cs.getDouble("chance"));
            FireworkEffect fireworkEffect = FireworkEffect.builder()
                    .withColor(Color.fromRGB(itemRarity.getRed(), itemRarity.getGreen(), itemRarity.getBlue()))
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .build();
            item.setFireworkEffect(fireworkEffect);
            try {
                caseitems.add(item);
            } catch (Exception ex) {
                MCKToolsLogger logger = new MCKToolsLogger();
                logger.error("Bad case configuration! " + cs.getName());
            }
        }
        ItemBuilder caseib = new ItemBuilder(Material.matchMaterial("CHEST"));
        ItemBuilder keyib = new ItemBuilder(Material.matchMaterial("TRIPWIRE_HOOK"));
        ItemBuilder backgroundib = new ItemBuilder(Material.matchMaterial(FileManager.getCases().getString("gui.background.material")));
        caseib.setData(FileManager.getCases().getInt("case.data"));
        caseib.setName(ChatUtil.color(FileManager.getCases().getString("case.name")));
        caseib.setLore(ChatUtil.color(FileManager.getCases().getStringList("case.lore")));
        caseib.setEnchantments((ArrayList<String>) FileManager.getCases().getStringList("case.enchantment"));
        keyib.setName(ChatUtil.color(FileManager.getCases().getString("key.name")));
        caseib.setData(FileManager.getCases().getInt("key.data"));
        keyib.setLore(ChatUtil.color(FileManager.getCases().getStringList("key.lore")));
        keyib.setEnchantments((ArrayList<String>) FileManager.getCases().getStringList("key.enchantment"));
        backgroundib.setName(ChatUtil.color(FileManager.getCases().getString("gui.background.name")));
        backgroundib.setData(FileManager.getCases().getInt("gui.background.data"));
        chest = caseib.toItemStack();
        key = keyib.toItemStack();
        background = backgroundib.toItemStack();
    }
}
