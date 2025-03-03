package com.hakan.core;

import com.hakan.core.command.HCommandExecutor;
import com.hakan.core.command.functional.HCommand;
import com.hakan.core.hologram.HHologram;
import com.hakan.core.hologram.HHologramHandler;
import com.hakan.core.hooks.Metrics;
import com.hakan.core.item.HItemStack;
import com.hakan.core.item.nbt.HNbtManager;
import com.hakan.core.listener.HListenerAdapter;
import com.hakan.core.message.HMessageHandler;
import com.hakan.core.message.bossbar.HBarColor;
import com.hakan.core.message.bossbar.HBarFlag;
import com.hakan.core.message.bossbar.HBarStyle;
import com.hakan.core.message.bossbar.HBossBar;
import com.hakan.core.message.title.HTitle;
import com.hakan.core.packet.HPacketHandler;
import com.hakan.core.particle.HParticle;
import com.hakan.core.particle.HParticleHandler;
import com.hakan.core.scheduler.HScheduler;
import com.hakan.core.spam.HSpam;
import com.hakan.core.ui.inventory.HInventory;
import com.hakan.core.ui.inventory.HInventoryHandler;
import com.hakan.core.ui.inventory.builder.HInventoryBuilder;
import com.hakan.core.ui.sign.HSign;
import com.hakan.core.ui.sign.HSignHandler;
import com.hakan.core.utils.HSerializer;
import com.hakan.core.worldborder.HWorldBorderHandler;
import com.hakan.core.worldborder.border.HBorderColor;
import com.hakan.core.worldborder.border.HWorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

/**
 * Main class of this core.
 * You can reach all APIs from this
 * class as static.
 */
@SuppressWarnings({"unused"})
public final class HCore {

    private static JavaPlugin INSTANCE;

    /**
     * Gets instance.
     *
     * @return Instance.
     */
    @Nonnull
    public static JavaPlugin getInstance() {
        return HCore.INSTANCE;
    }

    /**
     * Initializes all APIs.
     *
     * @param plugin Instance of main class.
     */
    public static void initialize(@Nonnull JavaPlugin plugin) {
        if (HCore.INSTANCE != null) return;
        HCore.INSTANCE = Objects.requireNonNull(plugin, "plugin cannot be null!");

        Metrics.initialize(plugin);
        HPacketHandler.initialize(plugin);
        HWorldBorderHandler.initialize(plugin);
        HInventoryHandler.initialize(plugin);
        HSignHandler.initialize(plugin);
        HMessageHandler.initialize();
        HParticleHandler.initialize();
        HHologramHandler.initialize();
        HItemStack.initialize();
    }


    /*
    OTHERS
     */

    /**
     * Gets version string (example: v1_8_R3)
     *
     * @return Version string
     */
    @Nonnull
    public static String getVersionString() {
        return Bukkit.getServer().getClass().getName().split("\\.")[3];
    }


    /*
    SCHEDULER
     */

    /**
     * Creates scheduler.
     *
     * @param async Async status.
     * @return HScheduler.
     */
    @Nonnull
    public static HScheduler scheduler(boolean async) {
        return new HScheduler(HCore.INSTANCE, async);
    }

    /**
     * Creates async scheduler.
     *
     * @return HScheduler.
     */
    @Nonnull
    public static HScheduler asyncScheduler() {
        return new HScheduler(HCore.INSTANCE, true);
    }

    /**
     * Creates sync scheduler.
     *
     * @return HScheduler.
     */
    @Nonnull
    public static HScheduler syncScheduler() {
        return new HScheduler(HCore.INSTANCE, false);
    }


    /*
    INVENTORY API
     */

    /**
     * Gets content as safe.
     *
     * @return Content.
     */
    @Nonnull
    public static Map<UUID, HInventory> getInventoryContentSafe() {
        return HInventoryHandler.getContentSafe();
    }

    /**
     * Gets content.
     *
     * @return Content.
     */
    @Nonnull
    public static Map<UUID, HInventory> getInventoryContent() {
        return HInventoryHandler.getContent();
    }

    /**
     * Gets values as safe.
     *
     * @return Values.
     */
    @Nonnull
    public static Collection<HInventory> getInventoryValuesSafe() {
        return HInventoryHandler.getValuesSafe();
    }

    /**
     * Gets values.
     *
     * @return Values.
     */
    @Nonnull
    public static Collection<HInventory> getInventoryValues() {
        return HInventoryHandler.getValues();
    }

    /**
     * Finds HInventory by player.
     *
     * @param player Player.
     * @return HInventory as optional.
     */
    @Nonnull
    public static Optional<HInventory> findInventoryByPlayer(@Nonnull Player player) {
        return HInventoryHandler.findByPlayer(player);
    }

    /**
     * Gets HInventory by player.
     *
     * @param player Player.
     * @return HInventory.
     */
    @Nonnull
    public static HInventory getInventoryByPlayer(@Nonnull Player player) {
        return HInventoryHandler.getByPlayer(player);
    }

    /**
     * Finds HInventory by UID.
     *
     * @param uid Player UID.
     * @return HInventory as optional.
     */
    @Nonnull
    public static Optional<HInventory> findInventoryByUID(@Nonnull UUID uid) {
        return HInventoryHandler.findByUID(uid);
    }

    /**
     * Gets HInventory by UID.
     *
     * @param uid Player UId.
     * @return HInventory.
     */
    @Nonnull
    public static HInventory getInventoryByUID(@Nonnull UUID uid) {
        return HInventoryHandler.getByUID(uid);
    }

    /**
     * Creates builder with ID.
     *
     * @param id ID.
     * @return HInventoryBuilder.
     */
    @Nonnull
    public static HInventoryBuilder buildInventory(@Nonnull String id) {
        return HInventoryHandler.builder(id);
    }

    /**
     * Creates HInventory with ID.
     *
     * @param id ID.
     * @return HInventory.
     */
    @Nonnull
    public static HInventory createInventory(@Nonnull String id, @Nonnull String title, @Nonnull InventoryType type, int size) {
        return HInventoryHandler.builder(id).title(title).type(type).size(size).build();
    }


    /*
    SIGN
     */

    /**
     * Gets content as safe.
     *
     * @return Content.
     */
    @Nonnull
    public static Map<UUID, HSign> getSignContentSafe() {
        return HSignHandler.getContentSafe();
    }

    /**
     * Gets content.
     *
     * @return Content
     */
    @Nonnull
    public static Map<UUID, HSign> getSignContent() {
        return HSignHandler.getContent();
    }

    /**
     * Gets values as safe.
     *
     * @return Values.
     */
    @Nonnull
    public static Collection<HSign> getSignValuesSafe() {
        return HSignHandler.getValuesSafe();
    }

    /**
     * Gets values.
     *
     * @return Values.
     */
    @Nonnull
    public static Collection<HSign> getSignValues() {
        return HSignHandler.getValues();
    }

    /**
     * Finds HSign by player.
     *
     * @param player Player.
     * @return HSign as optional.
     */
    @Nonnull
    public static Optional<HSign> findSignByPlayer(@Nonnull Player player) {
        return HSignHandler.findByPlayer(player);
    }

    /**
     * Gets HSign by player.
     *
     * @param player Player.
     * @return HSign.
     */
    @Nonnull
    public static HSign getSignByPlayer(@Nonnull Player player) {
        return HSignHandler.getByPlayer(player);
    }

    /**
     * Finds HSign by player UID.
     *
     * @param uid Player UID.
     * @return HSign as optional.
     */
    @Nonnull
    public static Optional<HSign> findSignByUID(@Nonnull UUID uid) {
        return HSignHandler.findByUID(uid);
    }

    /**
     * Gets HSign by player UID.
     *
     * @param uid Player UID.
     * @return HSign.
     */
    @Nonnull
    public static HSign getSignByUID(@Nonnull UUID uid) {
        return HSignHandler.getByUID(uid);
    }

    /**
     * Creates HSign.
     *
     * @param type  Sign type as Material.
     * @param lines Lines of sign.
     * @return Created HSign.
     */
    @Nonnull
    public static HSign createSign(@Nonnull Material type, @Nonnull String... lines) {
        return HSignHandler.create(type, lines);
    }


    /*
    COMMAND
     */

    /**
     * Registers a new command with aliases.
     *
     * @param command Command name.
     * @param aliases Aliases.
     * @return HCommand.
     */
    @Nonnull
    public static HCommand registerCommand(@Nonnull String command, @Nonnull String... aliases) {
        return new HCommand(command, aliases).register();
    }

    /**
     * Registers command executors.
     *
     * @param executors Command executors.
     */
    public static void registerCommands(@Nonnull HCommandExecutor... executors) {
        Arrays.asList(Objects.requireNonNull(executors, "executors cannot be null!")).forEach(HCommandExecutor::register);
    }


    /*
    LISTENERS
     */

    /**
     * Registers listeners to bukkit.
     *
     * @param listeners Listeners.
     */
    public static void registerListener(@Nonnull HListenerAdapter... listeners) {
        HListenerAdapter.register(listeners);
    }

    /**
     * Registers listeners to bukkit.
     *
     * @param listeners Listeners.
     */
    public static void registerListener(@Nonnull Collection<HListenerAdapter> listeners) {
        HListenerAdapter.register(listeners);
    }


    /*
    PACKET
     */

    /**
     * Sends packet to players
     *
     * @param player Player.
     * @param packet Packet.
     */
    public static void sendPacket(@Nonnull Player player, @Nonnull Object packet) {
        HCore.sendPacket(player, new Object[]{packet});
    }

    /**
     * Sends packets to player.
     *
     * @param player  Player.
     * @param packets Packets.
     */
    public static void sendPacket(@Nonnull Player player, @Nonnull Object... packets) {
        HPacketHandler.getContent().get(Objects.requireNonNull(player, "player cannot be null!")).send(packets);
    }

    /**
     * Sends packet to player list.
     *
     * @param players Player list.
     * @param packet  Packet.
     */
    public static void sendPacket(@Nonnull Collection<Player> players, @Nonnull Object packet) {
        HCore.sendPacket(players, new Object[]{packet});
    }

    /**
     * Sends packets to player list.
     *
     * @param players Player list.
     * @param packets Packets.
     */
    public static void sendPacket(@Nonnull Collection<Player> players, @Nonnull Object... packets) {
        Objects.requireNonNull(players, "players cannot be null!").forEach(player -> HCore.sendPacket(player, packets));
    }


    /*
    PARTICLE
     */

    /**
     * Plays particle for player.
     *
     * @param player   Player.
     * @param location Location.
     * @param particle HParticle class.
     */
    public static void playParticle(@Nonnull Player player, @Nonnull Location location, @Nonnull HParticle particle) {
        HParticleHandler.play(player, location, particle);
    }

    /**
     * Plays particle for players.
     *
     * @param players  Players.
     * @param location Location.
     * @param particle HParticle class.
     */
    public static void playParticle(@Nonnull Collection<Player> players, @Nonnull Location location, @Nonnull HParticle particle) {
        HParticleHandler.play(players, location, particle);
    }


    /*
    MESSAGE
     */

    /**
     * Sends title to player.
     *
     * @param player Player.
     * @param hTitle HTitle class.
     */
    public static void sendTitle(@Nonnull Player player, @Nonnull HTitle hTitle) {
        HMessageHandler.sendTitle(player, hTitle);
    }

    /**
     * Sends title to player.
     *
     * @param player   Player.
     * @param title    Title.
     * @param subTitle Subtitle.
     */
    public static void sendTitle(@Nonnull Player player, @Nonnull String title, @Nonnull String subTitle) {
        HMessageHandler.sendTitle(player, title, subTitle);
    }

    /**
     * Sends title to player.
     *
     * @param player   Player.
     * @param title    Title.
     * @param subTitle Subtitle.
     * @param stay     Stay time.
     * @param fadein   Fade in time.
     * @param fadeout  Fade out time.
     */
    public static void sendTitle(@Nonnull Player player, @Nonnull String title, @Nonnull String subTitle, int stay, int fadein, int fadeout) {
        HMessageHandler.sendTitle(player, title, subTitle, stay, fadein, fadeout);
    }

    /**
     * Sends title to players.
     *
     * @param players Players.
     * @param hTitle  HTitle class.
     */
    public static void sendTitle(@Nonnull Collection<Player> players, @Nonnull HTitle hTitle) {
        HMessageHandler.sendTitle(players, hTitle);
    }

    /**
     * Sends title to players.
     *
     * @param players  Players.
     * @param title    Title.
     * @param subTitle Subtitle.
     */
    public static void sendTitle(@Nonnull Collection<Player> players, @Nonnull String title, @Nonnull String subTitle) {
        HMessageHandler.sendTitle(players, title, subTitle);
    }

    /**
     * Sends title to players.
     *
     * @param players  Players.
     * @param title    Title.
     * @param subTitle Subtitle.
     * @param stay     Stay time.
     * @param fadein   Fade in time.
     * @param fadeout  Fade out time.
     */
    public static void sendTitle(@Nonnull Collection<Player> players, @Nonnull String title, @Nonnull String subTitle, int stay, int fadein, int fadeout) {
        HMessageHandler.sendTitle(players, title, subTitle, stay, fadein, fadeout);
    }

    /**
     * Sends action bar to player.
     *
     * @param player Player.
     * @param text   Text.
     */
    public static void sendActionBar(@Nonnull Player player, @Nonnull String text) {
        HMessageHandler.sendActionBar(player, text);
    }

    /**
     * Sends action bar to players.
     *
     * @param players Players.
     * @param text    Text.
     */
    public static void sendActionBar(@Nonnull Collection<Player> players, @Nonnull String text) {
        HMessageHandler.sendActionBar(players, text);
    }

    /**
     * Gets bossbar list as safe.
     *
     * @return Bossbar list.
     */
    @Nonnull
    public static List<HBossBar> getBossBarsSafe() {
        return HMessageHandler.getBossBarsSafe();
    }

    /**
     * Gets bossbar list.
     *
     * @return Bossbar list.
     */
    @Nonnull
    public static List<HBossBar> getBossBars() {
        return HMessageHandler.getBossBars();
    }

    /**
     * Gets list of bossbars from player.
     *
     * @param player Player.
     * @return List of boss bars.
     */
    @Nonnull
    public static List<HBossBar> getBossBarsByPlayer(@Nonnull Player player) {
        return HMessageHandler.getBossBarsByPlayer(player);
    }

    /**
     * Finds first bossbar from player.
     *
     * @param player Player.
     * @return Bossbar as optional.
     */
    @Nonnull
    public static Optional<HBossBar> findFirstBossBarByPlayer(@Nonnull Player player) {
        return HMessageHandler.findFirstBossBarByPlayer(player);
    }

    /**
     * Gets first bossbar from player.
     *
     * @param player Player.
     * @return Bossbar.
     */
    @Nonnull
    public static HBossBar getFirstBossBarByPlayer(@Nonnull Player player) {
        return HMessageHandler.getFirstBossBarByPlayer(player);
    }

    /**
     * Deletes bossbar.
     *
     * @param hBossBar Bossbar.
     */
    public static void deleteBossBar(@Nonnull HBossBar hBossBar) {
        HMessageHandler.deleteBossBar(hBossBar);
    }

    /**
     * Creates bossbar.
     *
     * @param title Title.
     * @param color Color.
     * @param style Style.
     * @param flags Flags.
     * @return New instance of HBossBar.
     */
    public static HBossBar createBossBar(@Nonnull String title, @Nonnull HBarColor color, @Nonnull HBarStyle style, @Nonnull HBarFlag... flags) {
        return HMessageHandler.createBossBar(title, color, style, flags);
    }

    /**
     * Creates bossbar.
     *
     * @param title Title.
     * @param color Color.
     * @param style Style.
     * @return New instance of HBossBar.
     */
    public static HBossBar createBossBar(@Nonnull String title, @Nonnull HBarColor color, @Nonnull HBarStyle style) {
        return HMessageHandler.createBossBar(title, color, style);
    }


    /*
    ITEMSTACK
     */

    /**
     * Gets NbtManager object.
     *
     * @return NbtManager object.
     */
    @Nonnull
    public static HNbtManager getNbtManager() {
        return HItemStack.getNbtManager();
    }


    /*
    WORLD BORDER
     */

    /**
     * Gets values as safe.
     *
     * @return Values.
     */
    @Nonnull
    public static Collection<HWorldBorder> getBorderValuesSafe() {
        return HWorldBorderHandler.getValuesSafe();
    }

    /**
     * Gets values.
     *
     * @return Values.
     */
    @Nonnull
    public static Collection<HWorldBorder> getBorderValues() {
        return HWorldBorderHandler.getValues();
    }

    /**
     * Finds world border from player.
     *
     * @param player Player.
     * @return HWorldBorder from player as optional.
     */
    @Nonnull
    public static Optional<HWorldBorder> findBorderByPlayer(@Nonnull Player player) {
        return HWorldBorderHandler.findByPlayer(player);
    }

    /**
     * Gets world border from player.
     *
     * @param player Player.
     * @return HWorldBorder from player.
     */
    @Nonnull
    public static HWorldBorder getBorderByPlayer(@Nonnull Player player) {
        return HWorldBorderHandler.getByPlayer(player);
    }

    /**
     * Creates world border.
     *
     * @param location        Center location.
     * @param size            Size of world border.
     * @param damageAmount    Damage amount.
     * @param damageBuffer    Damage buffer.
     * @param warningDistance Warning distance.
     * @param warningTime     Warning time.
     * @param color           Color of border.
     * @return Created world border.
     */
    @Nonnull
    public static HWorldBorder createBorder(@Nonnull Location location, double size, double damageAmount, double damageBuffer, int warningDistance, int warningTime, @Nonnull HBorderColor color) {
        return HWorldBorderHandler.create(location, size, damageAmount, damageBuffer, warningDistance, warningTime, color);
    }


    /*
    HOLOGRAM
     */

    /**
     * Gets content as safe.
     *
     * @return holograms map.
     */
    @Nonnull
    public static Map<String, HHologram> getHologramContentSafe() {
        return HHologramHandler.getContentSafe();
    }

    /**
     * Gets content.
     *
     * @return holograms map.
     */
    @Nonnull
    public static Map<String, HHologram> getHologramContent() {
        return HHologramHandler.getContent();
    }

    /**
     * Gets holograms as safe.
     *
     * @return holograms.
     */
    @Nonnull
    public static Collection<HHologram> getHologramValuesSafe() {
        return HHologramHandler.getValuesSafe();
    }

    /**
     * Gets holograms.
     *
     * @return holograms.
     */
    @Nonnull
    public static Collection<HHologram> getHologramValues() {
        return HHologramHandler.getValues();
    }

    /**
     * Finds a created hologram
     *
     * @param id hologram id that you want
     * @return hologram from id
     */
    @Nonnull
    public static Optional<HHologram> findHologramByID(@Nonnull String id) {
        return HHologramHandler.findByID(id);
    }

    /**
     * Gets a created hologram
     *
     * @param id hologram id that you want
     * @return hologram from id
     */
    @Nonnull
    public static HHologram getHologramByID(@Nonnull String id) {
        return HHologramHandler.getByID(id);
    }

    /**
     * Creates a new hologram
     *
     * @param id       hologram id
     * @param location location
     * @param players  player list
     * @return new hologram
     */
    @Nonnull
    public static HHologram createHologram(@Nonnull String id, @Nonnull Location location, @Nullable Set<UUID> players) {
        return HHologramHandler.create(id, location, players);
    }

    /**
     * Creates a new hologram
     *
     * @param id       hologram id
     * @param location location
     * @return new hologram
     */
    @Nonnull
    public static HHologram createHologram(@Nonnull String id, @Nonnull Location location) {
        return HHologramHandler.create(id, location);
    }

    /**
     * Deletes hologram by id
     *
     * @param id id of hologram
     * @return hologram to be deleted
     */
    @Nullable
    public static HHologram deleteHologram(@Nonnull String id) {
        return HHologramHandler.delete(id);
    }


    /*
    SPAM CONTROLLER
     */

    /**
     * Checks if id is spamming.
     *
     * @param id       The id to check.
     * @param duration The duration to check.
     * @return True if spamming.
     */
    public static boolean spam(@Nonnull String id, @Nonnull Duration duration) {
        return HSpam.spam(id, duration);
    }

    /**
     * Checks if id is spamming.
     *
     * @param id   The id to check.
     * @param time The time in milliseconds.
     * @return True if spamming.
     */
    public static boolean spam(@Nonnull String id, long time) {
        return HSpam.spam(id, time);
    }


    /*
    SERIALIZER
     */

    /**
     * Serializes object.
     *
     * @param object Object.
     * @return Serialized string as optional.
     */
    @Nonnull
    public synchronized static Optional<String> serializeSafe(@Nonnull Object object) {
        return HSerializer.serializeSafe(object);
    }

    /**
     * Serializes object.
     *
     * @param object Object.
     * @return Serialized string.
     */
    @Nonnull
    public synchronized static String serialize(@Nonnull Object object) {
        return HSerializer.serialize(object);
    }

    /**
     * Deserializes object.
     *
     * @param serializedText Text that want to deserialize.
     * @param clazz          Object type class.
     * @param <T>            Object type.
     * @return Deserialized object as optional.
     */
    @Nonnull
    public synchronized static <T> Optional<T> deserializeSafe(@Nonnull String serializedText, @Nonnull Class<T> clazz) {
        return HSerializer.deserializeSafe(serializedText, clazz);
    }

    /**
     * Deserializes object.
     *
     * @param serializedText Text that want to deserialize.
     * @param clazz          Object type class.
     * @param <T>            Object type.
     * @return Deserialized object as optional.
     */
    @Nonnull
    public synchronized static <T> T deserialize(@Nonnull String serializedText, @Nonnull Class<T> clazz) {
        return HSerializer.deserialize(serializedText, clazz);
    }
}