package com.cookiecraftmods.mdm.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlackModernFridge1Block extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public BlackModernFridge1Block() {
        super(Properties.of().strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(
            state -> {
                return switch ((Direction)state.getValue(FACING)) {
                    case NORTH -> Shapes.or(
                    box(0.0, 0.0, 3.0, 16.0, 32.0, 15.0),
                    box(1.0, 1.0, 2.5, 6.5, 31.0, 3.0),
                    box(7.0, 1.0, 2.5, 15.0, 31.0, 3.0),
                    box(0.975, 0.975, 2.975, 6.525, 31.025, 3.0),
                    box(6.975, 0.975, 2.975, 15.025, 31.025, 3.0),
                    box(5.75, 6.0, 2.25, 6.0, 23.0, 2.5),
                    box(5.5, 6.0, 2.0, 6.0, 23.0, 2.25),
                    box(7.5, 6.0, 2.25, 7.75, 23.0, 2.5),
                    box(7.5, 6.0, 2.0, 8.0, 23.0, 2.25),
                    box(1.0, 1.0, 15.0, 15.0, 31.0, 15.5),
                    box(13.25, 0.15, 15.0, 15.75, 0.825, 15.025)
                );
                    case EAST -> Shapes.or(
                    box(1.0, 0.0, 0.0, 13.0, 32.0, 16.0),
                    box(13.0, 1.0, 1.0, 13.5, 31.0, 6.5),
                    box(13.0, 1.0, 7.0, 13.5, 31.0, 15.0),
                    box(13.0, 0.975, 0.975, 13.025, 31.025, 6.525),
                    box(13.0, 0.975, 6.975, 13.025, 31.025, 15.025),
                    box(13.5, 6.0, 5.75, 13.75, 23.0, 6.0),
                    box(13.75, 6.0, 5.5, 14.0, 23.0, 6.0),
                    box(13.5, 6.0, 7.5, 13.75, 23.0, 7.75),
                    box(13.75, 6.0, 7.5, 14.0, 23.0, 8.0),
                    box(0.5, 1.0, 1.0, 1.0, 31.0, 15.0),
                    box(0.975, 0.15, 13.25, 1.0, 0.825, 15.75)
                );
                    case WEST -> Shapes.or(
                    box(3.0, 0.0, 0.0, 15.0, 32.0, 16.0),
                    box(2.5, 1.0, 9.5, 3.0, 31.0, 15.0),
                    box(2.5, 1.0, 1.0, 3.0, 31.0, 9.0),
                    box(2.975, 0.975, 9.475, 3.0, 31.025, 15.025),
                    box(2.975, 0.975, 0.975, 3.0, 31.025, 9.025),
                    box(2.25, 6.0, 10.0, 2.5, 23.0, 10.25),
                    box(2.0, 6.0, 10.0, 2.25, 23.0, 10.5),
                    box(2.25, 6.0, 8.25, 2.5, 23.0, 8.5),
                    box(2.0, 6.0, 8.0, 2.25, 23.0, 8.5),
                    box(15.0, 1.0, 1.0, 15.5, 31.0, 15.0),
                    box(15.0, 0.15, 0.25, 15.025, 0.825, 2.75)
                );
                    default -> Shapes.or(
                    box(0.0, 0.0, 1.0, 16.0, 32.0, 13.0),
                    box(9.5, 1.0, 13.0, 15.0, 31.0, 13.5),
                    box(1.0, 1.0, 13.0, 9.0, 31.0, 13.5),
                    box(9.475, 0.975, 13.0, 15.025, 31.025, 13.025),
                    box(0.975, 0.975, 13.0, 9.025, 31.025, 13.025),
                    box(10.0, 6.0, 13.5, 10.25, 23.0, 13.75),
                    box(10.0, 6.0, 13.75, 10.5, 23.0, 14.0),
                    box(8.25, 6.0, 13.5, 8.5, 23.0, 13.75),
                    box(8.0, 6.0, 13.75, 8.5, 23.0, 14.0),
                    box(1.0, 1.0, 0.5, 15.0, 31.0, 1.0),
                    box(0.25, 0.15, 0.975, 2.75, 0.825, 1.0)
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
