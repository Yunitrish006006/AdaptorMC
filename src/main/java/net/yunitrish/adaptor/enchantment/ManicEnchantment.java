package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class ManicEnchantment extends Enchantment {
    public ManicEnchantment() {
        super(new Properties(
                ItemTags.WEAPON_ENCHANTABLE,
                Optional.of(ItemTags.SWORDS),
                2,
                4,
                new Cost(8,7),
                new Cost(8,7),
                2,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND}
        ));
    }
    // 狂躁:血量越低，攻擊越高 -- (等級+1)x損失血量%數 的額外傷害，並對自身造成額外傷害的15%


    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getWorld().isClient) {
            ServerWorld world = (ServerWorld) user.getWorld();
            if (user.getMainHandStack().getItem() instanceof SwordItem) {
                float percentage = (user.getMaxHealth() - user.getHealth())/user.getMaxHealth();
                float total = percentage * level;
                DamageSource source = user.getDamageSources().sting(user);
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
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }
}
