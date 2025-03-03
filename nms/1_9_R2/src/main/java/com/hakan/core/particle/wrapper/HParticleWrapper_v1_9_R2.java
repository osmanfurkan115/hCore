package com.hakan.core.particle.wrapper;

import com.hakan.core.HCore;
import com.hakan.core.particle.HParticle;
import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * {@inheritDoc}
 */
public final class HParticleWrapper_v1_9_R2 implements HParticleWrapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(@Nonnull Player player, @Nonnull Location location, @Nonnull HParticle hParticle) {
        Validate.notNull(player, "player cannot be null!");
        Validate.notNull(location, "location cannot be null!");
        Validate.notNull(hParticle, "hParticle class cannot be null!");

        EnumParticle enumParticle = null;
        try {
            enumParticle = EnumParticle.valueOf(hParticle.getParticleName());
        } catch (Exception e) {
            for (EnumParticle particle : EnumParticle.values()) {
                if (particle.name().toLowerCase().contains(hParticle.getParticleName().toLowerCase())) {
                    enumParticle = particle;
                    break;
                }
            }
        }

        Validate.notNull(enumParticle, "Particle not found!");
        HCore.sendPacket(player, new PacketPlayOutWorldParticles(enumParticle, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), (float) hParticle.getOffset().getX(), (float) hParticle.getOffset().getY(), (float) hParticle.getOffset().getZ(), (float) hParticle.getSpeed(), hParticle.getAmount()));
    }
}