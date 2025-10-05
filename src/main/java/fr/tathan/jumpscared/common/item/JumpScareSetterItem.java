package fr.tathan.jumpscared.common.item;

import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import fr.tathan.jumpscared.common.registry.DataComponentsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Map;

public class JumpScareSetterItem extends Item {

    public JumpScareSetterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        BlockPos blockPos = context.getClickedPos();
        LevelChunk access = context.getLevel().getChunkAt(blockPos);
        ItemStack stack = context.getItemInHand();

        if(stack.has(DataComponentsRegistry.JUMPSCARE_COMPONENT)) {
            JumpScare jumpScare = stack.get(DataComponentsRegistry.JUMPSCARE_COMPONENT);
            JumpScare.Container container = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);

            if(container == null) {
                Map<BlockPos, JumpScare> map = Map.of(blockPos, JumpScare.DEFAULT);
                access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, new JumpScare.Container(map));
            } else {
                container.map().put(blockPos, jumpScare);
                access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, container);
            }
        }
        return super.useOn(context);
    }
}
