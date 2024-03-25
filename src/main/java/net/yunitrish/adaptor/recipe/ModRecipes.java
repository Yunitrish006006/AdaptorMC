package net.yunitrish.adaptor.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(AdaptorMain.MOD_ID,GemPolishingRecipe.ID),GemPolishingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(AdaptorMain.MOD_ID,GemPolishingRecipe.Type.ID),GemPolishingRecipe.Type.INSTANCE);
    }
}
