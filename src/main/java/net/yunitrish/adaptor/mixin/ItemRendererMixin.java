package net.yunitrish.adaptor.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.yunitrish.adaptor.Adaptor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useIronHammerModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (renderMode != ModelTransformationMode.GUI) {
            List<String> key = List.of(stack.getItem().getTranslationKey().split("\\."));
            String id = key.getLast();

            List<String> renderList3D = List.of("axe","pickaxe","sword","hoe","shovel","hammer");
            String itemType = id.split("_")[id.split("_").length - 1];
            if (renderList3D.contains(itemType)) {
                return ((ItemRendererAccessor) this).adaptor$getModels().getModelManager().getModel(new ModelIdentifier(Adaptor.MOD_ID,itemType+"/"+id+"_3d", "inventory"));
            }
            else {
//                AdaptorMain.LOGGER.info("Unknown id :{}", id.split("_")[id.split("_").length - 1]);
                return value;
            }
        }
        return value;
    }
}
