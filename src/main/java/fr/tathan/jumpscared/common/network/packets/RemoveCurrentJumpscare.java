package fr.tathan.jumpscared.common.network.packets;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RemoveCurrentJumpscare() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RemoveCurrentJumpscare> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Jumpscared.MODID, "my_data"));

    public static final StreamCodec<ByteBuf, RemoveCurrentJumpscare> STREAM_CODEC = new StreamCodec<ByteBuf, RemoveCurrentJumpscare>() {
        @Override
        public void encode(ByteBuf o, RemoveCurrentJumpscare removeCurrentJumpscare) {

        }

        @Override
        public RemoveCurrentJumpscare decode(ByteBuf buffer) {
            return new RemoveCurrentJumpscare();
        }
    };

    public static void handleDataOnMain(final RemoveCurrentJumpscare data, final IPayloadContext context) {
        context.player().removeData(DataAttachmentsRegistry.CURRENT_PLAYER_JUMPSCARE);
    }


    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


