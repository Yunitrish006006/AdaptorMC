package net.yunitrish.adaptor.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.yunitrish.adaptor.AdaptorServer;
import net.yunitrish.adaptor.common.ModConfig;

public class MessageReceiveListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Text message;
        if (AdaptorServer.data.isBindPlayer(event.getAuthor().getId())) {
            ServerPlayerEntity player = ModConfig.getPlayerFromDiscordId(event.getAuthor().getId());
            if (player != null) {
                message = Text.literal("<" + player.getName().getLiteralString() + "> " + event.getMessage().getContentDisplay());
            } else {
                message = Text.of(
                        "[" + event.getGuild().getName() + "#" + event.getChannel().getName() + "] " +
                                event.getAuthor().getName() + ": "
                                + event.getMessage().getContentDisplay());
            }
        } else {
            message = Text.of(
                    "[" + event.getGuild().getName() + "#" + event.getChannel().getName() + "] " +
                            event.getAuthor().getName() + ": "
                            + event.getMessage().getContentDisplay());
        }
        AdaptorServer.modServer.getPlayerManager().broadcast(message, false);
    }
}
