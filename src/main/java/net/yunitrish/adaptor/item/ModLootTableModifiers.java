package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    public static final Identifier JUNGLE_TEMPLE_ID = Identifier.ofVanilla("chests/jungle_temple");
    public static final Identifier GRASS_ID = Identifier.ofVanilla("blocks/short_grass");
    public static final Identifier CREEPER_ID = Identifier.ofVanilla("entities/creeper");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source) -> {
            if(RegistryKey.of(RegistryKeys.LOOT_TABLE,JUNGLE_TEMPLE_ID).equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.7f))
                        .with(ItemEntry.builder(ModItems.METAL_DETECTOR))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f,1f)
                        ).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if(RegistryKey.of(RegistryKeys.LOOT_TABLE,CREEPER_ID).equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.01f))
                        .with(ItemEntry.builder(Items.FLINT_AND_STEEL))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f,1f)
                        ).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if(RegistryKey.of(RegistryKeys.LOOT_TABLE,GRASS_ID).equals(id)) {
                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.07f))
                        .with(ItemEntry.builder(ModItems.SOYBEAN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0f,5f)
                        ).build()));
                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.04f))
                        .with(ItemEntry.builder(ModItems.MARIJUANA_SEEDS))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0f,2f)
                        ).build()));
            }
        });
    }
}
