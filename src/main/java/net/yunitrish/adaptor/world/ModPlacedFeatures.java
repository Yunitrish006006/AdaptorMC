package net.yunitrish.adaptor.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> GRAVEL_IRON_ORE_PLACED_KEY = registryKey("gravel_iron_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_GRAVEL_IRON_ORE_PLACED_KEY = registryKey("nether_gravel_iron_ore_key");

    public static final RegistryKey<PlacedFeature> CHESTNUT_PLACED_KEY = registryKey("chestnut_placed");

    public static void bootStrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(
                context,
                GRAVEL_IRON_ORE_PLACED_KEY,
                configuredFeatureRegistryLookup.getOrThrow(
                        ModConfiguredFeatures.GRAVEL_IRON_ORE_KEY),
                        ModOrePlacement.modifiersWithCount(
                            12,
                                HeightRangePlacementModifier.uniform(YOffset.fixed(-80),YOffset.fixed(80))
                        )
        );
        register(
                context,
                NETHER_GRAVEL_IRON_ORE_PLACED_KEY,
                configuredFeatureRegistryLookup.getOrThrow(
                        ModConfiguredFeatures.NETHER_GRAVEL_IRON_ORE_KEY),
                        ModOrePlacement.modifiersWithCount(
                            12,
                                HeightRangePlacementModifier.uniform(YOffset.fixed(-80),YOffset.fixed(80))
                        )
        );
        register(
                context,
                CHESTNUT_PLACED_KEY,
                configuredFeatureRegistryLookup.getOrThrow(ModConfiguredFeatures.CHESTNUT_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 1f, 2),
                        ChestnutSeries.CHESTNUT_SAPLING
                )
        );
    }

    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Adaptor.modIdentifier(name));
    }

    private static void register (Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?,?>> configureRation, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configureRation,List.copyOf(modifiers)));
    }
}
