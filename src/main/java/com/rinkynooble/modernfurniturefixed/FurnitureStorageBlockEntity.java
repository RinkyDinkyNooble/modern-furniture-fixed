package com.rinkynooble.modernfurniturefixed;

import com.cookiecraftmods.mdm.block.FurnitureBlock;
import com.cookiecraftmods.mdm.init.MdmModBlockEntities;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

/**
 * The inventory of a piece of furniture. It opens as a vanilla chest of 1 to 6 rows (the server config sets the
 * rows per block), works with hoppers, loot tables and the item capability, and plays a sound when it opens and
 * closes. Items saved in slots past the configured size are dropped when the block loads.
 */
public class FurnitureStorageBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private static final MenuType<?>[] MENUS = {
        MenuType.GENERIC_9x1, MenuType.GENERIC_9x2, MenuType.GENERIC_9x3,
        MenuType.GENERIC_9x4, MenuType.GENERIC_9x5, MenuType.GENERIC_9x6
    };

    private NonNullList<ItemStack> items;
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
    private final ContainerOpenersCounter openers = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            FurnitureStorageBlockEntity.this.playSound(state, true);
        }

        @Override
        protected void onClose(Level level, BlockPos pos, BlockState state) {
            FurnitureStorageBlockEntity.this.playSound(state, false);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int oldCount, int newCount) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            return player.containerMenu instanceof ChestMenu menu && menu.getContainer() == FurnitureStorageBlockEntity.this;
        }
    };

    public FurnitureStorageBlockEntity(BlockPos pos, BlockState state) {
        super(MdmModBlockEntities.typeFor(state.getBlock()), pos, state);
        this.items = NonNullList.withSize(rows(state) * 9, ItemStack.EMPTY);
    }

    private static int rows(BlockState state) {
        return state.getBlock() instanceof FurnitureBlock block ? MffConfig.storageRows(block.furniture()) : 3;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        int size = this.items.size();
        if (tag.contains("Items", Tag.TAG_LIST)) {
            ListTag list = tag.getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                size = Math.max(size, (list.getCompound(i).getByte("Slot") & 255) + 1);
            }
        }
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        int size = rows(this.getBlockState()) * 9;
        if (this.level != null && !this.level.isClientSide && this.items.size() != size) {
            NonNullList<ItemStack> resized = NonNullList.withSize(size, ItemStack.EMPTY);
            for (int i = 0; i < this.items.size(); i++) {
                if (i < size) {
                    resized.set(i, this.items.get(i));
                } else if (!this.items.get(i).isEmpty()) {
                    Containers.dropItemStack(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5,
                            this.worldPosition.getZ() + 0.5, this.items.get(i));
                }
            }
            this.items = resized;
            this.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        int rows = Math.max(1, Math.min(6, this.items.size() / 9));
        return new ChestMenu(MENUS[rows - 1], id, inventory, this, rows);
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openers.incrementOpeners(player, this.level, this.worldPosition, this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openers.decrementOpeners(player, this.level, this.worldPosition, this.getBlockState());
        }
    }

    /** Called from the block's scheduled tick, so the close sound plays even if a player leaves without closing. */
    public void recheckOpen() {
        if (!this.remove) {
            this.openers.recheckOpeners(this.level, this.worldPosition, this.getBlockState());
        }
    }

    private void playSound(BlockState state, boolean open) {
        if (this.level == null || !(state.getBlock() instanceof FurnitureBlock block) || block.furniture().storage() == null) {
            return;
        }
        this.level.playSound(null, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5,
                MffSounds.storage(block.furniture().storage().sound(), open), SoundSource.BLOCKS, 0.5F,
                this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        return !this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER
                ? this.handlers[facing.ordinal()].cast()
                : super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
            handler.invalidate();
        }
    }
}
