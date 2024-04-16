package net.yunitrish.adaptor.compatibility;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.recipe.StoneMillRecipe;
import net.yunitrish.adaptor.screen.StoneMillScreen;

public class AdaptorREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new StoneMillCategory());
        registry.addWorkstations(StoneMillCategory.STONE_MILL_DISPLAY_CATEGORY_IDENTIFIER, EntryStacks.of(ModBlocks.STONE_MILL));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(StoneMillRecipe.class, StoneMillRecipe.Type.INSTANCE, StoneMillDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75,30,20,30), StoneMillScreen.class, StoneMillCategory.STONE_MILL_DISPLAY_CATEGORY_IDENTIFIER);
    }
}
