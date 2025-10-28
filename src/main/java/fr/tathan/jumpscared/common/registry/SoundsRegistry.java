package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundsRegistry {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Jumpscared.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> MONSTER_SCREAM = SOUND_EVENTS.register("monster_scream",
            () -> SoundEvent.createVariableRangeEvent(Jumpscared.id("monster_scream"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> ALEX_SCREAM = SOUND_EVENTS.register("alex_scream",
            () -> SoundEvent.createVariableRangeEvent(Jumpscared.id("alex_scream"))
    );

}
