package fr.tathan.jumpscared.common.block.entity;

import fr.tathan.jumpscared.Config;
import fr.tathan.jumpscared.common.data.JumpScareData;
import fr.tathan.jumpscared.common.jumpscare.JumpScare;
import fr.tathan.jumpscared.common.menu.JumpscareWorkbenchMenu;
import fr.tathan.jumpscared.common.network.packets.SetJumpscare;
import fr.tathan.jumpscared.common.registry.BlockEntityRegistry;
import fr.tathan.jumpscared.common.registry.DataComponentsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;

public class JumpScareWorkbenchEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public ArrayList<JumpScare> jumpScares = new ArrayList<>();

    public JumpScareWorkbenchEntity( BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.JUMPSCARE_WORKBENCH_ENTITY.get(), pos, blockState);
        setAvailableJumpScares();
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ListTag listTag = new ListTag();
        for (JumpScare jumpScare : jumpScares) {
            CompoundTag jumpScareTag = new CompoundTag();
            jumpScare.saveToTag(jumpScareTag);
            listTag.add(jumpScareTag);
        }
        tag.put("JumpScares", listTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        jumpScares.clear();
        ListTag listTag = tag.getList("JumpScares", 10);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag jumpScareTag = listTag.getCompound(i);
            JumpScare jumpScare = JumpScare.readFromTag(jumpScareTag);
            jumpScares.add(jumpScare);
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu.jumpscared.jumpscared_table");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new JumpscareWorkbenchMenu(i, inventory, this, this);
    }


    @Override
    public int getContainerSize() {
        return 1;
    }

    private void setAvailableJumpScares() {
        int size = JumpScareData.JUMPSCARES.size();

        for(int i = 0; i < 3; i++) {
            int randomIndex = (int) (Math.random() * size);
            jumpScares.add((JumpScare) JumpScareData.JUMPSCARES.values().toArray()[randomIndex]);
        }
    }

    public void syncAndCreateJumpScareItem(JumpScare jumpScare, Player player) {
        PacketDistributor.sendToServer(new SetJumpscare(this.getBlockPos(), jumpScare));
        player.closeContainer();
    }

    public void createJumpScareItem(JumpScare jumpScare, Container container, boolean sync) {
        var pos = this.getBlockPos();

        container.getItem(0).set(DataComponentsRegistry.JUMPSCARE_ID_COMPONENT, jumpScare.id());

        if(Config.destroyWorkbench) {
            this.getLevel().removeBlock(this.getBlockPos(), false);
            this.getLevel().removeBlockEntity(this.getBlockPos());
            this.getLevel().addParticle(ParticleTypes.EXPLOSION, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.1, 0.1, 0.1);
        }

        setChanged();

    }
}
