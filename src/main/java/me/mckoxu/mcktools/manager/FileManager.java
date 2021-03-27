package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.MCKToolsLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class FileManager {

    private static YamlConfiguration msg;
    private static YamlConfiguration chat;
    private static YamlConfiguration data;
    private static YamlConfiguration kits;
    private static YamlConfiguration drops;
    private static YamlConfiguration cases;
    private static File users = new File(MCKTools.getInst().getDataFolder(), "users");

    public static void checkFiles() {
        if (!MCKTools.getInst().getDataFolder().exists()) {
            MCKTools.getInst().getDataFolder().mkdir();
        }
        if (!new File(MCKTools.getInst().getDataFolder(), "config.yml").exists()) {
            MCKTools.getInst().saveDefaultConfig();
        }
        File m = new File(MCKTools.getInst().getDataFolder(), "messages.yml");
        File c = new File(MCKTools.getInst().getDataFolder(), "chat.yml");
        File d = new File(MCKTools.getInst().getDataFolder(), "data.yml");
        File k = new File(MCKTools.getInst().getDataFolder(), "kits.yml");
        File dr = new File(MCKTools.getInst().getDataFolder(), "drops.yml");
        File ca = new File(MCKTools.getInst().getDataFolder(), "cases.yml");
        if (!m.exists()) {
            MCKTools.getInst().saveResource("messages.yml", true);
        }
        if (!c.exists()) {
            MCKTools.getInst().saveResource("chat.yml", true);
        }
        if (!d.exists()) {
            MCKTools.getInst().saveResource("data.yml", true);
        }
        if (!k.exists()) {
            MCKTools.getInst().saveResource("kits.yml", true);
        }
        if (!dr.exists()) {
            MCKTools.getInst().saveResource("drops.yml", true);
        }
        if (!ca.exists()) {
            MCKTools.getInst().saveResource("cases.yml", true);
        }
        msg = YamlConfiguration.loadConfiguration(m);
        chat = YamlConfiguration.loadConfiguration(c);
        data = YamlConfiguration.loadConfiguration(d);
        kits = YamlConfiguration.loadConfiguration(k);
        drops = YamlConfiguration.loadConfiguration(dr);
        cases = YamlConfiguration.loadConfiguration(ca);
        if (!users.exists()) {
            users.mkdir();
        }
    }

    public static YamlConfiguration getMsg() {
        return msg;
    }

    public static YamlConfiguration getChat() {
        return chat;
    }

    public static YamlConfiguration getData() {
        return data;
    }

    public static YamlConfiguration getKits() {
        return kits;
    }

    public static YamlConfiguration getDrops() {
        return drops;
    }

    public static YamlConfiguration getCases() {
        return cases;
    }

    public static FileConfiguration getConfig() {
        return MCKTools.getInst().getConfig();
    }

    public static File getPFile(Player p) {
        File f = new File(users, p.getName() + ".yml");
        if (!f.exists()) return null;
        return f;
    }

    public static File getUsersFolder() {
        return users;
    }

}
