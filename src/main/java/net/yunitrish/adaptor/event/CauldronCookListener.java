package net.yunitrish.adaptor.event;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class CauldronCookListener {

    public static void spawn(World world, BlockPos pos, Item item, int count) {
        ItemStack stack = item.getDefaultStack();
        stack.setCount(count);
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
    }

    public static ActionResult run(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (!player.getMainHandStack().isEmpty()) return ActionResult.PASS;
        if (!world.isClient() && world.getBlockState(pos).getBlock() == Blocks.WATER_CAULDRON) {
            BlockPos from = hitResult.getBlockPos();
            BlockPos end = hitResult.getBlockPos().add(1, 1, 1);
            Box box = new Box(from.getX(), from.getY(), from.getZ(), end.getX(), end.getY(), end.getZ());
            List<ItemEntity> cook = world.getEntitiesByClass(ItemEntity.class, box, itemEntity -> true);
            if (player.isSneaking()) {
                for (ItemEntity entity : cook) {
                    recipeGravel(entity, true);
                }
            } else {
                for (ItemEntity entity : cook) {
                    recipeGravel(entity, false);
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public static void recipeGravel(ItemEntity itemEntity, boolean max) {
        if (itemEntity.getStack().getItem() == Items.GRAVEL) {
            itemEntity.getWorld().playSound(itemEntity, itemEntity.getBlockPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 0.4f, 0.2f);
            if (max) {
                spawn(itemEntity.getWorld(), itemEntity.getBlockPos(), Items.SAND, itemEntity.getStack().getCount());
                spawn(itemEntity.getWorld(), itemEntity.getBlockPos(), Items.FLINT, itemEntity.getStack().getCount());
                itemEntity.setDespawnImmediately();
            } else {
                spawn(itemEntity.getWorld(), itemEntity.getBlockPos(), Items.SAND, 1);
                spawn(itemEntity.getWorld(), itemEntity.getBlockPos(), Items.FLINT, 1);
                itemEntity.getStack().decrement(1);
            }
        }
    }
}
