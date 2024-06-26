package net.yunitrish.adaptor;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;

public class CauldronCookListener {

    public static List<CauldronRecipe> recipes = new ArrayList<>();

    public static int lastTick = -1;

    public static void initialize() {
        CauldronRecipe gravel = CauldronRecipe
                .NormalBuilder
                .setRecipeItem(Items.GRAVEL.getDefaultStack())
                .setResultItem(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack());
        recipes.add(gravel);
    }

    public static ActionResult run(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!(lastTick == -1 || Math.abs(player.age - lastTick) > 20)) {
            return ActionResult.PASS;
        } else {
            lastTick = player.age;
        }
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (!player.getMainHandStack().isEmpty()) return ActionResult.PASS;

        for (CauldronRecipe recipe : recipes) {
            if (recipe.set(world, hitResult.getBlockPos()).run(player)) {
                BlockPos core = hitResult.getBlockPos();
                player.sendMessage(Text.of("o"), true);
                world.playSound(core.getX(), core.getY(), core.getZ(), recipe.sound, SoundCategory.PLAYERS, 0.8f, 0.5f, true);
                return ActionResult.SUCCESS;
            }
            player.sendMessage(Text.of("x"), true);
        }
        return ActionResult.PASS;
    }

    public static void spawn(World world, BlockPos pos, ItemStack itemStack, int count) {
        itemStack.setCount(count);
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack));
    }

    public static class CauldronRecipe {
        public static CauldronRecipe NormalBuilder = new CauldronRecipe() {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.WATER_CAULDRON;
            }
        };
        public static CauldronRecipe BoiledBuilder = new CauldronRecipe() {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.WATER_CAULDRON && world.getBlockState(core.add(0, -1, 0)).getBlock() == Blocks.CAMPFIRE;
            }
        };
        public static CauldronRecipe LavaBuilder = new CauldronRecipe() {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.LAVA_CAULDRON;
            }
        };
        public static CauldronRecipe FreezeBuilder = new CauldronRecipe() {
            @Override
            public boolean deviceReady() {
                return !world.isClient() && world.getBlockState(core).getBlock() == Blocks.POWDER_SNOW_CAULDRON;
            }
        };
        private final List<ItemStack> recipeItem = new ArrayList<>();
        private final List<LivingEntity> recipeEntity = new ArrayList<>();
        private final List<ItemStack> itemResults = new ArrayList<>();
        private final List<LivingEntity> entityResults = new ArrayList<>();
        SoundEvent sound = SoundEvents.AMBIENT_UNDERWATER_ENTER;
        BlockPos core;
        World world;
        Box box;

        public boolean deviceReady() {
            return false;
        }

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
            //cook
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
    }
}
