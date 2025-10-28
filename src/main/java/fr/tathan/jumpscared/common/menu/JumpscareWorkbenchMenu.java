package fr.tathan.jumpscared.common.menu;

import fr.tathan.jumpscared.common.block.entity.JumpScareWorkbenchEntity;
import fr.tathan.jumpscared.common.registry.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class JumpscareWorkbenchMenu extends AbstractContainerMenu {

    public final Container container;
    public final JumpScareWorkbenchEntity blockEntity;
    public final Player player;

    public static JumpscareWorkbenchMenu create(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        JumpScareWorkbenchEntity blockEntity = (JumpScareWorkbenchEntity) inventory.player.level().getBlockEntity(buf.readBlockPos());
        return new JumpscareWorkbenchMenu(containerId, inventory, blockEntity, new SimpleContainer(1));
    }



    public JumpscareWorkbenchMenu(int containerId, Inventory inventory, JumpScareWorkbenchEntity blockEntity, Container container) {
        super(MenuRegistry.JUMPSCARE_WORKBENCH_MENU.get(), containerId);
        this.player = inventory.player;
        this.container = container;
        this.blockEntity = blockEntity;
        checkContainerSize(container, 1);

        this.addSlot(new JumpScareSlot(container, 0, 22 , 32));

        addPlayerHotbar(inventory, 8, 142);
        addPlayerInventory(inventory, 8, 84);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.container instanceof net.minecraft.world.level.block.entity.BlockEntity be) {
            var pos = be.getBlockPos();
            return player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
        }
        return true;
    }

    public void addPlayerHotbar(Inventory playerInventory, int xOffset, int yOffset) {
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, xOffset + j * 18, yOffset));
        }
    }

    public void addPlayerInventory(Inventory playerInventory, int xOffset, int yOffset) {
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, xOffset + k * 18, yOffset + j * 18));
            }
        }
    }

}
