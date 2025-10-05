package fr.tathan.jumpscared.common.event;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid =  Jumpscared.MODID)
public class Events {

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        BlockPos pos = player.getOnPos();
        ChunkAccess access = player.level().getChunkAt(pos);

        JumpScare.Container container = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);
        if(container != null && container.map().containsKey(pos)) {
            JumpScare jumpScare = container.map().get(pos);
            Jumpscared.LOGGER.error("JumpScare triggered for player {} at position {}: {}", player.getName().getString(), pos, jumpScare);
        }

    }

}
