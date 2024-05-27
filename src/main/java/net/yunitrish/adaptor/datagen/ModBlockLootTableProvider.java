package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.building.DirtSeries;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.block.plant.MarijuanaCropBlock;
import net.yunitrish.adaptor.block.plant.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public LootTable.Builder gravelOreDrop(Block origin, Item ore, Block base) {
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
                                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.2f, 0.4f, 0.6f, 0.9f))
                                )
                                .with(this
                                        .applyExplosionDecay(
                                                origin,
                                                ItemEntry
                                                        .builder(Items.FLINT)
                                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.2f)))
                                        )
                                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.04f, 0.08f, 0.12f, 0.16f))
                                )
                                .with(
                                        (this
                                                .applyExplosionDecay(
                                                        origin,
                                                        ItemEntry
                                                                .builder(base)
                                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 1f)))
                                                )
                                        )
                                )
                );
    }


    @Override
    public void generate() {
        addDrop(ModBlocks.GRAVEL_IRON_ORE,gravelOreDrop(ModBlocks.GRAVEL_IRON_ORE, Items.RAW_IRON, Blocks.GRAVEL));
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


        addDrop(DirtSeries.DIRT_STAIRS);
        addDrop(DirtSeries.DIRT_SLAB, slabDrops(DirtSeries.DIRT_SLAB));
        addDrop(DirtSeries.DIRT_BUTTON);
        addDrop(DirtSeries.DIRT_PRESSURE_PLATE);
        addDrop(DirtSeries.DIRT_FENCE);
        addDrop(DirtSeries.DIRT_FENCE_GATE);
        addDrop(DirtSeries.DIRT_WALL);
        addDrop(DirtSeries.DIRT_DOOR, doorDrops(DirtSeries.DIRT_DOOR));
        addDrop(DirtSeries.DIRT_TRAPDOOR);

        addDrop(ChestnutSeries.CHESTNUT_LOG);
        addDrop(ChestnutSeries.CHESTNUT_WOOD);
        addDrop(ChestnutSeries.STRIPPED_CHESTNUT_LOG);
        addDrop(ChestnutSeries.STRIPPED_CHESTNUT_LOG);
        addDrop(ChestnutSeries.CHESTNUT_PLANKS);
        addDrop(ChestnutSeries.CHESTNUT_SAPLING);
        addDrop(ChestnutSeries.CHESTNUT_STAIRS);
        addDrop(ChestnutSeries.CHESTNUT_SLAB, slabDrops(ChestnutSeries.CHESTNUT_SLAB));
        addDrop(ChestnutSeries.CHESTNUT_BUTTON);
        addDrop(ChestnutSeries.CHESTNUT_PRESSURE_PLATE);
        addDrop(ChestnutSeries.CHESTNUT_FENCE);
        addDrop(ChestnutSeries.CHESTNUT_FENCE_GATE);
        addDrop(ChestnutSeries.CHESTNUT_DOOR, doorDrops(ChestnutSeries.CHESTNUT_DOOR));
        addDrop(ChestnutSeries.CHESTNUT_TRAPDOOR);

        addDrop(ChestnutSeries.CHESTNUT_LEAVES,leavesDrops(ChestnutSeries.CHESTNUT_LEAVES,ChestnutSeries.CHESTNUT_SAPLING,0.0025f));

        addDrop(ModBlocks.GLASS_SLAB, slabDrops(ModBlocks.GLASS_SLAB));
    }
}
