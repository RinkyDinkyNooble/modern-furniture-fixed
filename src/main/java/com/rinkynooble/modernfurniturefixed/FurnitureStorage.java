package com.rinkynooble.modernfurniturefixed;

import com.cookiecraftmods.mdm.block.FurnitureBlock;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Furniture storage. Each piece has one inventory, kept by one block: an unsplit piece itself, or the first part
 * listed as opening it. Using any part that opens it opens that inventory.
 */
public final class FurnitureStorage {
    private FurnitureStorage() {
    }

    /** True for the block that keeps the piece's items. */
    public static boolean holdsItems(FurnitureBlock block, BlockState state) {
        FurnitureData.Storage storage = block.furniture().storage();
        if (storage == null) {
            return false;
        }
        SplitPart part = SplitFurniture.part(block, state);
        return part == SplitPart.WHOLE || storage.parts().isEmpty() || storage.parts().get(0) == part;
    }

    /** Where the piece's items are, for a block that opens them, or null if this block doesn't open storage or the part is missing. */
    @Nullable
    public static BlockPos holderPos(FurnitureBlock block, BlockGetter level, BlockPos pos, BlockState state) {
        FurnitureData.Storage storage = block.furniture().storage();
        if (storage == null) {
            return null;
        }
        SplitPart part = SplitFurniture.part(block, state);
        if (part == SplitPart.WHOLE || storage.parts().isEmpty()) {
            return pos;
        }
        if (!storage.parts().contains(part)) {
            return null;
        }
        return SplitFurniture.find(block, level, pos, state, storage.parts().get(0));
    }

    public static InteractionResult use(FurnitureBlock block, BlockState state, Level level, BlockPos pos, Player player) {
        BlockPos holder = holderPos(block, level, pos, state);
        if (holder == null) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide && level.getBlockEntity(holder) instanceof FurnitureStorageBlockEntity storage) {
            player.openMenu(storage);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    /** Drops the contents when the block that keeps them is replaced by another block. */
    public static void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof FurnitureStorageBlockEntity storage) {
            Containers.dropContents(level, pos, storage);
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }
}
