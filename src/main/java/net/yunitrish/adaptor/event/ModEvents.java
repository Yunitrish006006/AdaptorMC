package net.yunitrish.adaptor.event;

import net.minecraft.util.ActionResult;
import net.yunitrish.adaptor.Adaptor;


public class ModEvents {
    public static void registerEvents() {
        Adaptor.LOGGER.info("registering events...");
        ZombieDeathCallback.EVENT.register((zombie) -> ActionResult.PASS);
    }
}
