package me.mckoxu.mcktools.command;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class TpacceptCmd implements CommandExecutor {

    public static Map<String, BukkitTask> tp = new HashMap<String, BukkitTask>();
    public static Map<String, BukkitTask> tph = new HashMap<String, BukkitTask>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpaccept")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                User u = UserManager.createUser(p.getUniqueId());
                if (p.hasPermission("mckt.tpaccept")) {
                    if (u.getTpaPlayer() != null) {
                        Player pa = u.getTpaPlayer();
                        if (!pa.isOnline()) {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.noplayer")));
                            u.setTpaPlayer(null);
                            return true;
                        }
                        Boolean typev = u.getTpaType();
                        if (typev == true) {
                            pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.msgyou").replace("{PLAYER}", p.getName())));
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.msgarg").replace("{PLAYER}", pa.getName())));
                            if (!p.hasPermission("mckt.teleportation.bypass")) {
                                final Player fp = pa;
                                final Player afp = p;
                                if (tp.containsKey(fp.getName())) {
                                    ((BukkitTask) tp.remove(fp.getName())).cancel();
                                }
                                fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.tpdelay").replace("{DELAY}", String.valueOf(FileManager.getConfig().getInt("config.teleportation.delay"))).replace("{PLAYER}", p.getName())));
                                BukkitTask bt = Bukkit.getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                                    public void run() {
                                        if (fp.isOnline() && afp.isOnline()) {
                                            fp.teleport(afp.getLocation());
                                            fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.teleportation").replace("{PLAYER}", afp.getName())));
                                            ((BukkitTask) tp.remove(fp.getName())).cancel();
                                        }
                                        u.setTpaPlayer(null);
                                    }
                                }, FileManager.getConfig().getInt("config.teleportation.delay") * 20);
                                tp.put(fp.getName(), bt);
                            } else {
                                pa.teleport(p.getLocation());
                                pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.teleportation").replace("{PLAYER}", p.getName())));
                            }
                        } else if (typev == false) {
                            pa.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.msgyou").replace("{PLAYER}", p.getName())));
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.msgarg").replace("{PLAYER}", pa.getName())));
                            if (!p.hasPermission("mckt.teleportation.bypass")) {
                                final Player fp = p;
                                final Player afp = pa;
                                if (tph.containsKey(afp.getName())) {
                                    ((BukkitTask) tph.remove(fp.getName())).cancel();
                                }
                                fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.tpdelay").replace("{DELAY}", String.valueOf(FileManager.getConfig().getInt("config.teleportation.delay"))).replace("{PLAUER}", pa.getName())));
                                BukkitTask bth = Bukkit.getScheduler().runTaskLater(MCKTools.getInst(), new Runnable() {
                                    public void run() {
                                        if (fp.isOnline() && afp.isOnline()) {
                                            fp.teleport(afp.getLocation());
                                            fp.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.teleportation").replace("{PLAYER}", afp.getName())));
                                            ((BukkitTask) tph.remove(fp.getName())).cancel();
                                        }
                                        u.setTpaPlayer(null);
                                    }
                                }, FileManager.getConfig().getInt("config.teleportation.delay") * 20);
                                tph.put(fp.getName(), bth);
                            } else {
                                p.teleport(pa.getLocation());
                                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.teleportation").replace("{PLAYER}", pa.getName())));
                            }
                        } else {
                            p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.noplayer")));
                            u.setTpaPlayer(null);
                        }
                    } else {
                        p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.tpaccept.noplayer")));
                    }
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.noperm")).replace("{PERM}", "mckt.tpaccept"));
                }
            } else {
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }

}
