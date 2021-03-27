package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.command.SpawnCmd;
import me.mckoxu.mcktools.command.TpacceptCmd;
import me.mckoxu.mcktools.command.WarpCmd;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();
        if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            if (SpawnCmd.tp.containsKey(e.getPlayer().getName())) {
                ((BukkitTask) SpawnCmd.tp.remove(e.getPlayer().getName())).cancel();
                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.move")));
            }
            if (WarpCmd.tp.containsKey(e.getPlayer().getName())) {
                ((BukkitTask) WarpCmd.tp.remove(e.getPlayer().getName())).cancel();
                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.move")));
            }
            if (TpacceptCmd.tp.containsKey(e.getPlayer().getName())) {
                ((BukkitTask) TpacceptCmd.tp.remove(e.getPlayer().getName())).cancel();
                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.move")));
            }
            if (TpacceptCmd.tph.containsKey(e.getPlayer().getName())) {
                ((BukkitTask) TpacceptCmd.tph.remove(e.getPlayer().getName())).cancel();
                p.sendMessage(ChatUtil.color(FileManager.getMsg().getString("messages.globalerrors.move")));
            }
        }
    }

}
