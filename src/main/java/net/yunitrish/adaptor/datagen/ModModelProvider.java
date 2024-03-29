package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.custom.CornCropBlock;
import net.yunitrish.adaptor.block.custom.MarijuanaPlantBlock;
import net.yunitrish.adaptor.block.custom.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;

import java.util.Optional;

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

        blockStateModelGenerator.registerCrop(ModBlocks.SOYBEAN_CROP, SoyBeanCropBlock.AGE,0,1,2,3,4,5);
        blockStateModelGenerator.registerCrop(ModBlocks.MARIJUANA_PLANT, MarijuanaPlantBlock.AGE,0,1,2,3,4,5);
        blockStateModelGenerator.registerCrop(ModBlocks.CORN_CROP, CornCropBlock.AGE,0,1,2,3,4,5,6,7,8);

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.DAHLIA, ModBlocks.POTTED_DAHLIA, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleState(ModBlocks.STONE_MILL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SALT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAMBOO_COAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOUR, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARIJUANA_LEAF, Models.GENERATED);
        itemModelGenerator.register(ModItems.MARIJUANA, Models.GENERATED);

        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.GENERATED);

        itemModelGenerator.register(ModItems.COPPER_AXE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_HOE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_PICKAXE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SHOVEL,Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SWORD,Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.COPPER_BOOTS);

        itemModelGenerator.register(ModItems.PORCUPINE_SPAWN_EGG, new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));

        itemModelGenerator.register(ModItems.BAR_BRAWL_MUSIC_DISC, Models.GENERATED);
        itemModelGenerator.register(ModItems.SAKURA_VALLEY_MUSIC_DISC, Models.GENERATED);
    }
}
