package net.yunitrish.adaptor.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DropCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("drop")
                .then(argument("target", EntityArgumentType.player())
                        .executes(context -> execute(context, EntityArgumentType.getPlayer(context, "target")))
                )
                .executes(context -> execute(context, context.getSource().getPlayer()))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) {
        EquipmentSlot[] equipmentSlots = EquipmentSlot.values();
        for (EquipmentSlot equipmentSlot : equipmentSlots) {
            assert player != null;
            ItemStack itemStack = player.getEquippedStack(equipmentSlot);
            player.getServerWorld().spawnEntity(new ItemEntity(player.getServerWorld(), player.getX() + 2, player.getY(), player.getZ() + 2, itemStack));
            player.sendMessage(Text.literal("slot:" + equipmentSlot.getEntitySlotId() + " item: " + itemStack.getTranslationKey()));
            player.getInventory().removeStack(equipmentSlot.getEntitySlotId());
        }
        return 1;
    }
}
