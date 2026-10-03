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

public class SmallChandelierBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public SmallChandelierBlock() {
        super(
            Properties.of()
                .sound(SoundType.GLASS)
                .strength(1.0F, 10.0F)
                .lightLevel(blockstate -> 15)
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
                    box(0.0, 14.0, 7.0, 16.0, 16.0, 9.0),
                    box(13.7, 10.0, 7.5, 14.7, 14.0, 8.5),
                    box(1.45, 10.0, 7.5, 2.45, 14.0, 8.5),
                    box(5.45, 10.0, 7.5, 6.45, 14.0, 8.5),
                    box(9.7, 10.0, 7.5, 10.7, 14.0, 8.5),
                    box(13.2, 8.0, 7.0, 15.2, 10.0, 9.0),
                    box(0.95, 8.0, 7.0, 2.95, 10.0, 9.0),
                    box(4.95, 8.0, 7.0, 6.95, 10.0, 9.0),
                    box(9.2, 8.0, 7.0, 11.2, 10.0, 9.0)
                );
                    case EAST -> Shapes.or(
                    box(7.0, 14.0, 0.0, 9.0, 16.0, 16.0),
                    box(7.5, 10.0, 13.7, 8.5, 14.0, 14.7),
                    box(7.5, 10.0, 1.45, 8.5, 14.0, 2.45),
                    box(7.5, 10.0, 5.45, 8.5, 14.0, 6.45),
                    box(7.5, 10.0, 9.7, 8.5, 14.0, 10.7),
                    box(7.0, 8.0, 13.2, 9.0, 10.0, 15.2),
                    box(7.0, 8.0, 0.95, 9.0, 10.0, 2.95),
                    box(7.0, 8.0, 4.95, 9.0, 10.0, 6.95),
                    box(7.0, 8.0, 9.2, 9.0, 10.0, 11.2)
                );
                    case WEST -> Shapes.or(
                    box(7.0, 14.0, 0.0, 9.0, 16.0, 16.0),
                    box(7.5, 10.0, 1.3, 8.5, 14.0, 2.3),
                    box(7.5, 10.0, 13.55, 8.5, 14.0, 14.55),
                    box(7.5, 10.0, 9.55, 8.5, 14.0, 10.55),
                    box(7.5, 10.0, 5.3, 8.5, 14.0, 6.3),
                    box(7.0, 8.0, 0.8, 9.0, 10.0, 2.8),
                    box(7.0, 8.0, 13.05, 9.0, 10.0, 15.05),
                    box(7.0, 8.0, 9.05, 9.0, 10.0, 11.05),
                    box(7.0, 8.0, 4.8, 9.0, 10.0, 6.8)
                );
                    default -> Shapes.or(
                    box(0.0, 14.0, 7.0, 16.0, 16.0, 9.0),
                    box(1.3, 10.0, 7.5, 2.3, 14.0, 8.5),
                    box(13.55, 10.0, 7.5, 14.55, 14.0, 8.5),
                    box(9.55, 10.0, 7.5, 10.55, 14.0, 8.5),
                    box(5.3, 10.0, 7.5, 6.3, 14.0, 8.5),
                    box(0.8, 8.0, 7.0, 2.8, 10.0, 9.0),
                    box(13.05, 8.0, 7.0, 15.05, 10.0, 9.0),
                    box(9.05, 8.0, 7.0, 11.05, 10.0, 9.0),
                    box(4.8, 8.0, 7.0, 6.8, 10.0, 9.0)
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
