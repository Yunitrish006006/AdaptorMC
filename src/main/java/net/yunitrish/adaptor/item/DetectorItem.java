package net.yunitrish.adaptor.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.yunitrish.adaptor.datagen.ModTags;
import net.yunitrish.adaptor.sound.ModSounds;

import java.util.List;

public class DetectorItem extends Item {
    public DetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null) return ActionResult.PASS;
        if(!context.getWorld().isClient()) {
            context.getStack().damage(1,context.getPlayer(), EquipmentSlot.MAINHAND);
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for (int i=0;i<=positionClicked.getY()+64;i++) {
                BlockState state = context.getWorld().getBlockState(positionClicked.down(i));
                if (isValuableBlock(state)) {
                    outputValuableCoordinates(positionClicked.down(i),player,state.getBlock());
                    foundBlock = true;
                    context.getWorld().playSound(null,positionClicked, ModSounds.METAL_DETECTOR_FOUND_ORE, SoundCategory.BLOCKS,1f,1f);
                    break;
                }
            }
            if (!foundBlock) {
                player.sendMessage(Text.translatable("item.adaptor.metal_detector.not_found"), true);
            }
        }

        return ActionResult.CONSUME;
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block block) {
        player.sendMessage(Text.translatable("item.adaptor.metal_detector.found",block.asItem().getName().getString(),(player.getBlockPos().getY() - blockPos.getY())),true);
    }

    private boolean isValuableBlock(BlockState state) {
        return  state.isIn(ModTags.Blocks.ORES);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.adaptor.metal_detector.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
