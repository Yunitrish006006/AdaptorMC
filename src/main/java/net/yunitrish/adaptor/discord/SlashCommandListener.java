package net.yunitrish.adaptor.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.yunitrish.adaptor.AdaptorServer;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "say" -> {
                String content = event.getOption("content", OptionMapping::getAsString);
                event.reply(content).queue();
            }
            case "leave" -> {
                if (event.getGuild() == null) return;
                event.reply("I'm leaving the server now!")
                        .setEphemeral(true)
                        .flatMap(m -> event.getGuild().leave())
                        .queue();
            }
            case "command" -> {
                String content = event.getOption("content", OptionMapping::getAsString);
                if (AdaptorServer.modServer != null) {
                    AdaptorServer.modServer.getCommandManager().executeWithPrefix(AdaptorServer.modServer.getCommandSource(), content);
                }
                event.reply("[" + content + "] √")
                        .setEphemeral(true)
                        .queue();
            }
            default -> {
            }
        }
    }
}
