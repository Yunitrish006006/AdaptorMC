package net.yunitrish.adaptor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.entity.ModBlockEntities;
import net.yunitrish.adaptor.creature.villager.ModCustomTrades;
import net.yunitrish.adaptor.creature.villager.ModVillagers;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.custom.PorcupineEntity;
import net.yunitrish.adaptor.event.EntityDamagedHandler;
import net.yunitrish.adaptor.item.ModItemGroups;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.mechanic.AutoPlant;
import net.yunitrish.adaptor.recipe.ModRecipes;
import net.yunitrish.adaptor.screen.ModScreenHandlers;
import net.yunitrish.adaptor.sound.ModSounds;
import net.yunitrish.adaptor.util.ModEvents;
import net.yunitrish.adaptor.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
		ModSounds.registerSounds();
		ModRecipes.registerRecipes();
		ModVillagers.registerVillagers();
		ModBlockEntities.registerBlockEntities();
		ModEnchantments.registerModEnchantments();
		ModLootTableModifiers.modifyLootTables();
		ModScreenHandlers.registerScreenHandlers();
		FuelRegistry.INSTANCE.add(ModItems.BAMBOO_COAL,120);
		AttackEntityCallback.EVENT.register(new EntityDamagedHandler());
		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());
		AutoPlant.setUp();
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockPos pos = hitResult.getBlockPos();
			BlockState state = world.getBlockState(pos);
			ItemStack itemStack = player.getStackInHand(hand);

			if (state.getBlock() != Blocks.COMPOSTER) return ActionResult.PASS;

			if (state.get(ComposterBlock.LEVEL) < 8) {
				if ( List.of(ModItems.CORN, ModItems.CORN_SEEDS,ModItems.SOYBEAN,ModItems.MARIJUANA,ModItems.MARIJUANA_LEAF,ModItems.MARIJUANA_SEEDS).contains(itemStack.getItem())) {
					world.setBlockState(pos, state.with(ComposterBlock.LEVEL, state.get(ComposterBlock.LEVEL) + 1));
					ItemStack result = player.getStackInHand(hand);
					result.setCount(player.getStackInHand(hand).getCount()-1);
					player.setStackInHand(hand,result);
					world.playSound(player,pos, SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS,1.0f,1.0f);
					return ActionResult.SUCCESS;
				}
			}
			else {
				world.playSound(player,pos, SoundEvents.BLOCK_COMPOSTER_FILL_SUCCESS, SoundCategory.BLOCKS,1.0f,1.0f);
				player.giveItemStack(new ItemStack(Items.BONE_MEAL));
				world.setBlockState(pos, state.with(ComposterBlock.LEVEL, 0));
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});
	}
}