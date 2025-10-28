package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.block.JumpScareWorkbench;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlocksRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Jumpscared.MODID);


    public static final DeferredBlock<JumpScareWorkbench> JUMPSCARE_BLOCK =
            BLOCKS.register("jumpscare_workbench", () -> new JumpScareWorkbench(BlockBehaviour.Properties.of()));


}
