package net.yunitrish.adaptor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.yunitrish.adaptor.common.ModConfigFile;
import net.yunitrish.adaptor.discord.MessageReceiveListener;
import net.yunitrish.adaptor.discord.SlashCommandListener;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class AdaptorServer implements DedicatedServerModInitializer {

    public static ModConfigFile data;
    public static MinecraftServer modServer;
    public static JDA jda;

    public static void sendDiscordMessage(String message) {
        List<TextChannel> channels = jda.getTextChannelsByName("一般", false);
        for (TextChannel ch : channels) {
            ch.sendMessage(message).queue();
        }
    }

    @Override
    public void onInitializeServer() {

        try {
            data = new ModConfigFile("adaptor.json");
            jda = JDABuilder.createLight(data.config.token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                    .addEventListeners(
                            new MessageReceiveListener(),
                            new SlashCommandListener()
                    )
                    .build();

            CommandListUpdateAction commands = jda.updateCommands();

            commands.addCommands(
                    Commands.slash("say", "Makes the bot say what you tell it to")
                            .addOption(STRING, "content", "What the bot should say", true),
                    Commands.slash("leave", "Makes the bot leave the server")
                            .setGuildOnly(true)
                            .setDefaultPermissions(DefaultMemberPermissions.DISABLED),
                    Commands.slash("command", "Make bot execute command")
                            .setGuildOnly(true)
                            .addOption(STRING, "content", "command to execute", true)
                            .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
            );
            commands.queue();
        } catch (IOException ignored) {
        }

        ServerMessageEvents.CHAT_MESSAGE.register((message, source, params) -> sendDiscordMessage("[" + source.getName().getLiteralString() + "] : " + message.getContent().getLiteralString()));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> sendDiscordMessage(player.getName().getLiteralString() + "傳送到不知道甚麼地方"));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> sendDiscordMessage("[" + handler.player.getName().getLiteralString() + "]" + "進入伺服器"));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> sendDiscordMessage("[" + handler.player.getName().getLiteralString() + "]" + "離開伺服器"));
        ServerLifecycleEvents.SERVER_STARTING.register(server -> sendDiscordMessage("伺服器啟動中..."));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> sendDiscordMessage("伺服器啟動完成"));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> sendDiscordMessage("伺服器關閉中..."));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> sendDiscordMessage("伺服器已離線"));
    }
}
