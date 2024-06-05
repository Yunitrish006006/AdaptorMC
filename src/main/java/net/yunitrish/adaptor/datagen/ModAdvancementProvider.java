package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.building.DirtSeries;
import net.yunitrish.adaptor.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    protected ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    public void buildingSeries(Consumer<AdvancementEntry> consumer) {
        AdvancementEntry sharpen_tools_better_job = Advancement.Builder.create()
                .display(
                        ModItems.IRON_HAMMER,
                        Text.translatable("advancements.sharpen_tools_better_job.title"),
                        Text.translatable("advancements.sharpen_tools_better_job.description"),
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("sharpen_tools_better_job", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(ModTags.Items.HAMMER).build()))
                .build(consumer, Adaptor.MOD_ID + "/sharpen_tools_better_job");

        AdvancementEntry as_hard_as_dirt = Advancement.Builder.create()
                .display(
                        DirtSeries.DIRT_STAIRS,
                        Text.translatable("advancements.as_hard_as_dirt.title"),
                        Text.translatable("advancements.as_hard_as_dirt.description"),
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .parent(sharpen_tools_better_job)
                .criterion("as_hard_as_dirt", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().items(Items.DIRT).build()))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_SLAB))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_STAIRS))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_BUTTON))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_PRESSURE_PLATE))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_WALL))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_DOOR))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_TRAPDOOR))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_FENCE))))
                .rewards(AdvancementRewards.Builder.recipe(Adaptor.modIdentifier(ModRecipeProvider.getRecipeNameSpace(DirtSeries.DIRT_FENCE_GATE))))
                .build(consumer, Adaptor.MOD_ID + "/as_hard_as_dirt");

        AdvancementEntry ingenuity = Advancement.Builder.create()
                .display(
                        ModItems.IRON_HAMMER,
                        Text.translatable("advancements.ingenuity.title"),
                        Text.translatable("advancements.ingenuity.description"),
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .parent(sharpen_tools_better_job)
                .criterion("ingenuity", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(ModTags.Items.HAMMER).build()))
                .build(consumer, Adaptor.MOD_ID + "/ingenuity");
    }

    public void battleSeries(Consumer<AdvancementEntry> consumer) {
//        AdvancementEntry LEACH = Advancement.Builder.create()
//                .display(
//                        ModItems.IRON_HAMMER,
//                        Text.translatable("advancements.leach.title"),
//                        Text.translatable("advancements.leach.description"),
//                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
//                        AdvancementFrame.TASK,
//                        true,
//                        true,
//                        false
//                )
//                .criterion("leach", )
//                .rewards(AdvancementRewards.Builder.experience(100))
//                .build(consumer, Adaptor.MOD_ID +"/root");
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {

        buildingSeries(consumer);

        battleSeries(consumer);
    }
}
