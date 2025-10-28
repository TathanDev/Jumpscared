package fr.tathan.jumpscared.client.registry;


import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.client.overlay.JumpScareOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = Jumpscared.MODID, value = Dist.CLIENT)
public class OverlayRegistry {

    @SubscribeEvent
    public static void onRenderGuiOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Jumpscared.MODID, "jumpscare_overlay"), JumpScareOverlay::render);
    }

}
