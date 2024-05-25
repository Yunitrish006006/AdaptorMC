package net.yunitrish.adaptor.world.tree;

import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.mixin.FoliagePlacerTypeInvoker;
import net.yunitrish.adaptor.world.tree.custom.ChestnutFoliagePlacer;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> CHESTNUT_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("chestnut_foliage_placer", ChestnutFoliagePlacer.CODEC);

    public static void register() {
        Adaptor.LOGGER.info("Registering foliage placer...");
    }
}
