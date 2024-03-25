package net.yunitrish.adaptor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.entity.ModBlockEntities;
import net.yunitrish.adaptor.creature.villager.ModCustomTrades;
import net.yunitrish.adaptor.creature.villager.ModVillagers;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.custom.PorcupineEntity;
import net.yunitrish.adaptor.event.EntityDamagedHandler;
import net.yunitrish.adaptor.item.ModItemGroups;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.recipe.ModRecipes;
import net.yunitrish.adaptor.screen.ModScreenHandlers;
import net.yunitrish.adaptor.sound.ModSounds;
import net.yunitrish.adaptor.util.ModEvents;
import net.yunitrish.adaptor.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdaptorMain implements ModInitializer {
	public static final String MOD_ID = "adaptor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModCustomTrades.registerCustomTrades();
		ModEvents.registerEvents();
		ModSounds.registerSounds();
		ModRecipes.registerRecipes();
		ModVillagers.registerVillagers();
		ModBlockEntities.registerBlockEntities();
		ModEnchantments.registerModEnchantments();
		ModLootTableModifiers.modifyLootTables();
		ModScreenHandlers.registerScreenHandlers();
		FuelRegistry.INSTANCE.add(ModItems.BAMBOO_COAL,120);
		AttackEntityCallback.EVENT.register(new EntityDamagedHandler());
		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());
	}
}