package net.yunitrish.adaptor.common;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.LockableContainer.utils.GenericContainerLock;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class Api {
    public static final ComponentType<GenericContainerLock> GENERIC_LOCK = register("generic_lock", builder -> builder.codec(GenericContainerLock.CODEC));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Adaptor.id(id), ((ComponentType.Builder) builderOperator.apply(ComponentType.builder())).build());
    }

    public static UUID uuidV5(String name) {

        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(name.getBytes(StandardCharsets.UTF_8));

            byte[] data = sha1.digest();
            data[6] = (byte) (data[6] & 0x0f);
            data[6] = (byte) (data[6] | 0x50); // set version 5
            data[8] = (byte) (data[8] & 0x3f);
            data[8] = (byte) (data[8] | 0x80);

            long msb = 0L;
            long lsb = 0L;

            for (int i = 0; i <= 7; i++)
                msb = (msb << 8) | (data[i] & 0xff);

            for (int i = 8; i <= 15; i++)
                lsb = (lsb << 8) | (data[i] & 0xff);

            long mostSigBits = msb;
            long leastSigBits = lsb;

            return new UUID(mostSigBits, leastSigBits);
        } catch (Exception e) {
            return UUID.fromString("46479116-73a6-54f1-952f-d144ae8bcf23");
        }

    }

    public static void spawnParticlesFromEntity(Entity entity, ParticleEffect particleEffect, int count) {
        if (entity.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = entity.getPos();
            for (int i = 0; i < count; i++) {
                double offsetX = (serverWorld.random.nextDouble() - 0.5) * entity.getWidth();
                double offsetY = serverWorld.random.nextDouble() * entity.getHeight();
                double offsetZ = (serverWorld.random.nextDouble() - 0.5) * entity.getWidth();
                serverWorld.spawnParticles(particleEffect, entityPos.x + offsetX, entityPos.y + offsetY, entityPos.z + offsetZ, 1, 0, 0, 0, 0);
            }
        }
    }

    public static void initialize() {
    }
}
