package net.yunitrish.adaptor.block.LockableContainer.StoneMill;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.yunitrish.adaptor.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class LockableStoneMillBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final VoxelShape SHAPE = LockableStoneMillBlock.createCuboidShape(0, 0, 0, 16, 12, 16);
    public static final MapCodec<LockableStoneMillBlock> CODEC = LockableStoneMillBlock.createCodec(LockableStoneMillBlock::new);

    public LockableStoneMillBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LockableStoneMillBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof LockableStoneMillBlockEntity blockEntity) {
            if (player.getMainHandStack().getItem().equals(ModItems.Tools.COPPER_KEY)) {
                blockEntity.installLock(player);
            } else {
                player.openHandledScreen(blockEntity);
                player.incrementStat(Stats.OPEN_CHEST);
                PiglinBrain.onGuardedBlockInteracted(player, true);
            }
        }
        return ActionResult.CONSUME;
    }
}
