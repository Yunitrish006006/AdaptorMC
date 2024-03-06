package net.yunitrish.adaptor.bot;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.yunitrish.adaptor.Adaptor;

import java.awt.*;

public class PlayersCommand {

    private static final int MAX_LIST = 20;

    public static void register(CommandDispatcher<Message> dispatcher) {
        dispatcher.register(DiscordCommandManager.literal("players").executes(PlayersCommand::playersCommand));
    }

    public static int playersCommand(CommandContext<Message> context) {
        Adaptor.withServer(s -> {
            final EmbedBuilder e = new EmbedBuilder();
            e.setColor(Color.GREEN);
            e.setTitle(String.format("%d/%d players online", s.getCurrentPlayerCount(), s.getMaxPlayerCount()));

            final PlayerManager players = s.getPlayerManager();

            int playersInList = 0;
            for (ServerPlayerEntity player : players.getPlayerList()) {
                e.appendDescription(player.getName().getString() + "\n");
                if (++playersInList == MAX_LIST) {
                    break;
                }
            }

            if (playersInList != s.getCurrentPlayerCount()) {
                e.appendDescription(String.format("...and %d more", s.getCurrentPlayerCount() - playersInList));
            }

            context.getSource().getChannel().sendMessageEmbeds(e.build()).queue();
        });
        return 0;
    }
}
