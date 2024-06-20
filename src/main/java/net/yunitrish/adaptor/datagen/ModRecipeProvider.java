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
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.ModItems;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static String getRecipeNameSpace(ItemConvertible item) {
        return getRecipeName(item);
    }

    private void createCopperTools(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.Tools.COPPER_AXE)
                .pattern("CC")
                .pattern("CS")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.Tools.COPPER_AXE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.Tools.COPPER_HOE)
                .pattern("CC")
                .pattern(" S")
                .pattern(" S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.Tools.COPPER_HOE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.Tools.COPPER_PICKAXE)
                .pattern("CCC")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.Tools.COPPER_PICKAXE)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.Tools.COPPER_SHOVEL)
                .pattern("C")
                .pattern("S")
                .pattern("S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.Tools.COPPER_SHOVEL)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.Tools.COPPER_SWORD)
                .pattern("C")
                .pattern("C")
                .pattern("S")
                .input('S', Items.STICK)
                .input('C',Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT),conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.Tools.COPPER_SWORD)));
    }

    private void woodBuildingProducts(RecipeExporter exporter, Block log, Block planks, Block slab, Block stair, Block button, Block pressurePlate, Block door, Block trapdoor, Block fence, Block fenceGate) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .input(log)
                .criterion(hasItem(log), conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(getRecipeName(planks)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, door, 2)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', planks)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, Identifier.of(getRecipeName(door)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .pattern("PSP")
                .pattern("PSP")
                .input('P', planks)
                .input('S', Items.STICK)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, Identifier.of(getRecipeName(fence)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', planks)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, Identifier.of(getRecipeName(trapdoor)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fenceGate, 2)
                .pattern("SPS")
                .pattern("SPS")
                .input('P', planks)
                .input('S', Items.STICK)
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, Identifier.of(getRecipeName(fenceGate)));
        buildingProducts(exporter, planks, slab, stair, button, pressurePlate);

    }

    private void stoneBuildingProducts(RecipeExporter exporter, Block element, Block slab, Block stair, Block button, Block pressurePlate, Block wall) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wall, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Identifier.of(getRecipeName(wall)));
        buildingProducts(exporter, element, slab, stair, button, pressurePlate);
    }

    private void hammerRecipe(RecipeExporter exporter) {
        Map<Item, Item> materialAndTool = Map.ofEntries(
                Map.entry(ModItems.Tools.COPPER_HAMMER, Items.COPPER_INGOT),
                Map.entry(ModItems.Tools.IRON_HAMMER, Items.IRON_INGOT),
                Map.entry(ModItems.Tools.GOLDEN_HAMMER, Items.GOLD_INGOT),
                Map.entry(ModItems.Tools.DIAMOND_HAMMER, Items.DIAMOND),
                Map.entry(ModItems.Tools.NETHERITE_HAMMER, Items.NETHERITE_INGOT)
        );

        Map<Item, TagKey<Item>> materialAndTool2 = Map.ofEntries(
                Map.entry(ModItems.Tools.WOODEN_HAMMER, ItemTags.PLANKS),
                Map.entry(ModItems.Tools.STONE_HAMMER, ItemTags.STONE_TOOL_MATERIALS)
        );
        for (Map.Entry<Item, TagKey<Item>> entry : materialAndTool2.entrySet()) {
            Item key = entry.getKey();
            TagKey<Item> value = entry.getValue();
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, key)
                    .pattern("III")
                    .pattern("ISI")
                    .pattern(" S ")
                    .input('S', Items.STICK)
                    .input('I', value)
                    .group("hammer")
                    .criterion("has_" + value.getName(), conditionsFromTag(value))
                    .offerTo(exporter, Identifier.of(getRecipeName(key)));
        }

        for (Map.Entry<Item, Item> entry : materialAndTool.entrySet()) {
            Item key = entry.getKey();
            Item value = entry.getValue();
            ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, key)
                    .pattern("III")
                    .pattern("ISI")
                    .pattern(" S ")
                    .input('S', Items.STICK)
                    .input('I', value)
                    .group("hammer")
                    .criterion(hasItem(value), conditionsFromItem(value))
                    .offerTo(exporter, Identifier.of(getRecipeName(key)));
        }
    }

    private void dirtBuildingProducts(RecipeExporter exporter, Block element, Block slab, Block stair, Block button, Block pressurePlate, Block wall, Block door, Block trapdoor, Block fence, Block fenceGate) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, wall, 2)
                .pattern("PPP")
                .pattern("SSS")
                .input('P', element)
                .input('S', Items.STICK)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(wall)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .pattern("PPP")
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(trapdoor)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, door, 2)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(door)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fence, 2)
                .pattern("PSP")
                .pattern("PSP")
                .input('P', element)
                .input('S', Items.STICK)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(fence)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, fenceGate, 2)
                .pattern("SPS")
                .pattern("SPS")
                .input('P', element)
                .input('S', Items.STICK)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(fenceGate)));
        buildingProducts(exporter, element, slab, stair, button, pressurePlate);
    }

    private void createBreads(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FLOUR)
                .input(Items.WHEAT)
                .input(Items.WHEAT)
                .input(Items.WHEAT)
                .criterion(hasItem(Items.WHEAT), conditionsFromItem(Items.WHEAT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.FLOUR)));

        for (int i = 1; i < 9; i++) {
            ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.DOUGH, i)
                    .input(ModItems.FLOUR, i)
                    .input(Items.WATER_BUCKET)
                    .criterion(hasItem(ModItems.FLOUR), conditionsFromItem(ModItems.FLOUR))
                    .group("flour_to_dough")
                    .offerTo(exporter, Identifier.of(i + "_flour_" + getRecipeName(ModItems.DOUGH) + "_with_bucket"));
        }

        offerSmelting(exporter, List.of(ModItems.DOUGH), RecipeCategory.FOOD, Items.BREAD, 2f, 200, "bread");
    }

    @Override
    public void generate(RecipeExporter exporter) {
        hammerRecipe(exporter);
        createCopperTools(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.Tools.METAL_DETECTOR)
                .pattern("S  ")
                .pattern("IS ")
                .pattern("R S")
                .input('S', Items.STICK)
                .input('I',Items.COPPER_INGOT)
                .input('R', Items.REDSTONE)
                .criterion(hasItem(Items.COPPER_INGOT),VanillaRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.Tools.METAL_DETECTOR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GLASS_SLAB, 6)
                .pattern("GGG")
                .input('G', Items.GLASS)
                .criterion(hasItem(Items.GLASS), VanillaRecipeProvider.conditionsFromItem(Items.GLASS))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.GLASS_SLAB)));
        dirtBuildingProducts(exporter, Blocks.DIRT, ModBlocks.Dirt.DIRT_SLAB, ModBlocks.Dirt.DIRT_STAIRS, ModBlocks.Dirt.DIRT_BUTTON, ModBlocks.Dirt.DIRT_PRESSURE_PLATE, ModBlocks.Dirt.DIRT_WALL, ModBlocks.Dirt.DIRT_DOOR, ModBlocks.Dirt.DIRT_TRAPDOOR, ModBlocks.Dirt.DIRT_FENCE, ModBlocks.Dirt.DIRT_FENCE_GATE);
        woodBuildingProducts(exporter, ModBlocks.Chestnut.CHESTNUT_LOG, ModBlocks.Chestnut.CHESTNUT_PLANKS, ModBlocks.Chestnut.CHESTNUT_SLAB, ModBlocks.Chestnut.CHESTNUT_STAIRS, ModBlocks.Chestnut.CHESTNUT_BUTTON, ModBlocks.Chestnut.CHESTNUT_PRESSURE_PLATE, ModBlocks.Chestnut.CHESTNUT_DOOR, ModBlocks.Chestnut.CHESTNUT_TRAPDOOR, ModBlocks.Chestnut.CHESTNUT_FENCE, ModBlocks.Chestnut.CHESTNUT_FENCE_GATE);
        createBreads(exporter);
    }

    private void buildingProducts(RecipeExporter exporter, Block element, Block slab, Block stair, Block button, Block pressurePlate) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(slab)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, stair, 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(stair)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, button)
                .pattern("P")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(button)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, pressurePlate)
                .pattern("PP")
                .input('P', element)
                .criterion(hasItem(element), conditionsFromItem(element))
                .offerTo(exporter, Adaptor.id(getRecipeName(pressurePlate)));
    }
}
