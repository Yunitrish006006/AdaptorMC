package net.yunitrish.adaptor.enchantment;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.datagen.ModTags;

import java.util.Random;

public class EnchantAttributeHandler {

    public static void registerEnchantmentAttributes() {

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                wisdomHandler(server, player);
                leachHandler(server, player);
                manicHandler(server, player);
            }
        });
    }

    public static void wisdomHandler(MinecraftServer server, ServerPlayerEntity player) {
        ItemStack item = player.getEquippedStack(EquipmentSlot.HEAD);
        if (hasEnchantment(item, ModTags.Enchantments.WISDOM)) {
            if (player.experienceLevel < level(server, item, "wisdom") * 10) {
                if (new Random().nextFloat() < 0.02) {
                    player.addExperience(1);
                }
            }
        }
    }

    public static void leachHandler(MinecraftServer server, ServerPlayerEntity player) {
        ItemStack item = player.getEquippedStack(EquipmentSlot.MAINHAND);
        if (hasEnchantment(item, ModTags.Enchantments.LEACH)) {
            int gap = player.age - player.getLastAttackTime();

            if (gap < 20 && gap % 5 == 0) {
                float value = (float) (player.getAttributes().getValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 0.02 * level(server, item, "leach"));
                player.heal(value);
                player.sendMessage(Text.of("+❤" + 4 * value), true);
            }
        }
    }

    public static void manicHandler(MinecraftServer server, ServerPlayerEntity player) {
        ItemStack item = player.getEquippedStack(EquipmentSlot.MAINHAND);
        if (hasEnchantment(item, ModTags.Enchantments.MANIC)) {
            int level = level(server, item, "manic");

            int gap = player.age - player.getLastAttackTime();

            if (gap < 20 && gap % 5 == 0) {
                float value = (float) (player.getAttributes().getValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 0.02 * level);
                LivingEntity target = getNearestEntity(player.getWorld(), player, level);
                if (target != null) target.damage(player.getDamageSources().magic(), value * 2);
                player.damage(player.getDamageSources().magic(), value);
            }
        }
    }

    public static boolean hasEnchantment(ItemStack itemStack, TagKey<Enchantment> enchantment) {
        return EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, enchantment);
    }

    private static int level(MinecraftServer server, ItemStack itemStack, String id) {
        return EnchantmentHelper.getLevel(of(server, id), itemStack);
    }

    private static RegistryEntry<Enchantment> of(MinecraftServer server, String id) {
        return server.getRegistryManager().get(RegistryKeys.ENCHANTMENT)
                .getEntry(Adaptor.id(id))
                .orElseThrow(() -> new IllegalArgumentException("Enchantment with id " + id + " not found"));
    }

    public static LivingEntity getNearestEntity(World world, Entity sourceEntity, double maxDistance) {
        Vec3d sourcePos = sourceEntity.getPos();
        Box searchBox = new Box(
                sourcePos.x - maxDistance, sourcePos.y - maxDistance, sourcePos.z - maxDistance,
                sourcePos.x + maxDistance, sourcePos.y + maxDistance, sourcePos.z + maxDistance
        );

        LivingEntity nearestEntity = null;
        double nearestDistance = Double.MAX_VALUE;

        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, searchBox, e -> e != sourceEntity)) {
            double distance = entity.squaredDistanceTo(sourcePos);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEntity = entity;
            }
        }

        return nearestEntity;
    }
}
