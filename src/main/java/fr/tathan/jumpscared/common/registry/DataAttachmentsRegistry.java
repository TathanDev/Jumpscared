package fr.tathan.jumpscared.common.registry;

import com.mojang.serialization.MapCodec;
import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DataAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Jumpscared.MODID);

    public static final Supplier<AttachmentType< Map<BlockPos, JumpScare> >> JUMPSCARE = ATTACHMENT_TYPES.register(
            "jumpscare", () -> AttachmentType.<Map<BlockPos, JumpScare>>builder(Map::of).serialize(Utils.pairMapCodec(BlockPos.CODEC, JumpScare.CODEC).fieldOf("data")).build()
    );

}
