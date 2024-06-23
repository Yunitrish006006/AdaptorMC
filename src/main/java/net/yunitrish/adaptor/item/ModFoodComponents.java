package net.yunitrish.adaptor.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent DOUGH = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 10), 0.2f)
            .build();
    public static final FoodComponent SOYBEAN = new FoodComponent.Builder()
            .nutrition(1)
            .saturationModifier(0.05f)
            .alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 20 * 30), 0.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 5), 0.4f)
            .build();
    public static final FoodComponent MARIJUANA = new FoodComponent.Builder()
            .nutrition(0)
            .saturationModifier(0.00f)
            .alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,20*10),1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,20*10),1f)
            .build();

//    public static final PotionContentsComponent SAKURA_POTION_COMPONENT = new PotionContentsComponent(Potions.SWIFTNESS);
}
