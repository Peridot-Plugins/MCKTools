package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.object.Drop;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DropManager {
    public static void loadDrop() {
        ConfigurationSection csk = FileManager.getDrops().getConfigurationSection("drop.items");
        for (String ss : csk.getKeys(false)) {
            ConfigurationSection cs = csk.getConfigurationSection(ss);
            Drop drop = new Drop();
            drop.setMaterial(Material.matchMaterial(cs.getString("material")));
            drop.setData(cs.getInt("data"));
            drop.setName(ChatUtil.color(cs.getString("name")));
            drop.setDurability(cs.getInt("durability"));
            drop.setLore(ChatUtil.color(cs.getStringList("lore")));
            drop.setEnchantment((ArrayList) cs.getList("enchantment"));
            drop.setMinAmount(cs.getInt("amount.min"));
            drop.setMaxAmount(cs.getInt("amount.max"));
            drop.setMinHeight(cs.getInt("height.min"));
            drop.setMaxHeight(cs.getInt("height.max"));
            drop.setChance(cs.getDouble("chance"));
            drop.setFortune(cs.getBoolean("fortune"));
            drop.setTurbodrop(cs.getBoolean("turbodrop"));
            drop.setUseTools(cs.getBoolean("usetools"));
            if (cs.getBoolean("usetools")) {
                List<Material> m = new ArrayList();
                for (String t : cs.getStringList("tools")) {
                    Material tm = Material.matchMaterial(t.toUpperCase());
                    if (tm == null) {
                        Bukkit.getLogger().info("[ERROR] Wrong 'tools' in drops.yml");
                        cs.set("usetools", Boolean.valueOf(false));
                        try {
                            FileManager.getDrops().save(MCKTools.getInst().getDataFolder() + "/drops.yml");
                        } catch (IOException ex) {
                            MCKToolsLogger logger = new MCKToolsLogger();
                            logger.error("Save Error", ex.getCause());
                        }
                        drop.setTools(null);
                    }
                    m.add(tm);
                }
                drop.setTools(m);
            }
            Drop.drops.add(drop);
        }
    }
}