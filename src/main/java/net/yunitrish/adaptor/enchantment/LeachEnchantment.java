package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

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
                spawnParticleLine(world,target.getEyePos().add(0,-1.3,0),user.getPos().add(0,-0.1,0));
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinPower(int level) {
        return 8 + (level-1) * 8;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 22;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }

    public static void spawnParticleLine(ServerWorld world, Vec3d from, Vec3d to) {



        Vec3d start = new Vec3d(from.getX() + 0.5, from.getY() + 0.5, from.getZ() + 0.5);
        Vec3d end = new Vec3d(to.getX() + 0.5, to.getY() + 0.5, to.getZ() + 0.5);

        double distance = start.distanceTo(end);
        int numberOfParticles = (int) (distance * 4); // Adjust as needed

        Vec3d direction = end.subtract(start).normalize();
        Vec3d offset = direction.multiply(4.0 / numberOfParticles);

        for (int i = 0; i < numberOfParticles; i++) {
            Vec3d particlePos = start.add(offset.multiply(i));
            world.spawnParticles(ParticleTypes.DAMAGE_INDICATOR,
                    particlePos.getX(), particlePos.getY(), particlePos.getZ(),
                    1, 0, 0, 0, 0.02);
        }
    }
}
