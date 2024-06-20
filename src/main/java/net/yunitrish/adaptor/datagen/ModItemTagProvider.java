package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.Tools.COPPER_AXE)
                .add(ModItems.Tools.WOODEN_HAMMER)
                .add(ModItems.Tools.STONE_HAMMER)
                .add(ModItems.Tools.COPPER_HAMMER)
                .add(ModItems.Tools.IRON_HAMMER)
                .add(ModItems.Tools.GOLDEN_HAMMER)
                .add(ModItems.Tools.DIAMOND_HAMMER)
                .add(ModItems.Tools.NETHERITE_HAMMER);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.Tools.COPPER_PICKAXE)
                .add(ModItems.Tools.WOODEN_HAMMER)
                .add(ModItems.Tools.STONE_HAMMER)
                .add(ModItems.Tools.COPPER_HAMMER)
                .add(ModItems.Tools.IRON_HAMMER)
                .add(ModItems.Tools.GOLDEN_HAMMER)
                .add(ModItems.Tools.DIAMOND_HAMMER)
                .add(ModItems.Tools.NETHERITE_HAMMER);
        getOrCreateTagBuilder(ModTags.Items.HAMMER)
                .add(ModItems.Tools.WOODEN_HAMMER)
                .add(ModItems.Tools.STONE_HAMMER)
                .add(ModItems.Tools.COPPER_HAMMER)
                .add(ModItems.Tools.IRON_HAMMER)
                .add(ModItems.Tools.GOLDEN_HAMMER)
                .add(ModItems.Tools.DIAMOND_HAMMER)
                .add(ModItems.Tools.NETHERITE_HAMMER);
        getOrCreateTagBuilder(ModTags.Items.BAKE)
                .add(ModItems.DOUGH);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.Tools.COPPER_SHOVEL);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.Tools.COPPER_SWORD);
        getOrCreateTagBuilder(ItemTags.HOES).add(ModItems.Tools.COPPER_HOE);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(
                        ModItems.Tools.COPPER_HELMET,
                        ModItems.Tools.COPPER_CHESTPLATE,
                        ModItems.Tools.COPPER_LEGGINGS,
                        ModItems.Tools.COPPER_BOOTS
                );
        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(
                        ModItems.Tools.SAKURA_VALLEY_MUSIC_DISC,
                        ModItems.Tools.BAR_BRAWL_MUSIC_DISC
                );
        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(
                        ModBlocks.Chestnut.CHESTNUT_PLANKS.asItem()
                );
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(
                        ModBlocks.Chestnut.CHESTNUT_LOG.asItem(),
                        ModBlocks.Chestnut.CHESTNUT_WOOD.asItem(),
                        ModBlocks.Chestnut.STRIPPED_CHESTNUT_LOG.asItem(),
                        ModBlocks.Chestnut.STRIPPED_CHESTNUT_WOOD.asItem()
                );
    }
}
