package net.yunitrish.adaptor.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent DOUGH = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100),0.2f)
            .build();
    public static final FoodComponent SOYBEAN = new FoodComponent.Builder()
            .hunger(1)
            .saturationModifier(0.05f)
            .alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK,200),0.03f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,60),0.1f)
            .build();
    public static final FoodComponent MARIJUANA = new FoodComponent.Builder()
            .hunger(0)
            .saturationModifier(0.00f)
            .alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,20*10),1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,20*10),1f)
            .build();
    public static final FoodComponent CORN = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.2f)
            .build();
}
