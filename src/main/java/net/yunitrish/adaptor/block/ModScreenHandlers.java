package net.yunitrish.adaptor.block;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.LockableContainer.StoneMill.LockableStoneMillScreenHandler;

public class ModScreenHandlers {
    public static void initialize() {
        Adaptor.LOGGER.info("registering screen handlers...");
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Adaptor.id(id), new ScreenHandlerType<T>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static final ScreenHandlerType<LockableStoneMillScreenHandler> STONE_MILL_HANDLER_TYPE = register("stone_mill_handler", LockableStoneMillScreenHandler::new);


}

