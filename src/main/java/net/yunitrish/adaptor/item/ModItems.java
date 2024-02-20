package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.item.custom.MetalDetectionItem;
import net.yunitrish.adaptor.item.custom.ModArmorItem;

public class ModItems {

    public static final Item SALT = registerItem("salt",new Item(new FabricItemSettings()));
    public static final Item FLOUR = registerItem("flour",new Item(new FabricItemSettings()));
    public static final Item DOUGH =  registerItem("dough", new Item(new FabricItemSettings().food(ModFoodComponents.DOUGH)));

    public static final Item SOYBEAN = registerItem("soybean",new AliasedBlockItem(ModBlocks.SOYBEAN_CROP,new FabricItemSettings().food(ModFoodComponents.SOYBEAN)));

    public static final Item CORN = registerItem("corn",new Item(new FabricItemSettings().food(ModFoodComponents.CORN)));
    public static final Item CORN_SEEDS = registerItem("corn_seeds",new AliasedBlockItem(ModBlocks.CORN_CROP,new FabricItemSettings()));

    public static final  Item METAL_DETECTOR = registerItem("metal_detector",new MetalDetectionItem(new FabricItemSettings().maxDamage(64)));

    public static final Item BAMBOO_COAL =  registerItem("bamboo_coal", new Item(new FabricItemSettings()));

    public static final Item IRON_HAMMER =  registerItem("iron_hammer", new Item(
            new FabricItemSettings()
                    .maxCount(1)
                    .maxDamage(512)
    ));


    public static final  Item COPPER_PICKAXE = registerItem("copper_pickaxe",new PickaxeItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));
    public static final  Item COPPER_AXE = registerItem("copper_axe",new AxeItem(ModToolMaterial.Copper,5,1.3f,new FabricItemSettings()));
    public static final  Item COPPER_SHOVEL = registerItem("copper_shovel",new ShovelItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));
    public static final  Item COPPER_SWORD = registerItem("copper_sword",new SwordItem(ModToolMaterial.Copper,4,1.7f,new FabricItemSettings()));
    public static final  Item COPPER_HOE = registerItem("copper_hoe",new HoeItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));


    public static final  Item COPPER_HELMET = registerItem("copper_helmet",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.HELMET,new FabricItemSettings()));
    public static final  Item COPPER_CHESTPLATE = registerItem("copper_chestplate",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.CHESTPLATE,new FabricItemSettings()));
    public static final  Item COPPER_LEGGINGS = registerItem("copper_leggings",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.LEGGINGS,new FabricItemSettings()));
    public static final  Item COPPER_BOOTS = registerItem("copper_boots",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.BOOTS,new FabricItemSettings()));


    private static void modIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(SALT);
        entries.add(FLOUR);
        entries.add(DOUGH);
        entries.add(SOYBEAN);
        entries.add(CORN);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Adaptor.MOD_ID,name),item);
    }

    public static void registerModItems() {
        Adaptor.LOGGER.info("Registering Mod Items for " + Adaptor.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::modIngredientTabItemGroup);
    }
}
