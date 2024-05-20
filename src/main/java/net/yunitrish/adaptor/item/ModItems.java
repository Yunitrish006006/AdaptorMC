package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.sound.ModSounds;

public class ModItems {
    public static Item registerItem(String name, Item item, boolean inItemGroup) {
        Item temp = Registry.register(Registries.ITEM, new Identifier(Adaptor.MOD_ID, name), item);
        if(inItemGroup) addToItemGroup("adaptor_group",temp);
        return temp;
    }
    public static Item registerItem(String name, Item item) {
        return registerItem(name, item, true);
    }

    private static Item registerItem(String name) {
        return registerItem(name,new Item(new Item.Settings()));
    }

    public static final Item SALT = registerItem("salt");
    public static final Item FLOUR = registerItem("flour");
    public static final Item PORCUPINE_SPAWN_EGG = registerItem("porcupine_spawn_egg", new SpawnEggItem(ModEntities.PORCUPINE,0xf1f1f1f1,0xd1d1d1d1,new Item.Settings()));
    public static final Item SOYBEAN = registerItem("soybean", new AliasedBlockItem(ModBlocks.SOYBEAN_CROP, new Item.Settings().food(ModFoodComponents.SOYBEAN)));
    public static final Item MARIJUANA_SEEDS = registerItem("marijuana_seeds", new AliasedBlockItem(ModBlocks.MARIJUANA_CROP, new Item.Settings()));
    public static final Item MARIJUANA_LEAF = registerItem("marijuana_leaf",new Item(new Item.Settings()));
    public static final Item MARIJUANA = registerItem("marijuana",new Item(new Item.Settings().food(ModFoodComponents.MARIJUANA)));
    public static final Item DOUGH = registerItem("dough", new Item(new Item.Settings().food(ModFoodComponents.DOUGH)));
    public static final Item METAL_DETECTOR = registerItem("metal_detector", new DetectorItem(new Item.Settings()));

    public static final Item WOODEN_HAMMER =  registerItem("wooden_hammer", new HammerItem(ToolMaterials.WOOD,new Item.Settings()));
    public static final Item STONE_HAMMER =  registerItem("stone_hammer", new HammerItem(ToolMaterials.STONE,new Item.Settings()));
    public static final Item COPPER_HAMMER =  registerItem("copper_hammer", new HammerItem(ModToolMaterial.COPPER,new Item.Settings()));
    public static final Item IRON_HAMMER =  registerItem("iron_hammer", new HammerItem(ToolMaterials.IRON,new Item.Settings()));
    public static final Item GOLDEN_HAMMER =  registerItem("golden_hammer", new HammerItem(ToolMaterials.GOLD,new Item.Settings()));
    public static final Item DIAMOND_HAMMER =  registerItem("diamond_hammer", new HammerItem(ToolMaterials.DIAMOND,new Item.Settings()));
    public static final Item NETHERITE_HAMMER =  registerItem("netherite_hammer", new HammerItem(ToolMaterials.NETHERITE,new Item.Settings()));

    public static final  Item COPPER_PICKAXE = registerItem("copper_pickaxe",new PickaxeItem(ModToolMaterial.COPPER,new Item.Settings()));
    public static final  Item COPPER_AXE = registerItem("copper_axe",new AxeItem(ModToolMaterial.COPPER,new Item.Settings()));
    public static final  Item COPPER_SHOVEL = registerItem("copper_shovel",new ShovelItem(ModToolMaterial.COPPER,new Item.Settings()));
    public static final  Item COPPER_SWORD = registerItem("copper_sword",new SwordItem(ModToolMaterial.COPPER,new Item.Settings()));
    public static final  Item COPPER_HOE = registerItem("copper_hoe",new HoeItem(ModToolMaterial.COPPER,new Item.Settings()));

    public static final  Item COPPER_HELMET = registerItem("copper_helmet",new ModArmorItem(ModArmorMaterial.COPPER,ArmorItem.Type.HELMET,new Item.Settings().maxCount(1)));
    public static final  Item COPPER_CHESTPLATE = registerItem("copper_chestplate",new ModArmorItem(ModArmorMaterial.COPPER,ArmorItem.Type.CHESTPLATE,new Item.Settings().maxCount(1)));
    public static final  Item COPPER_LEGGINGS = registerItem("copper_leggings",new ModArmorItem(ModArmorMaterial.COPPER,ArmorItem.Type.LEGGINGS,new Item.Settings().maxCount(1)));
    public static final  Item COPPER_BOOTS = registerItem("copper_boots",new ModArmorItem(ModArmorMaterial.COPPER,ArmorItem.Type.BOOTS,new Item.Settings().maxCount(1)));

    public static final Item BAR_BRAWL_MUSIC_DISC = registerItem("bar_brawl_music_disc",new MusicDiscItem(3, ModSounds.BAR_BRAWL,new Item.Settings().maxCount(1),122));
    public static final Item SAKURA_VALLEY_MUSIC_DISC = registerItem("sakura_valley_music_disc",new MusicDiscItem(3, ModSounds.SAKURA_VALLEY,new Item.Settings().maxCount(1),119));

    public static final Item CHESTNUT_SIGN = registerItem("chestnut_sign",new SignItem(new Item.Settings().maxCount(16), ChestnutSeries.STANDING_CHESTNUT_SIGN,ChestnutSeries.WALL_CHESTNUT_SIGN));
    public static final Item HANGING_CHESTNUT_SIGN = registerItem("chestnut_hanging_sign",new HangingSignItem(ChestnutSeries.CHESTNUT_HANGING_SIGN,ChestnutSeries.CHESTNUT_WALL_HANGING_SIGN,new Item.Settings().maxCount(16)));

    public static ItemGroup AdaptorGroup;
    public static void registerModItems() {
        Adaptor.LOGGER.info("Registering Mod Items for " + Adaptor.MOD_ID);
        AdaptorGroup = FabricItemGroup.builder()
                .displayName(Text.translatable("itemgroup.adaptor_group"))
                .icon(()-> new ItemStack(ModItems.METAL_DETECTOR))
                .build();
        Registry.register(Registries.ITEM_GROUP,Adaptor.modIdentifier("adaptor_group"),AdaptorGroup);
    }

    public static void addToItemGroup(String groupId, Item item) {
        ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP,Adaptor.modIdentifier(groupId))).register(entries -> entries.add(item));
    }
}