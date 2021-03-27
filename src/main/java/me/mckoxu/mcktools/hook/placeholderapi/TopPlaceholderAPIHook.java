package me.mckoxu.mcktools.hook.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.data.config.Messages;
import me.mckoxu.mcktools.enums.TopType;
import me.mckoxu.mcktools.manager.TopManager;
import me.mckoxu.mcktools.object.Top;
import org.bukkit.OfflinePlayer;

public class TopPlaceholderAPIHook extends PlaceholderExpansion {

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
        return "mcktools-top";
    }

    @Override
    public String getVersion() {
        return MCKTools.getInst().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        identifier = identifier.toLowerCase();
        int t = identifier.indexOf(":");
        if (t == -1) {
            return Messages.topNull;
        }
        int position = 1;
        String type = identifier.substring(0, t);
        try {
            position = Integer.valueOf(identifier.substring(t + 1));
        } catch (Exception ignored) {
        }
        if (position < 1) {
            position = 1;
        }
        if (type.equals("level")) {
            TopType tt = TopType.LEVEL;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("mined-blocks")) {
            TopType tt = TopType.MINED_BLOCKS;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("mined-stone")) {
            TopType tt = TopType.MINED_STONE;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("mined-obsidian")) {
            TopType tt = TopType.MINED_OBSIDIAN;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("placed-blocks")) {
            TopType tt = TopType.PLACED_BLOCKS;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("placed-obsidian")) {
            TopType tt = TopType.PLACED_OBSIDIAN;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("kills")) {
            TopType tt = TopType.KILLS;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("deaths")) {
            TopType tt = TopType.DEATHS;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("join-amount")) {
            TopType tt = TopType.JOIN_AMOUNT;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("eaten-items")) {
            TopType tt = TopType.EATEN_ITEMS;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("eaten-gold-apples")) {
            TopType tt = TopType.EATEN_GOLD_APPLES;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("eaten-enchanted-gold-apples")) {
            TopType tt = TopType.EATEN_ENCHANTED_GOLD_APPLES;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        if (type.equals("throwed-enderpearls")) {
            TopType tt = TopType.THROWED_ENDERPEARLS;
            if (position <= TopManager.list.get(tt).size()) {
                Top top = TopManager.list.get(tt).get(position - 1);
                return Messages.topFormat
                        .replace("{PLAYER}", top.getPlayer().getName())
                        .replace("{VALUE}", String.valueOf(top.getValue()));
            } else {
                return Messages.topNull;
            }
        }
        return Messages.topNull;
    }
}
