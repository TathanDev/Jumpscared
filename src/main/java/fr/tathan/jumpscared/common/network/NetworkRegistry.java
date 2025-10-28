package fr.tathan.jumpscared.common.network;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.network.packets.RemoveCurrentJumpscare;
import fr.tathan.jumpscared.common.network.packets.SetJumpscare;
import fr.tathan.jumpscared.common.network.packets.SyncJumpscare;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Jumpscared.MODID)
public class NetworkRegistry {

    @SubscribeEvent // on the mod event bus
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                RemoveCurrentJumpscare.TYPE,
                RemoveCurrentJumpscare.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        RemoveCurrentJumpscare::handleDataOnMain,
                        RemoveCurrentJumpscare::handleDataOnMain
                )
        );
        registrar.playBidirectional(
                SetJumpscare.TYPE,
                SetJumpscare.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SetJumpscare::handleDataOnMain,
                        SetJumpscare::handleDataOnMain
                )
        );

        registrar.playBidirectional(
                SyncJumpscare.TYPE,
                SyncJumpscare.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SyncJumpscare::handleDataOnMain,
                        SyncJumpscare::handleDataOnMain
                )
        );
    }


}
