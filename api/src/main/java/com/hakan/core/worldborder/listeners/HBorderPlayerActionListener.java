package com.hakan.core.worldborder.listeners;

import com.hakan.core.HCore;
import com.hakan.core.listener.HListenerAdapter;
import com.hakan.core.worldborder.HWorldBorderHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * Border action listeners class.
 */
public final class HBorderPlayerActionListener extends HListenerAdapter {

    /**
     * Creates new instance of this class.
     *
     * @param plugin Main class of plugin.
     */
    public HBorderPlayerActionListener(@Nonnull JavaPlugin plugin) {
        super(plugin);
    }

    /**
     * Teleport event.
     *
     * @param event Event.
     */
    @EventHandler
    public void onTeleport(@Nonnull PlayerTeleportEvent event) {
        HCore.syncScheduler().after(3).run(() -> {
            Player player = event.getPlayer();
            HWorldBorderHandler.findByPlayer(player)
                    .ifPresent(hWorldBorder -> hWorldBorder.show(player));
        });
    }

    /**
     * World change event.
     *
     * @param event Event.
     */
    @EventHandler
    public void onWorldChange(@Nonnull PlayerChangedWorldEvent event) {
        HCore.syncScheduler().after(3).run(() -> {
            Player player = event.getPlayer();
            HWorldBorderHandler.findByPlayer(player)
                    .ifPresent(hWorldBorder -> hWorldBorder.show(player));
        });
    }
}