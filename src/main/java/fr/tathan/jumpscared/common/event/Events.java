package fr.tathan.jumpscared.common.event;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.data.JumpScareData;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid =  Jumpscared.MODID)
public class Events {

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        if(event.getEntity().level().isClientSide) return;

        Player player = event.getEntity();
        BlockPos pos = player.getOnPos();
        ChunkAccess access = player.level().getChunkAt(pos);

        String positionString = getPos(pos);

        JumpScare.NewContainer container = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);

        if(container != null && container.map() != null) {
            container.map().forEach((pair) -> {
                if(pair.getFirst().equals(positionString)) {
                    if(!player.hasData(DataAttachmentsRegistry.CURRENT_PLAYER_JUMPSCARE)) {
                        pair.getSecond().trigger(player);
                    }
                }
            });
        }



        /**
         * We do the same things for the ID container to support old jumpscares
         */
        JumpScare.IdContainer idContainer = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_ID_CONTAINER);
        if(idContainer == null || idContainer.jumpscares() == null) {
            return;
        }
        idContainer.jumpscares().forEach((pair) -> {
            if(pair.getFirst().equals(positionString)) {
                if(!player.hasData(DataAttachmentsRegistry.CURRENT_PLAYER_JUMPSCARE)) {
                    JumpScareData.JUMPSCARES.getOrDefault(pair.getSecond(), JumpScare.DEFAULT).trigger(player);
                }
            }
        });


    }

    @SubscribeEvent
    public static void blockDestroyedEvent(BlockEvent.BreakEvent event) {
        if(event.getLevel().isClientSide()) return;

        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        ChunkAccess access = player.level().getChunkAt(pos);

        String positionString = getPos(pos);

        JumpScare.NewContainer container = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);

        if(container != null && container.map() != null) {
            container.map().forEach((pair) -> {
                if(pair.getFirst().equals(positionString)) {
                    if(!player.hasData(DataAttachmentsRegistry.CURRENT_PLAYER_JUMPSCARE)) {
                        pair.getSecond().trigger(player);
                        access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, container.removeJumpScareAt(positionString));
                    }
                }
            });
        }



        JumpScare.IdContainer idContainer = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_ID_CONTAINER);

        if(idContainer == null || idContainer.jumpscares() == null) {
            return;
        }

        idContainer.jumpscares().forEach((pair) -> {
            if(pair.getFirst().equals(positionString)) {
                if(!player.hasData(DataAttachmentsRegistry.CURRENT_PLAYER_JUMPSCARE)) {
                    JumpScareData.JUMPSCARES.getOrDefault(pair.getSecond(), JumpScare.DEFAULT).trigger(player);

                    access.setData(DataAttachmentsRegistry.JUMPSCARE_ID_CONTAINER, idContainer.removeJumpScareAt(positionString));
                }
            }
        });

    }

    public static BlockPos getBlockPosFromString(String posString) {
        String[] parts = posString.split("/");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2].trim());
        return new BlockPos(x, y, z);
    }

    public static String getPos(BlockPos pos) {
        return pos.getX() + "/" + pos.getY() + "/" + pos.getZ();
    }

}
