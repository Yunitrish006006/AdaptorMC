package net.yunitrish.adaptor.block;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.ChestLockSystem.custom.LockableChestBlockEntity;

public class ModBlockEntities {
//    public static final BlockEntityType<StoneMillBlockEntity> STONE_MILL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry
//            .register(
//                    Registries.BLOCK_ENTITY_TYPE,
//                    Adaptor.id("stone_mill_be"),
//                    BlockEntityType.Builder.create(StoneMillBlockEntity::new,ModBlocks.STONE_MILL).build()
//            );
public static final BlockEntityType<LockableChestBlockEntity> LOCKABLE_CHEST_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry
            .register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Adaptor.id("locked_container"),
                    BlockEntityType.Builder.create(LockableChestBlockEntity::new, ModBlocks.LOCKABLE_CHEST).build()
            );

    public static void registerBlockEntities() {
        Adaptor.LOGGER.info("Registering Block Entities...");
    }
}
