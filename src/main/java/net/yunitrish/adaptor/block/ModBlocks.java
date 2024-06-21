package net.yunitrish.adaptor.block;

import com.terraformersmc.terraform.sign.api.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallSignBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.ChestLockSystem.custom.LockableChestBlock;
import net.yunitrish.adaptor.block.functional.SoundBlock;
import net.yunitrish.adaptor.block.plant.MarijuanaCropBlock;
import net.yunitrish.adaptor.block.plant.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;
import net.yunitrish.adaptor.sound.ModSounds;
import net.yunitrish.adaptor.world.tree.ModSaplingGenerators;


public class ModBlocks {
    public static final Block LOCKABLE_CHEST = registerBlock(
            "lockable_chest",
            new LockableChestBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.OAK_TAN)
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.5f)
                            .sounds(BlockSoundGroup.WOOD)
                            .burnable()
            )
    );
    public static Block GLASS_SLAB = registerBlock(
            "glass_slab",
            new SlabBlock(
                    AbstractBlock.Settings.create()
                            .strength(0.3f)
                            .sounds(BlockSoundGroup.GLASS)
                            .nonOpaque()
                            .allowsSpawning(Blocks::never)
                            .solidBlock(Blocks::never)
                            .suffocates(Blocks::never)
                            .blockVision(Blocks::never)
            )
    );

    public static Block registerBlock(String name, Block block, boolean registerItem, boolean inItemGroup) {
        Block temp = Blocks.register("adaptor:" + name, block);
        if (registerItem) {
//            Adaptor.LOGGER.info("add {} to itemgroup", block.getName().getContent());
            ModItems.registerItem(name, new BlockItem(block, new Item.Settings()), inItemGroup);
        }
        return temp;
    }

    public static final Block SOUND_BLOCK = registerBlock(
            "sound_block",
            new SoundBlock(
                    AbstractBlock
                            .Settings
                            .copy(Blocks.IRON_BLOCK)
                            .sounds(ModSounds.SOUND_BLOCK_SOUNDS)
            )
    );
    public static Block GRAVEL_IRON_ORE = registerBlock(
            "gravel_iron_ore",
            new ColoredFallingBlock(
                    new ColorCode(-9356741),
                    AbstractBlock
                            .Settings
                            .copy(Blocks.GRAVEL)
            )
    );

    public static Block registerBlock(String name, Block block) {
        return registerBlock(name, block, true, true);
    }

    public static void registerModBlocks () {
        Adaptor.LOGGER.info("Registering blocks...");
        Chestnut.register();
        Crops.register();
        Dirt.register();
    }

    public static class Crops {
        public static Block SOYBEAN_CROP = registerBlock(
                "soybean_crop",
                new SoyBeanCropBlock(
                        AbstractBlock.Settings.create()
                                .noCollision()
                                .ticksRandomly()
                                .breakInstantly()
                                .sounds(BlockSoundGroup.CROP)
                                .pistonBehavior(PistonBehavior.DESTROY)
                ), false, false
        );
        public static Block MARIJUANA_CROP = registerBlock(
                "marijuana_crop",
                new MarijuanaCropBlock(
                        AbstractBlock.Settings.create()
                                .noCollision()
                                .ticksRandomly()
                                .breakInstantly()
                                .sounds(BlockSoundGroup.CROP)
                                .pistonBehavior(PistonBehavior.DESTROY)
                ), false, false
        );

        public static void register() {
        }
    }

    public static class Dirt {
        public static final Block DIRT_STAIRS = registerBlock("dirt_stairs", new StairsBlock(Blocks.DIRT.getDefaultState(), AbstractBlock.Settings.copy(Blocks.DIRT)));
        public static final Block DIRT_SLAB = registerBlock("dirt_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.DIRT)));
        public static final Block DIRT_FENCE = registerBlock("dirt_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.DIRT)));
        public static final Block DIRT_WALL = registerBlock("dirt_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.DIRT)));
        public static BlockSetType DIRT = new BlockSetType(
                "dirt", true, true, false,
                BlockSetType.ActivationRule.MOBS,
                BlockSoundGroup.GRAVEL,
                SoundEvents.BLOCK_GRAVEL_BREAK,
                SoundEvents.BLOCK_GRAVEL_PLACE,
                SoundEvents.BLOCK_GRAVEL_BREAK,
                SoundEvents.BLOCK_GRAVEL_PLACE,
                SoundEvents.BLOCK_GRAVEL_BREAK,
                SoundEvents.BLOCK_GRAVEL_PLACE,
                SoundEvents.BLOCK_GRAVEL_BREAK,
                SoundEvents.BLOCK_GRAVEL_PLACE
        );
        public static final Block DIRT_BUTTON = registerBlock("dirt_button", new ButtonBlock(DIRT, 10, AbstractBlock.Settings.copy(Blocks.DIRT)));
        public static final Block DIRT_PRESSURE_PLATE = registerBlock("dirt_pressure_plate", new PressurePlateBlock(DIRT, AbstractBlock.Settings.copy(Blocks.DIRT)));
        public static final Block DIRT_DOOR = registerBlock("dirt_door", new DoorBlock(DIRT, AbstractBlock.Settings.create().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
        public static final Block DIRT_TRAPDOOR = registerBlock("dirt_trapdoor", new TrapdoorBlock(DIRT, AbstractBlock.Settings.create().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
        static WoodType dirt = new WoodType("dirt", DIRT,
                BlockSoundGroup.ROOTED_DIRT,
                BlockSoundGroup.ROOTED_DIRT,
                SoundEvents.BLOCK_ROOTED_DIRT_PLACE,
                SoundEvents.BLOCK_ROOTED_DIRT_BREAK
        );
        public static final Block DIRT_FENCE_GATE = registerBlock("dirt_fence_gate", new FenceGateBlock(dirt, AbstractBlock.Settings.copy(Blocks.DIRT)));

        public static void register() {
        }
    }

    public static class Chestnut {
        public static final Block CHESTNUT_LOG = registerFlammable("chestnut_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(4f)), 5, 5);
        public static final Block CHESTNUT_WOOD = registerFlammable("chestnut_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(4f)), 5, 5);
        public static final Block STRIPPED_CHESTNUT_LOG = registerFlammable("stripped_chestnut_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).strength(4f)), 5, 5);
        public static final Block STRIPPED_CHESTNUT_WOOD = registerFlammable("stripped_chestnut_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f)), 5, 5);
        public static final Block CHESTNUT_PLANKS = registerFlammable("chestnut_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD).burnable()), 5, 20);
        public static final Block CHESTNUT_LEAVES = registerFlammable("chestnut_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(4f).nonOpaque()), 30, 60);
        public static final Block CHESTNUT_SAPLING = registerBlock("chestnut_sapling", new SaplingBlock(ModSaplingGenerators.CHESTNUT, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));
        public static final Identifier CHESTNUT_SIGN_TEXTURE = Adaptor.id("entity/signs/chestnut");
        public static final Identifier CHESTNUT_HANGING_SIGN_TEXTURE = Adaptor.id("entity/signs/hanging/chestnut");
        public static final Identifier CHESTNUT_HANGING_GUI_SIGN_TEXTURE = Adaptor.id("textures/gui/hanging_signs/chestnut");
        public static final Block STANDING_CHESTNUT_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_standing_sign"), new TerraformSignBlock(CHESTNUT_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
        public static final Block WALL_CHESTNUT_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_wall_sign"), new TerraformWallSignBlock(CHESTNUT_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)));
        public static final BlockFamily CHESTNUT_FAMILY = BlockFamilies
                .register(CHESTNUT_PLANKS)
                .sign(STANDING_CHESTNUT_SIGN, WALL_CHESTNUT_SIGN)
                .group("wooden")
                .unlockCriterionName("has_planks")
                .build();
        public static final Item CHESTNUT_SIGN = ModItems.registerItem("chestnut_sign", new SignItem(new Item.Settings().maxCount(16), Chestnut.STANDING_CHESTNUT_SIGN, Chestnut.WALL_CHESTNUT_SIGN));
        public static final Block CHESTNUT_HANGING_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_hanging_sign"), new TerraformHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE, CHESTNUT_HANGING_GUI_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)));

        public static final Block CHESTNUT_WALL_HANGING_SIGN = Registry.register(Registries.BLOCK, Adaptor.id("chestnut_wall_hanging_sign"), new TerraformWallHangingSignBlock(CHESTNUT_HANGING_SIGN_TEXTURE, CHESTNUT_HANGING_GUI_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN)));
        public static final Item HANGING_CHESTNUT_SIGN = ModItems.registerItem("chestnut_hanging_sign", new HangingSignItem(Chestnut.CHESTNUT_HANGING_SIGN, Chestnut.CHESTNUT_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));
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

        public static void register() {
            StrippableBlockRegistry.register(Chestnut.CHESTNUT_LOG, Chestnut.STRIPPED_CHESTNUT_LOG);
            StrippableBlockRegistry.register(Chestnut.CHESTNUT_WOOD, Chestnut.STRIPPED_CHESTNUT_WOOD);
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
                entries.addAfter(Items.MANGROVE_PLANKS, Chestnut.CHESTNUT_PLANKS);
                entries.addAfter(Items.MANGROVE_HANGING_SIGN, Chestnut.STANDING_CHESTNUT_SIGN, Chestnut.CHESTNUT_HANGING_SIGN);
            });
        }

        public static Block registerFlammable(String id, Block block, int burn, int spread) {
            FlammableBlockRegistry.getDefaultInstance().add(block, burn, spread);
            return registerBlock(id, block);
        }
//    public static final Item CHESTNUT_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_BOAT_ID, ModBoats.CHESTNUT_BOAT_KEY, false);
//    public static final Item CHESTNUT_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_CHEST_BOAT_ID, ModBoats.CHESTNUT_BOAT_KEY, true);

    }
}
