package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.sound.ModSounds;

public class ModItems {
    private static final RegistryKey<ItemGroup> AdaptorGroup = RegistryKey.of(RegistryKeys.ITEM_GROUP, Adaptor.id("adaptor_group"));
    public static final Item SOYBEAN = registerItem("soybean", new AliasedBlockItem(ModBlocks.Crops.SOYBEAN_CROP, new Item.Settings().food(ModFoodComponents.SOYBEAN)));
    public static final Item MARIJUANA_SEEDS = registerItem("marijuana_seeds", new AliasedBlockItem(ModBlocks.Crops.MARIJUANA_CROP, new Item.Settings()));

    // basic
    public static Item registerItem(String name, Item item, boolean inItemGroup) {
        Item temp = Registry.register(Registries.ITEM, Adaptor.id(name), item);
        if (inItemGroup) {
//            Adaptor.LOGGER.info("add {} into group",temp.getName().getContent());
            ItemGroupEvents.modifyEntriesEvent(AdaptorGroup).register(entries -> entries.add(temp));
        }
        return temp;
    }

    // overload name & item
    public static Item registerItem(String name, Item item) {
        return registerItem(name, item, true);
    }

    public static final Item SALT = registerItem("salt");
    public static final Item FLOUR = registerItem("flour");
    public static final Item PORCUPINE_SPAWN_EGG = registerItem("porcupine_spawn_egg", new SpawnEggItem(ModEntities.PORCUPINE,0xf1f1f1f1,0xd1d1d1d1,new Item.Settings()));

    // overload only name
    private static Item registerItem(String name) {
        return registerItem(name,new Item(new Item.Settings()));
    }

    public static void registerModItems() {
        Adaptor.LOGGER.info("Registering Mod Items for " + Adaptor.MOD_ID);
        Tools.register();
        Registry.register(
                Registries.ITEM_GROUP,
                Adaptor.id("adaptor_group"),
                FabricItemGroup
                        .builder()
                        .displayName(Text.translatable("itemgroup.adaptor_group"))
                        .icon(ModItems.Tools.IRON_HAMMER::getDefaultStack)
                        .build()
        );
//        ItemGroupEvents.modifyEntriesEvent(AdaptorGroup).register(ChestnutSeries.CHESTNUT_BOAT);
//        ItemGroupEvents.modifyEntriesEvent(AdaptorGroup).register(ChestnutSeries.CHESTNUT_CHEST_BOAT);
    }
    public static final Item MARIJUANA_LEAF = registerItem("marijuana_leaf",new Item(new Item.Settings()));
    public static final Item MARIJUANA = registerItem("marijuana",new Item(new Item.Settings().food(ModFoodComponents.MARIJUANA)));
    public static final Item DOUGH = registerItem("dough", new Item(new Item.Settings().food(ModFoodComponents.DOUGH)));

    public static class Tools {
        public static final Item METAL_DETECTOR = registerItem("metal_detector", new DetectorItem(new Item.Settings()));
        public static final Item WOODEN_HAMMER = registerItem("wooden_hammer", new HammerItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.WOOD, 1.0f, -2.8f))));
        public static final Item STONE_HAMMER = registerItem("stone_hammer", new HammerItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.STONE, 1.0f, -2.8f))));
        public static final Item COPPER_HAMMER = registerItem("copper_hammer", new HammerItem(ModToolMaterial.COPPER, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ModToolMaterial.COPPER, 1.0f, -2.8f))));
        public static final Item IRON_HAMMER = registerItem("iron_hammer", new HammerItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.IRON, 1.0f, -2.8f))));
        public static final Item GOLDEN_HAMMER = registerItem("golden_hammer", new HammerItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.GOLD, 1.0f, -2.8f))));
        public static final Item DIAMOND_HAMMER = registerItem("diamond_hammer", new HammerItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.DIAMOND, 1.0f, -2.8f))));
        public static final Item NETHERITE_HAMMER = registerItem("netherite_hammer", new HammerItem(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.NETHERITE, 1.0f, -2.8f))));
        public static final Item COPPER_SWORD = registerItem("copper_sword", new SwordItem(ModToolMaterial.COPPER, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterial.COPPER, 3, -2.4f))));
        public static final Item COPPER_SHOVEL = registerItem("copper_shovel", new ShovelItem(ModToolMaterial.COPPER, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterial.COPPER, 1.5f, -3.0f))));
        public static final Item COPPER_AXE = registerItem("copper_axe", new AxeItem(ModToolMaterial.COPPER, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterial.COPPER, 1.0f, -2.8f))));
        public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe", new PickaxeItem(ModToolMaterial.COPPER, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterial.COPPER, 5.0f, -3.0f))));
        public static final Item COPPER_HOE = registerItem("copper_hoe", new HoeItem(ModToolMaterial.COPPER, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterial.COPPER, -3.0f, 0.0f))));
        public static final Item COPPER_HELMET = registerItem("copper_helmet", new ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1).maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate", new ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1).maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        public static final Item COPPER_LEGGINGS = registerItem("copper_leggings", new ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1).maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        public static final Item COPPER_BOOTS = registerItem("copper_boots", new ModArmorItem(ModArmorMaterial.COPPER, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1).maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        public static final Item COPPER_KEY = registerItem("copper_key");
        public static final Item SAKURA_VALLEY_MUSIC_DISC = registerItem("sakura_valley_music_disc", new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(ModSounds.SAKURA_VALLEY_MUSIC)));
        public static final Item BAR_BRAWL_MUSIC_DISC = registerItem("bar_brawl_music_disc", new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(ModSounds.BAR_BRAWL_MUSIC)));

        public static void register() {
        }
    }
}