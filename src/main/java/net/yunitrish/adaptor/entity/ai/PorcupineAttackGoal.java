package net.yunitrish.adaptor.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import net.yunitrish.adaptor.entity.creature.PorcupineEntity;

public class PorcupineAttackGoal extends MeleeAttackGoal {
    private final PorcupineEntity entity;
    private int attackDelay = 20;
    private int ticksUnitNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;

    public PorcupineAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = (PorcupineEntity) mob;
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 20;
        ticksUnitNextAttack = 20;
    }

    @Override
    protected void attack(LivingEntity target) {
        if (isEnemyWithAttackDistance(target)) {
            shouldCountTillNextAttack = true;

            if( isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }
            if (isTimeToAttack()) {
                this.mob.getLookControl().lookAt(target);
                performAttack(target);
            }
        }
        else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationCooldown = 0;
        }
    }

    protected boolean isEnemyWithAttackDistance(LivingEntity entity) {
        return this.entity.distanceTo(entity)<=2f;
    }

    private void resetAttackCooldown() {
        this.ticksUnitNextAttack = this.getTickCount(attackDelay*2);
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUnitNextAttack <= attackDelay;
    }

    protected boolean isTimeToAttack() {
        return this.ticksUnitNextAttack <= 0;
    }

    protected void performAttack(LivingEntity entity) {
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(entity);
    }


    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            this.ticksUnitNextAttack = Math.max(this.ticksUnitNextAttack-1,0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
