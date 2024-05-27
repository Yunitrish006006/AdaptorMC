package net.yunitrish.adaptor.datagen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> ORES = createTag("ores");
        public static final TagKey<Block> HAMMER = createTag("hammer");

        private static TagKey<Block> createTag(String name) {
            return  TagKey.of(RegistryKeys.BLOCK,new Identifier(Adaptor.MOD_ID,name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return  TagKey.of(RegistryKeys.ITEM,new Identifier(Adaptor.MOD_ID,name));
        }
    }
}
