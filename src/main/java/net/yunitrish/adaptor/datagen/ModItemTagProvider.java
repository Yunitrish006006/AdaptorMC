package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.yunitrish.adaptor.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(
                ModItems.COPPER_HELMET,
                ModItems.COPPER_CHESTPLATE,
                ModItems.COPPER_LEGGINGS,
                ModItems.COPPER_BOOTS
        );
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(
                ModItems.BAR_BRAWL_MUSIC_DISC,
                ModItems.SAKURA_VALLEY_MUSIC_DISC
        );
        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(
                ModItems.BAR_BRAWL_MUSIC_DISC,
                ModItems.SAKURA_VALLEY_MUSIC_DISC
        );
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(
                ModItems.IRON_HAMMER
        );
        getOrCreateTagBuilder(ItemTags.AXES).add(
                ModItems.IRON_HAMMER
        );
    }
}
