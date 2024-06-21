package net.yunitrish.adaptor.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FlyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("fly")
                .then(argument("target", EntityArgumentType.player())
                        .executes(context -> execute(context, EntityArgumentType.getPlayer(context, "target")))
                )
                .executes(context -> execute(context, context.getSource().getPlayer()))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) {
        if (player != null) {
            player.getAbilities().allowFlying = !player.getAbilities().allowFlying;
            if (player.getAbilities().allowFlying) {
                player.getAbilities().flying = true;
                player.sendAbilitiesUpdate();
                context.getSource().sendFeedback(() -> Text.literal("Flying enabled for " + player.getName().getString() + "."), false);
            } else {
                player.getAbilities().flying = false;
                player.sendAbilitiesUpdate();
                context.getSource().sendFeedback(() -> Text.literal("Flying disabled for " + player.getName().getString() + "."), false);
            }
        }
        return 1;
    }

}
