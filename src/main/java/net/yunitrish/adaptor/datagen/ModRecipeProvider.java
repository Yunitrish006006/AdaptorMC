package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.ModItems;

import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {

    private static final List<ItemConvertible> SALT_SMELTS = List.of(
            ModBlocks.SALT_ORE,
            ModBlocks.DEEPSLATE_SALT_ORE,
            ModBlocks.END_STONE_SALT_ORE,
            ModBlocks.NETHER_SALT_ORE
    );

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    private void createBuildSeries(RecipeExporter exporter) {
        createDoorRecipe(ModBlocks.DIRT_DOOR, Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_DOOR)));

        createTrapdoorRecipe(ModBlocks.DIRT_TRAPDOOR, Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_TRAPDOOR)));

        createStairsRecipe(ModBlocks.DIRT_STAIRS, Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_STAIRS)));

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.DIRT_SLAB,Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_SLAB)));

        createFenceRecipe(ModBlocks.DIRT_FENCE,Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_FENCE)));

        createPressurePlateRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.DIRT_PRESSURE_PLATE,Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_PRESSURE_PLATE)));

        createFenceGateRecipe(ModBlocks.DIRT_FENCE_GATE,Ingredient.ofItems(Blocks.DIRT))
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_FENCE_GATE)));

        offerShapelessRecipe(exporter,ModBlocks.DIRT_BUTTON, Blocks.DIRT,"dirt_button",1);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS,ModBlocks.DIRT_WALL)
                .pattern("DDD")
                .pattern("DDD")
                .input('D', Blocks.DIRT)
                .criterion(hasItem(Items.DIRT),conditionsFromItem(Items.DIRT))
                .offerTo(exporter,new Identifier(getRecipeName(ModBlocks.DIRT_WALL)));
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

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter,SALT_SMELTS, RecipeCategory.MISC,ModItems.SALT,0.1f,300,"salt");
        offerBlasting(exporter,SALT_SMELTS, RecipeCategory.MISC,ModItems.SALT,0.1f,200,"salt");

        offerReversibleCompactingRecipes(exporter,
                RecipeCategory.BUILDING_BLOCKS, ModItems.SALT,
                RecipeCategory.DECORATIONS,ModBlocks.SALT_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS,ModItems.METAL_DETECTOR)
                .pattern("S  ")
                .pattern("IS ")
                .pattern("R S")
                .input('S', Items.STICK)
                .input('I',Items.IRON_INGOT)
                .input('R',Items.REDSTONE)
                .criterion(hasItem(Items.REDSTONE),conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter,new Identifier(getRecipeName(ModItems.METAL_DETECTOR)));

        createBuildSeries(exporter);

        createCopperTools(exporter);

    }
}
