package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class LeachEnchantment extends Enchantment {
    public LeachEnchantment() {
        super(new Properties(
                ItemTags.WEAPON_ENCHANTABLE,
                Optional.of(ItemTags.SWORDS),
                1,
                3,
                new Cost(8,7),
                new Cost(8,7),
                2,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND}
        ));
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getWorld().isClient) {
            ServerWorld world = (ServerWorld) user.getWorld();
            if (user.getMainHandStack().getItem() instanceof ToolItem) {
                float value = (float) (user.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 0.08 * Math.pow(1.2, level));
                ((ServerPlayerEntity) user).sendMessage(Text.translatable("+ " + Math.round(value * 100) / 100.0), true);
                user.heal(value);
                spawnParticleLine(world,target.getEyePos().add(0,-1.3,0),user.getPos().add(0,-0.1,0));
            }
        }
        super.onTargetDamaged(user, target, level);
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
