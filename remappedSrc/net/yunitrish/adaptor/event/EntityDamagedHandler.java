package net.yunitrish.adaptor.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EntityDamagedHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {

        if (!world.isClient) {
            if (entity instanceof Attackable) {
                if (player.getInventory().getMainHandStack().getItem() instanceof SwordItem tool) {
//                    double playerDamage = player.getAttributes().getBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
//                    double weaponDamage = tool.getAttackDamage();
//                    MarkerEntity indicator = new MarkerEntity(EntityType.MARKER,world);
//                    indicator.setPos(entity.getX(),entity.getY(),entity.getZ());
//                    indicator.setCustomNameVisible(true);
//                    indicator.setCustomName(Text.literal(String.valueOf((playerDamage+weaponDamage))));
//                    indicator.setInvisible(false);
//                    indicator.setGlowing(true);
//                    player.sendMessage(Text.literal(indicator.toString()));
//                    world.spawnEntity(indicator);
                }
            }
        }
        return ActionResult.PASS;
    }
}
