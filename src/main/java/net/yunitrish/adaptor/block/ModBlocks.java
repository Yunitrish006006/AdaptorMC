package net.yunitrish.adaptor.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.building.DirtSeries;
import net.yunitrish.adaptor.block.functional.SoundBlock;
import net.yunitrish.adaptor.block.functional.stoneMill.StoneMillBlock;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.block.plant.MarijuanaCropBlock;
import net.yunitrish.adaptor.block.plant.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.sound.ModSounds;

public class ModBlocks {
    public static void registerModBlocks () {
        Adaptor.LOGGER.info("Registering blocks...");
        DirtSeries.register();
        ChestnutSeries.register();
    }
    public static Block registerBlock(String name, Block block,boolean inItemGroup) {
        ModItems.registerItem(name, new BlockItem(block, new Item.Settings()),inItemGroup);
        return Registry.register(Registries.BLOCK, Adaptor.modIdentifier(name), block);
    }
    public static Block registerBlock(String name, Block block) {
        return registerBlock(name,block,true);
    }

    public static final Block SOUND_BLOCK = registerBlock("sound_block",new SoundBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(ModSounds.SOUND_BLOCK_SOUNDS)));

    public static final Block STONE_MILL = registerBlock(
            "stone_mill",
            new StoneMillBlock(
                    AbstractBlock
                            .Settings
                            .copy(Blocks.IRON_BLOCK)
                            .nonOpaque()
            )
    );
    public static Block GRAVEL_IRON_ORE = registerBlock(
            "gravel_iron_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(0,2),
                    AbstractBlock
                            .Settings
                            .copy(Blocks.GRAVEL)
            )
    );
    public static Block SOYBEAN_CROP = registerBlock(
            "soybean_crop",
            new SoyBeanCropBlock(
                    AbstractBlock.Settings.create()
                            .noCollision()
                            .ticksRandomly()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.CROP)
                            .pistonBehavior(PistonBehavior.DESTROY)
            ),false
    );
    public static Block MARIJUANA_CROP = registerBlock(
            "marijuana_crop",
            new MarijuanaCropBlock(
                    AbstractBlock.Settings.create()
                            .noCollision()
                            .ticksRandomly()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.CROP)
                            .pistonBehavior(PistonBehavior.DESTROY)
            ),false
    );
}
