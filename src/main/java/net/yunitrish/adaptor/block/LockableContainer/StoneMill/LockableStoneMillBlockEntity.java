package net.yunitrish.adaptor.block.LockableContainer.StoneMill;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.yunitrish.adaptor.block.LockableContainer.utils.GenericLootableContainerBlockEntity;
import net.yunitrish.adaptor.block.ModBlockEntities;

public class LockableStoneMillBlockEntity extends GenericLootableContainerBlockEntity implements ScreenHandlerFactory {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private final int progress = 0;
    private final int maxProgress = 72;
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public LockableStoneMillBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.LOCKABLE_STONE_MILL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registryLookup);
        }
        nbt.putInt("Progress", this.progress);
        nbt.putInt("MaxProgress", this.maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registryLookup);
        }
        nbt.putInt("Progress", this.progress);
        nbt.putInt("MaxProgress", this.maxProgress);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.adaptor.stone_mill");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new LockableStoneMillScreenHandler(syncId, playerInventory);
    }

    @Override
    public int size() {
        return 2;
    }
}
