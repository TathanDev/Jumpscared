package fr.tathan.jumpscared.common.command;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.data.JumpScareData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public class SuggestionProviders {

    public static SuggestionProvider<CommandSourceStack> AVAILABLE_JUMPSCARES = net.minecraft.commands.synchronization.SuggestionProviders.register(Jumpscared.id("jumpscare"), (context, builder) -> {
        for (ResourceLocation jumpscareId : JumpScareData.JUMPSCARES.keySet()) {
            builder.suggest(jumpscareId.toString());
        }
        return builder.buildFuture();
    });

    public static void init() {
    }

}
