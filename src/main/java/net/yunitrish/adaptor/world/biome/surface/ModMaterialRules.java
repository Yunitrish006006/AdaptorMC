package net.yunitrish.adaptor.world.biome.surface;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.world.biome.ModBiomes;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule GRAVEL_IRON_ORE = makeStateRule(ModBlocks.GRAVEL_IRON_ORE);
    private static final MaterialRules.MaterialRule RAW_GRAVEL_IRON_ORE = makeStateRule(ModBlocks.GRAVEL_IRON_ORE);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return MaterialRules.sequence(
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.CHESTNUT_BIOME),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, RAW_GRAVEL_IRON_ORE)),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, GRAVEL_IRON_ORE)),

                // Default to a grass and dirt surface
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
