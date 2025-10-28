package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.block.entity.JumpScareWorkbenchEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Jumpscared.MODID);

    public static Supplier<BlockEntityType<JumpScareWorkbenchEntity>> JUMPSCARE_WORKBENCH_ENTITY = BLOCKS_ENTITY.register("jumpscare_workbench",
            () -> BlockEntityType.Builder.of(JumpScareWorkbenchEntity::new, BlocksRegistry.JUMPSCARE_BLOCK.get()
                    ).build(null)

    );

}
