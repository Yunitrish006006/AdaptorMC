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
                ModBlocks.Crops.SOYBEAN_CROP,
                cropDrops(
                        ModBlocks.Crops.SOYBEAN_CROP,
                        ModItems.SOYBEAN,
                        ModItems.SOYBEAN,
                        BlockStatePropertyLootCondition
                                .builder(ModBlocks.Crops.SOYBEAN_CROP)
                                .properties(
                                        StatePredicate
                                                .Builder
                                                .create()
                                                .exactMatch(SoyBeanCropBlock.AGE,5)
                                )
                )
        );
        addDrop(
                ModBlocks.Crops.MARIJUANA_CROP,
                cropDrops(
                        ModBlocks.Crops.MARIJUANA_CROP,
                        ModItems.MARIJUANA_LEAF,
                        ModItems.MARIJUANA_SEEDS,
                        BlockStatePropertyLootCondition
                                .builder(ModBlocks.Crops.MARIJUANA_CROP)
                                .properties(
                                        StatePredicate
                                                .Builder
                                                .create()
                                                .exactMatch(MarijuanaCropBlock.AGE,5)
                                )
                )
        );


        addDrop(ModBlocks.Dirt.DIRT_STAIRS);
        addDrop(ModBlocks.Dirt.DIRT_SLAB, slabDrops(ModBlocks.Dirt.DIRT_SLAB));
        addDrop(ModBlocks.Dirt.DIRT_BUTTON);
        addDrop(ModBlocks.Dirt.DIRT_PRESSURE_PLATE);
        addDrop(ModBlocks.Dirt.DIRT_FENCE);
        addDrop(ModBlocks.Dirt.DIRT_FENCE_GATE);
        addDrop(ModBlocks.Dirt.DIRT_WALL);
        addDrop(ModBlocks.Dirt.DIRT_DOOR, doorDrops(ModBlocks.Dirt.DIRT_DOOR));
        addDrop(ModBlocks.Dirt.DIRT_TRAPDOOR);

        addDrop(ModBlocks.Chestnut.CHESTNUT_LOG);
        addDrop(ModBlocks.Chestnut.CHESTNUT_WOOD);
        addDrop(ModBlocks.Chestnut.STRIPPED_CHESTNUT_LOG);
        addDrop(ModBlocks.Chestnut.STRIPPED_CHESTNUT_LOG);
        addDrop(ModBlocks.Chestnut.CHESTNUT_PLANKS);
        addDrop(ModBlocks.Chestnut.CHESTNUT_SAPLING);
        addDrop(ModBlocks.Chestnut.CHESTNUT_STAIRS);
        addDrop(ModBlocks.Chestnut.CHESTNUT_SLAB, slabDrops(ModBlocks.Chestnut.CHESTNUT_SLAB));
        addDrop(ModBlocks.Chestnut.CHESTNUT_BUTTON);
        addDrop(ModBlocks.Chestnut.CHESTNUT_PRESSURE_PLATE);
        addDrop(ModBlocks.Chestnut.CHESTNUT_FENCE);
        addDrop(ModBlocks.Chestnut.CHESTNUT_FENCE_GATE);
        addDrop(ModBlocks.Chestnut.CHESTNUT_DOOR, doorDrops(ModBlocks.Chestnut.CHESTNUT_DOOR));
        addDrop(ModBlocks.Chestnut.CHESTNUT_TRAPDOOR);

        addDrop(ModBlocks.Chestnut.CHESTNUT_LEAVES, leavesDrops(ModBlocks.Chestnut.CHESTNUT_LEAVES, ModBlocks.Chestnut.CHESTNUT_SAPLING, 0.0025f));

        addDrop(ModBlocks.GLASS_SLAB, slabDrops(ModBlocks.GLASS_SLAB));
        addDrop(ModBlocks.LOCKABLE_CHEST);
    }
}
