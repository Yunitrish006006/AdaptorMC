package net.yunitrish.adaptor.block.plant;

import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;

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
    public static final Block CHESTNUT_PLANKS = registerBlock("chestnut_planks",new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(4f)));
    public static final Block CHESTNUT_LEAVES = registerBlock("chestnut_leaves",new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(4f).nonOpaque()));

    public static final Identifier CHESTNUT_SIGN_TEXTURE = Adaptor.modIdentifier("entity/signs/chestnut");
    public static final Identifier CHESTNUT_HANGING_SIGN_TEXTURE = Adaptor.modIdentifier("entity/signs/hanging/chestnut");
    public static final Identifier CHESTNUT_HANGING_GUI_SIGN_TEXTURE = Adaptor.modIdentifier("textures/gui/hanging_signs/chestnut");

    public static final Block STANDING_CHESTNUT_SIGN = Registry.register(Registries.BLOCK,Adaptor.modIdentifier("chestnut_standing_sign"), new TerraformSignBlock(CHESTNUT_SIGN_TEXTURE,AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
    public static final Block WALL_CHESTNUT_SIGN = Registry.register(Registries.BLOCK,Adaptor.modIdentifier("chestnut_wall_sign"), new TerraformWallSignBlock(CHESTNUT_SIGN_TEXTURE,AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)));

    public static final Block CHESTNUT_HANGING_SIGN = Registry.register(Registries.BLOCK,Adaptor.modIdentifier("chestnut_hanging_sign"), new TerraformHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE,CHESTNUT_HANGING_GUI_SIGN_TEXTURE,AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)));

    public static final Block CHESTNUT_WALL_HANGING_SIGN = Registry.register(Registries.BLOCK,Adaptor.modIdentifier("chestnut_wall_hanging_sign"), new TerraformWallHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE,CHESTNUT_HANGING_GUI_SIGN_TEXTURE,AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN)));

    public static final BlockFamily CHESTNUT_FAMILY = BlockFamilies
            .register(CHESTNUT_PLANKS)
            .sign(STANDING_CHESTNUT_SIGN,WALL_CHESTNUT_SIGN)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();
}
