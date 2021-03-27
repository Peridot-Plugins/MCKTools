package me.mckoxu.mcktools.listener;

import com.mojang.authlib.GameProfile;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.BanType;
import me.mckoxu.mcktools.manager.BanManager;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.Ban;
import me.mckoxu.mcktools.object.BanIP;
import me.mckoxu.mcktools.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import java.lang.reflect.Field;
import java.util.UUID;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        try {
            if (BanManager.uuidBanned(p.getUniqueId())) {
                Ban ban = Ban.get(p.getUniqueId());
                String time = "";
                boolean kick = false;
                if (ban.getType() == BanType.PERM_BAN) {
                    time = FileManager.getMsg().getString("messages.punishments.never");
                    kick = true;
                } else if (ban.getType() == BanType.TEMP_BAN) {
                    if (ban.getTime() <= System.currentTimeMillis()) {
                        BanManager.remove(p.getUniqueId());
                        kick = false;
                    } else {
                        time = MCKTools.getInst().df.format(ban.getTime());
                        kick = true;
                    }
                }
                if (kick) {
                    e.disallow(Result.KICK_BANNED, ChatUtil.color(FileManager.getConfig().getString("config.punishments.ban").replace("{ADMIN}", ban.getAdmin()).replace("{REASON}", ban.getReason()).replace("{TIME}", time).replace("||", "\n")));
                    return;
                }
            }
        } catch (Exception ex) {
        }
        try {
            if (BanManager.ipBanned(p.getAddress().getAddress().toString().replace("/", ""))) {
                BanIP ban = BanIP.get(p.getAddress().getAddress().toString().replace("/", ""));
                String time = "";
                boolean kick = false;
                if (ban.getType() == BanType.PERM_BAN) {
                    time = FileManager.getMsg().getString("messages.punishments.never");
                    kick = true;
                } else if (ban.getType() == BanType.TEMP_BAN) {
                    if (ban.getTime() <= System.currentTimeMillis()) {
                        BanManager.remove(p.getAddress().getAddress().toString().replace("/", ""));
                        kick = false;
                    } else {
                        time = MCKTools.getInst().df.format(ban.getTime());
                        kick = true;
                    }
                }
                if (kick) {
                    e.disallow(Result.KICK_BANNED, ChatUtil.color(FileManager.getConfig().getString("config.punishments.banip").replace("{ADMIN}", ban.getAdmin()).replace("{REASON}", ban.getReason()).replace("{TIME}", time).replace("||", "\n")));
                    return;
                }
            }
        } catch (Exception ex) {
        }
        if (e.getResult().equals(Result.KICK_WHITELIST)) {
            e.setKickMessage(ChatUtil.color(FileManager.getData().getString("whitelist.message").replace("||", "\n")));
        }
        if (p.equals(Result.KICK_FULL)) {
            if (p.hasPermission("mckt.joinfull")) {
                e.allow();
            } else {
                e.setKickMessage(ChatUtil.color(FileManager.getConfig().getString("config.motd.serverfull").replace("||", "\n")));
            }
        }
    }
}
