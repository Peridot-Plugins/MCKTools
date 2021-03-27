package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarpCmd implements CommandExecutor {

    public static Map<String, BukkitTask> tp = new HashMap<String, BukkitTask>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                ConfigurationSection csk = FileManager.getData().getConfigurationSection("warps");
                if (p.hasPermission("mckt.warp")) {
                    if (args.length >= 1) {
                        if (csk.contains(args[0])) {
                            double x = FileManager.getData().getDouble("warps." + args[0] + ".locx");
                            double y = FileManager.getData().getDouble("warps." + args[0] + ".locy");
                            double z = FileManager.getData().getDouble("warps." + args[0] + ".locz");
                            String wName = FileManager.getData().getString("warps." + args[0] + ".locworld");
                            Location loc = new Location(Bukkit.getWorld(wName), x, y, z);
                            if (args.length >= 2) {
                                if (p.hasPermission("mckt.warp.other")) {
                                    String pName = args[1];
                                    if (Bukkit.getPlayer(pName) != null) {
                                        Player pa = Bukkit.getPlayer(pName);
                                        pa.teleport(loc);
                                        pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.warp").replace("{WARP}", args[0])));
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.warpother").replace("{WARP}", args[0]).replace("{PLAYER}", pa.getName())));
                                    } else {
                                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noplayer")).replace("{PLAYER}", pName));
                                    }
                                } else {
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.warp.other"));
                                }
                            } else {
                                if (!p.hasPermission("mckt.teleportation.bypass")) {
                                    final Player fp = p;
                                    if (tp.containsKey(fp.getName())) {
                                        ((BukkitTask) tp.remove(fp.getName())).cancel();
                                    }
                                    fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.tpdelay").replace("{DELAY}", String.valueOf(FileManager.getConfig().getInt("config.teleportation.delay"))).replace("{WARP}", args[0])));
                                    BukkitTask bt = Bukkit.getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                                        public void run() {
                                            fp.teleport(loc);
                                            fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.warp").replace("{WARP}", args[0])));
                                            ((BukkitTask) tp.remove(fp.getName())).cancel();
                                        }
                                    }, FileManager.getConfig().getInt("config.teleportation.delay") * 20);
                                    tp.put(fp.getName(), bt);
                                } else {
                                    p.teleport(loc);
                                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.warp").replace("{WARP}", args[0])));
                                }
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.nowarp").replace("{WARP}", args[0])));
                        }
                    } else {
                        if (csk == null) {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.nowarps")));
                            return true;
                        }
                        Set<String> keys = csk.getKeys(false);
                        String lista = "";
                        for (String ss : keys) {
                            lista += "," + ss;
                        }
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.warp.warplist").replace("{WARPS}", lista.replaceFirst(",", ""))));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.warp"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
