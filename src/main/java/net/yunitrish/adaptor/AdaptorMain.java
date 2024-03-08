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
import net.yunitrish.adaptor.bot.callbacks.ChatMessageCallback;
import net.yunitrish.adaptor.bot.callbacks.DiscordChatCallback;
import net.yunitrish.adaptor.bot.callbacks.ServerMessageCallback;
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

public class AdaptorMain implements ModInitializer {
	public static final String MOD_ID = "adaptor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
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
}