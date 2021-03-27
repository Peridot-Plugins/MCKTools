package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.command.ChatCmd;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class AsyncPlayerChatListener implements Listener {

    private HashMap<Player, Integer> cooldownTime = new HashMap<Player, Integer>();
    private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<Player, BukkitRunnable>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        User u = UserManager.createUser(p.getUniqueId());
        String group = MCKTools.perm.getPrimaryGroup(p);
        String format = "";
        if (FileManager.getChat().getConfigurationSection("chatformat.groups") != null && FileManager.getChat().getConfigurationSection("chatformat.groups").contains(group)) {
            format = FileManager.getChat().getString("chatformat.groups." + group + ".format");
        } else {
            format = FileManager.getChat().getString("chatformat.default_format");
        }
        String msg = e.getMessage();
        if (!p.hasPermission("mckt.censorebypass")) {
            msg = ChatUtil.censore(msg, FileManager.getChat().getStringList("censore.words"), FileManager.getChat().getString("censore.symbol"));
        }
        if (p.hasPermission("mckt.chatformat.color")) {
            msg = ChatUtil.color(msg);
        }
        e.setFormat(ChatUtil.color(VariablesUtil.returner(p, format)).replace("{MSG}", msg));
        if (!e.isCancelled()) {
            if (u.getMute() <= -1) {
                e.setCancelled(true);
                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.mute.playermute")).replace("{TIME}", FileManager.getMsg().getString("messages.punishments.never")));
                return;
            } else if (u.getMute() > System.currentTimeMillis()) {
                e.setCancelled(true);
                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.punishments.mute.playermute")).replace("{TIME}", MCKTools.getInst().df.format(u.getMute())));
                return;
            }
            if (!ChatCmd.schat) {
                if (!p.hasPermission("mckt.chatbypass")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatUtil.color(FileManager.getChat().getString("messages.chatblock")));
                    return;
                }
            }
            if (!p.hasPermission("mckt.noslowmode")) {
                if (!cooldownTime.containsKey(p)) {
                    cooldownTime.put(p, FileManager.getChat().getInt("slowmode.time"));
                    cooldownTask.put(p, new BukkitRunnable() {
                        public void run() {
                            cooldownTime.put(p, cooldownTime.get(p) - 1);
                            if (cooldownTime.get(p) == 0) {
                                cooldownTime.remove(p);
                                cooldownTask.remove(p);
                                cancel();
                            }
                        }
                    });
                    cooldownTask.get(p).runTaskTimer(MCKTools.getInst(), 20, 20);
                    return;
                } else {
                    p.sendMessage(ChatUtil.color(FileManager.getChat().getString("slowmode.msg").replace("{TIME}", String.valueOf(cooldownTime.get(p)))));
                    e.setCancelled(true);
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }
}
