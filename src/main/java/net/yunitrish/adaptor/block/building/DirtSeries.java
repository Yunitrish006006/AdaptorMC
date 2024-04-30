package net.yunitrish.adaptor.block.building;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.yunitrish.adaptor.Adaptor;

import static net.yunitrish.adaptor.block.ModBlocks.registerBlock;

public class DirtSeries {
    public static void register(){
        Adaptor.LOGGER.info("Registering dirt series...");
    }
    public static BlockSetType DIRT = new BlockSetType(
            "dirt",true,true,false,
            BlockSetType.ActivationRule.MOBS,
            BlockSoundGroup.GRAVEL,
            SoundEvents.BLOCK_GRAVEL_BREAK,
            SoundEvents.BLOCK_GRAVEL_PLACE,
            SoundEvents.BLOCK_GRAVEL_BREAK,
            SoundEvents.BLOCK_GRAVEL_PLACE,
            SoundEvents.BLOCK_GRAVEL_BREAK,
            SoundEvents.BLOCK_GRAVEL_PLACE,
            SoundEvents.BLOCK_GRAVEL_BREAK,
            SoundEvents.BLOCK_GRAVEL_PLACE
    );
    public static final Block DIRT_STAIRS = registerBlock("dirt_stairs",new StairsBlock(Blocks.DIRT.getDefaultState(), AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block DIRT_SLAB = registerBlock("dirt_slab",new SlabBlock(AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block DIRT_BUTTON = registerBlock("dirt_button",new ButtonBlock(DIRT,10,AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block DIRT_PRESSURE_PLATE = registerBlock("dirt_pressure_plate",new PressurePlateBlock(DIRT, AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block DIRT_FENCE = registerBlock("dirt_fence",new FenceBlock( AbstractBlock.Settings.copy(Blocks.DIRT)));
    static WoodType dirt = new WoodType("dirt",DIRT,
            BlockSoundGroup.ROOTED_DIRT,
            BlockSoundGroup.ROOTED_DIRT,
            SoundEvents.BLOCK_ROOTED_DIRT_PLACE,
            SoundEvents.BLOCK_ROOTED_DIRT_BREAK
            );
    public static final Block DIRT_FENCE_GATE = registerBlock("dirt_fence_gate",new FenceGateBlock(dirt, AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block DIRT_WALL = registerBlock("dirt_wall",new WallBlock(AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block DIRT_DOOR = registerBlock("dirt_door",new DoorBlock(DIRT, AbstractBlock.Settings.create().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block DIRT_TRAPDOOR = registerBlock("dirt_trapdoor",new TrapdoorBlock(DIRT, AbstractBlock.Settings.create().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
}
