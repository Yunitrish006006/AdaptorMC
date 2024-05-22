package net.yunitrish.adaptor.world.generation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.yunitrish.adaptor.world.ModPlacedFeatures;

public class ModTreeGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.FOREST
                ),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.CHESTNUT_PLACED_KEY
        );
    }
}
