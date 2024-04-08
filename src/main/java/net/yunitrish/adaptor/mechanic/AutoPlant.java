package net.yunitrish.adaptor.mechanic;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.item.Items;
import net.yunitrish.adaptor.item.ModItems;

public class AutoPlant {
    public static void setUp() {
        DispenserBlock.registerBehavior(ModItems.CORN_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ModItems.SOYBEAN, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(ModItems.MARIJUANA_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEETROOT_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.MELON_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.PUMPKIN_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.WHEAT_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.TORCHFLOWER_SEEDS, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.NETHER_WART, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.POTATO, new BlockPlacementDispenserBehavior());
        DispenserBlock.registerBehavior(Items.CARROT, new BlockPlacementDispenserBehavior());
    }
}
