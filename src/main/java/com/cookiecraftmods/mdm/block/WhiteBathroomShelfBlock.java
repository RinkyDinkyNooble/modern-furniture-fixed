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

public class WhiteBathroomShelfBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public WhiteBathroomShelfBlock() {
        super(
            Properties.of()
                .sound(SoundType.WOOD)
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
                    box(0.0, 0.0, 9.0, 16.0, 16.0, 16.0),
                    box(1.0, 1.0, 8.5, 7.0, 15.0, 9.0),
                    box(9.0, 1.0, 8.5, 15.0, 15.0, 9.0),
                    box(6.0, 6.0, 8.25, 6.5, 13.0, 8.5),
                    box(9.5, 6.0, 8.25, 10.0, 13.0, 8.5)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 0.0, 0.0, 7.0, 16.0, 16.0),
                    box(7.0, 1.0, 1.0, 7.5, 15.0, 7.0),
                    box(7.0, 1.0, 9.0, 7.5, 15.0, 15.0),
                    box(7.5, 6.0, 6.0, 7.75, 13.0, 6.5),
                    box(7.5, 6.0, 9.5, 7.75, 13.0, 10.0)
                );
                    case WEST -> Shapes.or(
                    box(9.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                    box(8.5, 1.0, 9.0, 9.0, 15.0, 15.0),
                    box(8.5, 1.0, 1.0, 9.0, 15.0, 7.0),
                    box(8.25, 6.0, 9.5, 8.5, 13.0, 10.0),
                    box(8.25, 6.0, 6.0, 8.5, 13.0, 6.5)
                );
                    default -> Shapes.or(
                    box(0.0, 0.0, 0.0, 16.0, 16.0, 7.0),
                    box(9.0, 1.0, 7.0, 15.0, 15.0, 7.5),
                    box(1.0, 1.0, 7.0, 7.0, 15.0, 7.5),
                    box(9.5, 6.0, 7.5, 10.0, 13.0, 7.75),
                    box(6.0, 6.0, 7.5, 6.5, 13.0, 7.75)
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
