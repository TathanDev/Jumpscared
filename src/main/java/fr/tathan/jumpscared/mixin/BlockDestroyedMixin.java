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
        JumpScare.NewContainer container = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);

        if(container == null || container.map() == null) {
            return;
        }

        container.map().forEach((pair) -> {
            if(pair.getFirst().equals(positionString)) {
                access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, container.removeJumpScareAt(positionString));
            }
        });
    }
}
