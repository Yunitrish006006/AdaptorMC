package net.yunitrish.adaptor.world.tree.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.yunitrish.adaptor.world.tree.ModTrunkPlacerTypes;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ChestnutTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<ChestnutTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> ChestnutTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, ChestnutTrunkPlacer::new));

    public ChestnutTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.CHESTNUT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);
        int _height = height + random.nextBetween(firstRandomHeight, firstRandomHeight + 2) + random.nextBetween(secondRandomHeight - 1, secondRandomHeight + 1);
        for (int i = 0; i < _height; i++) {
            getAndSetState(world, replacer, random, startPos.up(i), config);
            if (i % 2 == 0 && random.nextBoolean()) {
                Map<Direction, Direction.Axis> directions = Map.of(
                        Direction.NORTH, Direction.Axis.Z,
                        Direction.SOUTH, Direction.Axis.Z,
                        Direction.WEST, Direction.Axis.X,
                        Direction.EAST, Direction.Axis.X
                );
                for (Map.Entry<Direction, Direction.Axis> d : directions.entrySet()) {
                    if (random.nextFloat() > 0.25f) {
                        for (int x = 1; x <= 4; x++) {
                            if (random.nextFloat() > 0.5f)
                                replacer.accept(startPos.up(i).offset(d.getKey(), x), (BlockState) Function.identity().apply(config.trunkProvider.get(random, startPos.up(i).offset(d.getKey(), x)).with(PillarBlock.AXIS, d.getValue())));
                            else
                                break;
                        }
                    }
                }
            }
        }
        return List.of(new FoliagePlacer.TreeNode(startPos.up(_height), 0, false));
    }
}
