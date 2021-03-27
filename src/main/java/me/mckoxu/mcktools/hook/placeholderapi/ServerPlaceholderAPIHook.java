package me.mckoxu.mcktools.hook.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.mckoxu.mcktools.MCKTools;
import org.bukkit.OfflinePlayer;

public class ServerPlaceholderAPIHook extends PlaceholderExpansion {

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
        return "mcktools-server";
    }

    @Override
    public String getVersion() {
        return MCKTools.getInst().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        identifier = identifier.toLowerCase();
        if (identifier.equals("join-amount")) {
            return String.valueOf(MCKTools.joinAmount);
        }
        return null;
    }
}
