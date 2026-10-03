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

public class SoapBarBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public SoapBarBlock() {
        super(
            Properties.of()
                .sound(SoundType.HONEY_BLOCK)
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
                    box(5.0, 0.0, 11.0, 11.0, 0.25, 15.0),
                    box(5.0, 0.25, 14.5, 11.0, 0.5, 15.0),
                    box(5.0, 0.25, 11.0, 11.0, 0.5, 11.5),
                    box(5.0, 0.25, 11.5, 5.5, 0.5, 14.5),
                    box(10.5, 0.25, 11.5, 11.0, 0.5, 14.5),
                    box(6.0, 0.25, 12.0, 10.0, 1.0, 14.0)
                );
                    case EAST -> Shapes.or(
                    box(1.0, 0.0, 5.0, 5.0, 0.25, 11.0),
                    box(1.0, 0.25, 5.0, 1.5, 0.5, 11.0),
                    box(4.5, 0.25, 5.0, 5.0, 0.5, 11.0),
                    box(1.5, 0.25, 5.0, 4.5, 0.5, 5.5),
                    box(1.5, 0.25, 10.5, 4.5, 0.5, 11.0),
                    box(2.0, 0.25, 6.0, 4.0, 1.0, 10.0)
                );
                    case WEST -> Shapes.or(
                    box(11.0, 0.0, 5.0, 15.0, 0.25, 11.0),
                    box(14.5, 0.25, 5.0, 15.0, 0.5, 11.0),
                    box(11.0, 0.25, 5.0, 11.5, 0.5, 11.0),
                    box(11.5, 0.25, 10.5, 14.5, 0.5, 11.0),
                    box(11.5, 0.25, 5.0, 14.5, 0.5, 5.5),
                    box(12.0, 0.25, 6.0, 14.0, 1.0, 10.0)
                );
                    default -> Shapes.or(
                    box(5.0, 0.0, 1.0, 11.0, 0.25, 5.0),
                    box(5.0, 0.25, 1.0, 11.0, 0.5, 1.5),
                    box(5.0, 0.25, 4.5, 11.0, 0.5, 5.0),
                    box(10.5, 0.25, 1.5, 11.0, 0.5, 4.5),
                    box(5.0, 0.25, 1.5, 5.5, 0.5, 4.5),
                    box(6.0, 0.25, 2.0, 10.0, 1.0, 4.0)
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
