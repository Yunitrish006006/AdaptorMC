package net.yunitrish.adaptor.block.functional.stoneMill;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.yunitrish.adaptor.block.ModBlockEntities;
import net.yunitrish.adaptor.item.ImplementedInventory;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.screen.StoneMillScreenHandler;
import org.jetbrains.annotations.Nullable;

public class StoneMillBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {


    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2,ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public StoneMillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_MILL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> StoneMillBlockEntity.this.progress;
                    case 1 -> StoneMillBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> StoneMillBlockEntity.this.progress = value;
                    case 1 -> StoneMillBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return StoneMillData.PACKET_CODEC;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.adaptor.stone_mill");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }


    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt,registryLookup);
        Inventories.writeNbt(nbt,inventory,registryLookup);
        nbt.putInt("stone_mill.progress",progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt,registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        progress = nbt.getInt("stone_mill.progress");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StoneMillScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient()) {
            return;
        }

        if(isOutputSlotEmptyOrReceivable()) {
            if(this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if(hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

//    public ItemStack getRenderStack() {
//        if (this.getStack(OUTPUT_SLOT).isEmpty()) return this.getStack(INPUT_SLOT);
//        else return this.getStack(OUTPUT_SLOT);
//    }

//    @Override
//    public void markDirty() {
//        if (world == null) return;
//        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
//        super.markDirty();
//    }
//
    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
//        Optional<RecipeEntry<StoneMillRecipe>> recipe = getCurrentRecipe();
        this.removeStack(INPUT_SLOT, 1);
        ItemStack result = new ItemStack(ModItems.FLOUR);
        this.setStack(OUTPUT_SLOT,new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
//        recipe.ifPresent(stoneMillRecipeRecipeEntry -> this.setStack(OUTPUT_SLOT, new ItemStack(stoneMillRecipeRecipeEntry.value().getResult(null).getItem(), getStack(OUTPUT_SLOT).getCount() + stoneMillRecipeRecipeEntry.value().getResult(null).getCount())));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }
//
    private boolean hasRecipe() {
//        Optional<RecipeEntry<StoneMillRecipe>> recipe = getCurrentRecipe();
//
//        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null)) && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
        ItemStack result = new ItemStack(ModItems.FLOUR);
        boolean hasInput = getStack(INPUT_SLOT).getItem() == Items.WHEAT;

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }
//
//    private Optional<RecipeEntry<StoneMillRecipe>> getCurrentRecipe() {
//        SimpleInventory inventory = new SimpleInventory(this.size());
//        for (int i=0;i<this.size();i++) {
//            inventory.setStack(i,this.getStack(i));
//        }
//        assert getWorld() != null;
//        return getWorld().getRecipeManager().getFirstMatch(StoneMillRecipe.Type.INSTANCE, inventory, getWorld());
//    }
//
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result){
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }
//
    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }
//
//    @Nullable
//    @Override
//    public Packet<ClientPlayPacketListener> toUpdatePacket() {
//        return BlockEntityUpdateS2CPacket.create(this);
//    }
//
//    @Override
//    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
//        return super.toInitialChunkDataNbt(registryLookup);
//    }
}

