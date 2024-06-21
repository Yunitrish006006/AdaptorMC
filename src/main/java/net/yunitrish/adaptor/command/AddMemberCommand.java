package net.yunitrish.adaptor.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.yunitrish.adaptor.ChestLockSystem.utils.GenericLockableContainerBlockEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class AddMemberCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("addmember")
                .then(argument("target", EntityArgumentType.player())
                        .then(argument("pos", BlockPosArgumentType.blockPos())
                                .executes(context -> execute(context, EntityArgumentType.getPlayer(context, "target"), BlockPosArgumentType.getBlockPos(context, "pos")))
                        )
                )
                .executes(context -> execute(context, context.getSource().getPlayer(), BlockPosArgumentType.getBlockPos(context, "pos")))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, BlockPos pos) {
        if (player != null) {
            World world = context.getSource().getWorld();

            if (world.getBlockEntity(pos) instanceof GenericLockableContainerBlockEntity blockEntity) {
                blockEntity.addToLock(context.getSource().getPlayer(), player);
                context.getSource().sendFeedback(() -> Text.translatable("container.addGroupmembers", player.getName(), pos.toShortString()), true);
            } else {
                context.getSource().sendError(Text.literal("No container found at " + pos.toShortString()));
            }
        }
        return 1;
    }
}
