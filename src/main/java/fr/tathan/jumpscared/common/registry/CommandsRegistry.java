package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.command.JumpscaredCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = Jumpscared.MODID)
public class CommandsRegistry {

    @SubscribeEvent
    public static void commandRegistry(RegisterCommandsEvent event) {
        new JumpscaredCommands(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
