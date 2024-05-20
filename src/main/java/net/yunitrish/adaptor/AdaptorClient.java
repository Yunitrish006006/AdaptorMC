package net.yunitrish.adaptor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.client.ModModelLayers;
import net.yunitrish.adaptor.entity.client.PorcupineModel;
import net.yunitrish.adaptor.entity.client.PorcupineRenderer;
import net.yunitrish.adaptor.screen.ModScreenHandlers;
import net.yunitrish.adaptor.screen.StoneMillScreen;

public class AdaptorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOYBEAN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MARIJUANA_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ChestnutSeries.CHESTNUT_LEAVES, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

        HandledScreens.register(ModScreenHandlers.STONE_MILL_SCREEN_HANDLER, StoneMillScreen::new);
    }
}
