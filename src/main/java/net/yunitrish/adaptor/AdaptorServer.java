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
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.yunitrish.adaptor.common.ModConfig;
import net.yunitrish.adaptor.common.ModConfigFile;
import net.yunitrish.adaptor.discord.MessageReceiveListener;
import net.yunitrish.adaptor.discord.SlashCommandListener;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class AdaptorServer implements DedicatedServerModInitializer {

    public static ModConfigFile data;
    public static MinecraftServer modServer;
    public static JDA jda;

    public static void sendMessage(String message, String url) {
        for (TextChannel channel : jda.getTextChannelsByName("一般", false)) {
            channel.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setAuthor(message, null, url)
                            .clearFields()
                            .build()
            ).queue();
        }
    }

    public static void sendMessage(String message) {
        sendMessage(message, null);
    }

    public static void sendEmbedMessage(String message, String minecraftId) {
        String id = data.config.getFirstMatchDiscordId(minecraftId);
        if (id == null) {
            sendMessage(minecraftId + " " + message);
        } else {
            jda.retrieveUserById(id).queue(
                    user -> {
                        if (user == null) {
                            sendMessage(minecraftId + " : " + message);
                        } else {
                            sendMessage(ModConfig.getCustomName(id) + " : " + message, user.getAvatarUrl());
                        }
                    }
            );
        }
    }

    public void updateStatus(OnlineStatus status, String parameter) {
        jda.getPresence().setStatus(status);
        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, parameter, "https://github.com/Yunitrish006006/FabricServerMods"));
    }

    @Override
    public void onInitializeServer() {
        try {
            run();
        } catch (Exception ignore) {
        }
    }

    public void run() {
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
                            .addOption(STRING, "minecraft_id", "你的minecraftId", true),
                    Commands.slash("name", "change your minecraft costume name")
                            .addOption(STRING, "name", "costume name", true),
                    Commands.slash("config_save", "save all the data of Adaptor mod config file")
                            .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
            );
            commands.queue();
        } catch (IOException ignored) {
            jda = null;
        }
        if (jda != null) {
            ServerTickEvents.END_WORLD_TICK.register((world) -> modServer = world.getServer());
            ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
                String customName = ModConfig.getCustomName(data.config.getFirstMatchDiscordId(sender.getName().getLiteralString()));
                for (ServerPlayerEntity player : AdaptorServer.modServer.getPlayerManager().getPlayerList()) {
                    player.sendMessage(Text.of("<" + customName + "> " + message.getSignedContent()));
                }
                sendEmbedMessage(message.getContent().getLiteralString(), sender.getName().getLiteralString());
                return false;
            });
            ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
                sendEmbedMessage("進入伺服器", handler.player.getName().getLiteralString());

            });
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
            ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {

                Map<String, String> worldDictionary = Map.ofEntries(
                        Map.entry("minecraft:overworld", "表世界"),
                        Map.entry("minecraft:the_nether", "地獄"),
                        Map.entry("minecraft:the_end", "終界"),
                        Map.entry("adaptor:pre_era_dimension_type", "始世界")
                );

                String worldName = worldDictionary.get(player.getWorld().getDimensionEntry().getIdAsString());

                sendEmbedMessage("傳送至 " + worldName, player.getName().getLiteralString());
            });
        }
    }
}
