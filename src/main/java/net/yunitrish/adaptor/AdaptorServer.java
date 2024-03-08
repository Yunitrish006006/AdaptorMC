package net.yunitrish.adaptor;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.vdurmont.emoji.EmojiParser;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.yunitrish.adaptor.bot.DiscordBot;
import net.yunitrish.adaptor.bot.DiscordCommandManager;
import net.yunitrish.adaptor.bot.ModConfig;
import net.yunitrish.adaptor.bot.ModConfigFile;
import net.yunitrish.adaptor.bot.callbacks.ChatMessageCallback;
import net.yunitrish.adaptor.bot.callbacks.DiscordChatCallback;
import net.yunitrish.adaptor.bot.callbacks.ServerMessageCallback;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import static net.yunitrish.adaptor.AdaptorMain.MOD_ID;

public class AdaptorServer implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ModConfig config = ModConfigFile.DEFAULT_CONFIG;
    public static ModConfigFile configFile = new ModConfigFile("discord.json");
    public static DiscordBot bot = new DiscordBot();
    public static Optional<MinecraftServer> server = Optional.empty();
    public static DiscordCommandManager commands = new DiscordCommandManager();
    public static int playerCount = -1;

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(AdaptorServer::onServerStart);
        ServerLifecycleEvents.SERVER_STOPPED.register(AdaptorServer::onServerStop);
        ServerTickEvents.END_WORLD_TICK.register(AdaptorServer::onServerTick);
        CommandRegistrationCallback.EVENT.register(AdaptorServer::onRegisterCommands);
        ChatMessageCallback.EVENT.register(AdaptorServer::onGameChat);
        ServerMessageCallback.EVENT.register(AdaptorServer::onServerMessage);
        DiscordChatCallback.EVENT.register(AdaptorServer::onDiscordChat);
    }

    public static boolean tryReconnect() {
        bot.disconnect();
        try {
            bot.connect(config.token);
        } catch (IllegalStateException e) {
            LOGGER.warn("An illegal state exception was thrown while trying to connect to Discord. You likely forgot to enable either the \"guild members\" or \"message content\" intents.");
            LOGGER.warn("For more information, please see https://github.com/chunkaligned/fabric-discord-integration#setting-up-a-discord-bot");
            return false;
        } catch (Exception e) {
            LOGGER.warn("An error occurred while trying to connect to Discord: " + e.getMessage());
            return false;
        }
        return true;
    }
    public static boolean readConfig() {
        try {
            config = configFile.read();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static void writeConfig() {
        try {
            configFile.write(config);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }
    public static void withServer(Consumer<MinecraftServer> action) {
        if (server.isPresent()) {
            action.accept(server.get());
        }
    }
    public static void relayToDiscord(String message) {
        bot.withConnection(c -> {
            String outgoing = message;
            for (Long channelID : config.relayChannelIDs) {
                final TextChannel channel = c.getTextChannelById(channelID);
                if (channel == null || !channel.canTalk()) {
                    LOGGER.warn("Relay channel " + channelID + " is invalid");
                    continue;
                }
                if (config.disableMentions) {
                    outgoing = outgoing.replaceAll("@", "@ ");
                }
                for (Emoji emoji : channel.getGuild().getEmojis()) {
                    final String emojiDisplay = String.format(":%s:", emoji.getName());
                    final String emojiFormatted = String.format("<:%s>", emoji.getAsReactionCode());
                    outgoing = outgoing.replaceAll(emojiDisplay, emojiFormatted);
                }

                channel.sendMessage(outgoing).queue();
            }
        });
    }
    private static void onServerStart(MinecraftServer server) {
        if (configFile.exists()) {
            if (readConfig()) {
                if (tryReconnect()) {
                    bot.setStatus("Starting...");
                }
            } else {
                LOGGER.warn("Config file is malformed. Aborting");
            }
        } else {
            LOGGER.warn("Config file doesn't exist, writing default");
            writeConfig();
        }
        AdaptorServer.server = Optional.of(server);
    }
    private static void onServerStop(MinecraftServer server) {
        AdaptorServer.server = Optional.empty();
        bot.disconnect();
    }
    private static void onServerTick(ServerWorld world) {
        if (server.isPresent()) {
            if (server.get().getTicks() % 100 == 0) {
                if (playerCount != server.get().getCurrentPlayerCount()) {
                    playerCount = server.get().getCurrentPlayerCount();
                    bot.setStatus(String.format("%d/%d players",
                            server.get().getCurrentPlayerCount(),
                            server.get().getMaxPlayerCount()));
                }
            }
        }
    }
    private static void onServerMessage(MinecraftServer server, Text text) {
        String formatted = config.systemMessageFormat.replaceAll("\\$MESSAGE", text.getString());
        relayToDiscord(formatted);
    }
    private static void onGameChat(MinecraftServer server, Text text, ServerPlayerEntity sender) {
        String formatted = config.chatMessageFormat.replaceAll("\\$NAME", sender.getName().getString());
        formatted = formatted.replaceAll("\\$MESSAGE", text.getString());
        relayToDiscord(formatted);
    }
    private static void onDiscordChat(Message message) {
        if (server.isEmpty()) {
            return;
        }

        if (!config.relayChannelIDs.contains(message.getChannel().getIdLong())) {
            return;
        }

        User author = message.getAuthor();

        if (author.isBot()) {
            return;
        }
        if (message.getType() != MessageType.DEFAULT) {
            return;
        }
        if (message.getContentDisplay().startsWith(config.commandPrefix)) {
            final String messageNoPrefix = message.getContentDisplay().substring(config.commandPrefix.length());

            try {
                commands.getDispatcher().execute(messageNoPrefix, message);
            } catch (CommandSyntaxException e) {
                LOGGER.warn(e.getMessage());
            }

            return;
        }
        String formatted = config.discordMessageFormat;
        formatted = formatted.replaceAll("\\$NAME",     String.format("%s#%s", author.getName(), author.getDiscriminator()));
        formatted = formatted.replaceAll("\\$USERNAME", author.getName());
        formatted = formatted.replaceAll("\\$DISCRIM",  author.getDiscriminator());
        formatted = formatted.replaceAll("\\$NICKNAME", message.getGuild().getMember(author).getEffectiveName());
        String content = EmojiParser.parseToAliases(message.getContentDisplay());
        if (!content.isEmpty()) {
            content += " ";
        }
        formatted = formatted.replaceAll("\\$MESSAGE", content);
        MutableText text = Text.literal(formatted);
        for (Message.Attachment attachment : message.getAttachments()) {
            final MutableText attachmentText = Text.literal(attachment.getFileName());
            attachmentText.setStyle(
                    attachmentText.getStyle()
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, attachment.getUrl()))
                            .withFormatting(Formatting.GREEN)
                            .withFormatting(Formatting.UNDERLINE)
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of("Click to open in your web browser")))
            );
            text.append(attachmentText).append(" ");
        }
        for (ServerPlayerEntity player : server.get().getPlayerManager().getPlayerList()) {
            player.sendMessage(text);
        }
        LogManager.getLogger("Minecraft").info(text.getString());
    }
    private static void onRegisterCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess reg, CommandManager.RegistrationEnvironment env) {
        dispatcher.register(CommandManager.literal("discord")
                .requires(source -> source.hasPermissionLevel(4))
                .then(CommandManager.literal("loadConfig").executes(AdaptorServer::commandLoadConfig))
                .then(CommandManager.literal("status").executes(AdaptorServer::commandStatus))
                .then(CommandManager.literal("reconnect").executes(AdaptorServer::commandReconnect))
        );
    }
    private static int commandLoadConfig(CommandContext<ServerCommandSource> context) {
        String response = "Discord: Loaded config";
        if (configFile.exists()) {
            if (!readConfig()) {
                response = "Discord: Config file is malformed";
            }
        } else {
            response = "Config file doesn't exist, writing default";
            writeConfig();
        }

        context.getSource().sendMessage(Text.of(response));
        return 0;
    }
    private static int commandStatus(CommandContext<ServerCommandSource> context) {
        final ServerCommandSource source = context.getSource();
        if (!bot.isConnected()) {
            source.sendMessage(Text.of("Discord: Not connected"));
        } else {
            bot.withConnection(c -> {
                source.sendMessage(Text.of("Discord: Connected"));
                source.sendMessage(Text.of("Status: " + c.getStatus()));
            });
        }

        return 0;
    }
    private static int commandReconnect(CommandContext<ServerCommandSource> context) {
        final ServerCommandSource source = context.getSource();
        bot.disconnect();
        source.sendMessage(Text.of("Discord: Disconnected"));
        if (tryReconnect()) {
            source.sendMessage(Text.of("Discord: Connected"));
        } else {
            source.sendMessage(Text.of("Discord: Failed to connect"));
        }

        return 0;
    }
}
