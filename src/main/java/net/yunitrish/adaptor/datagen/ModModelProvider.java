package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.building.DirtSeries;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.block.plant.MarijuanaCropBlock;
import net.yunitrish.adaptor.block.plant.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public static void registerWoodSeries(BlockStateModelGenerator modelGenerator, BlockFamily blockFamily, Block log, Block wood, Block stripped_log, Block stripped_wood, Block planks, Block leaves, Block sapling, Block stairs, Block slab, Block button, Block pressurePlate, Block fence, Block fenceGate, Block door, Block trapdoor) {
        modelGenerator
                .registerLog(log)
                .log(log)
                .wood(wood);
        modelGenerator
                .registerLog(stripped_log)
                .log(stripped_log)
                .wood(stripped_wood);
        modelGenerator.registerSimpleCubeAll(leaves);
        BlockStateModelGenerator.BlockTexturePool woodPool = modelGenerator.registerCubeAllModelTexturePool(planks);
        woodPool.family(blockFamily);
        woodPool.stairs(stairs);
        woodPool.slab(slab);
        woodPool.button(button);
        woodPool.pressurePlate(pressurePlate);
        woodPool.fence(fence);
        woodPool.fenceGate(fenceGate);
        modelGenerator.registerDoor(door);
        modelGenerator.registerTrapdoor(trapdoor);
        modelGenerator.registerTintableCross(sapling, BlockStateModelGenerator.TintType.NOT_TINTED);

    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GRAVEL_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUND_BLOCK);
        blockStateModelGenerator.registerCrop(ModBlocks.SOYBEAN_CROP, SoyBeanCropBlock.AGE,0,1,2,3,4,5);
        blockStateModelGenerator.registerCrop(ModBlocks.MARIJUANA_CROP, MarijuanaCropBlock.AGE,0,1,2,3,4,5);

        BlockStateModelGenerator.BlockTexturePool dirtPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DIRT);
        dirtPool.stairs(DirtSeries.DIRT_STAIRS);
        dirtPool.slab(DirtSeries.DIRT_SLAB);
        dirtPool.button(DirtSeries.DIRT_BUTTON);
        dirtPool.pressurePlate(DirtSeries.DIRT_PRESSURE_PLATE);
        dirtPool.fence(DirtSeries.DIRT_FENCE);
        dirtPool.fenceGate(DirtSeries.DIRT_FENCE_GATE);
        dirtPool.wall(DirtSeries.DIRT_WALL);
        blockStateModelGenerator.registerDoor(DirtSeries.DIRT_DOOR);
        blockStateModelGenerator.registerTrapdoor(DirtSeries.DIRT_TRAPDOOR);

        BlockStateModelGenerator.BlockTexturePool glassPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.GLASS);
        glassPool.slab(ModBlocks.GLASS_SLAB);

        registerWoodSeries(
                blockStateModelGenerator,
                ChestnutSeries.CHESTNUT_FAMILY,
                ChestnutSeries.CHESTNUT_LOG,
                ChestnutSeries.CHESTNUT_WOOD,
                ChestnutSeries.STRIPPED_CHESTNUT_LOG,
                ChestnutSeries.STRIPPED_CHESTNUT_WOOD,
                ChestnutSeries.CHESTNUT_PLANKS,
                ChestnutSeries.CHESTNUT_LEAVES,
                ChestnutSeries.CHESTNUT_SAPLING,
                ChestnutSeries.CHESTNUT_STAIRS,
                ChestnutSeries.CHESTNUT_SLAB,
                ChestnutSeries.CHESTNUT_BUTTON,
                ChestnutSeries.CHESTNUT_PRESSURE_PLATE,
                ChestnutSeries.CHESTNUT_FENCE,
                ChestnutSeries.CHESTNUT_FENCE_GATE,
                ChestnutSeries.CHESTNUT_DOOR,
                ChestnutSeries.CHESTNUT_TRAPDOOR
                );

        blockStateModelGenerator.registerSimpleState(ModBlocks.STONE_MILL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARIJUANA, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOUR, Models.GENERATED);
        itemModelGenerator.register(ModItems.SALT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARIJUANA_LEAF, Models.GENERATED);
        itemModelGenerator.register(ModItems.PORCUPINE_SPAWN_EGG, new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));

        itemModelGenerator.register(ModItems.COPPER_AXE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_HOE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_PICKAXE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SHOVEL,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SWORD,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_HAMMER,Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_BOOTS);

        itemModelGenerator.register(ModItems.WOODEN_HAMMER,Models.HANDHELD);
        itemModelGenerator.register(ModItems.STONE_HAMMER,Models.HANDHELD);
        itemModelGenerator.register(ModItems.IRON_HAMMER,Models.HANDHELD);
        itemModelGenerator.register(ModItems.GOLDEN_HAMMER,Models.HANDHELD);
        itemModelGenerator.register(ModItems.DIAMOND_HAMMER,Models.HANDHELD);
        itemModelGenerator.register(ModItems.NETHERITE_HAMMER,Models.HANDHELD);

        itemModelGenerator.register(ChestnutSeries.HANGING_CHESTNUT_SIGN, Models.GENERATED);

        itemModelGenerator.register(ModItems.BAR_BRAWL_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(ModItems.SAKURA_VALLEY_MUSIC_DISC, Models.GENERATED);

        itemModelGenerator.register(ChestnutSeries.CHESTNUT_BOAT, Models.GENERATED);
        itemModelGenerator.register(ChestnutSeries.CHESTNUT_CHEST_BOAT, Models.GENERATED);

//        itemModelGenerator.register(ModItems.SOYBEAN, Models.GENERATED);
//        itemModelGenerator.register(ModItems.MARIJUANA_SEEDS, Models.GENERATED);
    }
}
