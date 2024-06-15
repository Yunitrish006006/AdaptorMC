package net.yunitrish.adaptor.block.plant;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.yunitrish.adaptor.item.ModItems;

public class MarijuanaCropBlock extends CropBlock {
    public static final MapCodec<MarijuanaCropBlock> CODEC = MarijuanaCropBlock.createCodec(MarijuanaCropBlock::new);
    public static final IntProperty AGE = Properties.AGE_5;

    public MapCodec<MarijuanaCropBlock> getCodec() {
        return CODEC;
    }

    public MarijuanaCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.MARIJUANA_SEEDS;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) != 0) {
            super.randomTick(state, world, pos, random);
        }
    }

    @Override
    protected int getGrowthAmount(World world) {
        return super.getGrowthAmount(world) / 5;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
