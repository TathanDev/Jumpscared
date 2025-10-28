package fr.tathan.jumpscared.common.network.packets;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.block.entity.JumpScareWorkbenchEntity;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetJumpscare(BlockPos pos, JumpScare jumpScare) implements CustomPacketPayload {

    public static final Type<SetJumpscare> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Jumpscared.MODID, "set_jumpscare"));

    public static final StreamCodec<FriendlyByteBuf, SetJumpscare> STREAM_CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, SetJumpscare::pos, JumpScare.STREAM_CODEC, SetJumpscare::jumpScare, SetJumpscare::new);


    public static void handleDataOnMain(final SetJumpscare data, final IPayloadContext context) {

        BlockEntity entity = context.player().level().getBlockEntity(data.pos);
        if(entity instanceof JumpScareWorkbenchEntity workbenchEntity) {
            workbenchEntity.createJumpScareItem(data.jumpScare, workbenchEntity, false);
        }
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


