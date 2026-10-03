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

public class RainShowerBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public RainShowerBlock() {
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
                    box(7.25, 12.25, 8.0, 8.75, 13.75, 16.0),
                    box(7.25, 10.75, 8.0, 8.75, 12.25, 9.0),
                    box(6.25, 9.75, 6.75, 9.75, 10.75, 10.25),
                    box(6.75, 9.5, 7.25, 9.25, 9.75, 9.75),
                    box(4.0, 3.0, 14.0, 6.0, 5.0, 16.0),
                    box(10.0, 3.0, 14.0, 12.0, 5.0, 16.0)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 12.25, 7.25, 8.0, 13.75, 8.75),
                    box(7.0, 10.75, 7.25, 8.0, 12.25, 8.75),
                    box(5.75, 9.75, 6.25, 9.25, 10.75, 9.75),
                    box(6.25, 9.5, 6.75, 8.75, 9.75, 9.25),
                    box(0.0, 3.0, 4.0, 2.0, 5.0, 6.0),
                    box(0.0, 3.0, 10.0, 2.0, 5.0, 12.0)
                );
                    case WEST -> Shapes.or(
                    box(8.0, 12.25, 7.25, 16.0, 13.75, 8.75),
                    box(8.0, 10.75, 7.25, 9.0, 12.25, 8.75),
                    box(6.75, 9.75, 6.25, 10.25, 10.75, 9.75),
                    box(7.25, 9.5, 6.75, 9.75, 9.75, 9.25),
                    box(14.0, 3.0, 10.0, 16.0, 5.0, 12.0),
                    box(14.0, 3.0, 4.0, 16.0, 5.0, 6.0)
                );
                    default -> Shapes.or(
                    box(7.25, 12.25, 0.0, 8.75, 13.75, 8.0),
                    box(7.25, 10.75, 7.0, 8.75, 12.25, 8.0),
                    box(6.25, 9.75, 5.75, 9.75, 10.75, 9.25),
                    box(6.75, 9.5, 6.25, 9.25, 9.75, 8.75),
                    box(10.0, 3.0, 0.0, 12.0, 5.0, 2.0),
                    box(4.0, 3.0, 0.0, 6.0, 5.0, 2.0)
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
