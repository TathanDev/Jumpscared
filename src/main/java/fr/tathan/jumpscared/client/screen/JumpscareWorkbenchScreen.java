package fr.tathan.jumpscared.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.client.screen.components.JumpScareButton;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.menu.JumpscareWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;

public class JumpscareWorkbenchScreen extends AbstractContainerScreen<JumpscareWorkbenchMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Jumpscared.MODID, "textures/gui/jumpscare_workbench.png");

    public ArrayList<AbstractWidget> widgets = new ArrayList<>();

    public JumpscareWorkbenchMenu menu;

    public JumpscareWorkbenchScreen(JumpscareWorkbenchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = imageHeight - 94;
        titleLabelY = 4;
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();

        for(int i = 0; i < 3; i++) {
            JumpScare jumpScare = menu.blockEntity.jumpScares.get(i);

            JumpScareButton jumpScareButton = new JumpScareButton(this.leftPos + 59, this.topPos + 14 + i * 19, 109, 19, jumpScare.getDisplayName(), () -> {
                menu.blockEntity.syncAndCreateJumpScareItem(jumpScare, menu.player);

            });
            widgets.add(jumpScareButton);
            this.addRenderableWidget(jumpScareButton);
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if(menu.container.getItem(0).isEmpty()) {
            for (AbstractWidget widget : widgets) {
                widget.active = false;
            }
        } else {
            for (AbstractWidget widget : widgets) {
                widget.active = true;
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
    }
}
