package net.yunitrish.adaptor.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.entity.creature.PorcupineEntity;

public class ModEntities {
    public static final EntityType<PorcupineEntity> PORCUPINE = Registry.register(
            Registries.ENTITY_TYPE,
            Adaptor.id("porcupine"),
            EntityType
                    .Builder
                    .create(PorcupineEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f,1f)
                    .build()
    );
}
