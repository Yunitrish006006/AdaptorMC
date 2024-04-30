package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.plant.MarijuanaCropBlock;
import net.yunitrish.adaptor.block.plant.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public LootTable.Builder gravelOreDrop(Block origin, Item ore) {
        return BlockLootTableGenerator
                .dropsWithSilkTouch(
                        origin,
                        ((LeafEntry.Builder<?>)this.addSurvivesExplosionCondition(origin, ItemEntry.builder(ore)))
                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.02f, 0.04f, 0.06f, 0.1f))
                )
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0f))
                                .conditionally(WITHOUT_SILK_TOUCH)
                                .with(
                                        ((LeafEntry.Builder<?>)this
                                                .applyExplosionDecay(
                                                        origin,
                                                        ItemEntry
                                                                .builder(ore)
                                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.2f)))
                                                )
                                        )
                                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.02f, 0.04f, 0.06f, 0.1f))
                                )
                                .with(this
                                        .applyExplosionDecay(
                                                origin,
                                                ItemEntry
                                                        .builder(Items.FLINT)
                                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.2f)))
                                        )
                                )
                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.04f, 0.08f, 0.12f, 0.16f))
                );
    }


    @Override
    public void generate() {
        addDrop(ModBlocks.GRAVEL_IRON_ORE,gravelOreDrop(ModBlocks.GRAVEL_IRON_ORE, Items.RAW_IRON));
        addDrop(
                ModBlocks.SOYBEAN_CROP,
                cropDrops(
                        ModBlocks.SOYBEAN_CROP,
                        ModItems.SOYBEAN,
                        ModItems.SOYBEAN,
                        BlockStatePropertyLootCondition
                                .builder(ModBlocks.SOYBEAN_CROP)
                                .properties(
                                        StatePredicate
                                                .Builder
                                                .create()
                                                .exactMatch(SoyBeanCropBlock.AGE,5)
                                )
                )
        );
        addDrop(
                ModBlocks.MARIJUANA_CROP,
                cropDrops(
                        ModBlocks.MARIJUANA_CROP,
                        ModItems.MARIJUANA_LEAF,
                        ModItems.MARIJUANA_SEEDS,
                        BlockStatePropertyLootCondition
                                .builder(ModBlocks.MARIJUANA_CROP)
                                .properties(
                                        StatePredicate
                                                .Builder
                                                .create()
                                                .exactMatch(MarijuanaCropBlock.AGE,5)
                                )
                )
        );
    }
}
