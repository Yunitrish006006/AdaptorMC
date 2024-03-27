package net.yunitrish.adaptor.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;

public class ModModelLayers {
    public static final EntityModelLayer PORCUPINE =
            new EntityModelLayer(new Identifier(AdaptorMain.MOD_ID,"porcupine"),"main");
}
