package net.yunitrish.adaptor.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;
import net.yunitrish.adaptor.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<StoneMillBlockEntity> STONE_MILL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(AdaptorMain.MOD_ID,"gem_polishing_be"), FabricBlockEntityTypeBuilder.create(StoneMillBlockEntity::new, ModBlocks.STONE_MILL).build());

    public static void registerBlockEntities() {
        AdaptorMain.LOGGER.info("Registering Block Entities for " + AdaptorMain.MOD_ID);
    }
}
