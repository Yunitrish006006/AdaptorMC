package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

public class ManicEnchantment extends Enchantment {
    // 狂躁:血量越低，攻擊越高 -- (等級+1)x損失血量%數 的額外傷害，並對自身造成額外傷害的15%

    public ManicEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getWorld().isClient) {
            ServerWorld world = (ServerWorld) user.getWorld();
            if (user.getMainHandStack().getItem() instanceof SwordItem) {
                float percentage = (user.getMaxHealth() - user.getHealth())/user.getMaxHealth();
                float total = percentage * level;
                DamageSource source = user.getDamageSources().create(DamageTypes.MAGIC,user);
                target.damage(source, total);
                user.damage(source, total*0.15f);
                world.spawnParticles(ParticleTypes.SOUL,target.getX(),target.getY()+0.4,target.getZ(),Math.round(total*4),0.2,0.2,0.2,0.2);
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }
}
