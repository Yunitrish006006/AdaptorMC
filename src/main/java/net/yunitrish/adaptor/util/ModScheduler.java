package net.yunitrish.adaptor.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ModScheduler {
    private static final Map<Integer,DamageRelation> damageTimers = new HashMap<>();

    @Deprecated
    public static void registerSchedulers() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (damageTimers.isEmpty()) return;
            for (Map.Entry<Integer,DamageRelation> entry : damageTimers.entrySet()) {
                DamageRelation relation = entry.getValue();
                if (relation.getTarget() == null) return;
                if (relation.getTimer()>0) {
                    relation.countDown();
                    damageTimers.put(entry.getKey(),relation);
                }
                else {
                    relation.getTarget().damage(relation.getFrom().getDamageSources().create(relation.getDamageType(),relation.getFrom()),relation.getDamage());
                    damageTimers.remove(entry.getKey());
                }
            }
        });
    }

    @Deprecated
    public static void addDelayDamage(Entity from, Entity target, RegistryKey<DamageType> damageType, float damage, int delay) {
        Random random = new Random();
        damageTimers.put(random.nextInt(64),new DamageRelation(from,target,damageType,damage,delay));
    }
}

