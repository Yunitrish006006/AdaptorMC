package net.yunitrish.adaptor.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.yunitrish.adaptor.AdaptorMain;
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

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        this.addModel(new ModelIdentifier(AdaptorMain.MOD_ID, "hammer/"+"iron_"+"hammer_3d", "inventory"));
        List<String> materials = List.of("","wooden_","stone_","copper_","iron_","golden_","diamond_","netherite_");
        for (String x : materials) {
            this.addModel(new ModelIdentifier(AdaptorMain.MOD_ID, "axe/"+x+"axe_3d", "inventory"));
            this.addModel(new ModelIdentifier(AdaptorMain.MOD_ID, "pickaxe/"+x+"pickaxe_3d", "inventory"));
            this.addModel(new ModelIdentifier(AdaptorMain.MOD_ID, "sword/"+x+"sword_3d", "inventory"));
            this.addModel(new ModelIdentifier(AdaptorMain.MOD_ID, "hoe/"+x+"hoe_3d", "inventory"));
            this.addModel(new ModelIdentifier(AdaptorMain.MOD_ID, "shovel/"+x+"shovel_3d", "inventory"));
        }
    }
}
