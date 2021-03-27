package me.mckoxu.mcktools.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.enums.ErrorType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class HeadUtil {
    public static ItemStack createCustomSkull(ItemStack head, String url) {
        if (url.isEmpty()) return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.HEAD_CANTCREATEHEAD.getMessage(), ex.getCause());
        }
        head.setItemMeta(headMeta);
        return head;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack createSkull(String name, ArrayList<String> lore, String nick) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        if (name != null) {
            im.setDisplayName(name);
        }
        if (lore != null) {
            im.setLore(lore);
        }
        im.setOwner(nick);
        is.setItemMeta(im);
        return is;
    }
}
