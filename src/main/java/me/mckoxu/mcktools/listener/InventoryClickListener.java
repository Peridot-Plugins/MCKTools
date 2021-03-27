package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.inventory.*;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.Drop;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.Kit;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InventoryClickListener implements Listener {

    @SuppressWarnings({"unchecked"})
    @EventHandler
    public void onClick(InventoryClickEvent e) throws IOException {
        Player p = (Player) e.getWhoClicked();
        User u = UserManager.createUser(p.getUniqueId());
        World w = p.getWorld();
        MCKToolsLogger logger = new MCKToolsLogger();
        if (e.getInventory() != null) {
            if (e.getCurrentItem() != null) {
                ItemStack item = e.getCurrentItem();
                Inventory inv = e.getInventory();
                if (inv.equals(KitInventory.basicinv.get(p))) {
                    File f;
                    f = new File(FileManager.getUsersFolder(), p.getName() + ".yml");
                    f = FileManager.getPFile(p);
                    YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
                    for (Kit k : Kit.kits) {
                        if (Material.matchMaterial(k.getMaterial()) == item.getType() && k.getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                            String returntime = "";
                            if (fYml.getLong("kits." + k.getName()) <= System.currentTimeMillis() || fYml.getLong("kits." + k.getName()) == 0 || p.hasPermission("mckt.kits.bypass")) {
                                returntime = FileManager.getKits().getString("item.now");
                            } else {
                                returntime = MCKTools.getInst().df.format(fYml.getLong("kits." + k.getName()));
                            }
                            if (p.hasPermission(k.getPermission())) {
                                if (fYml.getLong("kits." + k.getName()) <= System.currentTimeMillis() || fYml.getLong("kits." + k.getName()) == 0 || p.hasPermission("mckt.kits.bypass")) {
                                    p.openInventory(KitInventory.getKitContent(p, k));
                                    fYml.set("kits." + k.getName(), k.getTime() * 1000 + System.currentTimeMillis());
                                    fYml.save(f);
                                } else {
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.kits.notime")).replace("{TIME}", returntime));
                                }
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", k.getName()));
                            }
                        }
                    }
                    e.setCancelled(true);
                } else if (inv.equals(StatsInventory.statsinv.get(p))) {
                    e.setCancelled(true);
                } else if (inv.equals(HelpInventory.helpinv.get(p))) {
                    e.setCancelled(true);
                    ConfigurationSection csec = FileManager.getConfig().getConfigurationSection("config.help.items");
                    for (String ss : csec.getKeys(false)) {
                        ConfigurationSection cs = csec.getConfigurationSection(ss);
                        ArrayList<String> lore = new ArrayList<>();
                        for (String msg : cs.getStringList("lore")) {
                            lore.add(ChatUtil.color(VariablesUtil.returner(p, VariablesUtil.serverreturner(VariablesUtil.returner(p, msg)))));
                        }
                        ArrayList<String> ench = (ArrayList<String>) cs.getList("enchantment");
                        if (item.equals(new ItemBuilder(Material.matchMaterial(cs.getString("material"))).setData(cs.getInt("data")).setAmount(cs.getInt("amount")).setName(ChatUtil.color(VariablesUtil.serverreturner(VariablesUtil.returner(p, cs.getString("name"))))).setLore(lore).setEnchantments(ench).toItemStack())) {
                            for (String cmds : cs.getStringList("commands")) {
                                Bukkit.dispatchCommand(p, cmds.replace("{PLAYER}", p.getName()));
                            }
                            for (String cmds : cs.getStringList("consolecommands")) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmds.replace("{PLAYER}", p.getName()));
                            }
                        }
                    }
                } else if (inv.equals(AdminInventory.inv)) {
                    e.setCancelled(true);
                } else if (inv.equals(RecipesInventory.recipesinv.get(p))) {
                    ConfigurationSection csec = FileManager.getConfig().getConfigurationSection("config.recipes.recipes");
                    if (item != null && RecipesInventory.next != null && RecipesInventory.previous != null) {
                        if (item.equals(RecipesInventory.next) || item.equals(RecipesInventory.previous)) {
                            if (item.equals(RecipesInventory.next)) {
                                RecipesInventory.pagenum.put(p, RecipesInventory.pagenum.get(p) + 1);
                            } else if (item.equals(RecipesInventory.previous)) {
                                RecipesInventory.pagenum.put(p, RecipesInventory.pagenum.get(p) - 1);
                            }
                            p.openInventory(RecipesInventory.getInventory(p, RecipesInventory.pagenum.get(p)));
                        }
                    }
                    e.setCancelled(true);
                } else if (inv.equals(DropInventory.getBasicInv(p, false))) {
                    ArrayList<String> lore = new ArrayList<String>();
                    String status = "";
                    if (u.getCobblestone()) {
                        status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.enable"));
                    } else {
                        status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.disable"));
                    }
                    for (String msg : FileManager.getDrops().getStringList("drop.gui.buttons.cobblestone.lore")) {
                        lore.add(ChatUtil.color(msg.replace("{STATUS}", status)));
                    }
                    if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.stone.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.stone.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.stone.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.stone.lore"))).toItemStack())) {
                        p.openInventory(DropInventory.getDropInv(p, false));
                    } else if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.cobblestone.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.cobblestone.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.cobblestone.name"))).setLore(lore).toItemStack())) {
                        u.setCobblestone(!u.getCobblestone());
                        p.openInventory(DropInventory.getBasicInv(p, true));
                    } else if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.case.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.case.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.case.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.case.lore"))).toItemStack())) {
                        p.openInventory(DropInventory.getCaseInv(p, false));
                    }
                    e.setCancelled(true);
                } else if (inv.equals(DropInventory.getCaseInv(p, false))) {
                    if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.back.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.back.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.back.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.back.lore"))).toItemStack())) {
                        p.openInventory(DropInventory.getBasicInv(p, false));
                    }
                    e.setCancelled(true);
                } else if (inv.equals(DropInventory.getDropInv(p, false))) {
                    for (Drop drops : Drop.drops) {
                        String status = "";
                        if (u.getDrops().get(drops) != null && u.getDrops().get(drops)) {
                            status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.enable"));
                        } else {
                            status = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.disable"));
                        }
                        String turbodrop = "";
                        if (drops.getTurbodrop()) {
                            turbodrop = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.yesvalue"));
                        } else {
                            turbodrop = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.novalue"));
                        }
                        String fortune = "";
                        if (drops.getTurbodrop()) {
                            fortune = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.yesvalue"));
                        } else {
                            fortune = ChatUtil.color(FileManager.getDrops().getString("drop.gui.status.novalue"));
                        }
                        String tools = "";
                        if (drops.getUseTools()) {
                            for (Material m : drops.getTools()) {
                                tools += "," + m;
                            }
                            tools = tools.replaceFirst(",", "");
                        } else {
                            tools = FileManager.getDrops().getString("drop.gui.status.anytool");
                        }
                        ArrayList<String> lore = new ArrayList<String>();
                        for (String msg : FileManager.getDrops().getStringList("drop.gui.lore")) {
                            lore.add(ChatUtil.color(msg.replace("{STATUS}", status)
                                    .replace("{CHANCE}", String.valueOf(drops.getChance()))
                                    .replace("{HEIGHT-MIN}", String.valueOf(drops.getMinHeight()))
                                    .replace("{HEIGHT-MAX}", String.valueOf(drops.getMaxHeight()))
                                    .replace("{AMOUNT-MIN}", String.valueOf(drops.getMinAmount())))
                                    .replace("{AMOUNT-MAX}", String.valueOf(drops.getMaxAmount()))
                                    .replace("{TURBODROP}", turbodrop)
                                    .replace("{FORTUNE}", fortune)
                                    .replace("{TOOLS}", tools));
                        }
                        if (item.getType() == drops.getMaterial() && item.getItemMeta().getDisplayName().equals(drops.getName())) {
                            u.setDrops(drops, !u.getDrops().get(drops));
                            p.openInventory(DropInventory.getDropInv(p, true));
                        }
                    }
                    if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.back.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.back.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.back.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.back.lore"))).toItemStack())) {
                        p.openInventory(DropInventory.getBasicInv(p, false));
                    } else if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.allon.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.allon.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.allon.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.allon.lore"))).toItemStack())) {
                        Drop.drops
                                .forEach(drop -> {
                                    u.setDrops(drop, true);
                                });
                        p.openInventory(DropInventory.getDropInv(p, true));
                    } else if (item.equals(new ItemBuilder(Material.matchMaterial(FileManager.getDrops().getString("drop.gui.buttons.alloff.material"))).setData(FileManager.getDrops().getInt("drop.gui.buttons.alloff.data")).setAmount(1).setName(ChatUtil.color(FileManager.getDrops().getString("drop.gui.buttons.alloff.name"))).setLore(ChatUtil.color(FileManager.getDrops().getStringList("drop.gui.buttons.alloff.lore"))).toItemStack())) {
                        Drop.drops
                                .forEach(drop -> {
                                    u.setDrops(drop, false);
                                });
                        p.openInventory(DropInventory.getDropInv(p, true));
                    } else {
                        p.openInventory(DropInventory.getDropInv(p, true));
                    }
                    e.setCancelled(true);
                } else if (inv.equals(TimeInventory.inv)) {
                    if (item.equals(TimeInventory.day)) {
                        w.setTime(1000);
                    } else if (item.equals(TimeInventory.night)) {
                        w.setTime(13000);
                    } else if (item.equals(TimeInventory.custom)) {
                        try {
                            new AnvilGUI(MCKTools.getInst(), p, "Podaj czas", (player, reply) -> {
                                long i = 0L;
                                try {
                                    i = Long.parseLong(reply);
                                } catch (Exception ex) {
                                    return "Podales niepoprawny czas!";
                                }
                                w.setTime(i);
                                return null;
                            });
                        } catch (Exception ex) {
                            logger.error("AnvilGUI ERROR", ex.getCause());
                        }
                    }
                    e.setCancelled(true);
                } else if (inv.equals(WeatherInventory.inv)) {
                    if (item.equals(WeatherInventory.sun)) {
                        w.setStorm(false);
                        w.setThundering(false);
                    } else if (item.equals(WeatherInventory.rain)) {
                        w.setStorm(true);
                        w.setThundering(false);
                    } else if (item.equals(WeatherInventory.storm)) {
                        w.setStorm(true);
                        w.setThundering(true);
                    }
                    e.setCancelled(true);
                } else if (inv.equals(CaseInventory.caseinv.get(p))) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
