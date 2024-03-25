package net.yunitrish.adaptor.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.ai.PorcupineAttackGoal;
import org.jetbrains.annotations.Nullable;

public class PorcupineEntity extends AnimalEntity {

    public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(PorcupineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState idlingAnimationState = new AnimationState();
    private int idleAnimationCooldown = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationCooldown = 0;

    @Override
    protected void initDataTracker() {
        this.dataTracker.set(ATTACKING, false);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    public PorcupineEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new PorcupineAttackGoal(this,10,true));
        this.goalSelector.add(1, new AnimalMateGoal(this,1.150));
        this.goalSelector.add(2, new TemptGoal(this,1.250, Ingredient.ofItems(Items.BEETROOT),false));
        this.goalSelector.add(3, new FollowParentGoal(this,1.150));
        this.goalSelector.add(4, new WanderAroundFarGoal(this,10));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class,4f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
    }

    private void setupAnimationStates() {
        if (this.idleAnimationCooldown <= 0) {
            this.idleAnimationCooldown = this.random.nextInt(40) + 80;
            this.idlingAnimationState.start(this.age);
        } else {
            --this.idleAnimationCooldown;
        }

        if (this.isAttacking() && attackAnimationCooldown <= 0) {
            attackAnimationCooldown = 40;
            attackAnimationState.start(this.age);
        }
        else {
            --this.attackAnimationCooldown;
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta+6.0f,1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f,0.2f);
    }

    public static DefaultAttributeContainer.Builder createPorcupineAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,25)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.07f)
                .add(EntityAttributes.GENERIC_ARMOR,0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,2)
                ;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PORCUPINE.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.BEETROOT);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CAT_DEATH;
    }
}
