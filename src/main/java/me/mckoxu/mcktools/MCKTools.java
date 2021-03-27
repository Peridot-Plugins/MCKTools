package me.mckoxu.mcktools;

import com.zaxxer.hikari.HikariDataSource;
import me.mckoxu.mcktools.command.*;
import me.mckoxu.mcktools.data.config.Config;
import me.mckoxu.mcktools.data.config.Messages;
import me.mckoxu.mcktools.data.mysql.Table;
import me.mckoxu.mcktools.enums.ErrorType;
import me.mckoxu.mcktools.hook.placeholderapi.PlayerPlaceholderAPIHook;
import me.mckoxu.mcktools.hook.placeholderapi.ServerPlaceholderAPIHook;
import me.mckoxu.mcktools.hook.placeholderapi.TopPlaceholderAPIHook;
import me.mckoxu.mcktools.inventory.AdminInventory;
import me.mckoxu.mcktools.inventory.RecipesInventory;
import me.mckoxu.mcktools.inventory.TimeInventory;
import me.mckoxu.mcktools.inventory.WeatherInventory;
import me.mckoxu.mcktools.listener.*;
import me.mckoxu.mcktools.manager.*;
import me.mckoxu.mcktools.scheduler.AutoMsgScheduler;
import me.mckoxu.mcktools.scheduler.AutoSaveScheduler;
import me.mckoxu.mcktools.scheduler.SpiralScheduler;
import me.mckoxu.mcktools.scheduler.TurboDropScheduler;
import me.mckoxu.mcktools.util.Util;
import me.mckoxu.mcktools.util.VersionUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class MCKTools extends JavaPlugin implements Listener {

    private static MCKTools instance;
    private HikariDataSource hikari;
    public SimpleDateFormat df = new SimpleDateFormat(getConfig().getString("config.format.date"));
    public DecimalFormat decf = new DecimalFormat(getConfig().getString("config.format.decimal"));
    public static Permission perm;
    String versionURL = "https://raw.githubusercontent.com/McKoxu/MCKTools/master/version.txt";
    public static int joinAmount;

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        MCKToolsLogger logger = new MCKToolsLogger();
        instance = this;

        FileManager.checkFiles();
        PluginDescriptionFile d = this.getDescription();
        /*if (getConfig().getBoolean("config.update.check")) {
            if (!d.getVersion().equalsIgnoreCase(VersionUtil.getLatestVersion(versionURL))) {
                logger.warning("------------------------------------------------------------");
                logger.warning("Wersja Pluginu: " + this.getDescription().getVersion());
                logger.warning("Najnowsza Wersja: " + VersionUtil.getLatestVersion(versionURL));
                logger.warning("------------------------------------------------------------");
            }
        }*/
        if (!d.getAuthors().get(0).equals("Peridot") || !d.getName().equals("MCKTools")) {
            logger.error("------------------------------------------------------------");
            logger.error("Zostala zmieniona nazwa pluginu lub autor!");
            logger.error("Poprawnie: MCKTools, Peridot");
            logger.error("Aktualnie: " + d.getName() + ", " + d.getAuthors().get(0));
            logger.error("------------------------------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        try {
            hikari = new HikariDataSource();
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", FileManager.getConfig().getString("config.mysql.hostname"));
            hikari.addDataSourceProperty("port", FileManager.getConfig().getInt("config.mysql.port"));
            hikari.addDataSourceProperty("databaseName", FileManager.getConfig().getString("config.mysql.database"));
            hikari.addDataSourceProperty("user", FileManager.getConfig().getString("config.mysql.user"));
            hikari.addDataSourceProperty("password", FileManager.getConfig().getString("config.mysql.password"));
        } catch (Exception ex) {
            logger.error(ErrorType.MYSQL_CANTCONNECT.getMessage(), ex.getCause());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Table.createUserTable();
        Table.createBanTable();
        Table.createBanIPTable();
        if (VersionUtil.getVersionNumber() <= 8) {
            if (!VersionUtil.getVersion().equalsIgnoreCase("v1_8_R3")) {
                logger.error("--------------------------------------------------------");
                logger.error("Posiadasz za stara wersje 1.8.x");
                logger.error("Jezeli chcesz uzywac wersji 1.8");
                logger.error("uzyj wersji 1.8.8!");
                logger.error("--------------------------------------------------------");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        }
        if (VersionUtil.getVersionNumber() >= 13) {
            logger.error("--------------------------------------------------------");
            logger.error("MCKTools nie wspiera wersji 1.13");
            logger.error("Musisz niestety cofnac sie do wersji 1.12");
            logger.error("--------------------------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        StringBuilder sB = new StringBuilder();
        boolean shouldStart = true;
        if (!checkPerm()) {
            sB.append("Vault");
            shouldStart = false;
        }
        if (Bukkit.getPluginManager().getPlugin("FunnyGuilds") == null) {
            if (!shouldStart) {
                sB.append(" i FunnyGuilds");
            } else {
                shouldStart = false;
                sB.append("FunnyGuilds");
            }
        }
        if (!shouldStart) {
            logger.error("--------------------------------------------------------");
            logger.error("Brakuje pluginu: " + sB.toString());
            logger.error("--------------------------------------------------------");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        joinAmount = FileManager.getData().getInt("stats.join");
        Config.loadConfig();
        Messages.loadMessages();
        RecipeManager.loadRecipes();
        CaseManager.loadChests();
        BanManager.loadBans();
        DropManager.loadDrop();
        KitManager.loadKits();
        new RecipesInventory();
        new WeatherInventory();
        new TimeInventory();
        new AdminInventory();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerPingListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new PrepareItemCraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchListener(), this);
        new MOTDManager(this);
        getCommand("kit").setExecutor(new KitCmd());
        getCommand("broadcast").setExecutor(new BroadcastCmd());
        getCommand("gamemode").setExecutor(new GamemodeCmd());
        getCommand("chat").setExecutor(new ChatCmd());
        getCommand("setspawn").setExecutor(new SetSpawnCmd());
        getCommand("spawn").setExecutor(new SpawnCmd());
        getCommand("stats").setExecutor(new StatsCmd());
        getCommand("nick").setExecutor(new NickCmd());
        getCommand("setname").setExecutor(new SetNameCmd());
        getCommand("setlore").setExecutor(new SetLoreCmd());
        getCommand("addlore").setExecutor(new AddLoreCmd());
        getCommand("enchant").setExecutor(new EnchantCmd());
        getCommand("enderchest").setExecutor(new EnderchestCmd());
        getCommand("feed").setExecutor(new FeedCmd());
        getCommand("heal").setExecutor(new HealCmd());
        getCommand("fly").setExecutor(new FlyCmd());
        getCommand("speed").setExecutor(new SpeedCmd());
        getCommand("setwarp").setExecutor(new SetWarpCmd());
        getCommand("warp").setExecutor(new WarpCmd());
        getCommand("delwarp").setExecutor(new DelwarpCmd());
        getCommand("msg").setExecutor(new MsgCmd());
        getCommand("reply").setExecutor(new ReplyCmd());
        getCommand("socialspy").setExecutor(new SocialSpyCmd());
        getCommand("commandspy").setExecutor(new CommandSpyCmd());
        getCommand("helpop").setExecutor(new HelpopCmd());
        getCommand("tpa").setExecutor(new TpaCmd());
        getCommand("tpahere").setExecutor(new TpahereCmd());
        getCommand("tpaccept").setExecutor(new TpacceptCmd());
        getCommand("tpadeny").setExecutor(new TpadenyCmd());
        getCommand("kick").setExecutor(new KickCmd());
        getCommand("kickall").setExecutor(new KickAllCmd());
        getCommand("vanish").setExecutor(new VanishCmd());
        getCommand("time").setExecutor(new TimeCmd());
        getCommand("weather").setExecutor(new WeatherCmd());
        getCommand("mute").setExecutor(new MuteCmd());
        getCommand("unmute").setExecutor(new UnMuteCmd());
        getCommand("list").setExecutor(new ListCmd());
        getCommand("whois").setExecutor(new WhoisCmd());
        getCommand("pomoc").setExecutor(new HelpCmd());
        getCommand("workbench").setExecutor(new WorkbenchCmd());
        getCommand("administracja").setExecutor(new AdminCmd());
        getCommand("whitelist").setExecutor(new WhitelistCmd());
        getCommand("op").setExecutor(new OpCmd());
        getCommand("deop").setExecutor(new DeopCmd());
        getCommand("itemshop").setExecutor(new ItemShopCmd());
        getCommand("recipes").setExecutor(new RecipesCmd());
        getCommand("drop").setExecutor(new DropCMD());
        getCommand("turbodrop").setExecutor(new TurboDropCmd());
        getCommand("teleport").setExecutor(new TpCmd());
        //getCommand("give").setExecutor(new GiveCmd());
        //getCommand("item").setExecutor(new ItemCmd());
        getCommand("ban").setExecutor(new BanCmd());
        getCommand("unban").setExecutor(new UnBanCmd());
        getCommand("god").setExecutor(new GodCmd());
        getCommand("case").setExecutor(new CaseCmd());
        getCommand("trash").setExecutor(new TrashCmd());
        AutoMsgScheduler.startTimer();
        AutoSaveScheduler.startTimer();
        TurboDropScheduler.startTimer();
        SpiralScheduler.startTimer();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayerPlaceholderAPIHook().register();
            new ServerPlaceholderAPIHook().register();
            new TopPlaceholderAPIHook().register();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onDisable() {
        Util.saveData();
        for (UUID id : VanishCmd.hidden) {
            if (Bukkit.getPlayer(id) != null) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.showPlayer(Bukkit.getPlayer(id));
                }
            }
        }
        if (hikari != null) {
            hikari.close();
        }
    }

    public static MCKTools getInst() {
        return instance;
    }

    public HikariDataSource getHikari() {
        return hikari;
    }

    private boolean checkPerm() {
        RegisteredServiceProvider<Permission> ecop = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (ecop == null) {
            return false;
        }
        perm = ecop.getProvider();
        if (perm == null) {
            return false;
        }
        return true;
    }

}
