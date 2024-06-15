package net.yunitrish.adaptor.block.plant;

import com.terraformersmc.terraform.sign.api.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallSignBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.world.tree.ModSaplingGenerators;

import static net.yunitrish.adaptor.block.ModBlocks.registerBlock;

public class ChestnutSeries {
    public static void register(){
        Adaptor.LOGGER.info("Registering chestnut series...");
        StrippableBlockRegistry.register(CHESTNUT_LOG,STRIPPED_CHESTNUT_LOG);
        StrippableBlockRegistry.register(CHESTNUT_WOOD,STRIPPED_CHESTNUT_WOOD);
        FlammableBlockRegistry.getDefaultInstance().add(CHESTNUT_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(CHESTNUT_WOOD,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_CHESTNUT_LOG,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_CHESTNUT_WOOD,5,5);
        FlammableBlockRegistry.getDefaultInstance().add(CHESTNUT_PLANKS,5,20);
        FlammableBlockRegistry.getDefaultInstance().add(CHESTNUT_LEAVES,30,60);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.MANGROVE_PLANKS, ChestnutSeries.CHESTNUT_PLANKS);
            entries.addAfter(Items.MANGROVE_HANGING_SIGN, ChestnutSeries.STANDING_CHESTNUT_SIGN, ChestnutSeries.CHESTNUT_HANGING_SIGN);
        });
    }
    public static final Block CHESTNUT_LOG = registerBlock("chestnut_log",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(4f)));
    public static final Block CHESTNUT_WOOD = registerBlock("chestnut_wood",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(4f)));
    public static final Block STRIPPED_CHESTNUT_LOG = registerBlock("stripped_chestnut_log",new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).strength(4f)));
    public static final Block STRIPPED_CHESTNUT_WOOD = registerBlock("stripped_chestnut_wood",new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f)));
    public static final Block CHESTNUT_PLANKS = registerBlock("chestnut_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block CHESTNUT_LEAVES = registerBlock("chestnut_leaves",new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(4f).nonOpaque()));
    public static final Block CHESTNUT_SAPLING = registerBlock("chestnut_sapling", new SaplingBlock(ModSaplingGenerators.CHESTNUT,AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

    public static final Identifier CHESTNUT_SIGN_TEXTURE = Adaptor.id("entity/signs/chestnut");
    public static final Identifier CHESTNUT_HANGING_SIGN_TEXTURE = Adaptor.id("entity/signs/hanging/chestnut");
    public static final Identifier CHESTNUT_HANGING_GUI_SIGN_TEXTURE = Adaptor.id("textures/gui/hanging_signs/chestnut");

    public static final Block STANDING_CHESTNUT_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_standing_sign"), new TerraformSignBlock(CHESTNUT_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
    public static final Block WALL_CHESTNUT_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_wall_sign"), new TerraformWallSignBlock(CHESTNUT_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)));

    public static final Block CHESTNUT_HANGING_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_hanging_sign"), new TerraformHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE, CHESTNUT_HANGING_GUI_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)));

    public static final Block CHESTNUT_WALL_HANGING_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_wall_hanging_sign"), new TerraformWallHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE, CHESTNUT_HANGING_GUI_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN)));

    public static final BlockFamily CHESTNUT_FAMILY = BlockFamilies
            .register(CHESTNUT_PLANKS)
            .sign(STANDING_CHESTNUT_SIGN,WALL_CHESTNUT_SIGN)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();
    public static final Block CHESTNUT_STAIRS = registerBlock("chestnut_stairs", new StairsBlock(CHESTNUT_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static final Block CHESTNUT_SLAB = registerBlock("chestnut_slab", new SlabBlock(AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static final Block CHESTNUT_FENCE = registerBlock("chestnut_fence", new FenceBlock(AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static BlockSetType CHESTNUT = new BlockSetType(
            "chestnut", true, true, true,
            BlockSetType.ActivationRule.MOBS,
            BlockSoundGroup.WOOD,
            SoundEvents.BLOCK_WOODEN_DOOR_CLOSE,
            SoundEvents.BLOCK_WOODEN_DOOR_OPEN,
            SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE,
            SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN,
            SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF,
            SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF,
            SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON
    );
    public static final Block CHESTNUT_BUTTON = registerBlock("chestnut_button", new ButtonBlock(CHESTNUT, 10, AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static final Block CHESTNUT_PRESSURE_PLATE = registerBlock("chestnut_pressure_plate", new PressurePlateBlock(CHESTNUT, AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static final Block CHESTNUT_DOOR = registerBlock("chestnut_door", new DoorBlock(CHESTNUT, AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static final Block CHESTNUT_TRAPDOOR = registerBlock("chestnut_trapdoor", new TrapdoorBlock(CHESTNUT, AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    static WoodType chestnut = new WoodType(
            "chestnut",
            CHESTNUT,
            BlockSoundGroup.WOOD,
            BlockSoundGroup.HANGING_SIGN,
            SoundEvents.BLOCK_FENCE_GATE_CLOSE,
            SoundEvents.BLOCK_FENCE_GATE_OPEN
    );
    public static final Block CHESTNUT_FENCE_GATE = registerBlock("chestnut_fence_gate", new FenceGateBlock(chestnut, AbstractBlock.Settings.copy(CHESTNUT_PLANKS)));
    public static final Item CHESTNUT_SIGN = ModItems.registerItem("chestnut_sign", new SignItem(new Item.Settings().maxCount(16), ChestnutSeries.STANDING_CHESTNUT_SIGN, ChestnutSeries.WALL_CHESTNUT_SIGN));
    public static final Item HANGING_CHESTNUT_SIGN = ModItems.registerItem("chestnut_hanging_sign", new HangingSignItem(ChestnutSeries.CHESTNUT_HANGING_SIGN, ChestnutSeries.CHESTNUT_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));
//    public static final Item CHESTNUT_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_BOAT_ID, ModBoats.CHESTNUT_BOAT_KEY, false);
//    public static final Item CHESTNUT_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_CHEST_BOAT_ID, ModBoats.CHESTNUT_BOAT_KEY, true);

}
