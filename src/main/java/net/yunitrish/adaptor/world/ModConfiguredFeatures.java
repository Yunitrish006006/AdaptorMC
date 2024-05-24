package net.yunitrish.adaptor.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.world.tree.custom.ChestnutTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> GRAVEL_IRON_ORE_KEY = registryKey("gravel_iron_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> NETHER_GRAVEL_IRON_ORE_KEY = registryKey("nether_gravel_iron_ore");

    public static final RegistryKey<ConfiguredFeature<?,?>> CHESTNUT_KEY = registryKey("chestnut");

    public static void bootStrap(Registerable<ConfiguredFeature<?,?>> context) {
        RuleTest stoneReplacebles = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacebles = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest gravelReplacebles = new TagMatchRuleTest(BlockTags.OVERWORLD_CARVER_REPLACEABLES);
        RuleTest netherReplacebles = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);

        List<OreFeatureConfig.Target> overworldGravelIronOres = List.of(
                OreFeatureConfig.createTarget(stoneReplacebles, ModBlocks.GRAVEL_IRON_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplacebles, ModBlocks.GRAVEL_IRON_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(gravelReplacebles, ModBlocks.GRAVEL_IRON_ORE.getDefaultState())
        );List<OreFeatureConfig.Target> netherGravelIronOres = List.of(
                OreFeatureConfig.createTarget(netherReplacebles, ModBlocks.GRAVEL_IRON_ORE.getDefaultState())
        );

        register(context, GRAVEL_IRON_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldGravelIronOres,12));
        register(context, NETHER_GRAVEL_IRON_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherGravelIronOres,12));

        register(context, CHESTNUT_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ChestnutSeries.CHESTNUT_LOG),
                new ChestnutTrunkPlacer(5, 4, 3),
                BlockStateProvider.of(ChestnutSeries.CHESTNUT_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1),2),
                new TwoLayersFeatureSize(1,0,2)
                ).build()
        );
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Adaptor.modIdentifier(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register (Registerable<ConfiguredFeature<?,?>> context, RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature,configuration));
    }
}
