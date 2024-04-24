package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.item.custom.HammerItem;
import net.yunitrish.adaptor.item.custom.MetalDetectionItem;
import net.yunitrish.adaptor.item.custom.ModArmorItem;
import net.yunitrish.adaptor.sound.ModSounds;

public class ModItems {

    public static final Item SALT = registerItem("salt",new Item(new FabricItemSettings()));
    public static final Item FLOUR = registerItem("flour",new Item(new FabricItemSettings()));
    public static final Item DOUGH =  registerItem("dough", new Item(new FabricItemSettings().food(ModFoodComponents.DOUGH)));

    public static final Item SOYBEAN = registerItem("soybean",new AliasedBlockItem(ModBlocks.SOYBEAN_CROP,new FabricItemSettings().food(ModFoodComponents.SOYBEAN)));

    public static final Item CORN = registerItem("corn",new Item(new FabricItemSettings().food(ModFoodComponents.CORN)));
    public static final Item CORN_SEEDS = registerItem("corn_seeds",new AliasedBlockItem(ModBlocks.CORN_CROP,new FabricItemSettings()));


    public static final Item MARIJUANA = registerItem("marijuana",new Item(new FabricItemSettings().food(ModFoodComponents.MARIJUANA)));
    public static final Item MARIJUANA_LEAF = registerItem("marijuana_leaf",new Item(new FabricItemSettings()));
    public static final Item MARIJUANA_SEEDS = registerItem("marijuana_seeds",new AliasedBlockItem(ModBlocks.MARIJUANA_PLANT,new FabricItemSettings()));

    public static final  Item METAL_DETECTOR = registerItem("metal_detector",new MetalDetectionItem(new FabricItemSettings().maxDamage(64)));

    public static final Item BAMBOO_COAL =  registerItem("bamboo_coal", new Item(new FabricItemSettings()));

    public static final Item WOODEN_HAMMER =  registerItem("wooden_hammer", new HammerItem(ToolMaterials.WOOD,1,2f,new FabricItemSettings()));
    public static final Item STONE_HAMMER =  registerItem("stone_hammer", new HammerItem(ToolMaterials.STONE,1,2f,new FabricItemSettings()));
    public static final Item COPPER_HAMMER =  registerItem("copper_hammer", new HammerItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));
    public static final Item IRON_HAMMER =  registerItem("iron_hammer", new HammerItem(ToolMaterials.IRON,1,2f,new FabricItemSettings()));
    public static final Item GOLDEN_HAMMER =  registerItem("golden_hammer", new HammerItem(ToolMaterials.GOLD,1,2f,new FabricItemSettings()));
    public static final Item DIAMOND_HAMMER =  registerItem("diamond_hammer", new HammerItem(ToolMaterials.DIAMOND,1,2f,new FabricItemSettings()));
    public static final Item NETHERITE_HAMMER =  registerItem("netherite_hammer", new HammerItem(ToolMaterials.NETHERITE,1,2f,new FabricItemSettings()));
    /*-------------------------------------------------------------------------*/
    public static final Item BAR_BRAWL_MUSIC_DISC = registerItem("bar_brawl_music_disc",new MusicDiscItem(3, ModSounds.BAR_BRAWL,new FabricItemSettings().maxCount(1),122));
    public static final Item SAKURA_VALLEY_MUSIC_DISC = registerItem("sakura_valley_music_disc",new MusicDiscItem(3, ModSounds.SAKURA_VALLEY,new FabricItemSettings().maxCount(1),119));
    /*-------------------------------------------------------------------------*/
    public static final  Item COPPER_PICKAXE = registerItem("copper_pickaxe",new PickaxeItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));
    public static final  Item COPPER_AXE = registerItem("copper_axe",new AxeItem(ModToolMaterial.Copper,5,1.3f,new FabricItemSettings()));
    public static final  Item COPPER_SHOVEL = registerItem("copper_shovel",new ShovelItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));
    public static final  Item COPPER_SWORD = registerItem("copper_sword",new SwordItem(ModToolMaterial.Copper,4,1.7f,new FabricItemSettings()));
    public static final  Item COPPER_HOE = registerItem("copper_hoe",new HoeItem(ModToolMaterial.Copper,1,2f,new FabricItemSettings()));


    public static final  Item COPPER_HELMET = registerItem("copper_helmet",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.HELMET,new FabricItemSettings()));
    public static final  Item COPPER_CHESTPLATE = registerItem("copper_chestplate",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.CHESTPLATE,new FabricItemSettings()));
    public static final  Item COPPER_LEGGINGS = registerItem("copper_leggings",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.LEGGINGS,new FabricItemSettings()));
    public static final  Item COPPER_BOOTS = registerItem("copper_boots",new ModArmorItem(ModArmorMaterials.COPPER,ArmorItem.Type.BOOTS,new FabricItemSettings()));

    public static final Item PORCUPINE_SPAWN_EGG = registerItem("porcupine_spawn_egg",new SpawnEggItem(ModEntities.PORCUPINE, 0xf1f1f1f1,0xd1d1d1d1,new FabricItemSettings()));


    public static final TagKey<Block> HAMMER_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier("adaptor", "mineable/hemmer"));

    private static void adaptorIngredientGroup(FabricItemGroupEntries entries) {
        entries.add(SALT);
        entries.add(FLOUR);
        entries.add(DOUGH);
        entries.add(SOYBEAN);
        entries.add(CORN);
        entries.add(CORN_SEEDS);
    }

    private static void adaptorToolsGroup(FabricItemGroupEntries entries) {
        entries.add(WOODEN_HAMMER);
        entries.add(STONE_HAMMER);
        entries.add(COPPER_HAMMER);
        entries.add(IRON_HAMMER);
        entries.add(GOLDEN_HAMMER);
        entries.add(DIAMOND_HAMMER);
        entries.add(NETHERITE_HAMMER);

        entries.add(COPPER_HOE);
        entries.add(COPPER_AXE);
        entries.add(COPPER_PICKAXE);
        entries.add(COPPER_SHOVEL);

        entries.add(METAL_DETECTOR);
    }

    private static void adaptorCombatGroup(FabricItemGroupEntries entries) {
        entries.add(COPPER_AXE);
        entries.add(COPPER_SWORD);
        entries.add(COPPER_HELMET);
        entries.add(COPPER_CHESTPLATE);
        entries.add(COPPER_LEGGINGS);
        entries.add(COPPER_BOOTS);
    }
    private static void adaptorBuildingGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.DIRT_DOOR);
        entries.add(ModBlocks.DIRT_FENCE);
        entries.add(ModBlocks.DIRT_BUTTON);
        entries.add(ModBlocks.DIRT_SLAB);
        entries.add(ModBlocks.DIRT_STAIRS);
        entries.add(ModBlocks.DIRT_FENCE_GATE);
        entries.add(ModBlocks.DIRT_TRAPDOOR);
        entries.add(ModBlocks.DIRT_WALL);
        entries.add(ModBlocks.DIRT_PRESSURE_PLATE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AdaptorMain.MOD_ID,name),item);
    }

    public static final ItemGroup AdaptorGroup = Registry.register(Registries.ITEM_GROUP,new Identifier(AdaptorMain.MOD_ID, "adaptor_group"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.adaptor_group")).icon(()-> new ItemStack(ModItems.IRON_HAMMER)).entries(((displayContext, entries) -> {
        entries.add(ModItems.FLOUR);
        entries.add(ModItems.DOUGH);
        entries.add(ModItems.SALT);

        entries.add(ModItems.SOYBEAN);
        entries.add(ModItems.CORN);
        entries.add(ModItems.CORN_SEEDS);
        entries.add(ModItems.MARIJUANA_LEAF);
        entries.add(ModItems.MARIJUANA_SEEDS);

        entries.add(ModItems.METAL_DETECTOR);
        entries.add(ModItems.IRON_HAMMER);

        entries.add(ModItems.COPPER_AXE);
        entries.add(ModItems.COPPER_HOE);
        entries.add(ModItems.COPPER_PICKAXE);
        entries.add(ModItems.COPPER_SHOVEL);
        entries.add(ModItems.COPPER_SWORD);

        entries.add(ModItems.COPPER_HELMET);
        entries.add(ModItems.COPPER_CHESTPLATE);
        entries.add(ModItems.COPPER_LEGGINGS);
        entries.add(ModItems.COPPER_BOOTS);

        entries.add(ModItems.BAMBOO_COAL);

        entries.add(ModBlocks.SALT_BLOCK);
        entries.add(ModBlocks.SALT_ORE);
        entries.add(ModBlocks.DEEPSLATE_SALT_ORE);
        entries.add(ModBlocks.NETHER_SALT_ORE);
        entries.add(ModBlocks.END_STONE_SALT_ORE);

        entries.add(ModBlocks.DIRT_BUTTON);
        entries.add(ModBlocks.DIRT_DOOR);
        entries.add(ModBlocks.DIRT_FENCE);
        entries.add(ModBlocks.DIRT_FENCE_GATE);
        entries.add(ModBlocks.DIRT_PRESSURE_PLATE);
        entries.add(ModBlocks.DIRT_SLAB);
        entries.add(ModBlocks.DIRT_STAIRS);
        entries.add(ModBlocks.DIRT_TRAPDOOR);
        entries.add(ModBlocks.DIRT_WALL);

        entries.add(ModItems.PORCUPINE_SPAWN_EGG);

        entries.add(ModBlocks.SOUND_BLOCK);
        entries.add(ModBlocks.STONE_MILL);

        entries.add(ModBlocks.DAHLIA);

        entries.add(ModItems.SAKURA_VALLEY_MUSIC_DISC);
        entries.add(ModItems.BAR_BRAWL_MUSIC_DISC);
    })).build());

    public static void registerModItems() {
        AdaptorMain.LOGGER.info("Registering Mod Items for " + AdaptorMain.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::adaptorIngredientGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::adaptorToolsGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::adaptorCombatGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModItems::adaptorBuildingGroup);
    }
}
