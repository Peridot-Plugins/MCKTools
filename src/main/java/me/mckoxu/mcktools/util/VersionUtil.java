package me.mckoxu.mcktools.util;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.enums.ErrorType;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionUtil {

    @SuppressWarnings("deprecation")
    public static String getLatestVersion(String link) {
        InputStream in = null;
        try {
            in = new URL(link).openStream();
        } catch (MalformedURLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.UPDATE_CANTCHECKUPDATE.getMessage(), ex.getCause());
        } catch (IOException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.UPDATE_CANTCHECKUPDATE.getMessage(), ex.getCause());
        }

        try {
            return IOUtils.readLines(in).get(0);
        } catch (IOException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.UPDATE_CANTCHECKUPDATE.getMessage(), ex.getCause());
        } finally {
            IOUtils.closeQuietly(in);
        }
        return null;
    }

    public static int getVersionNumber() {
        return Integer.parseInt(getVersion().split("_")[1]);
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
