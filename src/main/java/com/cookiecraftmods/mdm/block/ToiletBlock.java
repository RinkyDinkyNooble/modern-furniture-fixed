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

public class ToiletBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public ToiletBlock() {
        super(
            Properties.of()
                .sound(SoundType.DEEPSLATE_TILES)
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
                    box(6.0, 3.0, 14.0, 10.0, 5.0, 16.0),
                    box(8.0, 22.0, 15.95, 10.0, 24.0, 16.0),
                    box(5.0, 21.0, 15.975, 11.0, 25.0, 16.0),
                    box(6.0, 22.0, 15.95, 7.0, 24.0, 16.0),
                    box(5.0, 5.0, 7.0, 11.0, 6.0, 16.0),
                    box(3.0, 6.0, 3.0, 13.0, 9.0, 16.0),
                    box(3.0, 9.25, 3.0, 13.0, 10.25, 15.0),
                    box(4.0, 9.0, 4.0, 12.0, 9.25, 14.5)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 3.0, 6.0, 2.0, 5.0, 10.0),
                    box(0.0, 22.0, 8.0, 0.05, 24.0, 10.0),
                    box(0.0, 21.0, 5.0, 0.025, 25.0, 11.0),
                    box(0.0, 22.0, 6.0, 0.05, 24.0, 7.0),
                    box(0.0, 5.0, 5.0, 9.0, 6.0, 11.0),
                    box(0.0, 6.0, 3.0, 13.0, 9.0, 13.0),
                    box(1.0, 9.25, 3.0, 13.0, 10.25, 13.0),
                    box(1.5, 9.0, 4.0, 12.0, 9.25, 12.0)
                );
                    case WEST -> Shapes.or(
                    box(14.0, 3.0, 6.0, 16.0, 5.0, 10.0),
                    box(15.95, 22.0, 6.0, 16.0, 24.0, 8.0),
                    box(15.975, 21.0, 5.0, 16.0, 25.0, 11.0),
                    box(15.95, 22.0, 9.0, 16.0, 24.0, 10.0),
                    box(7.0, 5.0, 5.0, 16.0, 6.0, 11.0),
                    box(3.0, 6.0, 3.0, 16.0, 9.0, 13.0),
                    box(3.0, 9.25, 3.0, 15.0, 10.25, 13.0),
                    box(4.0, 9.0, 4.0, 14.5, 9.25, 12.0)
                );
                    default -> Shapes.or(
                    box(6.0, 3.0, 0.0, 10.0, 5.0, 2.0),
                    box(6.0, 22.0, 0.0, 8.0, 24.0, 0.05),
                    box(5.0, 21.0, 0.0, 11.0, 25.0, 0.025),
                    box(9.0, 22.0, 0.0, 10.0, 24.0, 0.05),
                    box(5.0, 5.0, 0.0, 11.0, 6.0, 9.0),
                    box(3.0, 6.0, 0.0, 13.0, 9.0, 13.0),
                    box(3.0, 9.25, 1.0, 13.0, 10.25, 13.0),
                    box(4.0, 9.0, 1.5, 12.0, 9.25, 12.0)
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
