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

public class ToiletPaperRollBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public ToiletPaperRollBlock() {
        super(
            Properties.of()
                .sound(SoundType.CROP)
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
                    box(4.5, 13.0, 13.0, 5.5, 14.0, 16.0),
                    box(10.5, 13.0, 13.0, 11.5, 14.0, 16.0),
                    box(6.0, 12.0, 12.0, 10.0, 15.0, 15.0),
                    box(6.0, 10.0, 11.975, 10.0, 15.0, 12.0),
                    box(5.5, 13.0, 13.0, 10.5, 14.0, 14.0)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 13.0, 4.5, 3.0, 14.0, 5.5),
                    box(0.0, 13.0, 10.5, 3.0, 14.0, 11.5),
                    box(1.0, 12.0, 6.0, 4.0, 15.0, 10.0),
                    box(4.0, 10.0, 6.0, 4.025, 15.0, 10.0),
                    box(2.0, 13.0, 5.5, 3.0, 14.0, 10.5)
                );
                    case WEST -> Shapes.or(
                    box(13.0, 13.0, 10.5, 16.0, 14.0, 11.5),
                    box(13.0, 13.0, 4.5, 16.0, 14.0, 5.5),
                    box(12.0, 12.0, 6.0, 15.0, 15.0, 10.0),
                    box(11.975, 10.0, 6.0, 12.0, 15.0, 10.0),
                    box(13.0, 13.0, 5.5, 14.0, 14.0, 10.5)
                );
                    default -> Shapes.or(
                    box(10.5, 13.0, 0.0, 11.5, 14.0, 3.0),
                    box(4.5, 13.0, 0.0, 5.5, 14.0, 3.0),
                    box(6.0, 12.0, 1.0, 10.0, 15.0, 4.0),
                    box(6.0, 10.0, 4.0, 10.0, 15.0, 4.025),
                    box(5.5, 13.0, 2.0, 10.5, 14.0, 3.0)
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
