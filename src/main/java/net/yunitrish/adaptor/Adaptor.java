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
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.bot.*;
import net.yunitrish.adaptor.creature.villager.ModCustomTrades;
import net.yunitrish.adaptor.creature.villager.ModVillagers;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.event.EntityDamagedHandler;
import net.yunitrish.adaptor.item.ModItemGroups;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.util.ModEvents;
import net.yunitrish.adaptor.util.ModLootTableModifiers;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class Adaptor implements ModInitializer {
	public static final String MOD_ID = "adaptor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ModConfig config = ModConfigFile.DEFAULT_CONFIG;
	public static ModConfigFile configFile = new ModConfigFile("discord.json");
	public static DiscordBot bot = new DiscordBot();
	public static Optional<MinecraftServer> server = Optional.empty();
	public static DiscordCommandManager commands = new DiscordCommandManager();
	public static int playerCount = -1;

	@Override
	public void onInitialize() {

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
		ServerLifecycleEvents.SERVER_STARTED.register(Adaptor::onServerStart);
		ServerLifecycleEvents.SERVER_STOPPED.register(Adaptor::onServerStop);
		ServerTickEvents.END_WORLD_TICK.register(Adaptor::onServerTick);
		CommandRegistrationCallback.EVENT.register(Adaptor::onRegisterCommands);
		ChatMessageCallback.EVENT.register(Adaptor::onGameChat);
		ServerMessageCallback.EVENT.register(Adaptor::onServerMessage);
		DiscordChatCallback.EVENT.register(Adaptor::onDiscordChat);

		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModCustomTrades.registerCustomTrades();
		ModEvents.registerEvents();
		ModVillagers.registerVillagers();
		ModEnchantments.registerModEnchantments();
		ModLootTableModifiers.modifyLootTables();
		FuelRegistry.INSTANCE.add(ModItems.BAMBOO_COAL,120);

		AttackEntityCallback.EVENT.register(new EntityDamagedHandler());
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
			e.printStackTrace();
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
	//
	// Sync the config file to disk
	//
	public static boolean writeConfig() {
		try {
			configFile.write(config);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	//
	// Do something with the server
	//
	public static void withServer(Consumer<MinecraftServer> action) {
		if (server.isPresent()) {
			action.accept(server.get());
		}
	}

	//
	// Relay a message to Discord
	//
	public static void relayToDiscord(String message) {
		bot.withConnection(c -> {
			String outgoing = message;
			for (Long channelID : config.relayChannelIDs) {
				final TextChannel channel = c.getTextChannelById(channelID);
				if (channel == null || !channel.canTalk()) {
					LOGGER.warn("Relay channel " + channelID + " is invalid");
					continue;
				}

				// Block mentions
				if (config.disableMentions) {
					outgoing = outgoing.replaceAll("@", "@ ");
				}

				// Format guild emojis
				for (Emoji emoji : channel.getGuild().getEmojis()) {
					final String emojiDisplay = String.format(":%s:", emoji.getName());
					final String emojiFormatted = String.format("<:%s>", emoji.getAsReactionCode());
					outgoing = outgoing.replaceAll(emojiDisplay, emojiFormatted);
				}

				channel.sendMessage(outgoing).queue();
			}
		});
	}

	//
	// Called on server start
	//
	private static void onServerStart(MinecraftServer server) {
		Adaptor.server = Optional.of(server);
	}

	//
	// Called on server stop
	//
	private static void onServerStop(MinecraftServer server) {
		Adaptor.server = Optional.empty();
		bot.disconnect();
	}

	//
	// Called on server tick
	//
	private static void onServerTick(ServerWorld world) {
		// Update player count every 5 seconds (100 ticks)
		if (server.get().getTicks() % 100 == 0) {
			if (playerCount != server.get().getCurrentPlayerCount()) {
				playerCount = server.get().getCurrentPlayerCount();
				bot.setStatus(String.format("%d/%d players",
						server.get().getCurrentPlayerCount(),
						server.get().getMaxPlayerCount()));
			}
		}
	}

	//
	// Called on game server message
	//
	private static void onServerMessage(MinecraftServer server, Text text) {
		// Format system message
		String formatted = config.systemMessageFormat.replaceAll("\\$MESSAGE", text.getString());
		// Relay
		relayToDiscord(formatted);
	}

	//
	// Called on game chat message
	//
	private static void onGameChat(MinecraftServer server, Text text, ServerPlayerEntity sender) {
		// Format chat message
		String formatted = config.chatMessageFormat.replaceAll("\\$NAME", sender.getName().getString());
		formatted = formatted.replaceAll("\\$MESSAGE", text.getString());
		// Relay
		relayToDiscord(formatted);
	}

	//
	// Called on Discord message
	//
	private static void onDiscordChat(Message message) {
		// Wait for server
		if (server.isEmpty()) {
			return;
		}

		// Only relay messages sent in relay channels
		if (!config.relayChannelIDs.contains((Long) message.getChannel().getIdLong())) {
			return;
		}

		User author = message.getAuthor();

		// Ignore messages from bots
		if (author.isBot()) {
			return;
		}

		// Ignore pins, joins, boosts, etc
		if (message.getType() != MessageType.DEFAULT) {
			return;
		}

		// Handle commands
		if (message.getContentDisplay().startsWith(config.commandPrefix)) {
			final String messageNoPrefix = message.getContentDisplay().substring(config.commandPrefix.length());

			try {
				commands.getDispatcher().execute(messageNoPrefix, message);
			} catch (CommandSyntaxException e) {
			}

			return;
		}

		// Format incoming message
		String formatted = config.discordMessageFormat;
		formatted = formatted.replaceAll("\\$NAME",     String.format("%s#%s", author.getName(), author.getDiscriminator()));
		formatted = formatted.replaceAll("\\$USERNAME", author.getName());
		formatted = formatted.replaceAll("\\$DISCRIM",  author.getDiscriminator());
		formatted = formatted.replaceAll("\\$NICKNAME", message.getGuild().getMember(author).getEffectiveName());
		String content = EmojiParser.parseToAliases(message.getContentDisplay());
		// Pad extra space for attachment names if not already empty
		if (content.length() > 0) {
			content += " ";
		}
		formatted = formatted.replaceAll("\\$MESSAGE", content);
		MutableText text = Text.literal(formatted);

		// Embed attachments as clickable text
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

		// Forward message to all clients
		// Send to each client explicitly to prevent feedback through server console messages
		for (ServerPlayerEntity player : server.get().getPlayerManager().getPlayerList()) {
			player.sendMessage(text);
		}

		// Forward message to server console as well
		LogManager.getLogger("Minecraft").info(text.getString());
	}
	private static void onRegisterCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess reg, CommandManager.RegistrationEnvironment env) {
		dispatcher.register(CommandManager.literal("discord")
				.requires(source -> source.hasPermissionLevel(4))
				.then(CommandManager.literal("loadConfig").executes(Adaptor::commandLoadConfig))
				.then(CommandManager.literal("status").executes(Adaptor::commandStatus))
				.then(CommandManager.literal("reconnect").executes(Adaptor::commandReconnect))
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