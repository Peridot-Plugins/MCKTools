package me.mckoxu.mcktools.util;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.data.mysql.UserDatabase;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.enums.TopType;
import me.mckoxu.mcktools.manager.BanManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.TopManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static String convertTime(int input) {
        int numberOfDays;
        int numberOfHours;
        int numberOfMinutes;
        int numberOfSeconds;

        numberOfDays = input / 86400;
        numberOfHours = (input % 86400) / 3600;
        numberOfMinutes = ((input % 86400) % 3600) / 60;
        numberOfSeconds = ((input % 86400) % 3600) % 60;

        String output = "";
        if (numberOfDays > 0) output = numberOfDays + "d. ";
        if (numberOfHours > 0) output = output + numberOfHours + "h. ";
        if (numberOfMinutes > 0) output = output + numberOfMinutes + "m. ";
        output = output + numberOfSeconds + "s ";

        return output;
    }

    public static boolean inventoryIsFull(Player player, ItemStack itemToAdd) {
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i) == null) {
                return false;
            }
            ItemStack item = player.getInventory().getItem(i);
            if (item.getType().equals(itemToAdd.getType()) && item.getItemMeta().equals(itemToAdd.getItemMeta()) && item.getDurability() == itemToAdd.getDurability() && item.getEnchantments().equals(itemToAdd.getEnchantments()) && item.getAmount() + itemToAdd.getAmount() <= itemToAdd.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    public static int itemsAmountInInventory(Player player, ItemStack item) {
        if (item == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(item))
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    public static int getExp(int expMultiplayer, int lvl) {
        int rlvl = 0;
        for (int i = 1; i < lvl + 1; i++) {
            rlvl += expMultiplayer * i;
        }
        return rlvl;
    }

    public static double getKDR(int kills, int deaths) {
        if (deaths == 0) {
            return kills;
        }
        return kills / deaths;
    }

    public static boolean getChance(double chance) {
        return (chance >= 100) || (chance >= RandomUtil.getRandom(0, 100));
    }

    public static boolean isInt(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public static void saveData() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            UserDatabase.save(UserManager.createUser(player.getUniqueId()));
        });
        TopManager.loadTop();
        BanManager.save();
        FileManager.getData().set("stats.join", MCKTools.joinAmount);
        try {
            FileManager.getData().save(MCKTools.getInst().getDataFolder() + "/" + "data.yml");
        } catch (IOException ex) {
            new MCKToolsLogger().error(ErrorType.DATA_CANTSAVE.getMessage(), ex.getCause());
        }
    }

    public static List<OfflinePlayer> getPlayersInTop(TopType topType) {
        List<OfflinePlayer> list = new ArrayList<>();
        TopManager.list.get(topType).forEach(top -> {
            list.add(top.getPlayer());
        });
        return list;
    }

    public static void createRecipe(CraftingInventory inv, Recipe recipe) {
        ItemStack[] matrix = inv.getMatrix();
        for (int i = 0; i < 9; i++) {
            if (recipe.getItemHashMap().containsKey(i)) {
                if (matrix[i] == null || !matrix[i].equals(recipe.getItemHashMap().get(i))) {
                    return;
                }
            } else {
                if (matrix[i] != null) {
                    return;
                }
            }
        }
        inv.setResult(recipe.getResult());
    }

    public static List<Location> drawParticleLine(Location from, Location to, double density) {
        List<Location> list = new ArrayList<>();
        int n = (int) Math.round(density*from.distance(to));
        double x = from.getX(), y = from.getY(), z = from.getZ();
        double dX = (to.getX()-x)/n;
        double dY = (to.getY()-y)/n;
        double dZ = (to.getZ()-z)/n;
        list.add(from);
        for(int i = 1; i < n; i++)
            list.add(new Location(from.getWorld(), x+i*dX, y+i*dY, z+i*dZ));
        return list;
    }
}
