package net.yunitrish.adaptor.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> METAL_DETECTOR_DETECTABLE_BLOCKS = createTag("metal_detector_detectable_blocks");

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
