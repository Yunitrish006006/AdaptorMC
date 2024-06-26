package net.yunitrish.adaptor.block.LockableContainer.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.yunitrish.adaptor.block.LockableContainer.Furnaces.GenericFurnaceBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericFurnaceBlockEntity
        extends GenericLockableContainerBlockEntity
        implements SidedInventory,
        RecipeUnlocker,
        RecipeInputProvider {
    public static final int BURN_TIME_PROPERTY_INDEX = 0;
    public static final int FUEL_TIME_PROPERTY_INDEX = 1;
    public static final int COOK_TIME_PROPERTY_INDEX = 2;
    public static final int COOK_TIME_TOTAL_PROPERTY_INDEX = 3;
    public static final int PROPERTY_COUNT = 4;
    public static final int DEFAULT_COOK_TIME = 200;
    public static final int field_31295 = 2;
    protected static final int INPUT_SLOT_INDEX = 0;
    protected static final int FUEL_SLOT_INDEX = 1;
    protected static final int OUTPUT_SLOT_INDEX = 2;
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    @Nullable
    private static volatile Map<Item, Integer> fuelTimes;
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return GenericFurnaceBlockEntity.this.burnTime;
                }
                case 1: {
                    return GenericFurnaceBlockEntity.this.fuelTime;
                }
                case 2: {
                    return GenericFurnaceBlockEntity.this.cookTime;
                }
                case 3: {
                    return GenericFurnaceBlockEntity.this.cookTimeTotal;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    GenericFurnaceBlockEntity.this.burnTime = value;
                    break;
                }
                case 1: {
                    GenericFurnaceBlockEntity.this.fuelTime = value;
                    break;
                }
                case 2: {
                    GenericFurnaceBlockEntity.this.cookTime = value;
                    break;
                }
                case 3: {
                    GenericFurnaceBlockEntity.this.cookTimeTotal = value;
                    break;
                }
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public GenericFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(type, pos, state);
        this.matchGetter = RecipeManager.createCachedMatchGetter(recipeType);
    }

    public static void clearFuelTimes() {
        fuelTimes = null;
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        Map<Item, Integer> map = fuelTimes;
        if (map != null) {
            return map;
        }
        LinkedHashMap<Item, Integer> map2 = Maps.newLinkedHashMap();
        GenericFurnaceBlockEntity.addFuel(map2, Items.LAVA_BUCKET, 20000);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.COAL_BLOCK, 16000);
        GenericFurnaceBlockEntity.addFuel(map2, Items.BLAZE_ROD, 2400);
        GenericFurnaceBlockEntity.addFuel(map2, Items.COAL, 1600);
        GenericFurnaceBlockEntity.addFuel(map2, Items.CHARCOAL, 1600);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.LOGS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.BAMBOO_BLOCKS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.PLANKS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.BAMBOO_MOSAIC, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_STAIRS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.BAMBOO_MOSAIC_STAIRS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_SLABS, 150);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.BAMBOO_MOSAIC_SLAB, 150);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_TRAPDOORS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_PRESSURE_PLATES, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_FENCES, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.FENCE_GATES, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.NOTE_BLOCK, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.BOOKSHELF, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.CHISELED_BOOKSHELF, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.LECTERN, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.JUKEBOX, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.CHEST, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.TRAPPED_CHEST, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.CRAFTING_TABLE, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.DAYLIGHT_DETECTOR, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.BANNERS, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Items.BOW, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Items.FISHING_ROD, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.LADDER, 300);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.SIGNS, 200);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.HANGING_SIGNS, 800);
        GenericFurnaceBlockEntity.addFuel(map2, Items.WOODEN_SHOVEL, 200);
        GenericFurnaceBlockEntity.addFuel(map2, Items.WOODEN_SWORD, 200);
        GenericFurnaceBlockEntity.addFuel(map2, Items.WOODEN_HOE, 200);
        GenericFurnaceBlockEntity.addFuel(map2, Items.WOODEN_AXE, 200);
        GenericFurnaceBlockEntity.addFuel(map2, Items.WOODEN_PICKAXE, 200);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_DOORS, 200);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.BOATS, 1200);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOOL, 100);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOODEN_BUTTONS, 100);
        GenericFurnaceBlockEntity.addFuel(map2, Items.STICK, 100);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.SAPLINGS, 100);
        GenericFurnaceBlockEntity.addFuel(map2, Items.BOWL, 100);
        GenericFurnaceBlockEntity.addFuel(map2, ItemTags.WOOL_CARPETS, 67);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.DRIED_KELP_BLOCK, 4001);
        GenericFurnaceBlockEntity.addFuel(map2, Items.CROSSBOW, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.BAMBOO, 50);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.DEAD_BUSH, 100);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.SCAFFOLDING, 50);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.LOOM, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.BARREL, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.CARTOGRAPHY_TABLE, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.FLETCHING_TABLE, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.SMITHING_TABLE, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.COMPOSTER, 300);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.AZALEA, 100);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.FLOWERING_AZALEA, 100);
        GenericFurnaceBlockEntity.addFuel(map2, Blocks.MANGROVE_ROOTS, 300);
        fuelTimes = map2;
        return map2;
    }

    /**
     * {@return whether the provided {@code item} is in the {@link
     * net.minecraft.registry.tag.ItemTags#NON_FLAMMABLE_WOOD non_flammable_wood} tag}
     */
    private static boolean isNonFlammableWood(Item item) {
        return item.getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(tag)) {
            if (GenericFurnaceBlockEntity.isNonFlammableWood(registryEntry.value())) continue;
            fuelTimes.put(registryEntry.value(), fuelTime);
        }
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        if (GenericFurnaceBlockEntity.isNonFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName(null).getString() + " a furnace fuel. That will not work!"));
            }
            return;
        }
        fuelTimes.put(item2, fuelTime);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GenericFurnaceBlockEntity blockEntity) {
        boolean bl4;
        boolean bl = blockEntity.isBurning();
        boolean bl2 = false;
        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
        }
        ItemStack itemStack = blockEntity.inventory.get(1);
        ItemStack itemStack2 = blockEntity.inventory.get(0);
        boolean bl3 = !itemStack2.isEmpty();
        boolean bl5 = bl4 = !itemStack.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            RecipeEntry recipeEntry = bl3 ? blockEntity.matchGetter.getFirstMatch(new SingleStackRecipeInput(itemStack2), world).orElse(null) : null;
            int i = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && GenericFurnaceBlockEntity.canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                blockEntity.fuelTime = blockEntity.burnTime = blockEntity.getFuelTime(itemStack);
                if (blockEntity.isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            blockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }
            if (blockEntity.isBurning() && GenericFurnaceBlockEntity.canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                ++blockEntity.cookTime;
                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    blockEntity.cookTimeTotal = GenericFurnaceBlockEntity.getCookTime(world, blockEntity);
                    if (GenericFurnaceBlockEntity.craftRecipe(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }
                    bl2 = true;
                }
            } else {
                blockEntity.cookTime = 0;
            }
        } else if (!blockEntity.isBurning() && blockEntity.cookTime > 0) {
            blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
        }
        if (bl != blockEntity.isBurning()) {
            bl2 = true;
            state = state.with(GenericFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (bl2) {
            GenericFurnaceBlockEntity.markDirty(world, pos, state);
        }
    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (slots.get(0).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack itemStack = recipe.value().getResult(registryManager);
        if (itemStack.isEmpty()) {
            return false;
        }
        ItemStack itemStack2 = slots.get(2);
        if (itemStack2.isEmpty()) {
            return true;
        }
        if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
            return false;
        }
        if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
            return true;
        }
        return itemStack2.getCount() < itemStack.getMaxCount();
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe == null || !GenericFurnaceBlockEntity.canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            return false;
        }
        ItemStack itemStack = slots.get(0);
        ItemStack itemStack2 = recipe.value().getResult(registryManager);
        ItemStack itemStack3 = slots.get(2);
        if (itemStack3.isEmpty()) {
            slots.set(2, itemStack2.copy());
        } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
            itemStack3.increment(1);
        }
        if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !slots.get(1).isEmpty() && slots.get(1).isOf(Items.BUCKET)) {
            slots.set(1, new ItemStack(Items.WATER_BUCKET));
        }
        itemStack.decrement(1);
        return true;
    }

    private static int getCookTime(World world, GenericFurnaceBlockEntity furnace) {
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(furnace.getStack(0));
        return furnace.matchGetter.getFirstMatch(singleStackRecipeInput, world).map(recipe -> recipe.value().getCookingTime()).orElse(200);
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return GenericFurnaceBlockEntity.createFuelTimeMap().containsKey(stack.getItem());
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float) multiplier * experience);
        float f = MathHelper.fractionalPart((float) multiplier * experience);
        if (f != 0.0f && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrbEntity.spawn(world, pos, i);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putShort("BurnTime", (short) this.burnTime);
        nbt.putShort("CookTime", (short) this.cookTime);
        nbt.putShort("CookTimeTotal", (short) this.cookTimeTotal);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), count));
        nbt.put("RecipesUsed", nbtCompound);
    }

    @Override
    protected Text getContainerName() {
        return null;
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return GenericFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        if (side == Direction.UP) {
            return TOP_SLOTS;
        }
        return SIDE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 1) {
            return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        this.inventory.set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        if (slot == 0 && !bl) {
            this.cookTimeTotal = GenericFurnaceBlockEntity.getCookTime(this.world, this);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    protected ScreenHandler createScreenHandler(int var1, PlayerInventory var2) {
        return null;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        }
        if (slot == 1) {
            ItemStack itemStack = this.inventory.get(1);
            return GenericFurnaceBlockEntity.canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    @Nullable
    public RecipeEntry<?> getLastRecipe() {
        return null;
    }

    @Override
    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.id();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);
        for (RecipeEntry<?> recipeEntry : list) {
            if (recipeEntry == null) continue;
            player.onRecipeCrafted(recipeEntry, this.inventory);
        }
        this.recipesUsed.clear();
    }

    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        ArrayList<RecipeEntry<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry entry : this.recipesUsed.object2IntEntrySet()) {
            world.getRecipeManager().get((Identifier) entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                GenericFurnaceBlockEntity.dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe) recipe.value()).getExperience());
            });
        }
        return list;
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }
    }
}
