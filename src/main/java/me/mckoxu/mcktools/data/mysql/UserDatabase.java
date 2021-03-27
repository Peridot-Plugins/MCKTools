package me.mckoxu.mcktools.data.mysql;

import me.mckoxu.mcktools.MCKToolsLogger;
import me.mckoxu.mcktools.MCKTools;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.enums.TurbodropType;
import me.mckoxu.mcktools.manager.FileManager;
import me.mckoxu.mcktools.object.Kit;
import me.mckoxu.mcktools.object.TurboDrop;
import me.mckoxu.mcktools.object.User;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {
    private static final String INSERT = "INSERT INTO mcktools_users VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SAVE = "UPDATE mcktools_users SET"
            + "`displayname`=?,"
            + "`kills`=?,"
            + "`deaths`=?,"
            + "`eatenitems`=?,"
            + "`eatengoldapples`=?,"
            + "`eatenenchantedgoldapples`=?,"
            + "`joinamount`=?,"
            + "`minedblocks`=?,"
            + "`minedstone`=?,"
            + "`minedobsidian`=?,"
            + "`placedblocks`=?,"
            + "`placedobsidian`=?,"
            + "`throwedenderpearls`=?,"
            + "`turbodrop`=?,"
            + "`level`=?,"
            + "`exp`=?,"
            + "`mute`=? WHERE name=?";
    private static final String SELECT = "SELECT * FROM mcktools_users WHERE name=?";

    public static void load(User u) {
        String displayname = "";
        int deaths = 0;
        int kills = 0;
        int eatenitems = 0;
        int eatengoldapples = 0;
        int eatenenchantedgoldapples = 0;
        int join = 0;
        int minedblocks = 0;
        int minedstone = 0;
        int minedobsidian = 0;
        int placedblocks = 0;
        int placedobsidian = 0;
        int throwedenderpearls = 0;
        int turbodrop = 0;
        int level = 0;
        int exp = 0;
        Long mute = 0L;
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement(SELECT)) {
            select.setString(1, u.getName());
            ResultSet result = select.executeQuery();
            if (result.next()) {
                displayname = result.getString("displayname");
                deaths = result.getInt("deaths");
                kills = result.getInt("kills");
                eatenitems = result.getInt("eatenitems");
                eatengoldapples = result.getInt("eatengoldapples");
                eatenenchantedgoldapples = result.getInt("eatenenchantedgoldapples");
                join = result.getInt("joinamount");
                minedblocks = result.getInt("minedblocks");
                minedstone = result.getInt("minedstone");
                minedobsidian = result.getInt("minedobsidian");
                placedblocks = result.getInt("placedblocks");
                placedobsidian = result.getInt("placedobsidian");
                throwedenderpearls = result.getInt("throwedenderpearls");
                turbodrop = result.getInt("turbodrop");
                level = result.getInt("level");
                exp = result.getInt("exp");
                mute = result.getLong("mute");
            }
            result.close();
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTGET.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
        u.setDisplayName(displayname);
        u.setDeaths(deaths);
        u.setKills(kills);
        u.setEatenItems(eatenitems);
        u.setEatenGoldApples(eatengoldapples);
        u.setEatenEnchantedGoldApples(eatenenchantedgoldapples);
        u.setJoinAmount(join);
        u.setMinedBlocks(minedblocks);
        u.setMinedStone(minedstone);
        u.setMinedObsidian(minedobsidian);
        u.setPlacedBlocks(placedblocks);
        u.setPlacedObsidian(placedobsidian);
        u.setThrowedEnderPearls(throwedenderpearls);
        TurboDrop td = new TurboDrop(u, TurbodropType.TURBODROP_PLAYER);
        td.setTime(turbodrop);
        u.setTurboDrop(td);
        u.setLevel(level);
        u.setExp(exp);
        u.setMute(mute);

    }

    public static void create(User u) {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement select = connection.prepareStatement(SELECT);
             PreparedStatement insert = connection.prepareStatement(INSERT)) {
            select.setString(1, u.getName());
            ResultSet result = select.executeQuery();
            if (!result.last()) {
                insert.setString(1, u.getName());
                insert.setString(2, String.valueOf(u.getUuid()));
                insert.setString(3, u.getPlayer().getDisplayName());
                insert.setInt(4, 0);
                insert.setInt(5, 0);
                insert.setInt(6, 0);
                insert.setInt(7, 0);
                insert.setInt(8, 0);
                insert.setInt(9, 0);
                insert.setInt(10, 0);
                insert.setInt(11, 0);
                insert.setInt(12, 0);
                insert.setInt(13, 0);
                insert.setInt(14, 0);
                insert.setInt(15, 0);
                insert.setInt(16, -1);
                insert.setInt(17, 0);
                insert.setInt(18, 0);
                insert.setLong(19, 0L);
                insert.execute();
            }
            result.close();
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTCREATEROW.getMessage(), ex.getCause());
        }
        File f;
        if (FileManager.getPFile(u.getPlayer()) == null) {
            f = new File(FileManager.getUsersFolder(), u.getName() + ".yml");
            try {
                f.createNewFile();
            } catch (IOException ex) {
                MCKToolsLogger logger = new MCKToolsLogger();
                logger.error(ErrorType.FILE_CANTCREATE.getMessage(), ex.getCause());
            }
        } else {
            return;
        }
        YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
        for (Kit k : Kit.kits) {
            if (fYml.getLong("kits." + k.getName()) <= 0) {
                fYml.set("kits." + k.getName(), 0L);
            }
        }
        try {
            fYml.save(f);
        } catch (IOException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.DATA_CANTSAVE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    public static void save(User u) {
        try (Connection connection = MCKTools.getInst().getHikari().getConnection();
             PreparedStatement save = connection.prepareStatement(SAVE)) {
            save.setString(1, u.getPlayer().getDisplayName());
            save.setInt(2, u.getKills());
            save.setInt(3, u.getDeaths());
            save.setInt(4, u.getEatenItems());
            save.setInt(5, u.getEatenGoldApples());
            save.setInt(6, u.getEatenEnchantedGoldApples());
            save.setInt(7, u.getJoinAmount());
            save.setInt(8, u.getMinedBlocks());
            save.setInt(9, u.getMinedStone());
            save.setInt(10, u.getMinedObsidian());
            save.setInt(11, u.getPlacedBlocks());
            save.setInt(12, u.getPlacedObsidian());
            save.setInt(13, u.getThrowedEnderPearls());
            if (u.getTurboDrop() != null && u.getTurboDrop().getType() == TurbodropType.TURBODROP_PLAYER) {
                save.setInt(14, u.getTurboDrop().getTime());
            } else {
                save.setInt(14, -1);
            }
            save.setInt(15, u.getLevel());
            save.setInt(16, u.getExp());
            save.setLong(17, u.getMute());
            save.setString(18, u.getName());
            save.execute();
        } catch (SQLException ex) {
            MCKToolsLogger logger = new MCKToolsLogger();
            logger.error(ErrorType.MYSQL_CANTSAVE.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(MCKTools.getInst());
        }
    }
}
