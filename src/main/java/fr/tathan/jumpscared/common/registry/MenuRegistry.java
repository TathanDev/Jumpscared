package fr.tathan.jumpscared.common.registry;

import fr.tathan.jumpscared.Jumpscared;
import fr.tathan.jumpscared.common.menu.JumpscareWorkbenchMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MenuRegistry {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, Jumpscared.MODID);

    public static Supplier<MenuType<JumpscareWorkbenchMenu>> JUMPSCARE_WORKBENCH_MENU = MENUS.register("jumpscare_workbench_menu",
            () -> IMenuTypeExtension.create(JumpscareWorkbenchMenu::create)
    );

}
