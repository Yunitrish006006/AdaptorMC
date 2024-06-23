package net.yunitrish.adaptor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.entity.ModEntities;
import net.yunitrish.adaptor.entity.client.ModModelLayers;
import net.yunitrish.adaptor.entity.client.PorcupineModel;
import net.yunitrish.adaptor.entity.client.PorcupineRenderer;

import java.util.List;

public class AdaptorClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModelLoadingPlugin.register(pluginContext -> {
            List<String> materials = List.of("", "wooden_", "stone_", "copper_", "iron_", "golden_", "diamond_", "netherite_");
            List<String> toolTypes = List.of("axe", "pickaxe", "sword", "hoe", "shovel", "hammer");
            for (String x : materials) {
                for (String y : toolTypes) {
                    pluginContext.addModels(Adaptor.id("item/" + y + "/" + x + y + "_3d"));
                }
            }

            // replace vanilla minecraft item skin
//            pluginContext.resolveModel().register(context -> {
//                UnbakedModel ret = null;
//                Identifier id = context.id();
//                if (id!=null && id.getPath().contains("item")) {
//                    if (id.getPath().contains("_sword") || id.getPath().contains("_axe") || id.getPath().contains("_hoe") || id.getPath().contains("_shovel") || id.getPath().contains("_pickaxe")  || id.getPath().contains("_hammer")) {
//                        if (!id.getPath().contains("3d")) {
//                            String s = id.getPath().split("/")[1];
//                            String toolType = s.split("_")[1];
//                            ret = context.getOrLoadModel(Adaptor.id("item/"+toolType+"/"+s+"_3d"));
//                        }
//                    }
//                }
//                return ret;
//            });
        });
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.Crops.SOYBEAN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.Crops.MARIJUANA_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.Chestnut.CHESTNUT_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.Chestnut.CHESTNUT_SAPLING, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

//        HandledScreens.register(ModScreenHandlers.STONE_MILL_HANDLER_TYPE, LockableStoneMillScreen::new);

//        TerraformBoatClientHelper.registerModelLayers(ModBoats.CHESTNUT_BOAT_ID,false);
    }
}
