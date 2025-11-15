package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.item.JumpScareItem;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemsRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Jumpscared.MODID);

    public static final Supplier<JumpScareItem> JUMPSCARE_ITEM = ITEMS.register("jumpscare",
            registryName -> new JumpScareItem(
                    new Item.Properties()
                            .component(DataComponentsRegistry.JUMPSCARE_ID_COMPONENT.get(), JumpScare.DEFAULT.id())
            )
    );

    public static final Supplier<BlockItem> JUMPSCARE_WORKBENCH = ITEMS.register("jumpscare_workbench",
            registryName -> new BlockItem(BlocksRegistry.JUMPSCARE_BLOCK.get(),
                    new Item.Properties()
                            .component(DataComponentsRegistry.JUMPSCARE_COMPONENT.get(), JumpScare.DEFAULT)
                            .component(DataComponentsRegistry.JUMPSCARE_ID_COMPONENT.get(), JumpScare.DEFAULT.id())
            )
    );


}
