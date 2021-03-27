package me.mckoxu.mcktools.hook.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.data.config.Config;
import me.mckoxu.mcktools.manager.UserManager;
import me.mckoxu.mcktools.object.User;
import me.mckoxu.mcktools.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerPlaceholderAPIHook extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return MCKTools.getInst().getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "mcktools-player";
    }

    @Override
    public String getVersion() {
        return MCKTools.getInst().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        identifier = identifier.toLowerCase();
        if (offlinePlayer.isOnline()) {
            Player player = offlinePlayer.getPlayer();
            User user = UserManager.createUser(player.getUniqueId());
            if (identifier.equals("exp")) {
                return String.valueOf(user.getExp());
            }
            if (identifier.equals("level")) {
                return String.valueOf(user.getLevel());
            }
            if (identifier.equals("exp-to-lvl")) {
                return String.valueOf((Config.expMultiplayer * user.getLevel()) + Config.expMultiplayer);
            }
            if (identifier.equals("exp-percent")) {
                return MCKTools.getInst().decf.format((user.getExp() * 100D / ((Config.expMultiplayer * user.getLevel()) + Config.expMultiplayer)));
            }
            if (identifier.equals("mined-blocks")) {
                return String.valueOf(user.getMinedBlocks());
            }
            if (identifier.equals("mined-stone")) {
                return String.valueOf(user.getMinedStone());
            }
            if (identifier.equals("mined-obsidian")) {
                return String.valueOf(user.getMinedObsidian());
            }
            if (identifier.equals("kills")) {
                return String.valueOf(user.getKills());
            }
            if (identifier.equals("deaths")) {
                return String.valueOf(user.getDeaths());
            }
            if (identifier.equals("placed-blocks")) {
                return String.valueOf(user.getPlacedBlocks());
            }
            if (identifier.equals("placed-obsidian")) {
                return String.valueOf(user.getPlacedObsidian());
            }
            if (identifier.equals("eaten-items")) {
                return String.valueOf(user.getEatenItems());
            }
            if (identifier.equals("eaten-gold-apples")) {
                return String.valueOf(user.getEatenGoldApples());
            }
            if (identifier.equals("eaten-enchanted-gold-apples")) {
                return String.valueOf(user.getEatenEnchantedGoldApples());
            }
            if (identifier.equals("join-amount")) {
                return String.valueOf(user.getJoinAmount());
            }
            if (identifier.equals("throwed-enderpearls")) {
                return String.valueOf(user.getThrowedEnderPearls());
            }
            if (identifier.equals("turbodrop-time")) {
                return String.valueOf(Util.convertTime(user.getTurboDrop().getTime()));
            }
        }
        return null;
    }
}
