package com.hakan.core.packet.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Packet custom event class
 * to listen packets.
 */
@SuppressWarnings({"unused", "unchecked"})
public final class PacketEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    /**
     * Gets handler list.
     *
     * @return Handler list.
     */
    @Nonnull
    public static HandlerList getHandlerList() {
        return PacketEvent.handlerList;
    }


    private final Player player;
    private final Object packet;
    private final Type type;
    private boolean cancelled;

    /**
     * Creates new instance of this class.
     *
     * @param player Player.
     * @param packet Packet.
     * @param type   Packet type.
     */
    public PacketEvent(@Nonnull Player player, @Nonnull Object packet, @Nonnull Type type) {
        this.player = Objects.requireNonNull(player, "player cannot be null!");
        this.packet = Objects.requireNonNull(packet, "packet cannot be null!");
        this.type = Objects.requireNonNull(type, "packet type cannot be null!");
    }

    /**
     * Gets handler list.
     *
     * @return Handler list.
     */
    @Nonnull
    public HandlerList getHandlers() {
        return PacketEvent.handlerList;
    }

    /**
     * Gets event player.
     *
     * @return Event player.
     */
    @Nonnull
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets packet.
     *
     * @param <T> Packet class.
     * @return Packet.
     */
    @Nonnull
    public <T> T getPacket() {
        return (T) this.packet;
    }

    /**
     * Gets packet as clazz.
     *
     * @param clazz Class of packet.
     * @return Packet.
     */
    @Nonnull
    public <T> T getPacket(@Nonnull Class<T> clazz) {
        return Objects.requireNonNull(clazz, "packet class cannot be null!").cast(this.packet);
    }

    /**
     * Gets packet type.
     *
     * @return Packet type.
     */
    @Nonnull
    public Type getType() {
        return this.type;
    }

    /**
     * Checks event is cancelled.
     *
     * @return If event is cancelled, returns true.
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets cancel status of event.
     *
     * @param cancelled Cancel status.
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets value from field in packet.
     *
     * @param fieldName Field.
     * @param <T>       Value class.
     * @return Value from field.
     */
    @Nonnull
    public <T> T getValue(@Nonnull String fieldName) {
        Objects.requireNonNull(fieldName, "field cannot be null!");

        try {
            Field field = this.packet.getClass().getDeclaredField(fieldName);
            boolean flag = field.isAccessible();

            field.setAccessible(true);
            Object value = field.get(this.packet);
            field.setAccessible(flag);

            return (T) value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) this.packet;
    }

    /**
     * Gets value from field in packet.
     *
     * @param fieldName Field.
     * @param clazz     Packet class.
     * @param <T>       Value class.
     * @return Value from field.
     */
    @Nonnull
    public <T> T getValue(@Nonnull String fieldName, @Nonnull Class<T> clazz) {
        Objects.requireNonNull(clazz, "class cannot be null!");
        Objects.requireNonNull(fieldName, "field cannot be null!");

        try {
            Field field = this.packet.getClass().getDeclaredField(fieldName);
            boolean flag = field.isAccessible();

            field.setAccessible(true);
            Object value = field.get(this.packet);
            field.setAccessible(flag);

            return clazz.cast(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clazz.cast(this.packet);
    }


    /**
     * Packet types.
     */
    public enum Type {
        READ,
        WRITE
    }
}