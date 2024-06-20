package net.yunitrish.adaptor.ChestLockSystem;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LockedContainerBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final MapCodec<LockedContainerBlock> CODEC = LockedContainerBlock.createCodec(LockedContainerBlock::new);

    public LockedContainerBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(OPEN, false));
    }

    public MapCodec<LockedContainerBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LockedContainerEntity lockedContainerEntity) {

            String ownerName = "Stranger";

            if (!lockedContainerEntity.owner.equals("none")) {
                Adaptor.LOGGER.info("##-" + lockedContainerEntity.owner);
                if (world.getPlayerByUuid(UUID.fromString(lockedContainerEntity.owner)) != null) {
                    assert world.getPlayerByUuid(UUID.fromString(lockedContainerEntity.owner)) != null;
                    ownerName = world.getPlayerByUuid(UUID.fromString(lockedContainerEntity.owner)).getName().getString();
                }
            }

            if (player.getInventory().getMainHandStack().getItem().equals(ModItems.Tools.COPPER_KEY)) {
                if (lockedContainerEntity.owner.equals("none")) {
                    lockedContainerEntity.owner = player.getUuidAsString();
                    player.sendMessage(Text.translatable("adaptor.container.lock_set"), true);
                } else if (lockedContainerEntity.owner.equals(player.getUuidAsString())) {
                    player.openHandledScreen(lockedContainerEntity);
                    player.incrementStat(Stats.OPEN_BARREL);
                    PiglinBrain.onGuardedBlockInteracted(player, true);
                    player.sendMessage(Text.translatable("adaptor.container.opened"), true);
                } else {
                    player.sendMessage(Text.translatable("adaptor.container.locked", ownerName), true);
                }
            } else if (lockedContainerEntity.owner.equals("none")) {
                player.openHandledScreen(lockedContainerEntity);
                player.incrementStat(Stats.OPEN_BARREL);
                PiglinBrain.onGuardedBlockInteracted(player, true);
            } else if (lockedContainerEntity.owner.equals(player.getUuidAsString())) {
                player.openHandledScreen(lockedContainerEntity);
                player.incrementStat(Stats.OPEN_BARREL);
                PiglinBrain.onGuardedBlockInteracted(player, true);
                player.sendMessage(Text.translatable("adaptor.container.opened"), true);
            } else {
                player.sendMessage(Text.translatable("adaptor.container.locked", ownerName), true);
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ItemScatterer.onStateReplaced(state, newState, world, pos);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LockedContainerEntity) {
            ((LockedContainerEntity) blockEntity).tick();
        }
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LockedContainerEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }
}
