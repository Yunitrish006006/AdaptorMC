package net.yunitrish.adaptor.world.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.yunitrish.adaptor.world.tree.ModFoliagePlacerTypes;

public class ChestnutFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<ChestnutFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance ->
                    fillFoliagePlacerFields(instance)
                            .and(
                                    instance.group(
                                            (IntProvider
                                                    .createValidatingCodec(1, 512)
                                                    .fieldOf("foliage_height"))
                                                    .forGetter(placer -> placer.foliageHeight),
                                            (Codec
                                                    .intRange(0, 256)
                                                    .fieldOf("leaf_placement_attempts"))
                                                    .forGetter(placer -> placer.leafPlacementAttempts)
                                    )
                            )
                            .apply(instance, ChestnutFoliagePlacer::new));

    private final IntProvider foliageHeight;
    private final int leafPlacementAttempts;

    public ChestnutFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
        super(radius, offset);
        this.foliageHeight = foliageHeight;
        this.leafPlacementAttempts = leafPlacementAttempts;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.CHESTNUT_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        boolean bl = treeNode.isGiantTrunk();
        BlockPos blockPos = treeNode.getCenter().up(offset);
        this.generateSquare(world, placer, random, config, blockPos, radius + treeNode.getFoliageRadius(), -1 - foliageHeight, bl);
        this.generateSquare(world, placer, random, config, blockPos, radius - 1, -foliageHeight, bl);
        this.generateSquare(world, placer, random, config, blockPos, radius + treeNode.getFoliageRadius() - 1, 0, bl);
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 0;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (y == 0) {
            return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
        }
        return dx == radius && dz == radius && radius > 0;
    }
}
