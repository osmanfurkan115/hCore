package com.hakan.core.hologram.line.entity;

import com.hakan.core.HCore;
import com.hakan.core.hologram.HHologram;
import net.minecraft.server.v1_10_R1.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

/**
 * {@inheritDoc}
 */
public final class HHologramArmorStand_v1_10_R1 implements HHologramArmorStand {

    private final HHologram hologram;
    private final EntityArmorStand armorStand;

    /**
     * {@inheritDoc}
     */
    private HHologramArmorStand_v1_10_R1(@Nonnull HHologram hHologram, @Nonnull Location location) {
        World world = ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle();
        this.hologram = Objects.requireNonNull(hHologram, "hologram class cannot be null!");
        this.armorStand = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());

        this.armorStand.setMarker(true);
        this.armorStand.setArms(false);
        this.armorStand.setBasePlate(false);
        this.armorStand.setNoGravity(true);
        this.armorStand.setInvisible(true);
        this.armorStand.setSmall(true);
        this.armorStand.setCustomNameVisible(true);
        this.armorStand.setHealth(114.13f);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String getText() {
        return this.armorStand.getCustomName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(@Nonnull String text) {
        this.armorStand.setCustomName(Objects.requireNonNull(text, "text cannot be null!"));
        HCore.sendPacket(this.hologram.getRenderer().getShownViewersAsPlayer(),
                new PacketPlayOutEntityMetadata(this.armorStand.getId(), this.armorStand.getDataWatcher(), true));
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Location getLocation() {
        return this.armorStand.getBukkitEntity().getLocation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocation(@Nonnull Location location) {
        Validate.notNull(location, "location cannot be null");

        World world = ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle();
        if (!world.equals(this.armorStand.getWorld())) this.armorStand.spawnIn(world);
        this.armorStand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        HCore.sendPacket(this.hologram.getRenderer().getShownViewersAsPlayer(),
                new PacketPlayOutEntityTeleport(this.armorStand));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show(@Nonnull List<Player> players) {
        HCore.sendPacket(Objects.requireNonNull(players, "players cannot be null"),
                new PacketPlayOutSpawnEntityLiving(this.armorStand),
                new PacketPlayOutEntityMetadata(this.armorStand.getId(), this.armorStand.getDataWatcher(), true),
                new PacketPlayOutEntityTeleport(this.armorStand));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide(@Nonnull List<Player> players) {
        HCore.sendPacket(Objects.requireNonNull(players, "players cannot be null"),
                new PacketPlayOutEntityDestroy(this.armorStand.getId()));
    }
}