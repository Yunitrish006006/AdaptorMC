package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SALT_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SALT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_SALT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_STONE_SALT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_SALT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUND_BLOCK);

        BlockStateModelGenerator.BlockTexturePool dirtPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DIRT);

        dirtPool.stairs(ModBlocks.DIRT_STAIRS);
        dirtPool.slab(ModBlocks.DIRT_SLAB);
        dirtPool.button(ModBlocks.DIRT_BUTTON);
        dirtPool.pressurePlate(ModBlocks.DIRT_PRESSURE_PLATE);
        dirtPool.fence(ModBlocks.DIRT_FENCE);
        dirtPool.fenceGate(ModBlocks.DIRT_FENCE_GATE);
        dirtPool.wall(ModBlocks.DIRT_WALL);



        blockStateModelGenerator.registerDoor(ModBlocks.DIRT_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.DIRT_TRAPDOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SALT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAMBOO_COAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOUR, Models.GENERATED);

        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.GENERATED);
    }
}
