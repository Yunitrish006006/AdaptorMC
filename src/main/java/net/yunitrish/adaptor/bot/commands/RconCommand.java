package net.yunitrish.adaptor.bot.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.yunitrish.adaptor.AdaptorMain;
import net.yunitrish.adaptor.AdaptorServer;
import net.yunitrish.adaptor.bot.DiscordCommandManager;

public class RconCommand {

    public static void register(CommandDispatcher<Message> dispatcher) {
        dispatcher.register(DiscordCommandManager.literal("rcon")
                .then(DiscordCommandManager.argument("command", StringArgumentType.greedyString())
                        .executes(RconCommand::rconCommand)));
    }

    public static int rconCommand(CommandContext<Message> context) {
        AdaptorServer.withServer(s -> {
            final String command = StringArgumentType.getString(context, "command");
            // Check if the user has rcon permissions
            if (!AdaptorServer.config.rconUserIDs.contains(context.getSource().getAuthor().getIdLong())) {
                // No permission
                // :no_entry_sign:
                context.getSource().addReaction(Emoji.fromUnicode("U+1F6AB")).queue();
            } else {
                // Send as the server
                s.getCommandManager().executeWithPrefix(s.getCommandSource(), command);
                // :white_check_mark:
                context.getSource().addReaction(Emoji.fromUnicode("U+2705")).queue();
            }
        });
        return 0;
    }

}
