package net.yunitrish.adaptor.enchantment.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class TitanEnchantment extends Enchantment {
    public TitanEnchantment() {
        super(new Properties(
                ItemTags.CHEST_ARMOR_ENCHANTABLE,
                Optional.of(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                3, 3,
                new Cost(8, 7),
                new Cost(8, 7),
                1,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.CHEST}
        ));
    }
}
