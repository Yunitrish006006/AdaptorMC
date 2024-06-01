package net.yunitrish.adaptor.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.yunitrish.adaptor.Adaptor;

import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static void register() {
        Adaptor.LOGGER.info("registering commands...");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("fly")
                .executes(context -> {
                    ServerPlayerEntity player =  context.getSource().getPlayer();

                    assert player != null;
                    player.getAbilities().allowFlying = !player.getAbilities().allowFlying;
                    if (player.getAbilities().allowFlying) {
                        player.getAbilities().flying = true;
                        player.sendAbilitiesUpdate();
                        context.getSource().sendFeedback(() -> Text.literal("Flying enabled."), false);
                    } else {
                        player.getAbilities().flying = false;
                        player.sendAbilitiesUpdate();
                        context.getSource().sendFeedback(() -> Text.literal("Flying disabled."), false);
                    }
                    return 1;
                })));
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(literal("drop")
                .executes(
                        context -> {
                            EquipmentSlot[] var4 = EquipmentSlot.values();

                            for (EquipmentSlot equipmentSlot : var4) {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                assert player != null;
                                ItemStack itemStack = player.getEquippedStack(equipmentSlot);
                                player.getServerWorld().spawnEntity(new ItemEntity(player.getServerWorld(), player.getX() + 2, player.getY(), player.getZ() + 2, itemStack));
                                player.sendMessage(Text.literal("slot:" + equipmentSlot.getEntitySlotId() + " item: " + itemStack.getTranslationKey()));
                                player.getInventory().removeStack(equipmentSlot.getEntitySlotId());
                            }
                            return 1;
                        }
                ))));
    }
}
