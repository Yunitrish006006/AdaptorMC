package net.yunitrish.adaptor.block;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.LockableContainer.Barrel.LockableBarrelBlockEntity;
import net.yunitrish.adaptor.block.LockableContainer.Crate.LockableCrateBlockEntity;
import net.yunitrish.adaptor.block.LockableContainer.StoneMill.LockableStoneMillBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<LockableStoneMillBlockEntity> LOCKABLE_STONE_MILL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry
            .register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Adaptor.id("locked_stone_mill"),
                    BlockEntityType.Builder.create(LockableStoneMillBlockEntity::new, ModBlocks.Container.LOCKABLE_STONE_MILL).build()
            );
    public static final BlockEntityType<LockableCrateBlockEntity> LOCKABLE_CRATE_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry
            .register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Adaptor.id("locked_crate"),
                    BlockEntityType.Builder.create(LockableCrateBlockEntity::new, ModBlocks.Container.LOCKABLE_CRATE).build()
            );
    public static final BlockEntityType<LockableBarrelBlockEntity> LOCKABLE_BARREL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry
            .register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Adaptor.id("locked_barrel"),
                    BlockEntityType.Builder.create(LockableBarrelBlockEntity::new, ModBlocks.Container.LOCKABLE_BARREL).build()
            );

    public static void registerBlockEntities() {
        Adaptor.LOGGER.info("Registering Block Entities...");
    }
}
