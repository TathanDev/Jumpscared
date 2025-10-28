package fr.tathan.jumpscared.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.network.packets.RemoveCurrentJumpscare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;

public class JumpScareOverlay {

    private static int durationTicks = 0;

    public static void render(GuiGraphics guiGraphics, DeltaTracker tracker) {

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;


        if(player !=null && player.hasData(DataAttachmentsRegistry.JUMPSCARE)) {
            JumpScare jumpScare = player.getData(DataAttachmentsRegistry.JUMPSCARE);

            int windowWidth = mc.getWindow().getGuiScaledWidth();

            int windowHeight = mc.getWindow().getGuiScaledHeight();

            int imageWidth = 200;

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1,1, 1, Mth.sin((float) durationTicks / jumpScare.durationTick() * (float) Math.PI ) );

            guiGraphics.blit(jumpScare.image(), windowWidth / 2 - imageWidth /2 , windowHeight / 2 - imageWidth / 2 , 0, 0, imageWidth, imageWidth, imageWidth, imageWidth);
            RenderSystem.setShaderColor(1,1, 1, 1);
            RenderSystem.disableBlend();

            if(durationTicks <= jumpScare.durationTick()) {
                durationTicks++;
            } else {
                PacketDistributor.sendToServer(new RemoveCurrentJumpscare());
                durationTicks = 0;
            }
        }
    }
}
