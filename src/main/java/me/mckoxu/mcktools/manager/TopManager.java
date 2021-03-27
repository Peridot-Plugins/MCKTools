package me.mckoxu.mcktools.manager;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.enums.TopType;
import me.mckoxu.mcktools.object.Top;
import me.mckoxu.mcktools.util.UniversalObjectSorter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TopManager {

    public static Map<TopType, List<Top>> list = new HashMap<>();
    private static String SELECT = "SELECT * FROM mcktools_users ORDER BY ?";

    public static void loadTop() {
        Arrays.stream(TopType.values()).forEach(topType -> {
            List<Top> tops = new ArrayList<>();
            String typeString = String.valueOf(topType).toLowerCase().replace("_", "");
            SELECT = SELECT.replace("?", typeString);
            try (Connection connection = MCKTools.getInst().getHikari().getConnection();
                 PreparedStatement select = connection.prepareStatement(SELECT)) {
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    tops.add(new Top(Bukkit.getOfflinePlayer(result.getString("name")), topType, result.getInt(typeString)));
                }
                result.close();
            } catch (SQLException ex) {
                MCKToolsLogger logger = new MCKToolsLogger();
                logger.error(ErrorType.MYSQL_CANTGET.getMessage(), ex.getCause());
                Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
            }
            List<Top> sorted = (List<Top>) UniversalObjectSorter.sortCollection(tops, true, "getValue");
            list.put(topType, sorted);
        });
    }
}
