package net.yunitrish.adaptor;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.creature.villager.ModCustomTrades;
import net.yunitrish.adaptor.creature.villager.ModVillagers;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.event.EntityDamagedHandler;
import net.yunitrish.adaptor.item.ModItemGroups;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.util.ModEvents;
import net.yunitrish.adaptor.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Adaptor implements ModInitializer {
	public static final String MOD_ID = "adaptor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModCustomTrades.registerCustomTrades();
		ModEvents.registerEvents();
		ModVillagers.registerVillagers();
		ModEnchantments.registerModEnchantments();
		ModLootTableModifiers.modifyLootTables();
		FuelRegistry.INSTANCE.add(ModItems.BAMBOO_COAL,120);

		AttackEntityCallback.EVENT.register(new EntityDamagedHandler());
	}
}