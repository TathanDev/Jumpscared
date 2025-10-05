package fr.tathan.jumpscared.common.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.HashMap;
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
            JumpScare planet = JumpScare.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            Jumpscared.LOGGER.info(String.format("Loaded planet %s", key.toString()));
        });

    }

    @SubscribeEvent
    public static void registerData(AddReloadListenerEvent event) {
        Jumpscared.LOGGER.info("registering jumpscare data listener");

        event.addListener(new JumpScareData());
    }
}
