package net.yunitrish.adaptor.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EntityMixin {
    @Shadow
    protected abstract void attackLivingEntity(LivingEntity target);

    @Inject(method = "onAttacking", at = @At("TAIL"))
    private void onAttack(Entity target, CallbackInfo info) {
        LivingEntity attacker = (LivingEntity) (Object) this;
        if (attacker.getMainHandStack().hasEnchantments()) {
            ItemStack item = attacker.getMainHandStack();
        }
    }
}
