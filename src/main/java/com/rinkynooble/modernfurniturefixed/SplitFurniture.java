package com.rinkynooble.modernfurniturefixed;

import com.cookiecraftmods.mdm.block.FurnitureBlock;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

/**
 * Placing, breaking and finding the parts of furniture that is split into one block per space it fills.
 * Each part works out its model and hitbox from its own block state, so a part whose neighbours are missing
 * (a building that lost a block, a part broken on its own) stays as it is.
 */
public final class SplitFurniture {
    private static final int PARTICLES_DESTROY_BLOCK = 2001;

    private SplitFurniture() {
    }

    public static SplitPart part(FurnitureBlock block, BlockState state) {
        EnumProperty<SplitPart> property = block.furniture().partProperty();
        return property != null ? state.getValue(property) : SplitPart.WHOLE;
    }

    /**
     * The part to place where the player clicked: the main part, or, if the piece doesn't fit there, a part
     * below it in the same column, which raises the piece so its lowest part takes the clicked space (a tall
     * mirror placed on the floor stands on the floor).
     */
    @Nullable
    public static BlockState placedPart(FurnitureBlock block, BlockPlaceContext context, @Nullable BlockState main) {
        EnumProperty<SplitPart> property = block.furniture().partProperty();
        if (main == null || property == null || partsFit(block, main, context.getLevel(), context.getClickedPos())) {
            return main;
        }
        for (int depth = 1; depth <= 2; depth++) {
            for (SplitPart part : property.getPossibleValues()) {
                BlockState candidate = main.setValue(property, part);
                if (part != SplitPart.WHOLE && block.offset(candidate).equals(new Vec3i(0, -depth, 0))
                        && partsFit(block, candidate, context.getLevel(), context.getClickedPos())) {
                    return candidate;
                }
            }
        }
        return main;
    }

    /** True if the spaces the other parts need are free, for a part about to be placed at {@code pos}. */
    public static boolean partsFit(FurnitureBlock block, BlockState state, LevelReader level, BlockPos pos) {
        SplitPart placed = part(block, state);
        if (placed == SplitPart.WHOLE) {
            return true;
        }
        BlockPos main = pos.subtract(block.offset(state));
        for (SplitPart part : block.furniture().partProperty().getPossibleValues()) {
            if (part == SplitPart.WHOLE || part == placed) {
                continue;
            }
            BlockState partState = state.setValue(block.furniture().partProperty(), part);
            BlockPos partPos = main.offset(block.offset(partState));
            BlockState there = level.getBlockState(partPos);
            if (level.isOutsideBuildHeight(partPos) || !level.getWorldBorder().isWithinBounds(partPos)
                    || !(there.canBeReplaced() || samePiece(there, partState, block))) {
                return false;
            }
        }
        return true;
    }

    /** Places the other parts around a part that a player just placed. */
    public static void placeParts(FurnitureBlock block, Level level, BlockPos pos, BlockState state) {
        SplitPart placed = part(block, state);
        if (level.isClientSide || placed == SplitPart.WHOLE) {
            return;
        }
        BlockPos main = pos.subtract(block.offset(state));
        for (SplitPart part : block.furniture().partProperty().getPossibleValues()) {
            if (part != SplitPart.WHOLE && part != placed) {
                BlockState partState = state.setValue(block.furniture().partProperty(), part);
                level.setBlock(main.offset(block.offset(partState)), partState, Block.UPDATE_ALL);
            }
        }
        level.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
    }

    /**
     * When a player breaks a part and the server config says to, breaks the rest of the piece too.
     * The main part is broken normally, so it drops the item once; the others are removed, and the part
     * that holds the storage drops its contents as it goes.
     */
    public static void breakPiece(FurnitureBlock block, Level level, BlockPos pos, BlockState state, Player player) {
        SplitPart broken = part(block, state);
        if (level.isClientSide || broken == SplitPart.WHOLE || !MffConfig.breakWholePiece()) {
            return;
        }
        BlockPos main = pos.subtract(block.offset(state));
        for (SplitPart part : block.furniture().partProperty().getPossibleValues()) {
            if (part == SplitPart.WHOLE || part == broken) {
                continue;
            }
            BlockState expected = state.setValue(block.furniture().partProperty(), part);
            BlockPos partPos = main.offset(block.offset(expected));
            BlockState partState = level.getBlockState(partPos);
            if (!samePiece(partState, expected, block)) {
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

    /** Where another part of the same piece is, or null if it isn't there. */
    @Nullable
    public static BlockPos find(FurnitureBlock block, BlockGetter level, BlockPos pos, BlockState state, SplitPart part) {
        if (part(block, state) == part) {
            return pos;
        }
        BlockState expected = state.setValue(block.furniture().partProperty(), part);
        BlockPos found = pos.subtract(block.offset(state)).offset(block.offset(expected));
        return samePiece(level.getBlockState(found), expected, block) ? found : null;
    }

    /** True for the part that drops the furniture's item: the main part, or an unsplit piece. */
    public static boolean dropsItem(FurnitureBlock block, BlockState state) {
        SplitPart part = part(block, state);
        return part == SplitPart.MAIN || part == SplitPart.WHOLE;
    }

    /** Swaps left and right after a piece is mirrored, when the piece has both sides. */
    public static BlockState mirrorPart(FurnitureBlock block, BlockState mirrored) {
        EnumProperty<SplitPart> property = block.furniture().partProperty();
        if (property == null) {
            return mirrored;
        }
        SplitPart swapped = mirrored.getValue(property).mirrored();
        return property.getPossibleValues().contains(swapped) ? mirrored.setValue(property, swapped) : mirrored;
    }

    /** True if a block state is the expected part of the same piece: same block, same part, same facing. */
    private static boolean samePiece(BlockState candidate, BlockState expected, FurnitureBlock block) {
        if (!candidate.is(block) || part(block, candidate) != part(block, expected)) {
            return false;
        }
        for (Property<?> property : candidate.getProperties()) {
            if (property != BlockStateProperties.WATERLOGGED && !candidate.getValue(property).equals(expected.getValue(property))) {
                return false;
            }
        }
        return true;
    }
}
