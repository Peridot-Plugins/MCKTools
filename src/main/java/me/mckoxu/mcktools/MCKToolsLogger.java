package me.mckoxu.mcktools;

import me.mckoxu.mcktools.util.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MCKToolsLogger {

    private Logger logger = Bukkit.getLogger();

    public void info(String message) {
        logger.info("MCKTools > " + message);
    }

    public void warning(String message) {
        logger.warning("MCKTools > " + message);
    }

    public void error(String message) {
        logger.severe("MCKTools > " + message);
    }

    public void error(String message, Throwable cause) {
        String laodedplugins = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(plugin -> !plugin.getName().equals("MCKTools"))
                .map(plugin -> plugin.getName() + " " + plugin.getDescription().getVersion())
                .collect(Collectors.joining(", "));
        PluginDescriptionFile d = MCKTools.getInst().getDescription();
        error(message);
        error("---------------------------------------------------------");
        error("Plugin version: " + d.getVersion());
        error("Loaded plugins: " + laodedplugins);
        error("Bukkit: " + VersionUtil.getVersion());
        error("Java" + System.getProperty("java.version"));
        error("---------------------------------------------------------");
        cause.printStackTrace();
    }
}
