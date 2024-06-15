package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
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


    @Override
    public void generate() {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        addDrop(
                ModBlocks.GRAVEL_IRON_ORE,
                (Block block) -> dropsWithSilkTouch(
                        block,
                        this.applyExplosionDecay(
                                block,
                                (
                                        ItemEntry
                                                .builder(Items.RAW_IRON)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 6.0f)))
                                )
                                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        )
                )
        );

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
