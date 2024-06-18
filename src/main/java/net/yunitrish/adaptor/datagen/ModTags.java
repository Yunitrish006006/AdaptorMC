package net.yunitrish.adaptor.datagen;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.yunitrish.adaptor.Adaptor;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> ORES = createTag("ores");
        public static final TagKey<Block> HAMMER = createTag("hammer");
        public static final TagKey<Block> HAMMER_EFFICIENT = createTag("hammer_efficient");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Adaptor.id(name));
        }
    }

    public static class Items {

        public static final TagKey<Item> HAMMER = createTag("hammer");
        public static final TagKey<Item> BAKE = createTag("bake");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Adaptor.id(name));
        }
    }

    public static class Enchantments {

        public static final TagKey<Enchantment> WISDOM = createTag("wisdom");
        public static final TagKey<Enchantment> LEACH = createTag("leach");
        public static final TagKey<Enchantment> MANIC = createTag("manic");

        private static TagKey<Enchantment> createTag(String name) {
            return TagKey.of(RegistryKeys.ENCHANTMENT, Adaptor.id(name));
        }
    }
}
