package fr.tathan.jumpscared.common.jumpscare;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import fr.tathan.jumpscared.common.registry.SoundsRegistry;
import fr.tathan.jumpscared.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record JumpScare(ResourceLocation image, ResourceLocation sound, int durationTick) {

    public static Codec<JumpScare> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("image").forGetter(JumpScare::image),
            ResourceLocation.CODEC.fieldOf("sound").forGetter(JumpScare::sound),
            Codec.INT.fieldOf("duration_tick").forGetter(JumpScare::durationTick)
    ).apply(instance, JumpScare::new));

    public static StreamCodec<FriendlyByteBuf, JumpScare> STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, JumpScare::image, ResourceLocation.STREAM_CODEC, JumpScare::sound, ByteBufCodecs.INT, JumpScare::durationTick, JumpScare::new);

    public static JumpScare DEFAULT = new JumpScare(ResourceLocation.parse("minecraft:textures/item/acacia_boat.png"), SoundsRegistry.MONSTER_SCREAM.getId(), 60);


    public void trigger(Player player) {
        player.setData(DataAttachmentsRegistry.JUMPSCARE, this);

        player.getServer().registryAccess().registry(BuiltInRegistries.SOUND_EVENT.key()).ifPresent((sound) -> {

            player.playNotifySound(sound.get(SoundEvents.BREEZE_JUMP.getLocation()), SoundSource.MASTER, 1.0F, 0.50F);
        });
    }



    public record Container(Map<BlockPos, JumpScare> map) {

        public static Codec<Container> CONTAINER_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Utils.pairListMap(BlockPos.CODEC, JumpScare.CODEC).fieldOf("jumpscares").forGetter(Container::map)
        ).apply(instance, Container::new));

        public static StreamCodec<FriendlyByteBuf, Container> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull Container decode(FriendlyByteBuf byteBuf) {

                Map<BlockPos, JumpScare> map = byteBuf.readMap(BlockPos.STREAM_CODEC, JumpScare.STREAM_CODEC);

                return new Container(map);
            }

            @Override
            public void encode(FriendlyByteBuf byteBuf, Container container) {
                byteBuf.writeMap(container.map(), BlockPos.STREAM_CODEC, JumpScare.STREAM_CODEC);

            }
        };
    }
}
