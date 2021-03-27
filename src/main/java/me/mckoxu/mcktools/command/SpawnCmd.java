package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SpawnCmd implements CommandExecutor {
    public static Map<String, BukkitTask> tp = new HashMap<String, BukkitTask>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                double x = FileManager.getData().getDouble("spawn.locx");
                double y = FileManager.getData().getDouble("spawn.locy");
                double z = FileManager.getData().getDouble("spawn.locz");
                String wName = FileManager.getData().getString("spawn.locworld");
                Location loc = new Location(Bukkit.getWorld(wName), x, y, z);
                if (p.hasPermission("mckt.spawn")) {
                    if (args.length >= 1) {
                        if (p.hasPermission("mckt.spawn.other")) {
                            String pName = args[0];
                            if (Bukkit.getPlayer(pName) != null) {
                                Player pa = Bukkit.getPlayer(pName);
                                pa.teleport(loc);
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.spawn.tpspawn")));
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.spawn.tpspawnother")).replace("{PLAYER}", pa.getName()));
                            } else {
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.spawn.other"));
                        }
                    } else {
                        if (!p.hasPermission("mckt.teleportation.bypass")) {
                            final Player fp = p;
                            if (tp.containsKey(fp.getName())) {
                                ((BukkitTask) tp.remove(fp.getName())).cancel();
                            }
                            fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.spawn.tpdelay").replace("{DELAY}", String.valueOf(FileManager.getConfig().getInt("config.teleportation.delay")))));
                            BukkitTask bt = Bukkit.getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                                public void run() {
                                    fp.teleport(loc);
                                    fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.spawn.tpspawn")));
                                    ((BukkitTask) tp.remove(fp.getName())).cancel();
                                }
                            }, FileManager.getConfig().getInt("config.teleportation.delay") * 20);
                            tp.put(fp.getName(), bt);
                        } else {
                            p.teleport(loc);
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.spawn.tpspawn")));
                        }
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.spawn"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}
