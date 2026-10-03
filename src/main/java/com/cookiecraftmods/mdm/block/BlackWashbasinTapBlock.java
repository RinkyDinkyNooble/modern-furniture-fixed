package com.cookiecraftmods.mdm.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlackWashbasinTapBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public BlackWashbasinTapBlock() {
        super(
            Properties.of()
                .sound(SoundType.METAL)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(
            state -> {
                return switch ((Direction)state.getValue(FACING)) {
                    case NORTH -> Shapes.or(
                    box(7.5, 2.0, 12.0, 8.5, 2.5, 16.0),
                    box(6.0, 0.0, 14.5, 7.0, 0.75, 15.5),
                    box(9.0, 0.0, 14.5, 10.0, 0.75, 15.5),
                    box(7.75, 1.75, 12.25, 8.25, 2.0, 12.75)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 2.0, 7.5, 4.0, 2.5, 8.5),
                    box(0.5, 0.0, 6.0, 1.5, 0.75, 7.0),
                    box(0.5, 0.0, 9.0, 1.5, 0.75, 10.0),
                    box(3.25, 1.75, 7.75, 3.75, 2.0, 8.25)
                );
                    case WEST -> Shapes.or(
                    box(12.0, 2.0, 7.5, 16.0, 2.5, 8.5),
                    box(14.5, 0.0, 9.0, 15.5, 0.75, 10.0),
                    box(14.5, 0.0, 6.0, 15.5, 0.75, 7.0),
                    box(12.25, 1.75, 7.75, 12.75, 2.0, 8.25)
                );
                    default -> Shapes.or(
                    box(7.5, 2.0, 0.0, 8.5, 2.5, 4.0),
                    box(9.0, 0.0, 0.5, 10.0, 0.75, 1.5),
                    box(6.0, 0.0, 0.5, 7.0, 0.75, 1.5),
                    box(7.75, 1.75, 3.25, 8.25, 2.0, 3.75)
                );
                };
            }
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.shapes.get(state);
    }
}
