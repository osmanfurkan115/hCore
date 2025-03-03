package com.hakan.core.message;

import com.hakan.core.HCore;
import com.hakan.core.message.actionbar.HActionBarHandler;
import com.hakan.core.message.bossbar.HBarColor;
import com.hakan.core.message.bossbar.HBarFlag;
import com.hakan.core.message.bossbar.HBarStyle;
import com.hakan.core.message.bossbar.HBossBar;
import com.hakan.core.message.title.HTitle;
import com.hakan.core.message.title.HTitleHandler;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.*;

public final class HMessageHandler {

    private static HActionBarHandler hActionBarHandler;
    private static HTitleHandler hTitleHandler;
    private static List<HBossBar> bossBarList;

    /**
     * Initializes message api.
     */
    public static void initialize() {
        try {
            String version = HCore.getVersionString();
            HMessageHandler.hActionBarHandler = (HActionBarHandler) Class.forName("com.hakan.core.message.actionbar.HActionBarHandler_" + version).getConstructor().newInstance();
            HMessageHandler.hTitleHandler = (HTitleHandler) Class.forName("com.hakan.core.message.title.HTitleHandler_" + version).getConstructor().newInstance();
            HMessageHandler.bossBarList = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    TITLE
     */

    /**
     * Sends title to player.
     *
     * @param player Player.
     * @param hTitle HTitle class.
     */
    public static void sendTitle(@Nonnull Player player, @Nonnull HTitle hTitle) {
        HMessageHandler.hTitleHandler.send(Objects.requireNonNull(player, "player cannot be null!"), Objects.requireNonNull(hTitle, "hTitle cannot be null!"));
    }

    /**
     * Sends title to player.
     *
     * @param player   Player.
     * @param title    Title.
     * @param subTitle Subtitle.
     */
    public static void sendTitle(@Nonnull Player player, @Nonnull String title, @Nonnull String subTitle) {
        HMessageHandler.sendTitle(player, new HTitle(title, subTitle));
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
        HMessageHandler.sendTitle(player, new HTitle(title, subTitle, stay, fadein, fadeout));
    }

    /**
     * Sends title to players.
     *
     * @param players Players.
     * @param hTitle  HTitle class.
     */
    public static void sendTitle(@Nonnull Collection<Player> players, @Nonnull HTitle hTitle) {
        Objects.requireNonNull(players, "players cannot be null!").forEach(player -> HMessageHandler.sendTitle(player, hTitle));
    }

    /**
     * Sends title to players.
     *
     * @param players  Players.
     * @param title    Title.
     * @param subTitle Subtitle.
     */
    public static void sendTitle(@Nonnull Collection<Player> players, @Nonnull String title, @Nonnull String subTitle) {
        HMessageHandler.sendTitle(players, new HTitle(title, subTitle));
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
        HMessageHandler.sendTitle(players, new HTitle(title, subTitle, stay, fadein, fadeout));
    }


    /*
    ACTION BAR
     */

    /**
     * Sends action bar to player.
     *
     * @param player Player.
     * @param text   Text.
     */
    public static void sendActionBar(@Nonnull Player player, @Nonnull String text) {
        HMessageHandler.hActionBarHandler.send(Objects.requireNonNull(player, "player cannot be null!"), Objects.requireNonNull(text, "text cannot be null!"));
    }

    /**
     * Sends action bar to players.
     *
     * @param players Players.
     * @param text    Text.
     */
    public static void sendActionBar(@Nonnull Collection<Player> players, @Nonnull String text) {
        players.forEach(player -> HMessageHandler.sendActionBar(player, text));
    }


    /*
    BOSS BAR
     */

    /**
     * Gets bossbar list as safe.
     *
     * @return Bossbar list.
     */
    @Nonnull
    public static List<HBossBar> getBossBarsSafe() {
        return new ArrayList<>(HMessageHandler.bossBarList);
    }

    /**
     * Gets bossbar list.
     *
     * @return Bossbar list.
     */
    @Nonnull
    public static List<HBossBar> getBossBars() {
        return HMessageHandler.bossBarList;
    }

    /**
     * Gets list of boss bars from player.
     *
     * @param player Player.
     * @return List of boss bars.
     */
    @Nonnull
    public static List<HBossBar> getBossBarsByPlayer(@Nonnull Player player) {
        Validate.notNull(player, "player cannot be null!");

        List<HBossBar> bossBars = new ArrayList<>();
        for (HBossBar bossBar : HMessageHandler.bossBarList)
            if (bossBar.getPlayers().contains(player))
                bossBars.add(bossBar);
        return bossBars;
    }

    /**
     * Finds first bossbar from player.
     *
     * @param player Player.
     * @return Bossbar as optional.
     */
    @Nonnull
    public static Optional<HBossBar> findFirstBossBarByPlayer(@Nonnull Player player) {
        Validate.notNull(player, "player cannot be null!");

        for (HBossBar bossBar : HMessageHandler.bossBarList)
            if (bossBar.getPlayers().contains(player))
                return Optional.of(bossBar);
        return Optional.empty();
    }

    /**
     * Gets first bossbar from player.
     *
     * @param player Player.
     * @return Bossbar.
     */
    @Nonnull
    public static HBossBar getFirstBossBarByPlayer(@Nonnull Player player) {
        return HMessageHandler.findFirstBossBarByPlayer(player)
                .orElseThrow(() -> new IllegalArgumentException("player " + player.getName() + " has no bossbar!"));
    }

    /**
     * Deletes bossbar.
     *
     * @param hBossBar Bossbar.
     */
    public static void deleteBossBar(@Nonnull HBossBar hBossBar) {
        Validate.notNull(hBossBar, "hBossBar cannot be null!");

        HMessageHandler.bossBarList.remove(hBossBar);
        hBossBar.removeAll();
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
    @Nonnull
    public static HBossBar createBossBar(@Nonnull String title, @Nonnull HBarColor color, @Nonnull HBarStyle style, @Nonnull HBarFlag... flags) {
        Validate.notNull(title, "title cannot be null!");
        Validate.notNull(color, "color cannot be null!");
        Validate.notNull(style, "style cannot be null!");
        Validate.notNull(flags, "flags cannot be null!");

        try {
            String version = HCore.getVersionString();
            HBossBar bossBar = (HBossBar) Class.forName("com.hakan.core.message.bossbar.HBossBar_" + version)
                    .getConstructor(String.class, HBarColor.class, HBarStyle.class, HBarFlag[].class)
                    .newInstance(title, color, style, flags);
            HMessageHandler.bossBarList.add(bossBar);
            return bossBar;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates bossbar.
     *
     * @param title Title.
     * @param color Color.
     * @param style Style.
     * @return New instance of HBossBar.
     */
    @Nonnull
    public static HBossBar createBossBar(@Nonnull String title, @Nonnull HBarColor color, @Nonnull HBarStyle style) {
        return HMessageHandler.createBossBar(title, color, style, new HBarFlag[0]);
    }
}