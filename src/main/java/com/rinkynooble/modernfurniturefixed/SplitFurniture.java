package com.rinkynooble.modernfurniturefixed;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/**
 * Placing, breaking and using furniture that is split into one block per space it fills.
 * Each part works out its model and hitbox from its own block state, so a part whose neighbours are missing
 * (a building that lost a block, a part broken on its own) stays as it is.
 */
public final class SplitFurniture {
    private static final int PARTICLES_DESTROY_BLOCK = 2001;

    private SplitFurniture() {
    }

    /** The main part's state, or null if a space the other parts need isn't free. */
    @Nullable
    public static BlockState placementState(SplitLayout layout, BlockPlaceContext context, @Nullable BlockState state) {
        if (state == null) {
            return null;
        }
        Level level = context.getLevel();
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos main = context.getClickedPos();
        for (SplitPart part : layout.parts()) {
            if (part == SplitPart.MAIN) {
                continue;
            }
            BlockPos pos = layout.partPos(main, facing, part);
            if (level.isOutsideBuildHeight(pos) || !level.getWorldBorder().isWithinBounds(pos)
                    || !level.getBlockState(pos).canBeReplaced(context)) {
                return null;
            }
        }
        return state.setValue(layout.property(), SplitPart.MAIN);
    }

    /** Places the other parts next to a main part that was just placed. */
    public static void placeParts(SplitLayout layout, Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide || state.getValue(layout.property()) != SplitPart.MAIN) {
            return;
        }
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        for (SplitPart part : layout.parts()) {
            if (part != SplitPart.MAIN) {
                level.setBlock(layout.partPos(pos, facing, part), state.setValue(layout.property(), part), Block.UPDATE_ALL);
            }
        }
        level.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
    }

    /**
     * When a player breaks a part and the server config says to, breaks the rest of the piece too.
     * The main part is broken normally, so it drops the item and its contents once; the others are removed.
     */
    public static void breakPiece(SplitLayout layout, Level level, BlockPos pos, BlockState state, Player player) {
        EnumProperty<SplitPart> property = layout.property();
        SplitPart broken = state.getValue(property);
        if (level.isClientSide || broken == SplitPart.WHOLE || !MffConfig.breakWholePiece()) {
            return;
        }
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos main = layout.mainPos(pos, facing, broken);
        for (SplitPart part : layout.parts()) {
            if (part == broken) {
                continue;
            }
            BlockPos partPos = layout.partPos(main, facing, part);
            BlockState partState = level.getBlockState(partPos);
            if (!isPart(partState, state, layout, part)) {
                continue;
            }
            if (part == SplitPart.MAIN) {
                level.destroyBlock(partPos, !player.isCreative(), player);
            } else {
                level.setBlock(partPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
                level.levelEvent(null, PARTICLES_DESTROY_BLOCK, partPos, Block.getId(partState));
            }
        }
    }

    /** Where the part that holds the piece's item and contents is, or null if it's missing. */
    @Nullable
    public static BlockPos holderPos(SplitLayout layout, BlockGetter level, BlockPos pos, BlockState state) {
        SplitPart part = state.getValue(layout.property());
        if (holdsItems(layout, state)) {
            return pos;
        }
        BlockPos main = layout.mainPos(pos, state.getValue(BlockStateProperties.HORIZONTAL_FACING), part);
        return isPart(level.getBlockState(main), state, layout, SplitPart.MAIN) ? main : null;
    }

    /** True for the part that drops the item and keeps the contents: the main part, or an unsplit piece. */
    public static boolean holdsItems(SplitLayout layout, BlockState state) {
        SplitPart part = state.getValue(layout.property());
        return part == SplitPart.MAIN || part == SplitPart.WHOLE;
    }

    /** Swaps left and right after the piece is mirrored, when the piece has both sides. */
    public static BlockState mirror(SplitLayout layout, BlockState mirrored) {
        SplitPart swapped = mirrored.getValue(layout.property()).mirrored();
        return layout.property().getPossibleValues().contains(swapped) ? mirrored.setValue(layout.property(), swapped) : mirrored;
    }

    private static boolean isPart(BlockState candidate, BlockState piece, SplitLayout layout, SplitPart part) {
        return candidate.is(piece.getBlock())
                && candidate.getValue(BlockStateProperties.HORIZONTAL_FACING) == piece.getValue(BlockStateProperties.HORIZONTAL_FACING)
                && candidate.getValue(layout.property()) == part;
    }
}
