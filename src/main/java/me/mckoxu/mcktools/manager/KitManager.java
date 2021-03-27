package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.Kit;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class KitManager {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void loadKits() {
        ConfigurationSection csk = FileManager.getKits().getConfigurationSection("kits");
        for (String ss : csk.getKeys(false)) {
            ConfigurationSection cs = csk.getConfigurationSection(ss);
            Kit k = new Kit(cs.getName());
            k.setDisplayName(ChatUtil.color(cs.getString("kitname")));
            k.setPermission(cs.getString("permission"));
            k.setTime(cs.getLong("time"));
            k.setLore(ChatUtil.color(FileManager.getKits().getStringList("item.lore")));
            k.setMaterial(cs.getString("material"));
            ConfigurationSection cskil = cs.getConfigurationSection("items");
            for (String sss : cskil.getKeys(false)) {
                ConfigurationSection cski = cskil.getConfigurationSection(sss);
                ArrayList<String> ench = (ArrayList) cski.getList("enchantment");
                k.addItem(new ItemBuilder(Material.matchMaterial(cski.getString("material")))
                        .setData(cski.getInt("data"))
                        .setAmount(cski.getInt("amount"))
                        .setName(ChatUtil.color(cski.getString("name")))
                        .setLore(ChatUtil.color(cski.getStringList("lore")))
                        .setEnchantments(ench).toItemStack());
            }
        }
    }
}
