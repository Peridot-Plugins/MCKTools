package me.mckoxu.mcktools.object;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class User {

    private final Player player;
    private final String name;
    private final UUID uuid;
    private String displayname;
    private boolean cobblestone;
    private boolean sidebar;
    private boolean socialspy;
    private boolean commandspy;
    private boolean vanish;
    private boolean god;
    private Player msgplayer;
    private Player tpaplayer;
    private boolean tpatype;
    private TurboDrop turbodrop;
    private HashMap<Drop, Boolean> drops = new HashMap<Drop, Boolean>();
    private int lvl;
    private int exp;
    private int joinamount;
    private int deaths;
    private int kills;
    private int minedblocks;
    private int minedstone;
    private int minedobsidian;
    private int placedblocks;
    private int placedobsidian;
    private int eatenitems;
    private int eatengoldapples;
    private int eatenenchantedgoldapples;
    private int throwedenderpearls;
    private Long mute;

    public User(final Player player) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        for (Drop drops : Drop.drops) {
            setDrops(drops, true);
        }
        setCobblestone(true);
        setSidebar(true);
        setSocialspy(false);
        setCommandspy(false);
        setVanish(false);
    }

    public String getName() {
        return this.name;
    }


    public String getDisplayName() {
        return this.displayname;
    }

    public void setDisplayName(String displayname) {
        this.displayname = displayname;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HashMap<Drop, Boolean> getDrops() {
        return this.drops;
    }

    public void setDrops(Drop drop, boolean status) {
        this.drops.put(drop, status);
    }

    public boolean getCobblestone() {
        return this.cobblestone;
    }

    public void setCobblestone(boolean cobblestone) {
        this.cobblestone = cobblestone;
    }

    public boolean getSidebar() {
        return this.sidebar;
    }

    public void setSidebar(boolean sidebar) {
        this.sidebar = sidebar;
    }

    public boolean getSocialspy() {
        return this.socialspy;
    }

    public void setSocialspy(boolean socialspy) {
        this.socialspy = socialspy;
    }

    public boolean getCommandspy() {
        return this.commandspy;
    }

    public void setCommandspy(boolean commandspy) {
        this.commandspy = commandspy;
    }

    public boolean getVanish() {
        return this.vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public boolean getGod() {
        return this.god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public Player getMsgPlayer() {
        return this.msgplayer;
    }

    public void setMsgPlayer(Player msgplayer) {
        this.msgplayer = msgplayer;
    }

    public Player getTpaPlayer() {
        return this.tpaplayer;
    }

    public void setTpaPlayer(Player tpaplayer) {
        this.tpaplayer = tpaplayer;
    }

    public boolean getTpaType() {
        return this.tpatype;
    }

    public void setTpaType(boolean tpatype) {
        this.tpatype = tpatype;
    }

    public TurboDrop getTurboDrop() {
        return this.turbodrop;
    }

    public void setTurboDrop(TurboDrop turbodrop) {
        this.turbodrop = turbodrop;
    }

    public int getLevel() {
        return this.lvl;
    }

    public void setLevel(int lvl) {
        this.lvl = lvl;
    }

    public void addLevel(int lvl) {
        this.lvl = this.lvl + lvl;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void addExp(int exp) {
        this.exp = this.exp + exp;
    }

    public int getJoinAmount() {
        return this.joinamount;
    }

    public void setJoinAmount(int joinamount) {
        this.joinamount = joinamount;
    }

    public void addJoinAmount(int joinamount) {
        this.joinamount = this.joinamount + joinamount;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addDeaths(int deaths) {
        this.deaths = this.deaths + deaths;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKills(int kills) {
        this.kills = this.kills + kills;
    }

    public int getMinedBlocks() {
        return this.minedblocks;
    }

    public void setMinedBlocks(int minedblocks) {
        this.minedblocks = minedblocks;
    }

    public void addMinedBlocks(int minedblocks) {
        this.minedblocks = this.minedblocks + minedblocks;
    }

    public int getMinedStone() {
        return this.minedstone;
    }

    public void setMinedStone(int minedstone) {
        this.minedstone = minedstone;
    }

    public void addMinedStone(int minedstone) {
        this.minedstone = this.minedstone + minedstone;
    }

    public int getMinedObsidian() {
        return this.minedobsidian;
    }

    public void setMinedObsidian(int minedobsidian) {
        this.minedobsidian = minedobsidian;
    }

    public void addMinedObsidian(int minedobsidian) {
        this.minedobsidian = this.minedobsidian + minedobsidian;
    }

    public int getPlacedBlocks() {
        return this.placedblocks;
    }

    public void setPlacedBlocks(int placedblocks) {
        this.placedblocks = placedblocks;
    }

    public void addPlacedBlocks(int placedblocks) {
        this.placedblocks = this.placedblocks + placedblocks;
    }

    public int getPlacedObsidian() {
        return this.placedobsidian;
    }

    public void setPlacedObsidian(int placedobsidian) {
        this.placedobsidian = placedobsidian;
    }

    public void addPlacedObsidian(int placedobsidian) {
        this.placedobsidian = this.placedobsidian + placedobsidian;
    }

    public int getEatenItems() {
        return this.eatenitems;
    }

    public void setEatenItems(int eatenitems) {
        this.eatenitems = eatenitems;
    }

    public void addEatenItems(int eatenitems) {
        this.eatenitems = this.eatenitems + eatenitems;
    }

    public int getEatenGoldApples() {
        return this.eatengoldapples;
    }

    public void setEatenGoldApples(int eatengoldapples) {
        this.eatengoldapples = eatengoldapples;
    }

    public void addEatenGoldApples(int eatengoldapples) {
        this.eatengoldapples = this.eatengoldapples + eatengoldapples;
    }

    public int getEatenEnchantedGoldApples() {
        return this.eatenenchantedgoldapples;
    }

    public void setEatenEnchantedGoldApples(int eatenenchantedgoldapples) {
        this.eatenenchantedgoldapples = eatenenchantedgoldapples;
    }

    public void addEatenEnchantedGoldApples(int eatenenchantedgoldapples) {
        this.eatenenchantedgoldapples = this.eatenenchantedgoldapples + eatenenchantedgoldapples;
    }

    public int getThrowedEnderPearls() {
        return this.throwedenderpearls;
    }

    public void setThrowedEnderPearls(int throwedenderpearls) {
        this.throwedenderpearls = throwedenderpearls;
    }

    public void addThrowedEnderpearls(int throwedenderpearls) {
        this.throwedenderpearls = this.throwedenderpearls + throwedenderpearls;
    }

    public Long getMute() {
        return this.mute;
    }

    public void setMute(Long mute) {
        this.mute = mute;
    }
}
