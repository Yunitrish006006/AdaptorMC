package net.yunitrish.adaptor.world.tree;

import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.mixin.TrunkPlacerTypeInvoker;
import net.yunitrish.adaptor.world.tree.custom.ChestnutTrunkPlacer;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> CHESTNUT_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("chestnut_trunk_placer", ChestnutTrunkPlacer.CODEC);

    public static void register() {
        Adaptor.LOGGER.info("registering trunk placers...");
    }
}
