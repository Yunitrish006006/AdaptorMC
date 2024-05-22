package net.yunitrish.adaptor.world.tree;

import net.minecraft.block.SaplingGenerator;
import net.yunitrish.adaptor.world.ModConfiguredFeatures;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator CHESTNUT = new SaplingGenerator(
            "chestnut",
            0f,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.CHESTNUT_KEY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );
}
