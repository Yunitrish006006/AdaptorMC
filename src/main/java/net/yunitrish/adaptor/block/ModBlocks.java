package net.yunitrish.adaptor.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
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

    public static final Block SOUND_BLOCK = registerBlock(
            "sound_block",
            new SoundBlock(
                    AbstractBlock
                            .Settings
                            .copy(Blocks.IRON_BLOCK)
                            .sounds(ModSounds.SOUND_BLOCK_SOUNDS)
            )
    );
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
            new ColoredFallingBlock(
                    new ColorCode(-9356741),
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
            ), false, false
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
            ), false, false
    );

    public static Block registerBlock(String name, Block block, boolean registerItem, boolean inItemGroup) {
        Block temp = Registry.register(Registries.BLOCK, Adaptor.modIdentifier(name), block);
        if (registerItem) ModItems.registerItem(name, new BlockItem(temp, new Item.Settings()), inItemGroup);
        return temp;
    }

    public static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, true, true);
    }
    public static Block GLASS_SLAB = registerBlock(
            "glass_slab",
            new SlabBlock(
                    AbstractBlock.Settings.create()
                            .strength(0.3f)
                            .sounds(BlockSoundGroup.GLASS)
                            .nonOpaque()
                            .allowsSpawning(Blocks::never)
                            .solidBlock(Blocks::never)
                            .suffocates(Blocks::never)
                            .blockVision(Blocks::never)
            )
    );
}
