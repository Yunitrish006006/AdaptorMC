package net.yunitrish.adaptor.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.yunitrish.adaptor.item.ModItemGroups;

public class HammerItem extends MiningToolItem {


    public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, material, ModItemGroups.HAMMER_MINEABLE, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isIn(BlockTags.PLANKS) || state.isIn(BlockTags.STONE_BRICKS) || state.isIn(BlockTags.DOORS)) {
            return 16.0f;
        } else return super.getMiningSpeedMultiplier(stack, state);
    }
}