package net.yunitrish.adaptor.block.LockableContainer.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerLootComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.LootableInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class GenericLootableContainerBlockEntity
        extends GenericLockableContainerBlockEntity
        implements LootableInventory {

    @Nullable
    protected RegistryKey<LootTable> lootTable;
    protected long lootTableSeed = 0L;

    protected GenericLootableContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Nullable
    @Override
    public RegistryKey<LootTable> getLootTable() {
        return this.lootTable;
    }

    @Override
    public void setLootTable(@Nullable RegistryKey<LootTable> lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    @Override
    public boolean isEmpty() {
        this.generateLoot(null);
        return super.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        this.generateLoot(null);
        return super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        this.generateLoot(null);
        return super.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        this.generateLoot(null);
        return super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.generateLoot(null);
        super.setStack(slot, stack);
    }

    @Override
    public boolean checkUnlocked(PlayerEntity player) {
        return super.checkUnlocked(player) && (this.lootTable == null || !player.isSpectator());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.checkUnlocked(playerEntity)) {
            this.generateLoot(playerInventory.player);
            return this.createScreenHandler(i, playerInventory);
        }
        return super.createMenu(i, playerInventory, playerEntity);
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        ContainerLootComponent containerLootComponent = components.get(DataComponentTypes.CONTAINER_LOOT);
        if (containerLootComponent != null) {
            this.lootTable = containerLootComponent.lootTable();
            this.lootTableSeed = containerLootComponent.seed();
        }
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        if (this.lootTable != null) {
            componentMapBuilder.add(DataComponentTypes.CONTAINER_LOOT, new ContainerLootComponent(this.lootTable, this.lootTableSeed));
        }
    }

    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        super.removeFromCopiedStackNbt(nbt);
        nbt.remove("LootTable");
        nbt.remove("LootTableSeed");
    }
}
