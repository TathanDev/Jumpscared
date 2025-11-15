package fr.tathan.jumpscared.common.menu;

import fr.tathan.jumpscared.common.registry.DataComponentsRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class JumpScareSlot extends Slot {

    public JumpScareSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.has(DataComponentsRegistry.JUMPSCARE_ID_COMPONENT.get());
    }
}
