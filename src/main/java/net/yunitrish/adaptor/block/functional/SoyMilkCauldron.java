//package net.yunitrish.adaptor.block.functional;
//
//import com.mojang.serialization.MapCodec;
//import net.minecraft.block.AbstractCauldronBlock;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.cauldron.CauldronBehavior;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.ItemEntity;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.fluid.FluidState;
//import net.minecraft.item.Items;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class SoyMilkCauldron extends AbstractCauldronBlock {
//    public SoyMilkCauldron(Settings settings, CauldronBehavior.CauldronBehaviorMap behaviorMap) {
//        super(settings, behaviorMap);
//    }
//
//    @Override
//    protected double getFluidHeight(BlockState state) {
//        return 0.9375;
//    }
//
//    @Override
//    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
//        if(this.isEntityTouchingFluid(state,pos,entity)){
//            if (entity instanceof ItemEntity itemEntity) {
//                if (itemEntity.getStack().getItem().equals(Items.TUFF)) {
//                    itemEntity.setVelocity(0,1,0);
//                }
//            } else if (entity instanceof LivingEntity livingEntity) {
//                removeActiveEffects(livingEntity);
//            }
//        }
//    }
//
//    @Override
//    protected FluidState getFluidState(BlockState state) {
//        return super.getFluidState(state);
//    }
//
//    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
//        return 3;
//    }
//    public static void removeActiveEffects(LivingEntity entity) {
//        entity.clearStatusEffects();
//    }
//
//    @Override
//    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
//        return null;
//    }
//
//    @Override
//    public boolean isFull(BlockState state) {
//        return false;
//    }
//}
