package fr.tathan.jumpscared.common.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.network.packets.SyncJumpscare;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = Jumpscared.MODID)
public class JumpScareData extends SimpleJsonResourceReloadListener {

    public static HashMap<ResourceLocation , JumpScare> JUMPSCARES = new HashMap<>();

    public JumpScareData() {
        super(Jumpscared.GSON, "jumpscares");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        resourceLocationJsonElementMap.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "jumpscare");
            JumpScare jumpscare = JumpScare.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            JUMPSCARES.put(key, jumpscare);
            Jumpscared.LOGGER.info("Loaded jumpscare {}", key.toString());
        });

    }

    public static void addAllJumpscares(List<JumpScare> jumpScares) {
        JUMPSCARES.clear();
        jumpScares.forEach(jumpscare ->  JUMPSCARES.put(jumpscare.id(), jumpscare));
    }

    @SubscribeEvent
    public static void registerData(AddReloadListenerEvent event) {
        Jumpscared.LOGGER.info("registering jumpscare data listener");

        event.addListener(new JumpScareData());
    }

    public static void syncData(ServerPlayer player, boolean joined) {
        if (joined) {
            PacketDistributor.sendToPlayer(player, new SyncJumpscare(new ArrayList<>(JumpScareData.JUMPSCARES.values())));
        }
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            syncData(event.getPlayer(), true);
        }
        else {
            event.getPlayerList().getPlayers().forEach((player) -> syncData(player, true));
        }
    }

}
