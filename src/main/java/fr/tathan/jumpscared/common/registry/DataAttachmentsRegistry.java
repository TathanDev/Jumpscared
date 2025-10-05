package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.function.Supplier;

public class DataAttachmentsRegistry {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Jumpscared.MODID);

    /**
     * Used to store JumpScare data for chunks
     */
    public static final Supplier<AttachmentType<JumpScare.Container>> JUMPSCARE_CONTAINER = ATTACHMENT_TYPES.register(
            "jumpscares", () -> AttachmentType.builder(() -> new JumpScare.Container(new HashMap<>()))
                    .serialize(JumpScare.Container.CONTAINER_CODEC)
                    .sync(JumpScare.Container.STREAM_CODEC).build()
    );

    /**
     * Used to store JumpScare data for player
     */
    public static final Supplier<AttachmentType<JumpScare>> JUMPSCARE = ATTACHMENT_TYPES.register(
            "jumpscare", () -> AttachmentType.<JumpScare>builder(() -> null)
                    .serialize(JumpScare.CODEC)
                    .sync(JumpScare.STREAM_CODEC)
                    .build()
    );

}
