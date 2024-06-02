package net.yunitrish.adaptor.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.yunitrish.adaptor.AdaptorServer;
import net.yunitrish.adaptor.common.ModConfig;

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
            case "bind" -> {
                String minecraft_id = event.getOption("minecraft_id", OptionMapping::getAsString);
                String discord_id = event.getUser().getId();
                if (ModConfig.getPlayerFromDiscordId(discord_id) != null) {
                    event.reply("已有綁定帳號 minecraftID: " + ModConfig.getMinecraftId(discord_id))
                            .setEphemeral(true)
                            .queue();
                    return;
                }
                if (ModConfig.getPlayerFromMinecraftId(minecraft_id) != null) {
                    AdaptorServer.data.addBindData(event.getUser().getId(), minecraft_id);

                    event.reply("成功綁定帳號至 minecraftID: " + minecraft_id)
                            .setEphemeral(true)
                            .queue();
                    return;
                }
                event.reply("查無此ID: " + minecraft_id)
                        .setEphemeral(true)
                        .queue();
            }
            default -> {
            }
        }
    }
}
