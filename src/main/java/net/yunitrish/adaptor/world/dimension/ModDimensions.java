package net.yunitrish.adaptor.world.dimension;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.yunitrish.adaptor.Adaptor;

import java.util.OptionalLong;

//https://misode.github.io/dimension/
public class ModDimensions {
    public static final RegistryKey<DimensionOptions> PRE_ERA_KEY = RegistryKey.of(RegistryKeys.DIMENSION, Adaptor.modIdentifier("pre_era_dimension"));
    public static final RegistryKey<World> PRE_ERA_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD, Adaptor.modIdentifier("pre_era_dimension"));
    public static final RegistryKey<DimensionType> PRE_ERA_DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, Adaptor.modIdentifier("pre_era_dimension_type"));

    public static void bootStrapType(Registerable<DimensionType> context) {
        context.register(PRE_ERA_DIMENSION_TYPE, new DimensionType(
                OptionalLong.of(12000),
                false,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionTypes.OVERWORLD_ID,
                0.2f,
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)
        ));
    }
}
