package fr.tathan.jumpscared.mixin;

import fr.tathan.jumpscared.common.event.Events;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockDestroyedMixin {

    @Inject(at = @At(value = "TAIL"), method = "destroy")
    public void blockDestroyed(LevelAccessor level, BlockPos pos, BlockState state, CallbackInfo ci) {
        String positionString = Events.getPos(pos);

        var access = level.getChunk(pos);
        JumpScare.NewContainer oldContainer = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);

        if(oldContainer == null || oldContainer.map() == null) {
            return;
        }

        oldContainer.map().forEach((pair) -> {
            if(pair.getFirst().equals(positionString)) {
                access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, oldContainer.removeJumpScareAt(positionString));
            }
        });

        JumpScare.IdContainer idContainer = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_ID_CONTAINER);

        if(idContainer == null || idContainer.jumpscares() == null) {
            return;
        }

        idContainer.jumpscares().forEach((pair) -> {
            if(pair.getFirst().equals(positionString)) {
                access.setData(DataAttachmentsRegistry.JUMPSCARE_ID_CONTAINER, idContainer.removeJumpScareAt(positionString));
            }
        });

    }
}
