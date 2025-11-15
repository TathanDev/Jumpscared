package fr.tathan.jumpscared.common.jumpscare;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import fr.tathan.jumpscared.common.registry.SoundsRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record JumpScare(ResourceLocation id, ResourceLocation image, ResourceLocation sound, int durationTick) {

    public static Codec<JumpScare> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(JumpScare::id),
            ResourceLocation.CODEC.fieldOf("image").forGetter(JumpScare::image),
            ResourceLocation.CODEC.fieldOf("sound").forGetter(JumpScare::sound),
            Codec.INT.fieldOf("duration_tick").forGetter(JumpScare::durationTick)
    ).apply(instance, JumpScare::new));

    public static StreamCodec<FriendlyByteBuf, JumpScare> STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, JumpScare::id, ResourceLocation.STREAM_CODEC, JumpScare::image, ResourceLocation.STREAM_CODEC, JumpScare::sound, ByteBufCodecs.INT, JumpScare::durationTick, JumpScare::new);

    public static JumpScare DEFAULT = new JumpScare(ResourceLocation.parse("jumpscared:default"), ResourceLocation.parse("minecraft:textures/mob_effect/blindness.png"), SoundsRegistry.MONSTER_SCREAM.getId(), 120);

    public void saveToTag(CompoundTag tag) {
        tag.putString("id", id.toString());
        tag.putString("image", image.toString());
        tag.putString("sound", sound.toString());
        tag.putInt("duration_tick", durationTick);
    }

    public Component getDisplayName() {
       return Component.translatable(  "jumpscare."+ this.id().getNamespace() + "." + this.id().getPath());
    }

    public static JumpScare readFromTag(CompoundTag tag) {
        ResourceLocation id = ResourceLocation.parse(tag.getString("id"));
        ResourceLocation image = ResourceLocation.parse(tag.getString("image"));
        ResourceLocation sound = ResourceLocation.parse(tag.getString("sound"));
        int durationTick = tag.getInt("duration_tick");
        return new JumpScare(id, image, sound, durationTick);
    }

    public void trigger(Player player) {
        player.setData(DataAttachmentsRegistry.CURRENT_PLAYER_JUMPSCARE, this);

        player.getServer().registryAccess().registry(BuiltInRegistries.SOUND_EVENT.key()).ifPresent((sound) -> {

            SoundEvent soundEvent = sound.get(this.sound) != null ? sound.get(this.sound) : SoundsRegistry.MONSTER_SCREAM.get();

            player.playNotifySound(soundEvent, SoundSource.MASTER, 1.0F, 0.50F);
        });
    }



    public record IdContainer(List<Pair<String, ResourceLocation>> jumpscares) {
        public static Codec<IdContainer> ID_CONTAINER = RecordCodecBuilder.create(instance -> instance.group(
                Codec.pair(Codec.STRING.fieldOf("blockpos").codec(), ResourceLocation.CODEC.fieldOf("jumpscareId").codec()).listOf().fieldOf("jumpscares").forGetter(IdContainer::jumpscares)
        ).apply(instance, IdContainer::new));

        public static StreamCodec<FriendlyByteBuf, IdContainer> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull JumpScare.IdContainer decode(FriendlyByteBuf byteBuf) {

                int size = byteBuf.readInt();
                List<Pair<String, ResourceLocation>> list = new java.util.ArrayList<>();
                for (int i = 0; i < size; i++) {
                    String key = byteBuf.readUtf();
                    ResourceLocation id = byteBuf.readResourceLocation();
                    list.add(Pair.of(key, id));
                }

                return new IdContainer(list);
            }

            @Override
            public void encode(FriendlyByteBuf byteBuf, IdContainer idContainer) {
                byteBuf.writeInt(idContainer.jumpscares.size());
                for (Pair<String, ResourceLocation> pair : idContainer.jumpscares) {
                    byteBuf.writeUtf(pair.getFirst());
                    byteBuf.writeResourceLocation(pair.getSecond());
                }
            }
        };

        public IdContainer removeJumpScareAt(String posString) {

            ArrayList<Pair<String, ResourceLocation>> list = new ArrayList<>(jumpscares);

            list.removeIf(pair -> pair.getFirst().equals(posString));
            return new IdContainer(list);
        }
    }



    @Deprecated
    public record NewContainer(List<Pair<String, JumpScare>> map) {

        public static Codec<NewContainer> CONTAINER_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.pair(Codec.STRING.fieldOf("float").codec(), JumpScare.CODEC).listOf().fieldOf("jumpscares").forGetter(NewContainer::map)
        ).apply(instance, NewContainer::new));

        public static StreamCodec<FriendlyByteBuf, NewContainer> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull JumpScare.NewContainer decode(FriendlyByteBuf byteBuf) {

                int size = byteBuf.readInt();
                List<Pair<String, JumpScare>> list = new java.util.ArrayList<>();
                for (int i = 0; i < size; i++) {
                    String key = byteBuf.readUtf();
                    JumpScare value = JumpScare.STREAM_CODEC.decode(byteBuf);
                    list.add(Pair.of(key, value));
                }

                return new NewContainer(list);
            }

            @Override
            public void encode(FriendlyByteBuf byteBuf, NewContainer newContainer) {
                byteBuf.writeInt(newContainer.map.size());
                for (Pair<String, JumpScare> pair : newContainer.map) {
                    byteBuf.writeUtf(pair.getFirst());
                    JumpScare.STREAM_CODEC.encode(byteBuf, pair.getSecond());
                }
            }
        };

        public NewContainer removeJumpScareAt(String posString) {

            ArrayList<Pair<String, JumpScare>> list = new ArrayList<>(map);

            list.removeIf(pair -> pair.getFirst().equals(posString));
            return new NewContainer(list);
        }
    }
}
