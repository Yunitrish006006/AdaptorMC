package net.yunitrish.adaptor.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;

class DamageRelation {
    private final Entity from;
    private final Entity target;
    private final RegistryKey<DamageType> damageType;
    private final float damage;
    private int timer;

    public DamageRelation(Entity from, Entity target, RegistryKey<DamageType> damageType, float damage, int timer) {

        this.from = from;
        this.target = target;
        this.damageType = damageType;
        this.damage = damage;
        this.timer = timer;
    }

    public Entity getFrom() {
        return from;
    }

    public Entity getTarget() {
        return target;
    }

    public RegistryKey<DamageType> getDamageType() {
        return damageType;
    }

    public float getDamage() {
        return damage;
    }

    public int getTimer() {
        return timer;
    }

    public void countDown() {
        timer = timer - 1;
    }
}
