package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.item.JumpScareSetterItem;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemsRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Jumpscared.MODID);

    public static final Supplier<JumpScareSetterItem> JUMPSCARE_ITEM = ITEMS.register("jumpscare_item",
            registryName -> new JumpScareSetterItem(
                    new Item.Properties()
                            .component(DataComponentsRegistry.JUMPSCARE_COMPONENT.get(), JumpScare.DEFAULT)
            )
    );

}
