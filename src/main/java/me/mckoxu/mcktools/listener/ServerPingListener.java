package me.mckoxu.mcktools.listener;

import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.event.ServerPingEvent;
import me.mckoxu.mcktools.util.ChatUtil;
import me.mckoxu.mcktools.util.VariablesUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ServerPingListener implements Listener {
    @EventHandler
    public void onPing(ServerPingEvent e) {
        e.setMotdLine1(VariablesUtil.serverreturner(ChatUtil.color(MCKTools.getInst().getConfig().getString("config.motd.motd.line1"))));
        e.setMotdLine2(VariablesUtil.serverreturner(ChatUtil.color(MCKTools.getInst().getConfig().getString("config.motd.motd.line2"))));
        List<String> list = new ArrayList<>();
        for (String s : MCKTools.getInst().getConfig().getStringList("config.motd.hovertext")) {
            list.add(VariablesUtil.serverreturner(ChatUtil.color(s)));
        }
        e.setVersion(VariablesUtil.serverreturner(ChatUtil.color(MCKTools.getInst().getConfig().getString("config.motd.versionname"))));
        e.setHover(list);
    }
}
