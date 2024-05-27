package net.yunitrish.adaptor.item;

import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.yunitrish.adaptor.datagen.ModTags;

public class HammerItem extends MiningToolItem {
    public HammerItem(ToolMaterial material, Settings settings) {
        super(material, ModTags.Blocks.HAMMER, settings);
    }
}
