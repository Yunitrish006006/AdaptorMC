package net.yunitrish.adaptor.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieMixin extends HostileEntity {
    protected ZombieMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!this.getWorld().isClient && this.isAlive() && !this.isAiDisabled()) {
            if (Math.round(getHealth()) < Math.round(getMaxHealth()) && random.nextFloat() < 0.05 && this.getTarget() != null) {
                setHealth(getHealth() + 2f);
            }
        }
    }

    @Inject(method = "damage", at = @At("TAIL"))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (getTarget() != null && random.nextDouble() < 0.05) {
            addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2, 1));
            Vec3d vec = getTarget().getPos().subtract(this.getPos()).add(0, 0.4, 0);
            setVelocity(vec.multiply(random.nextDouble() * 0.4));
        }
    }


    @Inject(method = "initAttributes", at = @At("TAIL"))
    public void applyAttributeModifiers(CallbackInfo ci) {
        if (random.nextFloat() < 0.4f) {
            EntityAttributeInstance zombieScale = getAttributeInstance(EntityAttributes.GENERIC_SCALE);
            EntityAttributeInstance zombieMaxHealth = getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            EntityAttributeInstance zombieSpeed = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (zombieScale != null && zombieMaxHealth != null && zombieSpeed != null) {
                double value = (random.nextDouble() - 0.5) / 2;
                zombieScale.addPersistentModifier(new EntityAttributeModifier("Random spawn bonus scale", value, EntityAttributeModifier.Operation.ADD_VALUE));
                zombieMaxHealth.addPersistentModifier(new EntityAttributeModifier("Random spawn bonus max health", value * 24, EntityAttributeModifier.Operation.ADD_VALUE));
                zombieSpeed.addPersistentModifier(new EntityAttributeModifier("Random spawn bonus speed", value * 0.03, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }
    }
}
