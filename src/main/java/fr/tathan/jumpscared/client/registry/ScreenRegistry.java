package fr.tathan.jumpscared.client.registry;

import fr.tathan.jumpscared.client.screen.JumpscareWorkbenchScreen;
import fr.tathan.jumpscared.common.registry.MenuRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ScreenRegistry {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.JUMPSCARE_WORKBENCH_MENU.get(), JumpscareWorkbenchScreen::new);
        // Register your screens here
    }

}
