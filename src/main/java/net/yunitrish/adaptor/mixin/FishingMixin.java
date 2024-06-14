package net.yunitrish.adaptor.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingMixin extends ProjectileEntity {
    @Shadow
    private Entity hookedEntity;
    @Shadow
    private int hookCountdown;
    @Final
    @Shadow
    private int luckOfTheSeaLevel;
    @Shadow @Final private int lureLevel;
    public FishingMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    @Nullable
    public PlayerEntity getPlayerOwner() {
        return this.getOwner() instanceof PlayerEntity ? (PlayerEntity)this.getOwner() : null;
    }

    @Shadow
    private boolean removeIfInvalid(PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        boolean bl = itemStack.isOf(Items.FISHING_ROD);
        boolean bl2 = itemStack2.isOf(Items.FISHING_ROD);
        if (player.isRemoved() || !player.isAlive() || !bl && !bl2 || this.squaredDistanceTo(player) > 1024.0) {
            this.discard();
            return true;
        }
        return false;
    }


    @Shadow
    protected void pullHookedEntity(Entity entity) {
        Entity entity2 = this.getOwner();
        if (entity2 == null) {
            return;
        }
        Vec3d vec3d = new Vec3d(entity2.getX() - this.getX(), entity2.getY() - this.getY(), entity2.getZ() - this.getZ()).multiply(0.1);
        entity.setVelocity(entity.getVelocity().add(vec3d));
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        PlayerEntity playerEntity = getPlayerOwner();
        if (playerEntity == null) return;
        if (this.getWorld().isClient || this.removeIfInvalid(playerEntity)) {
            cir.setReturnValue(0);
            cir.cancel();
        }
        int i = 0;
        if (this.hookedEntity != null) {
            this.pullHookedEntity(this.hookedEntity);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PULL_HOOKED_ENTITY);
            i = this.hookedEntity instanceof ItemEntity ? 3 : 5;
        } else if (this.hookCountdown > 0) {
            LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder((ServerWorld)this.getWorld()).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.TOOL, usedItem).add(LootContextParameters.THIS_ENTITY, this).luck((float)this.luckOfTheSeaLevel + playerEntity.getLuck()).build(LootContextTypes.FISHING);
            if (this.getWorld().getServer() == null ) return;
            LootTable lootTable = this.getWorld().getServer().getReloadableRegistries().getLootTable(LootTables.FISHING_GAMEPLAY);
            ObjectArrayList<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);
            for (ItemStack itemStack : list) {

                int rn = Math.abs(random.nextBetween(0, (lureLevel * luckOfTheSeaLevel + 1) / 3) + 1);
                if (itemStack.isIn(ItemTags.FISHES)) {
                    playerEntity.getWorld().spawnEntity(new ExperienceOrbEntity(playerEntity.getWorld(), playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5, this.random.nextInt(6) + 1));
                    for (int t=0;t<rn;t++) this.getWorld().spawnEntity(getFishEntity(playerEntity,itemStack));
                    playerEntity.increaseStat(Stats.FISH_CAUGHT, 1);
                }
                else {
                    ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), itemStack);
                    double d = playerEntity.getX() - this.getX();
                    double e = playerEntity.getY() - this.getY();
                    double f = playerEntity.getZ() - this.getZ();
                    itemEntity.setVelocity(d * 0.1, e * 0.1 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08 + 0.05, f * 0.1);
                    this.getWorld().spawnEntity(itemEntity);
                    playerEntity.getWorld().spawnEntity(new ExperienceOrbEntity(playerEntity.getWorld(), playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5, this.random.nextInt(6) + 1));
                }
            }
            i = 1;
        }
        if (this.isOnGround()) {
            i = 2;
        }
        this.discard();
        cir.setReturnValue(i);
        cir.cancel();
    }

    @Unique
    private FishEntity getFishType(Item item, World world) {
        if (item.equals(Items.COD)) {
            return new CodEntity(EntityType.COD, world);
        }
        else if (item.equals(Items.SALMON)) {
            return new SalmonEntity(EntityType.SALMON, world);
        }
        else if (item.equals(Items.PUFFERFISH)) {
            return new PufferfishEntity(EntityType.PUFFERFISH, world);
        }
        else if (item.equals(Items.TROPICAL_FISH)) {
            return new TropicalFishEntity(EntityType.TROPICAL_FISH, world);
        }
        return new CodEntity(EntityType.COD, world);
    }

    @Unique
    @NotNull
    private FishEntity getFishEntity(PlayerEntity playerEntity, ItemStack itemStack) {
        FishEntity fish = getFishType(itemStack.getItem(),this.getWorld());
        double d = playerEntity.getX() - this.getX();
        double e = playerEntity.getY() - this.getY();
        double f = playerEntity.getZ() - this.getZ();
        fish.setHeadYaw(playerEntity.headYaw);
        fish.setVelocity(d * 0.12, e * 0.12 + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.12, f * 0.12);
        fish.setPosition(this.getPos());
        fish.setHealth(0.2f);
        return fish;
    }
}
