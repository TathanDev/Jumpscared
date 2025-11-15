package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DataComponentsRegistry {

    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPE = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Jumpscared.MODID);

    @Deprecated
    public static final Supplier<DataComponentType<JumpScare>> JUMPSCARE_COMPONENT = DATA_COMPONENT_TYPE.registerComponentType("jumpscare",
            builder -> builder.persistent(JumpScare.CODEC).networkSynchronized(JumpScare.STREAM_CODEC));

    public static final Supplier<DataComponentType<ResourceLocation>> JUMPSCARE_ID_COMPONENT = DATA_COMPONENT_TYPE.registerComponentType("jumpscare_id",
            builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));


}
