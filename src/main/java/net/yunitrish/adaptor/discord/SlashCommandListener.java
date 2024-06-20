package net.yunitrish.adaptor.discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.yunitrish.adaptor.AdaptorServer;
import net.yunitrish.adaptor.common.ModConfig;
import net.yunitrish.adaptor.common.UserData;

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
            case "config_save" -> {
                AdaptorServer.data.write();
                event.reply("server config saved √")
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
                if (ModConfig.getPlayer(minecraft_id) != null) {
                    if (event.getGuild() != null) {
                        event.getGuild().retrieveMember(event.getUser()).queue((member) -> {
                            AdaptorServer.data.addBindData(event.getUser().getId(), minecraft_id, member.getNickname());
                            event.reply("成功綁定帳號至 minecraftID: " + minecraft_id)
                                    .setEphemeral(true)
                                    .queue();
                        });
                    }
                } else {
                    event.reply("查無此ID: " + minecraft_id)
                            .setEphemeral(true)
                            .queue();
                }
            }
            case "name" -> {
                String name = event.getOption("name", OptionMapping::getAsString);
                String discord_id = event.getUser().getId();
                if (ModConfig.getPlayerFromDiscordId(discord_id) != null) {
                    UserData userData = ModConfig.getUserDataFromDiscordId(discord_id);
                    userData.customName = name;
                    AdaptorServer.data.addBindData(discord_id, userData);
                    event.reply("update ur costume name to " + name).setEphemeral(true).queue();
                }
            }
            default -> {
            }
        }
    }
}
