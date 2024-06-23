package net.yunitrish.adaptor.block.LockableContainer.StoneMill;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;

public class LockableStoneMillScreen extends HandledScreen<LockableStoneMillScreenHandler> {
    private static final Identifier TEXTURE = Adaptor.id("textures/gui/stone_mill_gui.png");

    public LockableStoneMillScreen(LockableStoneMillScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    public LockableStoneMillScreen(LockableStoneMillScreenHandler handler, PlayerInventory inventory) {
        this(handler, inventory, Text.translatable("block.adaptor.stone_mill"));
    }

    public LockableStoneMillScreen(LockableStoneMillScreenHandler handler, PlayerInventory playerInventory, Inventory inventory) {
        this(handler, playerInventory, Text.translatable("block.adaptor.stone_mill"));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        if (client != null) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
