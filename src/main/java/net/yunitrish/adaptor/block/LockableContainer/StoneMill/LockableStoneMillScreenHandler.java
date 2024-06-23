package net.yunitrish.adaptor.block.LockableContainer.StoneMill;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.yunitrish.adaptor.block.ModScreenHandlers;

public class LockableStoneMillScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public LockableStoneMillScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    public LockableStoneMillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.STONE_MILL_HANDLER_TYPE, syncId);
        if (inventory == null) {
            this.inventory = playerInventory;
        } else {
            this.inventory = inventory;
        }

        // Add your slots here, for example:
//        for (int i = 0; i < 3; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                this.addSlot(new Slot(inventory, j + i * 9, 8 + j * 18, 18 + i * 18));
//            }
//        }

        // Add the player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Add the player hotbar slots
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }
}
