package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.building.DirtSeries;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES);
        getOrCreateTagBuilder(ModTags.Blocks.ORES)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.COPPER_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .add(Blocks.NETHERRACK);
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.GRAVEL_IRON_ORE);
        getOrCreateTagBuilder(BlockTags.FENCES).add(DirtSeries.DIRT_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(DirtSeries.DIRT_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS).add(DirtSeries.DIRT_WALL);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(
                        ChestnutSeries.CHESTNUT_LOG,
                        ChestnutSeries.CHESTNUT_WOOD,
                        ChestnutSeries.STRIPPED_CHESTNUT_LOG,
                        ChestnutSeries.STRIPPED_CHESTNUT_WOOD
                );
    }
}
