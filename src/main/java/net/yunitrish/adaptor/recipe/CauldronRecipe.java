package net.yunitrish.adaptor.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;

public abstract class CauldronRecipe {
    private final List<ItemStack> recipeItem = new ArrayList<>();
    private final List<LivingEntity> recipeEntity = new ArrayList<>();
    private final List<ItemStack> itemResults = new ArrayList<>();
    private final List<LivingEntity> entityResults = new ArrayList<>();
    protected final SoundEvent sound = SoundEvents.AMBIENT_UNDERWATER_ENTER;
    protected BlockPos core;
    protected World world;
    protected Box box;

    protected abstract boolean deviceReady();

    public CauldronRecipe setRecipeItem(ItemStack... items) {
        recipeItem.addAll(Arrays.asList(items));
        return this;
    }

    public CauldronRecipe setResultItem(ItemStack... items) {
        itemResults.addAll(Arrays.asList(items));
        return this;
    }
    public CauldronRecipe setRecipeEntity(LivingEntity... entities) {
        recipeEntity.addAll(Arrays.asList(entities));
        return this;
    }

    public CauldronRecipe setResultEntity(LivingEntity... entities) {
        entityResults.addAll(Arrays.asList(entities));
        return this;
    }

    public CauldronRecipe set(World world, BlockPos blockPos) {
        this.core = blockPos;
        this.world = world;
        this.box = Box.of(core.toCenterPos(), 0.5, 0.5, 0.5);
        return this;
    }

    public boolean run(PlayerEntity player) {
        if (!deviceReady()) return false;
        Map<Item, ItemEntity> itemIngredient = new HashMap<>();
        //list item in cauldron
        for (ItemEntity itemEntity : world.getEntitiesByClass(ItemEntity.class, box, itemEntity -> {
            for (ItemStack recipe : recipeItem) {
                if (recipe.getItem() == itemEntity.getStack().getItem()) {
                    return true;
                }
            }
            return false;
        })) {
            Item type = itemEntity.getStack().getItem();
            if (itemIngredient.containsKey(type)) {
                itemIngredient.get(type).getStack().increment(itemEntity.getStack().getCount());
                itemEntity.setDespawnImmediately();
            } else {
                itemIngredient.put(itemEntity.getStack().getItem(), itemEntity);
            }
        }
        int maxCount = Integer.MAX_VALUE;
        //check recipe
        for (ItemStack recipe : recipeItem) {
            Item type = recipe.getItem();
            if (itemIngredient.containsKey(type)) {
                int temp = itemIngredient.get(type).getStack().getCount() / recipe.getCount();
                if (temp < maxCount) {
                    maxCount = temp;
                }
            } else {
                return false;
            }
        }
        if (player.isSneaking()) {
            for (ItemStack recipe : recipeItem) {
                Item type = recipe.getItem();
                itemIngredient.get(type).getStack().decrement(maxCount);
            }
            for (ItemStack result : itemResults) {
                spawn(world, core, result, maxCount);
            }
            return true;
        } else {
            for (ItemStack recipe : recipeItem) {
                Item type = recipe.getItem();
                itemIngredient.get(type).getStack().decrement(1);
            }
            for (ItemStack result : itemResults) {
                spawn(world, core, result, 1);
            }
            return true;
        }
    }
    //------------concrete pre build------------------//
    public static RecipeType.NormalCauldronRecipe NORMALRECIPE = new RecipeType.NormalCauldronRecipe();
    public static RecipeType.BoiledCauldronRecipe BOILRECIPE = new RecipeType.BoiledCauldronRecipe();
    public static RecipeType.LavaCauldronRecipe LAVARECIPE = new RecipeType.LavaCauldronRecipe();
    public static RecipeType.FreezeCauldronRecipe FREEZERECIPE = new RecipeType.FreezeCauldronRecipe();

    //----------end concrete pre build---------------//
    public static class RecipeType {
        public static class NormalCauldronRecipe extends CauldronRecipe {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.WATER_CAULDRON;
            }
        }
        public static class BoiledCauldronRecipe extends CauldronRecipe {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.WATER_CAULDRON && world.getBlockState(core.down()).getBlock() == Blocks.CAMPFIRE;
            }
        }
        public static class LavaCauldronRecipe extends CauldronRecipe {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.LAVA_CAULDRON;
            }
        }

        public static class FreezeCauldronRecipe extends CauldronRecipe {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.POWDER_SNOW_CAULDRON;
            }
        }
    }

    protected void spawn(World world, BlockPos pos, ItemStack itemStack, int count) {
        itemStack.setCount(count);
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack.copy()));
        world.playSound(null, pos, sound, SoundCategory.PLAYERS, 0.8f, 0.5f);
    }
}