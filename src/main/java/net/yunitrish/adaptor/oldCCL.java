package net.yunitrish.adaptor;


import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;

/*
 * this works fine currently
 * */
public class oldCCL {
    public static ActionResult run(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (!player.getMainHandStack().isEmpty()) return ActionResult.PASS;

        CauldronRecipe gravel = new CauldronRecipe()
                .setName("gravel")
                .set(world, hitResult.getBlockPos())
                .setRecipeItem(Items.GRAVEL.getDefaultStack())
                .setResultItem(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack())
                .setDeviceReady(!world.isClient && world.getBlockState(hitResult.getBlockPos()).getBlock().equals(Blocks.WATER_CAULDRON));
        if (gravel.run(player)) return ActionResult.SUCCESS;
        CauldronRecipe sand = new CauldronRecipe()
                .setName("sand")
                .set(world, hitResult.getBlockPos())
                .setRecipeItem(Items.SAND.getDefaultStack())
                .setResultItem(Items.CLAY.getDefaultStack())
                .setDeviceReady(!world.isClient && world.getBlockState(hitResult.getBlockPos()).getBlock().equals(Blocks.WATER_CAULDRON));
        if (sand.run(player)) return ActionResult.SUCCESS;
        return ActionResult.PASS;
    }

    public static void spawnItem(World world, BlockPos pos, ItemStack itemStack, int count) {
        ItemStack spawned = itemStack.copy();
        spawned.setCount(count);
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, spawned));
    }

    public static void spawnEntity(World world, BlockPos pos, String entity) {
        LivingEntity result;

        switch (entity) {
            case "minecraft.entity.zombie": {
                result = new ZombieEntity(world);
                result.getType().getTranslationKey();
                break;
            }
            case "minecraft.entity.husk": {
                result = new HuskEntity(EntityType.HUSK, world);
                break;
            }
            default: {
                result = new PigEntity(EntityType.PIG, world);
                break;
            }
        }
        result.setPos(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(result);
    }

    public static class CauldronRecipe {
        private final Map<String, Integer> recipeEntity = new HashMap<>();
        private final List<ItemStack> recipeItem = new ArrayList<>();
        private final Map<String, Integer> entityResults = new HashMap<>();
        private final List<ItemStack> itemResults = new ArrayList<>();
        private final List<ItemStack> Structure = new ArrayList<>();
        public String id;
        BlockPos core;
        World world;
        Box box;
        boolean deviceReady;

        public CauldronRecipe setDeviceReady(boolean deviceReady) {
            this.deviceReady = deviceReady;
            return this;
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
            for (LivingEntity entity : entities) {
                if (recipeEntity.containsKey(entity.getType().getTranslationKey())) {
                    recipeEntity.replace(entity.getType().getTranslationKey(), recipeEntity.get(entity.getType().getTranslationKey()) + 1);
                } else {
                    recipeEntity.put(entity.getType().getTranslationKey(), 1);
                }
            }
            return this;
        }

        public CauldronRecipe setResultEntity(LivingEntity... entities) {
            for (LivingEntity entity : entities) {
                if (entityResults.containsKey(entity.getType().getTranslationKey())) {
                    entityResults.replace(entity.getType().getTranslationKey(), entityResults.get(entity.getType().getTranslationKey()) + 1);
                } else {
                    entityResults.put(entity.getType().getTranslationKey(), 1);
                }
            }
            return this;
        }

        public CauldronRecipe set(World world, BlockPos blockPos) {
            this.core = blockPos;
            this.world = world;
            this.box = Box.of(core.toCenterPos(), 0.5, 0.5, 0.5);
            return this;
        }

        public CauldronRecipe setName(String name) {
            this.id = name;
            return this;
        }

        public boolean run(PlayerEntity player) {
            Adaptor.LOGGER.info(id + "# entered");
            if (!deviceReady) return false;
            Adaptor.LOGGER.info(id + "# device checked");
            //list item in cauldron
            Map<Item, ItemEntity> itemIngredient = new HashMap<>();
            for (ItemEntity itemEntity : world.getEntitiesByClass(ItemEntity.class, box, itemEntity -> {
                for (ItemStack recipe : recipeItem) {
                    if (recipe.getItem() == itemEntity.getStack().getItem()) {
                        return true;
                    }
                }
                return false;
            })) {
                Item itemType = itemEntity.getStack().getItem();
                if (itemIngredient.containsKey(itemType)) {
                    itemIngredient.get(itemType).getStack().increment(itemEntity.getStack().getCount());
                    itemEntity.setDespawnImmediately();
                } else {
                    itemIngredient.put(itemEntity.getStack().getItem(), itemEntity);
                }
            }
            Adaptor.LOGGER.info(id + "# item ingredient searched");
            //list entity in cauldron
            Map<String, List<LivingEntity>> entityIngredient = new HashMap<>();
            for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, box, entity -> {
                for (Map.Entry<String, Integer> recipe : recipeEntity.entrySet()) {
                    if (recipe.getKey().equals(entity.getType().getTranslationKey())) {
                        return true;
                    }
                }
                return false;
            })) {
                EntityType<?> entityType = entity.getType();

                if (entityIngredient.containsKey(entityType.getTranslationKey())) {
                    entityIngredient.get(entityType.getTranslationKey()).add(entity);
                } else {
                    entityIngredient.put(entityType.getTranslationKey(), List.of(entity));
                }
            }
            Adaptor.LOGGER.info(id + "# entity ingredient searched");
            int maxCount = Integer.MAX_VALUE;
            //check item recipe
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
            Adaptor.LOGGER.info(id + "# item recipe checked");
            //check entity recipe
            for (Map.Entry<String, Integer> recipe : recipeEntity.entrySet()) {
                String type = recipe.getKey();
                if (entityIngredient.containsKey(type)) {
                    int temp = entityIngredient.get(type).size() / recipe.getValue();
                    if (temp < maxCount) {
                        maxCount = temp;
                    }
                } else {
                    return false;
                }
            }
            Adaptor.LOGGER.info(id + "# entity recipe checked");
            //cook
            if (player.isSneaking()) {
                cook(maxCount, world, core, recipeItem, recipeEntity, itemIngredient, entityIngredient, itemResults, entityResults);
            } else {
                cook(1, world, core, recipeItem, recipeEntity, itemIngredient, entityIngredient, itemResults, entityResults);
            }
            Adaptor.LOGGER.info(id + "# cooked");
            return true;
        }

        public void cook(
                int maxCount, World world, BlockPos core,
                List<ItemStack> recipeItem, Map<String, Integer> recipeEntity,
                Map<Item, ItemEntity> itemIngredient, Map<String, List<LivingEntity>> entityIngredient,
                List<ItemStack> itemResults, Map<String, Integer> entityResults
        ) {
            for (ItemStack recipe : recipeItem) {
                Item type = recipe.getItem();
                itemIngredient.get(type).getStack().decrement(maxCount);
            }
            for (Map.Entry<String, Integer> recipe : recipeEntity.entrySet()) {
                String type = recipe.getKey();
                for (int i = 0; i < maxCount; i++) {
                    entityIngredient.get(type).getLast().kill();
                }
            }
            for (ItemStack result : itemResults) {
                spawnItem(world, core, result, maxCount);
            }
            for (Map.Entry<String, Integer> result : entityResults.entrySet()) {
                for (int i = 0; i < maxCount; i++) {
                    spawnEntity(world, core, result.getKey());
                }
            }
        }
    }
}
