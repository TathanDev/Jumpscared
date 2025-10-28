package fr.tathan.jumpscared.common.item;

import com.mojang.datafixers.util.Pair;
import fr.tathan.jumpscared.common.event.Events;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.registry.DataAttachmentsRegistry;
import fr.tathan.jumpscared.common.registry.DataComponentsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.List;

public class JumpScareItem extends Item {

    public JumpScareItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        BlockPos blockPos = context.getClickedPos();
        LevelChunk access = context.getLevel().getChunkAt(blockPos);
        ItemStack stack = context.getItemInHand();

        if(stack.has(DataComponentsRegistry.JUMPSCARE_COMPONENT)) {
            JumpScare jumpScare = stack.get(DataComponentsRegistry.JUMPSCARE_COMPONENT);
            JumpScare.NewContainer container = access.getExistingDataOrNull(DataAttachmentsRegistry.JUMPSCARE_CONTAINER);

            if(container == null || container.map() == null) {

                List<Pair<String, JumpScare>> pairs = List.of(new Pair<>(Events.getPos(blockPos), jumpScare));
                access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, new JumpScare.NewContainer(pairs));
            } else {
                String stringBlockPos = Events.getPos(blockPos);


                ArrayList<Pair<String, JumpScare>> pairs = new ArrayList<>(container.map());
                if(pairs.stream().anyMatch(pair -> pair.getFirst().equals(stringBlockPos))) {
                    return InteractionResult.FAIL;
                }
                pairs.add(Pair.of(stringBlockPos, jumpScare));

                access.setData(DataAttachmentsRegistry.JUMPSCARE_CONTAINER, new JumpScare.NewContainer(pairs));
            }

            context.getLevel().playSound(null, blockPos, SoundEvents.WARDEN_LISTENING_ANGRY, context.getPlayer() != null ? context.getPlayer().getSoundSource() : null, 1.0F, 1.0F);
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if(stack.has(DataComponentsRegistry.JUMPSCARE_COMPONENT)) {
            JumpScare jumpScare = stack.get(DataComponentsRegistry.JUMPSCARE_COMPONENT);
            tooltipComponents.add(Component.translatable("item.jumpscared.jumpscare_setter.tooltip").append(" ").append(jumpScare.getDisplayName()).withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("item.jumpscared.jumpscare_setter.no_jumpscare.tooltip"));
        }
    }
}
