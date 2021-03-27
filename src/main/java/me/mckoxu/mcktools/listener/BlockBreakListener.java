package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.data.config.Config;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.Drop;
import me.mckoxu.mcktools.object.ItemBuilder;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.RandomUtil;
import me.mckoxu.mcktools.util.Util;
import net.dzikoysk.funnyguilds.element.notification.NotificationUtil;
import net.dzikoysk.funnyguilds.util.nms.PacketSender;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockBreakListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBreak(BlockBreakEvent e) throws IOException {
        Player p = e.getPlayer();
        User u = UserManager.createUser(p.getUniqueId());

        Block b = e.getBlock();
        ItemStack tool = p.getItemInHand();
        Location loc = b.getLocation();
        if (!e.isCancelled()) {
            u.addMinedBlocks(1);
            if (b.getType() == Material.STONE) {
                u.addMinedStone(1);
                u.addExp(RandomUtil.getRandom(Config.minExp, Config.maxExp));
                if (u.getExp() >= (Config.expMultiplayer * u.getLevel()) + Config.expMultiplayer) {
                    u.setExp(0);
                    u.addLevel(1);
                    int exp = (Config.expMultiplayer * u.getLevel()) + Config.expMultiplayer;
                    int lvl = u.getLevel();
                    PacketSender.sendPacket(p, NotificationUtil.createActionbarNotification(ChatUtil.color(Config.actionbarMining).replace("{LVL}", String.valueOf(lvl)).replace("{EXP}", String.valueOf(exp))));
                }
                if (!u.getCobblestone()) {
                    if (b.getDrops().equals(Material.COBBLESTONE)) {
                        e.getBlock().getDrops().clear();
                        e.getBlock().setType(Material.AIR);
                    }
                }
                p.giveExp(RandomUtil.getRandom(FileManager.getDrops().getInt("drop.exp.min"), FileManager.getDrops().getInt("drop.exp.max")));
                for (Drop drop : Drop.drops) {
                    if (u.getDrops().get(drop)) {
                        if (!drop.getUseTools() || drop.getUseTools() && drop.getTools().contains(tool.getType())) {
                            double chance = drop.getChance();
                            if (drop.getFortune()) {
                                int fortune = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                                if (fortune == 1) {
                                    chance = chance + (drop.getChance() * FileManager.getDrops().getDouble("drop.bonuspercent.fortune.fortune1") / 100D);
                                } else if (fortune == 2) {
                                    chance = chance + (drop.getChance() * FileManager.getDrops().getDouble("drop.bonuspercent.fortune.fortune2") / 100D);
                                } else if (fortune >= 3) {
                                    chance = chance + (drop.getChance() * FileManager.getDrops().getDouble("drop.bonuspercent.fortune.fortune3") / 100D);
                                }
                            }
                            if (drop.getTurbodrop()) {
                                if (u.getTurboDrop() != null && u.getTurboDrop().getTime() >= 1) {
                                    chance = chance + (drop.getChance() * FileManager.getDrops().getDouble("drop.bonuspercent.turbodrop") / 100);
                                }
                            }
                            if (chance > 100) {
                                chance = 100D;
                            }
                            if (Util.getChance(chance)) {
                                if (loc.getBlockY() >= drop.getMinHeight() && loc.getBlockY() <= drop.getMaxHeight()) {
                                    ItemStack item = new ItemBuilder(drop.getMaterial())
                                            .setData(drop.getData())
                                            .setAmount(RandomUtil.getRandom(drop.getMinAmount(), drop.getMaxAmount()))
                                            .setName(FileManager.getDrops().getName())
                                            .setLore(drop.getLore())
                                            .setEnchantments(drop.getEnchantment())
                                            .toItemStack();
                                    if (Util.inventoryIsFull(p, item)) {
                                        p.getWorld().dropItem(p.getLocation(), item);
                                    } else {
                                        p.getInventory().addItem(item);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (b.getType() == Material.OBSIDIAN) {
                u.addMinedObsidian(1);
            }
            List<Material> m = new ArrayList<Material>();
            for (String ms : FileManager.getDrops().getStringList("drop.blockedblocks.items")) {
                m.add(Material.matchMaterial(ms));
            }
            if (m.contains(b.getType())) {
                p.sendMessage(ChatUtil.color(FileManager.getDrops().getString("drop.blockedblocks.message").replace("{BLOCK}", String.valueOf(b.getType()))));
                b.setType(Material.AIR);
            }
        } else {
            return;
        }
    }
}
