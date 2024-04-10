package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class LeachEnchantment extends Enchantment {
    public LeachEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getWorld().isClient) {
            ServerWorld world = (ServerWorld) user.getWorld();
            if (user.getMainHandStack().getItem() instanceof SwordItem tool) {
                float total = (float) ((user.getAttributes().getBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)+tool.getAttackDamage())*level*0.2);
                user.heal(total);
                world.spawnParticles(ParticleTypes.SOUL,target.getX(),target.getY()+0.4,target.getZ(),Math.round(total*4),1,1,1,0.2);
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
