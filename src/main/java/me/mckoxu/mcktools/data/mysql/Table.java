package me.mckoxu.mcktools.data.mysql;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.ErrorType;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.Statement;

public class Table {
    public static void createUserTable() {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mcktools_users` (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    " `name` text NOT NULL," +
                    " `uuid` binary(36) NOT NULL," +
                    " `displayname` text NOT NULL," +
                    " `kills` int(11) NOT NULL," +
                    " `deaths` int(11) NOT NULL," +
                    " `eatenitems` int(11) NOT NULL," +
                    " `eatengoldapples` int(11) NOT NULL," +
                    " `eatenenchantedgoldapples` int(11) NOT NULL," +
                    " `joinamount` int(11) NOT NULL," +
                    " `minedblocks` int(11) NOT NULL," +
                    " `minedstone` int(11) NOT NULL," +
                    " `minedobsidian` int(11) NOT NULL," +
                    " `placedblocks` int(11) NOT NULL," +
                    " `placedobsidian` int(11) NOT NULL," +
                    " `throwedenderpearls` int(11) NOT NULL," +
                    " `turbodrop` int(11) NOT NULL," +
                    " `level` int(11) NOT NULL," +
                    " `exp` int(11) NOT NULL," +
                    " `mute` bigint(20) NOT NULL," +
                    " PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8");
        } catch (Exception ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTCREATETABLE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static void createBanTable() {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mcktools_bans` (" +
                    " `id` int(11) NOT NULL AUTO_INCREMENT," +
                    " `uuid` binary(36) NOT NULL," +
                    " `admin` text NOT NULL," +
                    " `reason` text NOT NULL," +
                    " `time` bigint(20) NOT NULL," +
                    " `type` text NOT NULL," +
                    " PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8");
        } catch (Exception ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTCREATETABLE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static void createBanIPTable() {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `mcktools_bansip` (" +
                    " `id` int(11) NOT NULL AUTO_INCREMENT," +
                    " `ip` text NOT NULL," +
                    " `admin` text NOT NULL," +
                    " `reason` text NOT NULL," +
                    " `time` bigint(20) NOT NULL," +
                    " `type` text NOT NULL," +
                    " PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8");
        } catch (Exception ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTCREATETABLE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }
}
