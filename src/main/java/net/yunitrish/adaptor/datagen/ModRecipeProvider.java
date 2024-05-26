package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.VanillaRecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.ModItems;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void hammerRecipe(RecipeExporter exporter) {
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.METAL_DETECTOR)
                .pattern("GGG")
                .pattern("GGG")
                .input('G', Items.GLASS)
                .criterion(hasItem(Items.GLASS), VanillaRecipeProvider.conditionsFromItem(Items.GLASS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.GLASS_SLAB)));
    }
}
