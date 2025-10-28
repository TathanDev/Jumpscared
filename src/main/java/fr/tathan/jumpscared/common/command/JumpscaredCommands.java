package fr.tathan.jumpscared.common.command;

import com.mojang.brigadier.CommandDispatcher;
import fr.tathan.jumpscared.common.data.JumpScareData;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;

public class JumpscaredCommands {


    public JumpscaredCommands(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("jumpscared")
                .then(Commands.literal("trigger")
                        .then(Commands.argument("jumpscare", ResourceLocationArgument.id()).suggests(SuggestionProviders.AVAILABLE_JUMPSCARES)
                                .executes((context -> {
                                    ResourceLocation jumpScareId = ResourceLocationArgument.getId(context, "jumpscare");
                                    JumpScare jumpScare = JumpScareData.JUMPSCARES.getOrDefault(jumpScareId, JumpScare.DEFAULT);

                                    jumpScare.trigger(context.getSource().getPlayer());
                                    return 1;
                                }))
                        )
                        .executes((context -> {
                            JumpScare.DEFAULT.trigger(context.getSource().getPlayer());
                            return 1;
                        }))
                )
        );

    }

}
