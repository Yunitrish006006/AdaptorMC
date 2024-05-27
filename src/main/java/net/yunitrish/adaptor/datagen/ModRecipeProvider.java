package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.VanillaRecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.building.DirtSeries;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.item.ModItems;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private void hammerRecipe(RecipeExporter exporter) {
        Map<Item, Item> materialAndTool = Map.of(
                ModItems.COPPER_HAMMER,Items.COPPER_INGOT,
                ModItems.IRON_HAMMER,Items.IRON_INGOT,
                ModItems.GOLDEN_HAMMER,Items.GOLD_INGOT,
                ModItems.DIAMOND_HAMMER,Items.DIAMOND,
                ModItems.NETHERITE_HAMMER,Items.NETHERITE_INGOT
        );

        for (Map.Entry<Item, Item> entry : materialAndTool.entrySet()) {
            Item key = entry.getKey();
            Item value = entry.getValue();

            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, key)
                    .pattern("III")
                    .pattern("ISI")
                    .pattern(" S ")
                    .input('S', Items.STICK)
                    .input('I', value)
                    .criterion(hasItem(value), conditionsFromItem(value))
                    .offerTo(exporter,new Identifier(getRecipeName(key)));

        }
    }

    private void createCopperTools(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS,ModItems.COPPER_AXE)
                .pattern("CC")
                .pattern("CS")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.COPPER_AXE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS,ModItems.COPPER_HOE)
                .pattern("CC")
                .pattern(" S")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.COPPER_HOE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS,ModItems.COPPER_PICKAXE)
                .pattern("CCC")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.COPPER_PICKAXE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS,ModItems.COPPER_SHOVEL)
                .pattern("C")
                .pattern("S")
                .pattern("S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.COPPER_SHOVEL)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS,ModItems.COPPER_SWORD)
                .pattern("C")
                .pattern("C")
                .pattern("S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.COPPER_SWORD)));
    }

    private void woodBuildingProducts(RecipeExporter exporter, Block log, Block planks, Block slab, Block stair, Block button, Block pressurePlate, Block door, Block trapdoor, Block fence, Block fenceGate) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .input(log)
                .criterion(hasItem(log), conditionsFromItem(log))
                .offerTo(exporter, new Identifier(getRecipeName(planks)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, door, 2)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', planks)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, new Identifier(getRecipeName(door)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .pattern("PSP")
                .pattern("PSP")
                .input('P', planks)
                .input('S', Items.STICK)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, new Identifier(getRecipeName(fence)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', planks)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, new Identifier(getRecipeName(trapdoor)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fenceGate, 2)
                .pattern("SPS")
                .pattern("SPS")
                .input('P', planks)
                .input('S', Items.STICK)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, new Identifier(getRecipeName(fenceGate)));
        buildingProducts(exporter, planks, slab, stair, button, pressurePlate);

    }

    private void stoneBuildingProducts(RecipeExporter exporter, Block element, Block slab, Block stair, Block button, Block pressurePlate, Block wall) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wall, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(wall)));
        buildingProducts(exporter, element, slab, stair, button, pressurePlate);
    }

    private void dirtBuildingProducts(RecipeExporter exporter, Block element, Block slab, Block stair, Block button, Block pressurePlate, Block wall, Block door, Block trapdoor, Block fence, Block fenceGate) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wall, 2)
                .pattern("PPP")
                .pattern("SSS")
                .input('P', element)
                .input('S', Items.STICK)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(wall)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(trapdoor)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, door, 2)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(door)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fence, 2)
                .pattern("PSP")
                .pattern("PSP")
                .input('P', element)
                .input('S', Items.STICK)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(fence)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fenceGate, 2)
                .pattern("SPS")
                .pattern("SPS")
                .input('P', element)
                .input('S', Items.STICK)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(fenceGate)));
        buildingProducts(exporter, element, slab, stair, button, pressurePlate);
    }

    private void buildingProducts(RecipeExporter exporter, Block element, Block slab, Block stair, Block button, Block pressurePlate) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(slab)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stair, 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(stair)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, button)
                .pattern("P")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(button)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, pressurePlate)
                .pattern("PP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, new Identifier(getRecipeName(pressurePlate)));
    }

    private void createBreads(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FLOUR)
                .input(Items.WHEAT)
                .input(Items.WHEAT)
                .input(Items.WHEAT)
                .criterion(hasItem(Items.WHEAT), conditionsFromItem(Items.WHEAT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.FLOUR)));

        for (int i = 1; i < 9; i++) {
            ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.DOUGH, i)
                    .input(ModItems.FLOUR, i)
                    .input(Items.WATER_BUCKET)
                    .criterion(hasItem(ModItems.FLOUR), conditionsFromItem(ModItems.FLOUR))
                    .group("flour_to_dough")
                    .offerTo(exporter, new Identifier(i + "_flour_" + getRecipeName(ModItems.DOUGH) + "_with_bucket"));
        }

        offerSmelting(exporter, List.of(ModItems.DOUGH), RecipeCategory.FOOD, Items.BREAD, 2f, 200, "bread");
    }

    @Override
    public void generate(RecipeExporter exporter) {
        hammerRecipe(exporter);
        createCopperTools(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.METAL_DETECTOR)
                .pattern("S  ")
                .pattern("IS ")
                .pattern("R S")
                .input('S', Items.STICK)
                .input('I',Items.COPPER_INGOT)
                .input('R', Items.REDSTONE)
                .criterion(hasItem(Items.COPPER_INGOT),VanillaRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.METAL_DETECTOR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GLASS_SLAB, 6)
                .pattern("GGG")
                .input('G', Items.GLASS)
                .criterion(hasItem(Items.GLASS), VanillaRecipeProvider.conditionsFromItem(Items.GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.GLASS_SLAB)));
        dirtBuildingProducts(exporter, Blocks.DIRT, DirtSeries.DIRT_SLAB, DirtSeries.DIRT_STAIRS, DirtSeries.DIRT_BUTTON, DirtSeries.DIRT_PRESSURE_PLATE, DirtSeries.DIRT_WALL, DirtSeries.DIRT_DOOR, DirtSeries.DIRT_TRAPDOOR, DirtSeries.DIRT_FENCE, DirtSeries.DIRT_FENCE_GATE);
        woodBuildingProducts(exporter, ChestnutSeries.CHESTNUT_LOG, ChestnutSeries.CHESTNUT_PLANKS, ChestnutSeries.CHESTNUT_SLAB, ChestnutSeries.CHESTNUT_STAIRS, ChestnutSeries.CHESTNUT_BUTTON, ChestnutSeries.CHESTNUT_PRESSURE_PLATE, ChestnutSeries.CHESTNUT_DOOR, ChestnutSeries.CHESTNUT_TRAPDOOR, ChestnutSeries.CHESTNUT_FENCE, ChestnutSeries.CHESTNUT_FENCE_GATE);
        createBreads(exporter);
    }
}
