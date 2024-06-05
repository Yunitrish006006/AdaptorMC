package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class WisdomEnchantment extends Enchantment {
    public WisdomEnchantment() {
        super(new Enchantment.Properties(
                ItemTags.HEAD_ARMOR_ENCHANTABLE,
                Optional.of(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                2, 3,
                new Enchantment.Cost(8, 7),
                new Enchantment.Cost(8, 7),
                1,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.HEAD}
        ));
    }
}
