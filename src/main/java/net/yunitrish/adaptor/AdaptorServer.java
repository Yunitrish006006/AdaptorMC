package net.yunitrish.adaptor;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.yunitrish.adaptor.common.ModConfigFile;
import net.yunitrish.adaptor.discord.MessageReceiveListener;
import net.yunitrish.adaptor.discord.SlashCommandListener;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class AdaptorServer implements DedicatedServerModInitializer {

    public static ModConfigFile data;
    public static MinecraftServer modServer;
    public static JDA jda;

    public static void sendEmbedMessage(String message, String minecraftId) {
        if (data.config.getFirstMatchDiscordId(minecraftId) == null) {

            for (TextChannel channel : jda.getTextChannelsByName("一般", false)) {
                channel.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setAuthor(minecraftId + " " + message, null, null)
                                .clearFields()
                                .build()
                ).queue();
            }
        } else {
            jda.retrieveUserById(data.config.getFirstMatchDiscordId(minecraftId)).queue(
                    user -> {
                        MessageEmbed content;
                        if (user == null) {
                            content = new EmbedBuilder()
                                    .setAuthor(minecraftId + " : " + message, null, null)
                                    .clearFields()
                                    .build();
                        } else {
                            content = new EmbedBuilder()
                                    .setAuthor(user.getName() + " : " + message, null, user.getAvatarUrl())
                                    .clearFields()
                                    .build();
                        }

                        for (TextChannel channel : jda.getTextChannelsByName("一般", false)) {
                            channel.sendMessageEmbeds(content).queue();
                        }
                    }
            );
        }
    }

    @Override
    public void onInitializeServer() {
        //*
        try {
            data = new ModConfigFile("adaptor.json");
            if (Objects.equals(data.config.token, "YOUR-TOKEN-HERE")) return;
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
            jda = null;
        }

        ServerTickEvents.END_WORLD_TICK.register((world) -> modServer = world.getServer());
        ServerMessageEvents.CHAT_MESSAGE.register((message, source, params) -> sendEmbedMessage(message.getContent().getLiteralString(), source.getName().getLiteralString()));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> sendEmbedMessage("傳送到不知道哪邊", player.getName().getLiteralString()));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> sendEmbedMessage("進入伺服器", handler.player.getName().getLiteralString()));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> sendEmbedMessage("離開伺服器", handler.player.getName().getLiteralString()));
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity.getCustomName() != null) {
                sendEmbedMessage(damageSource.getDeathMessage(entity).getString().replaceFirst(entity.getCustomName().getString(), ""), entity.getCustomName().getString());
            } else if (entity instanceof ServerPlayerEntity player) {
                sendEmbedMessage(damageSource.getDeathMessage(player).getString().replaceFirst(player.getName().getString(), "").trim(), player.getName().getString());
            }
        });
        ServerLifecycleEvents.SERVER_STARTING.register(server -> updateStatus(OnlineStatus.IDLE, "伺服器正在啟動..."));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> updateStatus(OnlineStatus.ONLINE, "伺服器運行中 ✓"));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> updateStatus(OnlineStatus.DO_NOT_DISTURB, "伺服器關閉中..."));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            updateStatus(OnlineStatus.DO_NOT_DISTURB, "伺服器已離線");
            jda.shutdown();
        });
        //*/
    }

    public void updateStatus(OnlineStatus status, String parameter) {
        jda.getPresence().setStatus(status);
        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, parameter, "https://github.com/Yunitrish006006/FabricServerMods"));
    }
}
