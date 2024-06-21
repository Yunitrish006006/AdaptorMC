package net.yunitrish.adaptor.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.yunitrish.adaptor.Adaptor;

public class ModCommands {
    public static void register() {
        Adaptor.LOGGER.info("registering commands...");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> FlyCommand.register(dispatcher));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> DropCommand.register(dispatcher));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> AddMemberCommand.register(dispatcher));
    }
}
