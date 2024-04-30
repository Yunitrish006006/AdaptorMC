package net.yunitrish.adaptor.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.functional.stoneMill.StoneMillData;

public class ModScreenHandlers {
    public static final ScreenHandlerType<StoneMillScreenHandler> STONE_MILL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Adaptor.modIdentifier("stone_mill"),
                    new ExtendedScreenHandlerType<>(StoneMillScreenHandler::new, StoneMillData.PACKET_CODEC));
    public static void registerScreenHandlers() {
        Adaptor.LOGGER.info("Registering Screen Handlers...");
    }
}
