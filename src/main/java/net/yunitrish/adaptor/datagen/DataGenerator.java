package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.yunitrish.adaptor.world.ModConfiguredFeatures;
import net.yunitrish.adaptor.world.ModPlacedFeatures;
import net.yunitrish.adaptor.world.biome.ModBiomes;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModAdvancementProvider::new);
        pack.addProvider(ModWorldGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootStrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootStrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootStrap);
    }
}
