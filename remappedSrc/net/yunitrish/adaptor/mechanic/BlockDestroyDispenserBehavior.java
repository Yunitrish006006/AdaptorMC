package net.yunitrish.adaptor.mechanic;

import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;

public class BlockDestroyDispenserBehavior implements DispenserBehavior {
    @Override
    public ItemStack dispense(BlockPointer pointer, ItemStack stack) {
        return null;
    }
}
