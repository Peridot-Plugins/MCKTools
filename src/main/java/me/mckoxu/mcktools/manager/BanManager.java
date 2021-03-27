package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.BanType;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.object.Ban;
import me.mckoxu.mcktools.object.BanIP;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BanManager {

    private static final String INSERTBAN = "INSERT INTO mcktools_bans VALUES (NULL,?,?,?,?,?)";
    private static final String INSERTBANIP = "INSERT INTO mcktools_bansip VALUES (NULL,?,?,?,?,?)";
    private static final String SAVEBAN = "UPDATE mcktools_bans SET"
            + "`admin`=?,"
            + "`reason`=?,"
            + "`time`=?,"
            + "`type`=? WHERE uuid=?";
    private static final String SAVEBANIP = "UPDATE mcktools_bansip SET"
            + "`admin`=?,"
            + "`reason`=?,"
            + "`time`=?,"
            + "`type`=? WHERE ip=?";
    private static final String SELECTBAN = "SELECT * FROM mcktools_bans WHERE uuid=?";
    private static final String SELECTBANIP = "SELECT * FROM mcktools_bansip WHERE ip=?";
    private static final String SELECTALLBANS = "SELECT * FROM mcktools_bans";
    private static final String SELECTALLBANSIP = "SELECT * FROM mcktools_bansip";
    private static final String DELETEBAN = "DELETE FROM mcktools_bans WHERE uuid=?";
    private static final String DELETEBANIP = "DELETE FROM mcktools_bansip WHERE ip=?";

    public static void save() {
        for (Ban ban : Ban.bans) {
            try (Connection connection = MCKTools.getInst().getHikari().getConnection();
                 PreparedStatement select = connection.prepareStatement(SELECTBAN);
                 PreparedStatement insert = connection.prepareStatement(INSERTBAN);
                 PreparedStatement save = connection.prepareStatement(SAVEBAN)) {
                select.setString(1, ban.getUuid().toString());
                ResultSet result = select.executeQuery();
                if (!result.last()) {
                    insert.setString(1, ban.getUuid().toString());
                    insert.setString(2, ban.getAdmin());
                    insert.setString(3, ban.getReason());
                    insert.setLong(4, ban.getTime());
                    insert.setString(5, String.valueOf(ban.getType()));
                    insert.execute();
                } else {
                    save.setString(1, ban.getAdmin());
                    save.setString(2, ban.getReason());
                    save.setLong(3, ban.getTime());
                    save.setString(4, String.valueOf(ban.getType()));
                    save.setString(5, ban.getUuid().toString());
                    save.execute();
                }
                result.close();
            } catch (SQLException ex) {
                MCKToolsLogger logger = new MCKToolsLogger();
                logger.error(ErrorType.MYSQL_CANTSAVE.getMessage(), ex.getCause());
                Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
            }
        }
        for (BanIP ban : BanIP.bans) {
            try (Connection connection = MCKTools.getInst().getHikari().getConnection();
                 PreparedStatement select = connection.prepareStatement(SELECTBANIP);
                 PreparedStatement insert = connection.prepareStatement(INSERTBANIP);
                 PreparedStatement save = connection.prepareStatement(SAVEBANIP)) {
                select.setString(1, ban.getIp());
                ResultSet result = select.executeQuery();
                if (!result.last()) {
                    insert.setString(1, ban.getIp());
                    insert.setString(2, ban.getAdmin());
                    insert.setString(3, ban.getReason());
                    insert.setLong(4, ban.getTime());
                    insert.setString(5, String.valueOf(ban.getType()));
                    insert.execute();
                } else {
                    save.setString(1, ban.getAdmin());
                    save.setString(2, ban.getReason());
                    save.setLong(3, ban.getTime());
                    save.setString(4, String.valueOf(ban.getType()));
                    save.setString(5, ban.getIp());
                    save.execute();
                }
                result.close();
            } catch (SQLException ex) {
                MCKToolsLogger logger = new MCKToolsLogger();
                logger.error(ErrorType.MYSQL_CANTSAVE.getMessage(), ex.getCause());
                Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
            }
        }
    }

    public static void saveForUuid(UUID uuid, Ban ban) {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement(SELECTBAN);
             PreparedStatement insert = connection.prepareStatement(INSERTBAN);
             PreparedStatement save = connection.prepareStatement(SAVEBAN)) {
            select.setString(1, ban.getUuid().toString());
            ResultSet result = select.executeQuery();
            if (!result.last()) {
                insert.setString(1, uuid.toString());
                insert.setString(2, ban.getAdmin());
                insert.setString(3, ban.getReason());
                insert.setLong(4, ban.getTime());
                insert.setString(5, String.valueOf(ban.getType()));
                insert.execute();
            } else {
                save.setString(1, ban.getAdmin());
                save.setString(2, ban.getReason());
                save.setLong(3, ban.getTime());
                save.setString(4, String.valueOf(ban.getType()));
                save.setString(5, uuid.toString());
                save.execute();
            }
            result.close();
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTSAVE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static void saveForIp(String ip, BanIP ban) {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement(SELECTBANIP);
             PreparedStatement insert = connection.prepareStatement(INSERTBANIP);
             PreparedStatement save = connection.prepareStatement(SAVEBANIP)) {
            select.setString(1, ip);
            ResultSet result = select.executeQuery();
            if (!result.last()) {
                insert.setString(1, ip);
                insert.setString(2, ban.getAdmin());
                insert.setString(3, ban.getReason());
                insert.setLong(4, ban.getTime());
                insert.setString(5, String.valueOf(ban.getType()));
                insert.execute();
            } else {
                save.setString(1, ban.getAdmin());
                save.setString(2, ban.getReason());
                save.setLong(3, ban.getTime());
                save.setString(4, String.valueOf(ban.getType()));
                save.setString(5, ip);
                save.execute();
            }
            result.close();
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTSAVE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static void loadBans() {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement selectban = connection.prepareStatement(SELECTALLBANS);
             PreparedStatement selectbanip = connection.prepareStatement(SELECTALLBANSIP)) {
            ResultSet rsban = selectban.executeQuery();
            ResultSet rsbanip = selectbanip.executeQuery();
            while (rsban.next()) {
                Ban ban = new Ban(UUID.fromString(rsban.getString("uuid")));
                ban.setAdmin(rsban.getString("admin"));
                ban.setReason(rsban.getString("reason"));
                ban.setTime(rsban.getLong("time"));
                ban.setType(BanType.valueOf(rsban.getString("type")));
            }
            while (rsbanip.next()) {
                BanIP ban = new BanIP(rsbanip.getString("ip"));
                ban.setAdmin(rsbanip.getString("admin"));
                ban.setReason(rsbanip.getString("reason"));
                ban.setTime(rsbanip.getLong("time"));
                ban.setType(BanType.valueOf(rsbanip.getString("type")));
            }
            rsbanip.close();
            rsban.close();
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTGET.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static void remove(UUID uuid) {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement(SELECTBAN);
             PreparedStatement delete = connection.prepareStatement(DELETEBAN)) {
            select.setString(1, uuid.toString());
            delete.setString(1, uuid.toString());
            ResultSet result = select.executeQuery();
            if (result.last()) {
                delete.execute();
                delete.close();
            }
            result.close();
            Ban.bans.remove(Ban.get(uuid));
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTREMOVE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static void remove(String ip) {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement(SELECTBANIP);
             PreparedStatement delete = connection.prepareStatement(DELETEBANIP)) {
            select.setString(1, ip);
            delete.setString(1, ip);
            ResultSet result = select.executeQuery();
            if (result.last()) {
                delete.execute();
                delete.close();
            }
            result.close();
            BanIP.bans.remove(BanIP.get(ip));
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTREMOVE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    public static boolean uuidBanned(UUID uuid) {
        for (Ban ban : Ban.bans) {
            if (ban.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static boolean ipBanned(String ip) {
        for (BanIP ban : BanIP.bans) {
            if (ban.getIp().equals(ip)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIp(String ip) {
        String[] str = ip.split("\\.", 4);
        String ip1;
        String ip2;
        String ip3;
        String ip4;
        try {
            ip1 = str[0];
            ip2 = str[1];
            ip3 = str[2];
            ip4 = str[3];
        } catch (Exception ex) {
            ip1 = "0";
            ip2 = "0";
            ip3 = "0";
            ip4 = "0";
        }
        String regex = "\\d+";
        if (ip1.length() <= 3 && ip1.matches(regex) && ip2.length() <= 3 && ip2.matches(regex) && ip3.length() <= 3 && ip3.matches(regex) && ip4.length() <= 3 && ip4.matches(regex) && ip.contains(".")) {
            return true;
        }
        return false;
    }
}
