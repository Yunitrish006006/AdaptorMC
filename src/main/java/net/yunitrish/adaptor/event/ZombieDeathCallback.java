package net.yunitrish.adaptor.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;

public interface ZombieDeathCallback {
    Event<ZombieDeathCallback> EVENT = EventFactory.createArrayBacked(ZombieDeathCallback.class,
            (listeners) -> (zombie) -> {
                for (ZombieDeathCallback listener : listeners) {
                    ActionResult result = listener.interact(zombie);

                    Random random = Random.create();

                    if (zombie.getHealth() <= 3.0f) {
                        zombie.heal(random.nextFloat()*10);
                        zombie.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,2*20,0,true,true,true),zombie);
                        zombie.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,StatusEffectInstance.INFINITE,0,true,true,true),zombie);
                        zombie.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,StatusEffectInstance.INFINITE,0,true,true,true),zombie);
                        zombie.setCanBreakDoors(true);
                    }

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ZombieEntity zombie);
}
