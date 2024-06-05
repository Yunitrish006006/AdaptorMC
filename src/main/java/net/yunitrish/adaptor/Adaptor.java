package net.yunitrish.adaptor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.block.ModBlockEntities;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.command.ModCommands;
import net.yunitrish.adaptor.enchantment.EnchantAttributeHandler;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.entity.ModBoats;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.creature.PorcupineEntity;
import net.yunitrish.adaptor.entity.creature.villager.ModCustomTrades;
import net.yunitrish.adaptor.entity.creature.villager.ModVillagers;
import net.yunitrish.adaptor.event.ModEvents;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.item.ModLootTableModifiers;
import net.yunitrish.adaptor.recipe.ModRecipes;
import net.yunitrish.adaptor.screen.ModScreenHandlers;
import net.yunitrish.adaptor.sound.ModSounds;
import net.yunitrish.adaptor.world.generation.ModWorldGeneration;
import net.yunitrish.adaptor.world.tree.ModFoliagePlacerTypes;
import net.yunitrish.adaptor.world.tree.ModTrunkPlacerTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Adaptor implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("adaptor");
	public static final String MOD_ID = "adaptor";
	public static Identifier modIdentifier(String name) {
		return new Identifier(Adaptor.MOD_ID,name);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing");
		ModEvents.registerEvents();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBoats.registerBoats();
		ModEnchantments.registerModEnchantments();
		ModTrunkPlacerTypes.register();
        ModFoliagePlacerTypes.register();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();
		ModSounds.registerSounds();
		ModCommands.register();
		ModVillagers.registerVillagers();
		ModCustomTrades.registerCustomTrades();
		ModLootTableModifiers.modifyLootTables();
		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());
		ModWorldGeneration.generateModWorldGeneration();

        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.MOSSY_COBBLESTONE)
                .lightWithFluid(Fluids.WATER)
                .destDimID(modIdentifier("pre_era_dimension"))
                .tintColor(0x575c3b)
                .onlyLightInOverworld()
                .registerPortal();
		EnchantAttributeHandler.registerEnchantmentAttributes();
	}
}