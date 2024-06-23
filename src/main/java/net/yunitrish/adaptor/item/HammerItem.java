package net.yunitrish.adaptor.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.yunitrish.adaptor.datagen.ModTags;

import java.util.List;
import java.util.Map;

public class HammerItem extends ToolItem {
    public HammerItem(ToolMaterial material, Settings settings) {
        super(material, settings.component(DataComponentTypes.TOOL, createComponent(material)));
    }

    public static ToolComponent createComponent(ToolMaterial material) {
        float multiplier = material.getMiningSpeedMultiplier();
        return new ToolComponent(
                List.of(
                        ToolComponent.Rule.ofNeverDropping(material.getInverseTag()),
                        ToolComponent.Rule.ofAlwaysDropping(ModTags.Blocks.HAMMER_EFFICIENT, multiplier * 1.2f),
                        ToolComponent.Rule.ofAlwaysDropping(BlockTags.PICKAXE_MINEABLE, multiplier * 0.3f),
                        ToolComponent.Rule.ofAlwaysDropping(BlockTags.AXE_MINEABLE, multiplier * 0.3f)
                ),
                1.0f,
                1
        );
    }


    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent
                .builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) return ActionResult.PASS;
        if (context.getPlayer() == null) return ActionResult.PASS;
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());

        Map<Block, Block> list = Map.ofEntries(
                Map.entry(Blocks.COBBLESTONE, Blocks.GRAVEL),
                Map.entry(Blocks.STONE, Blocks.COBBLESTONE)
        );

        for (Map.Entry<Block, Block> entry : list.entrySet()) {
            if (state.getBlock() == entry.getKey()) {
                context.getWorld().breakBlock(context.getBlockPos(), false, context.getPlayer());
                context.getStack().damage(1, context.getPlayer(), EquipmentSlot.MAINHAND);
                context.getWorld().setBlockState(context.getBlockPos(), entry.getValue().getDefaultState());
            }
        }
        return super.useOnBlock(context);
    }
}
