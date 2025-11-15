package fr.tathan.jumpscared;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Jumpscared.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue DESTROY_WORKBENCH = BUILDER.comment("Whether the jumpscare workbench should disappear after use.")
            .define("destroyWorkbench", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean destroyWorkbench;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        destroyWorkbench = DESTROY_WORKBENCH.get();
    }
}
