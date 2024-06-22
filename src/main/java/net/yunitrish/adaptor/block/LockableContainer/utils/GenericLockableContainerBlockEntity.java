package net.yunitrish.adaptor.block.LockableContainer.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.yunitrish.adaptor.common.AdaptorApi;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class GenericLockableContainerBlockEntity
        extends BlockEntity
        implements Inventory,
        NamedScreenHandlerFactory,
        Nameable {
    private GenericContainerLock lock = GenericContainerLock.EMPTY;
    @Nullable
    private Text customName;

    public GenericLockableContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static boolean checkUnlocked(PlayerEntity player, GenericContainerLock lock, Text containerName) {
        if (player.isSpectator() || lock.canOpen(player.getUuid())) {
            return true;
        }
        player.sendMessage(Text.translatable("adaptor.container.is_locked", containerName), true);
        player.playSoundToPlayer(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return false;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.lock = GenericContainerLock.fromNbt(nbt);
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            this.customName = GenericLockableContainerBlockEntity.tryParseCustomName(nbt.getString("CustomName"), registryLookup);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        this.lock.writeNbt(nbt);
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serialization.toJsonString(this.customName, registryLookup));
        }
    }

    @Override
    public Text getName() {
        if (this.customName != null) {
            return this.customName;
        }
        return this.getContainerName();
    }

    @Override
    public Text getDisplayName() {
        return this.getName();
    }

    @Override
    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    protected abstract Text getContainerName();

    public boolean checkUnlocked(PlayerEntity player) {
        return GenericLockableContainerBlockEntity.checkUnlocked(player, this.lock, this.getDisplayName());
    }

    public void installLock(PlayerEntity player) {
        if (this.lock.owner() == null) {
            this.lock = new GenericContainerLock(player.getUuid(), new ArrayList<>());
            player.sendMessage(Text.translatable("adaptor.container.lock_installed"), true);
        } else {
            player.sendMessage(Text.translatable("adaptor.container.is_locked", getContainerName()), true);
        }
    }

    public void addToLock(PlayerEntity owner, PlayerEntity player) {
        if (this.lock.owner() == null) {
            player.sendMessage(Text.translatable("adaptor.container.no_owner"), true);
        } else if (this.lock.owner().equals(owner.getUuid())) {
            this.lock.members().add(player.getUuid());
            player.sendMessage(Text.translatable("adaptor.container.member_added"), true);
        } else {
            player.sendMessage(Text.translatable("container.isLocked", getContainerName()), true);
        }
    }

    protected abstract DefaultedList<ItemStack> getHeldStacks();

    protected abstract void setHeldStacks(DefaultedList<ItemStack> var1);

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.getHeldStacks()) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.getHeldStacks().get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.getHeldStacks(), slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.getHeldStacks(), slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.getHeldStacks().set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        this.getHeldStacks().clear();
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (this.checkUnlocked(playerEntity)) {
            return this.createScreenHandler(i, playerInventory);
        }
        return null;
    }

    protected abstract ScreenHandler createScreenHandler(int var1, PlayerInventory var2);

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        this.customName = components.get(DataComponentTypes.CUSTOM_NAME);
        this.lock = components.getOrDefault(AdaptorApi.GENERIC_LOCK, GenericContainerLock.EMPTY);
        components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.getHeldStacks());
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.CUSTOM_NAME, this.customName);
        if (!this.lock.equals(GenericContainerLock.EMPTY)) {
            componentMapBuilder.add(AdaptorApi.GENERIC_LOCK, this.lock);
        }
        componentMapBuilder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.getHeldStacks()));
    }

    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        nbt.remove("CustomName");
        nbt.remove("GenericLock");
        nbt.remove("Items");
    }
}
