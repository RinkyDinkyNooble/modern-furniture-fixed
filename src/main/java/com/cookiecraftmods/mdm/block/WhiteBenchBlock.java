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

public class WhiteBenchBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public WhiteBenchBlock() {
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
                    box(26.0, 0.0, 0.0, 29.0, 15.0, 16.0),
                    box(-13.0, 0.0, 0.0, -10.0, 15.0, 16.0),
                    box(-10.0, 11.0, 1.0, 26.0, 13.0, 5.0),
                    box(-10.0, 11.0, 11.0, 26.0, 13.0, 15.0),
                    box(-10.0, 11.0, 6.0, 26.0, 13.0, 10.0)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 0.0, 26.0, 16.0, 15.0, 29.0),
                    box(0.0, 0.0, -13.0, 16.0, 15.0, -10.0),
                    box(11.0, 11.0, -10.0, 15.0, 13.0, 26.0),
                    box(1.0, 11.0, -10.0, 5.0, 13.0, 26.0),
                    box(6.0, 11.0, -10.0, 10.0, 13.0, 26.0)
                );
                    case WEST -> Shapes.or(
                    box(0.0, 0.0, -13.0, 16.0, 15.0, -10.0),
                    box(0.0, 0.0, 26.0, 16.0, 15.0, 29.0),
                    box(1.0, 11.0, -10.0, 5.0, 13.0, 26.0),
                    box(11.0, 11.0, -10.0, 15.0, 13.0, 26.0),
                    box(6.0, 11.0, -10.0, 10.0, 13.0, 26.0)
                );
                    default -> Shapes.or(
                    box(-13.0, 0.0, 0.0, -10.0, 15.0, 16.0),
                    box(26.0, 0.0, 0.0, 29.0, 15.0, 16.0),
                    box(-10.0, 11.0, 11.0, 26.0, 13.0, 15.0),
                    box(-10.0, 11.0, 1.0, 26.0, 13.0, 5.0),
                    box(-10.0, 11.0, 6.0, 26.0, 13.0, 10.0)
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
