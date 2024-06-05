package net.yunitrish.adaptor.enchantment;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

public class EnchantAttributeHandler {

    Enchantment enchantment;

    RegistryEntry<EntityAttribute> attribute;

    UUID attributeId;
    double base;
    double multiplier;

    public EnchantAttributeHandler(Enchantment enchantment, RegistryEntry<EntityAttribute> entityAttribute, UUID id, double base, double multiplier) {
        this.enchantment = enchantment;
        this.attribute = entityAttribute;
        this.attributeId = id;
        this.base = base;
        this.multiplier = multiplier;
    }

    public static void registerEnchantmentAttributes() {
        EnchantAttributeHandler dexterity_movement_speed = new EnchantAttributeHandler(ModEnchantments.DEXTERITY, EntityAttributes.GENERIC_MOVEMENT_SPEED, uuidV5("dexterity_movement_speed"), 0.01, 1.6);
        EnchantAttributeHandler dexterity_attack_speed = new EnchantAttributeHandler(ModEnchantments.DEXTERITY, EntityAttributes.GENERIC_ATTACK_SPEED, uuidV5("dexterity_attack_speed"), 0.03, 1.4);

        EnchantAttributeHandler wisdom_enchantment = new EnchantAttributeHandler(ModEnchantments.WISDOM, EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, uuidV5("wisdom_entity_interaction"), 0.5, 1.4);

        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, equipmentSlot, previous, next) -> {
            dexterity_movement_speed.checkAndApply(livingEntity, previous, next);
            dexterity_attack_speed.checkAndApply(livingEntity, previous, next);
            wisdom_enchantment.checkAndApply(livingEntity, previous, next);
        });
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ItemStack item = player.getEquippedStack(EquipmentSlot.HEAD);
                if (wisdom_enchantment.hasSpecificEnchantment(item)) {
                    if (new Random().nextFloat() < 0.02 && player.experienceLevel < wisdom_enchantment.getLevel(item) * 10)
                        player.addExperience(1);
                }
            }
        });
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

    public void checkAndApply(LivingEntity entity, ItemStack previous, ItemStack next) {
        if (hasSpecificEnchantment(previous)) removeEnchantAttribute(entity);
        if (hasSpecificEnchantment(next)) applyEnchantAttribute(entity, next);
    }

    private boolean hasSpecificEnchantment(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(enchantment, itemStack) > 0;
    }

    private double getTotal(double base, double multiplier, Enchantment enchantment, ItemStack itemStack) {
        return base * Math.pow(multiplier, EnchantmentHelper.getLevel(enchantment, itemStack));
    }

    private void applyEnchantAttribute(LivingEntity entity, ItemStack itemStack) {
        EntityAttributeInstance attributeInstance = entity.getAttributeInstance(attribute);
        if (attributeInstance == null) return;
        double total = getTotal(base, multiplier, enchantment, itemStack);
        EntityAttributeModifier modifier = attributeInstance.getModifier(attributeId);
        if (modifier == null) {
            attributeInstance.addPersistentModifier(new EntityAttributeModifier(attributeId, enchantment.getTranslationKey(), total, EntityAttributeModifier.Operation.ADD_VALUE));
        } else {
            if (total != modifier.value()) {
                removeEnchantAttribute(entity);
                applyEnchantAttribute(entity, itemStack);
            }
        }
    }

    private void removeEnchantAttribute(LivingEntity entity) {
        EntityAttributeInstance attributeInstance = entity.getAttributeInstance(attribute);
        if (attributeInstance == null) return;
        if (attributeInstance.getModifier(attributeId) == null) return;
        attributeInstance.removeModifier(attributeId);
    }

    private int getLevel(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(enchantment, itemStack);
    }
}
