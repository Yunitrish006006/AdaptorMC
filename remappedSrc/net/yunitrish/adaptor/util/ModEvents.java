package net.yunitrish.adaptor.util;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.yunitrish.adaptor.item.ModItems;

import java.util.List;

public class ModEvents {
    public static void registerEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemStack itemStack = player.getStackInHand(hand);

            if (state.getBlock() != Blocks.COMPOSTER) return ActionResult.PASS;

            if (state.get(ComposterBlock.LEVEL) < 8) {
                if ( List.of(ModItems.CORN, ModItems.CORN_SEEDS,ModItems.SOYBEAN,ModItems.MARIJUANA,ModItems.MARIJUANA_LEAF,ModItems.MARIJUANA_SEEDS).contains(itemStack.getItem())) {
                    world.setBlockState(pos, state.with(ComposterBlock.LEVEL, state.get(ComposterBlock.LEVEL) + 1));
                    ItemStack result = player.getStackInHand(hand);
                    result.setCount(player.getStackInHand(hand).getCount()-1);
                    player.setStackInHand(hand,result);
                    world.playSound(player,pos, SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS,1.0f,1.0f);
                    return ActionResult.SUCCESS;
                }
            }
            else {
                world.playSound(player,pos, SoundEvents.BLOCK_COMPOSTER_FILL_SUCCESS, SoundCategory.BLOCKS,1.0f,1.0f);
                player.giveItemStack(new ItemStack(Items.BONE_MEAL));
                world.setBlockState(pos, state.with(ComposterBlock.LEVEL, 0));
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}
