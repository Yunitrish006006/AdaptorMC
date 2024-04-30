package net.yunitrish.adaptor.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.yunitrish.adaptor.Adaptor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V",
            ordinal = 3,
            shift = At.Shift.AFTER
            )
    )
    public void addModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {

        List<String> materials = List.of("","wooden_","stone_","copper_","iron_","golden_","diamond_","netherite_");
        List<String> toolTypes = List.of("axe","pickaxe","sword","hoe","shovel","hammer");
        for (String x : materials) {
            for (String y : toolTypes) {
                this.addModel(new ModelIdentifier(Adaptor.MOD_ID, y+"/"+x+y+"_3d", "inventory"));
            }
        }
    }
}
