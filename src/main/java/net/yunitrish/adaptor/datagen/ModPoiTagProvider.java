package net.yunitrish.adaptor.datagen;

import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;

import java.util.concurrent.CompletableFuture;

public class ModPoiTagProvider extends TagProvider {
    public ModPoiTagProvider(DataOutput output, CompletableFuture registryLookupFuture) {
        super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(new Identifier(AdaptorMain.MOD_ID,"chefpoi"));
        this.getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(new Identifier(AdaptorMain.MOD_ID,"scavengerpoi"));
    }
}
