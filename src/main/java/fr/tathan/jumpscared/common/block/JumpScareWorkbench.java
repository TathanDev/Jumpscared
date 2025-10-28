package fr.tathan.jumpscared.common.block;

import com.mojang.serialization.MapCodec;
import fr.tathan.jumpscared.common.block.entity.JumpScareWorkbenchEntity;
import fr.tathan.jumpscared.common.menu.JumpscareWorkbenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JumpScareWorkbench extends Block implements EntityBlock {
    public JumpScareWorkbench(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<JumpScareWorkbench> codec() {
        return simpleCodec(JumpScareWorkbench::new);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof JumpScareWorkbenchEntity entity) {
                player.openMenu(new SimpleMenuProvider(
                        (containerId, playerInventory, p) -> new JumpscareWorkbenchMenu(containerId, playerInventory, entity, entity),
                        Component.translatable("menu.jumpscared.jumpscared_table")
                ), (buf) -> buf.writeBlockPos(pos));

            }

            return InteractionResult.CONSUME;
        }
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new JumpScareWorkbenchEntity(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level.isClientSide) {

        }

        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BaseContainerBlockEntity containerBlockEntity) {
                Containers.dropContents(level, pos, containerBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
                state.updateNeighbourShapes(level, pos, UPDATE_NEIGHBORS);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

}
