package me.mckoxu.mcktools.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.data.config.Config;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class VariablesUtil {

    public static String returner(Player p, String s) {
        String sr = s;
        String gm;
        String fly;
        String turbodrop;
        String turbodroptime;
        String sidebar;
        String socialspy;
        String commandspy;
        String vanish;
        String god;
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            sr = PlaceholderAPI.setPlaceholders(p, sr);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("FunnyGuilds")) {
            net.dzikoysk.funnyguilds.basic.user.User user = net.dzikoysk.funnyguilds.basic.user.User.get(p);
            if (user.hasGuild()) {
                sr = sr.replace("{G-TAG}", user.getGuild().getTag())
                        .replace("{G-NAME}", user.getGuild().getName())
                        .replace("{G-POINTS}", String.valueOf(user.getGuild().getRank().getPoints()))
                        .replace("{G-POSITION}", String.valueOf(user.getGuild().getRank().getPosition()));
            } else {
                sr = sr.replace("{G-TAG}", FileManager.getMsg().getString("messages.globalerrors.noguild"))
                        .replace("{G-NAME}", FileManager.getMsg().getString("messages.globalerrors.noguild"))
                        .replace("{G-POINTS}", FileManager.getMsg().getString("messages.globalerrors.noguild"))
                        .replace("{G-POSITION}", FileManager.getMsg().getString("messages.globalerrors.noguild"));
            }
            sr = sr.replace("{POINTS}", String.valueOf(user.getRank().getPoints()))
                    .replace("{POSITION}", String.valueOf(user.getRank().getPosition()));
        }
        User u = UserManager.createUser(p.getUniqueId());
        if (u.getTurboDrop() != null) {
            turbodrop = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
            turbodroptime = Util.convertTime(u.getTurboDrop().getTime());
        } else {
            turbodrop = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
            turbodroptime = Util.convertTime(0);
        }
        if (u.getSidebar()) {
            sidebar = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
        } else {
            sidebar = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
        }
        if (u.getSocialspy()) {
            socialspy = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
        } else {
            socialspy = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
        }
        if (u.getCommandspy()) {
            commandspy = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
        } else {
            commandspy = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
        }
        if (u.getVanish()) {
            vanish = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
        } else {
            vanish = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
        }
        if (u.getGod()) {
            god = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
        } else {
            god = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
        }
        if (p.getGameMode().equals(GameMode.SURVIVAL)) {
            gm = ChatUtil.color(FileManager.getMsg().getString("messages.variables.gamemode.survival"));
        } else if (p.getGameMode().equals(GameMode.CREATIVE)) {
            gm = ChatUtil.color(FileManager.getMsg().getString("messages.variables.gamemode.creative"));
        } else if (p.getGameMode().equals(GameMode.ADVENTURE)) {
            gm = ChatUtil.color(FileManager.getMsg().getString("messages.variables.gamemode.adventure"));
        } else if (p.getGameMode().equals(GameMode.SPECTATOR)) {
            gm = ChatUtil.color(FileManager.getMsg().getString("messages.variables.gamemode.spectator"));
        } else {
            gm = "Imposible Gamemode";
        }
        if (p.getAllowFlight()) {
            fly = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.enable"));
        } else {
            fly = ChatUtil.color(FileManager.getMsg().getString("messages.variables.status.disable"));
        }
        sr = sr.replace("{PLAYER}", p.getName())
                .replace("{DISPLAYNAME}", p.getDisplayName())
                .replace("{UUID}", String.valueOf(p.getUniqueId()))
                .replace("{IP}", String.valueOf(p.getAddress()).replace("/", ""))
                .replace("{HEALTH}", String.valueOf(p.getHealth()))
                .replace("{MAXHEALTH}", String.valueOf(p.getHealthScale()))
                .replace("{FOOD}", String.valueOf(p.getFoodLevel()))
                .replace("{WORLD}", p.getWorld().getName())
                .replace("{X}", String.valueOf(p.getLocation().getBlockX()))
                .replace("{Y}", String.valueOf(p.getLocation().getBlockY()))
                .replace("{Z}", String.valueOf(p.getLocation().getBlockZ()))
                .replace("{GAMEMODE}", gm)
                .replace("{FLY}", fly)
                .replace("{JOINAMOUNTPLAYER}", String.valueOf(u.getJoinAmount()))
                .replace("{DEATHS}", String.valueOf(u.getDeaths()))
                .replace("{KILLS}", String.valueOf(u.getKills()))
                .replace("{KDR}", MCKTools.getInst().decf.format(Util.getKDR(u.getKills(), u.getDeaths())))
                .replace("{MINEDBLOCKS}", String.valueOf(u.getMinedBlocks()))
                .replace("{MINEDSTONE}", String.valueOf(u.getMinedStone()))
                .replace("{MINEDOBSIDIAN}", String.valueOf(u.getMinedObsidian()))
                .replace("{PLACEDBLOCKS}", String.valueOf(u.getPlacedBlocks()))
                .replace("{PLACEDOBSIDIAN}", String.valueOf(u.getPlacedObsidian()))
                .replace("{EATENITEMS}", String.valueOf(u.getEatenItems()))
                .replace("{EATENGOLDAPPLES}", String.valueOf(u.getEatenGoldApples()))
                .replace("{EATENENCHANTEDGOLDAPPLES}", String.valueOf(u.getEatenEnchantedGoldApples()))
                .replace("{LVL}", String.valueOf(u.getLevel()))
                .replace("{EXP}", String.valueOf(u.getExp()))
                .replace("{EXPTOLVL}", String.valueOf((Config.expMultiplayer * u.getLevel()) + Config.expMultiplayer))
                .replace("{EXPPERCENT}", MCKTools.getInst().decf.format((u.getExp() * 100D / ((Config.expMultiplayer * u.getLevel()) + Config.expMultiplayer))))
                .replace("{THROWEDENDERPEARLS}", String.valueOf(u.getThrowedEnderPearls()))
                .replace("{TURBODROP}", turbodrop)
                .replace("{TURBODROPTIME}", turbodroptime)
                .replace("{SIDEBAR}", sidebar)
                .replace("{SOCIALSPY}", socialspy)
                .replace("{COMMANDSPY}", commandspy)
                .replace("{VANISH}", vanish)
                .replace("{GOD}", god);
        return sr;
    }

    public static String serverreturner(String s) {
        s = s.replace("{JOINAMOUNTGLOBAL}", String.valueOf(MCKTools.joinAmount))
                .replace("{ONLINE}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("{MAXONLINE}", String.valueOf(Bukkit.getServer().getMaxPlayers()));
        return s;
    }
}
