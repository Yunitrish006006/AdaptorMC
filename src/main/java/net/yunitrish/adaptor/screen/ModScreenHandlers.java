package net.yunitrish.adaptor.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;

public class ModScreenHandlers {
    public static final ScreenHandlerType<StoneMillScreenHandler> STONE_MILL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(AdaptorMain.MOD_ID, "stone_mill"),
                    new ExtendedScreenHandlerType<>(StoneMillScreenHandler::new));

    public static void registerScreenHandlers() {
        AdaptorMain.LOGGER.info("Registering Screen Handlers for " + AdaptorMain.MOD_ID);
    }
}
