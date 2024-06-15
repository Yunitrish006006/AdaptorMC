package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.COPPER_AXE)
                .add(ModItems.WOODEN_HAMMER)
                .add(ModItems.STONE_HAMMER)
                .add(ModItems.COPPER_HAMMER)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLDEN_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.COPPER_PICKAXE)
                .add(ModItems.WOODEN_HAMMER)
                .add(ModItems.STONE_HAMMER)
                .add(ModItems.COPPER_HAMMER)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLDEN_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER);
        getOrCreateTagBuilder(ModTags.Items.HAMMER)
                .add(ModItems.WOODEN_HAMMER)
                .add(ModItems.STONE_HAMMER)
                .add(ModItems.COPPER_HAMMER)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLDEN_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER);
        getOrCreateTagBuilder(ModTags.Items.BAKE)
                .add(ModItems.DOUGH);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.COPPER_SHOVEL);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.COPPER_SWORD);
        getOrCreateTagBuilder(ItemTags.HOES).add(ModItems.COPPER_HOE);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(
                        ModItems.COPPER_HELMET,
                        ModItems.COPPER_CHESTPLATE,
                        ModItems.COPPER_LEGGINGS,
                        ModItems.COPPER_BOOTS
                );
        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(
                        ModItems.SAKURA_VALLEY_MUSIC_DISC,
                        ModItems.BAR_BRAWL_MUSIC_DISC
                );
//        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
//                .add(
//                        ModItems.SAKURA_VALLEY_MUSIC_DISC,
//                        ModItems.BAR_BRAWL_MUSIC_DISC
//                );

        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(
                        ChestnutSeries.CHESTNUT_PLANKS.asItem()
                );
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(
                        ChestnutSeries.CHESTNUT_LOG.asItem(),
                        ChestnutSeries.CHESTNUT_WOOD.asItem(),
                        ChestnutSeries.STRIPPED_CHESTNUT_LOG.asItem(),
                        ChestnutSeries.STRIPPED_CHESTNUT_WOOD.asItem()
                );
    }
}
