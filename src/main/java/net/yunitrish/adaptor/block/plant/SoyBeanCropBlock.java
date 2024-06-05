package net.yunitrish.adaptor.block.plant;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.yunitrish.adaptor.item.ModItems;

public class SoyBeanCropBlock extends CropBlock {
    public SoyBeanCropBlock(Settings settings) {
        super(settings);
    }

    public static final MapCodec<SoyBeanCropBlock> CODEC = SoyBeanCropBlock.createCodec(SoyBeanCropBlock::new);
    public static final int MAX_AGE = 5;
    public static final IntProperty AGE = Properties.AGE_5;

    public MapCodec<SoyBeanCropBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.SOYBEAN;
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
