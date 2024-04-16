package net.yunitrish.adaptor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.entity.ModBlockEntities;
import net.yunitrish.adaptor.block.entity.renderer.StoneMillBlockEntityRenderer;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.client.ModModelLayers;
import net.yunitrish.adaptor.entity.client.PorcupineModel;
import net.yunitrish.adaptor.entity.client.PorcupineRenderer;
import net.yunitrish.adaptor.screen.StoneMillScreen;
import net.yunitrish.adaptor.screen.ModScreenHandlers;

public class AdaptorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOYBEAN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MARIJUANA_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAHLIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DAHLIA, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlers.STONE_MILL_SCREEN_HANDLER, StoneMillScreen::new);

        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

        BlockEntityRendererFactories.register(ModBlockEntities.STONE_MILL_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, StoneMillBlockEntityRenderer::new);
    }
}
