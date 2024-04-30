package net.yunitrish.adaptor.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;

public class ModSounds {

    public static final SoundEvent METAL_DETECTOR_FOUND_ORE = registerSoundEvent("metal_detector_found_ore");
    public static final SoundEvent SOUND_BLOCK_STEP = registerSoundEvent("sound_block_step");
    public static final SoundEvent SOUND_BLOCK_BREAK = registerSoundEvent("sound_block_break");
    public static final SoundEvent SOUND_BLOCK_place = registerSoundEvent("sound_block_place");
    public static final SoundEvent SOUND_BLOCK_HIT = registerSoundEvent("sound_block_hit");
    public static final SoundEvent SOUND_BLOCK_FALL = registerSoundEvent("sound_block_fall");

    public static final SoundEvent BAR_BRAWL = registerSoundEvent("bar_brawl");
    public static final SoundEvent SAKURA_VALLEY = registerSoundEvent("sakura_valley");

    public static final BlockSoundGroup SOUND_BLOCK_SOUNDS = new BlockSoundGroup(
            1f,1f,
            ModSounds.SOUND_BLOCK_STEP,
            ModSounds.SOUND_BLOCK_BREAK,
            ModSounds.SOUND_BLOCK_place,
            ModSounds.SOUND_BLOCK_HIT,
            ModSounds.SOUND_BLOCK_FALL
            );

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Adaptor.MOD_ID,name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Adaptor.LOGGER.info("Registering sounds for "+ Adaptor.MOD_ID);
    }
}
