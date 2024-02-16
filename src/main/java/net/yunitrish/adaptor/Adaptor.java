package net.yunitrish.adaptor;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.ModItemGroups;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Adaptor implements ModInitializer {
	public static final String MOD_ID = "adaptor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModLootTableModifiers.modifyLootTables();
		FuelRegistry.INSTANCE.add(ModItems.BAMBOO_COAL,120);
	}
}