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

import java.util.Random;
import java.util.UUID;

import static net.yunitrish.adaptor.common.AdaptorApi.uuidV5;

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

        EnchantAttributeHandler titan_enchantment = new EnchantAttributeHandler(ModEnchantments.TITAN, EntityAttributes.GENERIC_SCALE, uuidV5("titan_entity_scale"), 0.1, 1.2);

        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, equipmentSlot, previous, next) -> {
            dexterity_movement_speed.checkAndApply(livingEntity, previous, next);
            dexterity_attack_speed.checkAndApply(livingEntity, previous, next);
            wisdom_enchantment.checkAndApply(livingEntity, previous, next);
            titan_enchantment.checkAndApply(livingEntity, previous, next);
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
