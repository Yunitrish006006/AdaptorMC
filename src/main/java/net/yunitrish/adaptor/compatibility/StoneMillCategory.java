package net.yunitrish.adaptor.compatibility;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.AdaptorMain;
import net.yunitrish.adaptor.block.ModBlocks;

import java.util.LinkedList;
import java.util.List;

public class StoneMillCategory implements DisplayCategory<BasicDisplay> {

    public static final Identifier TEXTURE = new Identifier(AdaptorMain.MOD_ID, "textures/gui/stone_mill_gui.png");
    public static final CategoryIdentifier<StoneMillDisplay> STONE_MILL_DISPLAY_CATEGORY_IDENTIFIER = CategoryIdentifier.of(AdaptorMain.MOD_ID, "stone_mill");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return STONE_MILL_DISPLAY_CATEGORY_IDENTIFIER;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("gui.adaptor.stone_mill");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.STONE_MILL.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {

        final Point startPoint = new Point(bounds.getCenterX()-87, bounds.getCenterY()-35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE,new Rectangle(startPoint.x,startPoint.y,175,82)));
        widgets.add(
                Widgets.createSlot(new Point(startPoint.x+80, startPoint.y+11))
                .entries(display.getInputEntries().get(0))
        );
        widgets.add(
                Widgets.createSlot(new Point(startPoint.x+80, startPoint.y+59))
                .markOutput().entries(display.getOutputEntries().get(0))
        );
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return DisplayCategory.super.getDisplayHeight();
    }
}
