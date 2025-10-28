package fr.tathan.jumpscared.common.network.packets;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.block.entity.JumpScareWorkbenchEntity;
import fr.tathan.jumpscared.common.data.JumpScareData;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record SyncJumpscare(List<JumpScare> jumpscares) implements CustomPacketPayload {

    public static final Type<SyncJumpscare> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Jumpscared.MODID, "sync_jumpscare"));

    public static final StreamCodec<FriendlyByteBuf, SyncJumpscare> STREAM_CODEC = StreamCodec.composite(JumpScare.STREAM_CODEC.apply(ByteBufCodecs.list()), SyncJumpscare::jumpscares, SyncJumpscare::new);


    public SyncJumpscare(List<JumpScare> jumpscares) {
        this.jumpscares = jumpscares;
    }


    public static void handleDataOnMain(final SyncJumpscare data, final IPayloadContext context) {
        JumpScareData.addAllJumpscares(data.jumpscares());
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
