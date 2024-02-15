package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.item.custom.MetalDetectionItem;

public class ModItems {

    public static final Item SALT = registerItem("salt",new Item(new FabricItemSettings()));
    public static final Item FLOUR = registerItem("flour",new Item(new FabricItemSettings()));
    public static final Item DOUGH =  registerItem("dough", new Item(new FabricItemSettings().food(ModFoodComponents.DOUGH)));

    public static final  Item METAL_DETECTOR = registerItem("metal_detector",new MetalDetectionItem(new FabricItemSettings().maxDamage(64)));

    public static final Item BAMBOO_COAL =  registerItem("bamboo_coal", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(SALT);
        entries.add(FLOUR);
        entries.add(DOUGH);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Adaptor.MOD_ID,name),item);
    }

    public static void registerModItems() {
        Adaptor.LOGGER.info("Registering Mod Items for " + Adaptor.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
