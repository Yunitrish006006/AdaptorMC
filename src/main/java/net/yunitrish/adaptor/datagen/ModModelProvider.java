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
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
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
        blockStateModelGenerator.registerCrop(ModBlocks.Crops.SOYBEAN_CROP, Properties.AGE_5, 0, 1, 2, 3, 4, 5);
        blockStateModelGenerator.registerCrop(ModBlocks.Crops.MARIJUANA_CROP, Properties.AGE_5, 0, 1, 2, 3, 4, 5);

        BlockStateModelGenerator.BlockTexturePool dirtPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.DIRT);
        dirtPool.stairs(ModBlocks.Dirt.DIRT_STAIRS);
        dirtPool.slab(ModBlocks.Dirt.DIRT_SLAB);
        dirtPool.button(ModBlocks.Dirt.DIRT_BUTTON);
        dirtPool.pressurePlate(ModBlocks.Dirt.DIRT_PRESSURE_PLATE);
        dirtPool.fence(ModBlocks.Dirt.DIRT_FENCE);
        dirtPool.fenceGate(ModBlocks.Dirt.DIRT_FENCE_GATE);
        dirtPool.wall(ModBlocks.Dirt.DIRT_WALL);
        blockStateModelGenerator.registerDoor(ModBlocks.Dirt.DIRT_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.Dirt.DIRT_TRAPDOOR);

        BlockStateModelGenerator.BlockTexturePool glassPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.GLASS);
        glassPool.slab(ModBlocks.GLASS_SLAB);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.Container.LOCKABLE_CRATE);

        registerWoodSeries(
                blockStateModelGenerator,
                ModBlocks.Chestnut.CHESTNUT_FAMILY,
                ModBlocks.Chestnut.CHESTNUT_LOG,
                ModBlocks.Chestnut.CHESTNUT_WOOD,
                ModBlocks.Chestnut.STRIPPED_CHESTNUT_LOG,
                ModBlocks.Chestnut.STRIPPED_CHESTNUT_WOOD,
                ModBlocks.Chestnut.CHESTNUT_PLANKS,
                ModBlocks.Chestnut.CHESTNUT_LEAVES,
                ModBlocks.Chestnut.CHESTNUT_SAPLING,
                ModBlocks.Chestnut.CHESTNUT_STAIRS,
                ModBlocks.Chestnut.CHESTNUT_SLAB,
                ModBlocks.Chestnut.CHESTNUT_BUTTON,
                ModBlocks.Chestnut.CHESTNUT_PRESSURE_PLATE,
                ModBlocks.Chestnut.CHESTNUT_FENCE,
                ModBlocks.Chestnut.CHESTNUT_FENCE_GATE,
                ModBlocks.Chestnut.CHESTNUT_DOOR,
                ModBlocks.Chestnut.CHESTNUT_TRAPDOOR
                );

        blockStateModelGenerator.registerSimpleState(ModBlocks.Container.LOCKABLE_STONE_MILL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.Tools.METAL_DETECTOR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARIJUANA, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOUR, Models.GENERATED);
        itemModelGenerator.register(ModItems.SALT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARIJUANA_LEAF, Models.GENERATED);
        itemModelGenerator.register(ModItems.PORCUPINE_SPAWN_EGG, new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));

        itemModelGenerator.register(ModItems.Tools.COPPER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.COPPER_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.COPPER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.COPPER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.COPPER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.COPPER_HAMMER, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.Tools.COPPER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.Tools.COPPER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.Tools.COPPER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.Tools.COPPER_BOOTS);

        itemModelGenerator.register(ModItems.Tools.COPPER_KEY, Models.GENERATED);

        itemModelGenerator.register(ModItems.Tools.WOODEN_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.STONE_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.IRON_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.GOLDEN_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.DIAMOND_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.Tools.NETHERITE_HAMMER, Models.HANDHELD);

        itemModelGenerator.register(ModBlocks.Chestnut.HANGING_CHESTNUT_SIGN, Models.GENERATED);

        itemModelGenerator.register(ModItems.Tools.BAR_BRAWL_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(ModItems.Tools.SAKURA_VALLEY_MUSIC_DISC, Models.GENERATED);

//        itemModelGenerator.register(ChestnutSeries.CHESTNUT_BOAT, Models.GENERATED);
//        itemModelGenerator.register(ChestnutSeries.CHESTNUT_CHEST_BOAT, Models.GENERATED);

//        itemModelGenerator.register(ModItems.SOYBEAN, Models.GENERATED);
//        itemModelGenerator.register(ModItems.MARIJUANA_SEEDS, Models.GENERATED);
    }
}
