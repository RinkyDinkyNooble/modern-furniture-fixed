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

public class BlackModerStairsBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public BlackModerStairsBlock() {
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
                    box(0.0, 8.0, 0.0, 16.0, 9.0, 7.0),
                    box(0.0, 16.0, 9.0, 16.0, 17.0, 16.0),
                    box(6.5, 0.0, 0.0, 9.5, 1.0, 6.0),
                    box(6.5, 1.0, 2.5, 9.5, 8.0, 3.5),
                    box(6.5, 7.0, 15.0, 9.5, 16.0, 16.0),
                    box(6.5, 9.75, 12.5, 9.5, 17.75, 13.5),
                    box(6.5, 6.0, 3.5, 9.5, 7.0, 16.0)
                );
                    case EAST -> Shapes.or(
                    box(9.0, 8.0, 0.0, 16.0, 9.0, 16.0),
                    box(0.0, 16.0, 0.0, 7.0, 17.0, 16.0),
                    box(10.0, 0.0, 6.5, 16.0, 1.0, 9.5),
                    box(12.5, 1.0, 6.5, 13.5, 8.0, 9.5),
                    box(0.0, 7.0, 6.5, 1.0, 16.0, 9.5),
                    box(2.5, 9.75, 6.5, 3.5, 17.75, 9.5),
                    box(0.0, 6.0, 6.5, 12.5, 7.0, 9.5)
                );
                    case WEST -> Shapes.or(
                    box(0.0, 8.0, 0.0, 7.0, 9.0, 16.0),
                    box(9.0, 16.0, 0.0, 16.0, 17.0, 16.0),
                    box(0.0, 0.0, 6.5, 6.0, 1.0, 9.5),
                    box(2.5, 1.0, 6.5, 3.5, 8.0, 9.5),
                    box(15.0, 7.0, 6.5, 16.0, 16.0, 9.5),
                    box(12.5, 9.75, 6.5, 13.5, 17.75, 9.5),
                    box(3.5, 6.0, 6.5, 16.0, 7.0, 9.5)
                );
                    default -> Shapes.or(
                    box(0.0, 8.0, 9.0, 16.0, 9.0, 16.0),
                    box(0.0, 16.0, 0.0, 16.0, 17.0, 7.0),
                    box(6.5, 0.0, 10.0, 9.5, 1.0, 16.0),
                    box(6.5, 1.0, 12.5, 9.5, 8.0, 13.5),
                    box(6.5, 7.0, 0.0, 9.5, 16.0, 1.0),
                    box(6.5, 9.75, 2.5, 9.5, 17.75, 3.5),
                    box(6.5, 6.0, 0.0, 9.5, 7.0, 12.5)
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
