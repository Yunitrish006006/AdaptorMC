package net.yunitrish.adaptor;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
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

            commands = commands.addCommands(
                    Commands.slash("say", "Makes the bot say what you tell it to")
                            .addOption(STRING, "content", "What the bot should say", true),
                    Commands.slash("leave", "Makes the bot leave the server")
                            .setGuildOnly(true)
                            .setDefaultPermissions(DefaultMemberPermissions.DISABLED),
                    Commands.slash("command", "Make bot execute command")
                            .setGuildOnly(true)
                            .addOption(STRING, "content", "command to execute", true)
                            .setDefaultPermissions(DefaultMemberPermissions.DISABLED),
                    Commands.slash("bind", "Make bot execute command")
                            .addOption(STRING, "minecraft_id", "你的minecraftId", true)
            );
            commands.queue();
        } catch (IOException ignored) {
            return;
        }

        ServerTickEvents.END_WORLD_TICK.register((world) -> modServer = world.getServer());
        ServerMessageEvents.CHAT_MESSAGE.register((message, source, params) -> {
            String content = message.getContent().getLiteralString();
            String minecraftId = source.getName().getLiteralString();
            if (data.config.isMinecraftIdInDataBind(minecraftId)) {
                Adaptor.LOGGER.info("1");
                List<TextChannel> channels = jda.getTextChannelsByName("一般", false);
                for (TextChannel ch : channels) {
                    jda.retrieveUserById(data.config.getFirstMatchDiscordId(minecraftId)).queue(
                            user -> {
                                if (user == null) {
                                    sendDiscordMessage("[" + minecraftId + "] : " + content);
                                } else {
                                    ch.sendMessageEmbeds(
                                            new EmbedBuilder()
                                                    .setAuthor(user.getName() + " : " + content, null, user.getAvatarUrl())
                                                    .clearFields()
                                                    .build()
                                    ).queue();
                                }
                            }
                    );
                }
            } else {
                Adaptor.LOGGER.info("1");

            }
        });
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> sendDiscordMessage("[" + player.getName().getLiteralString() + "] " + "傳送到不知道甚麼地方"));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> sendDiscordMessage("[" + handler.player.getName().getLiteralString() + "]" + "進入伺服器"));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> sendDiscordMessage("[" + handler.player.getName().getLiteralString() + "]" + "離開伺服器"));
        ServerLifecycleEvents.SERVER_STARTING.register(server -> updateStatus(OnlineStatus.IDLE, "伺服器正在啟動..."));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> updateStatus(OnlineStatus.ONLINE, "伺服器運行中 ✓"));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> updateStatus(OnlineStatus.DO_NOT_DISTURB, "伺服器關閉中..."));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            updateStatus(OnlineStatus.DO_NOT_DISTURB, "伺服器已離線");
            jda.shutdown();
        });
    }

    public void updateStatus(OnlineStatus status, String parameter) {
        jda.getPresence().setStatus(status);
        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, parameter, "https://github.com/Yunitrish006006/FabricServerMods"));
    }
}
