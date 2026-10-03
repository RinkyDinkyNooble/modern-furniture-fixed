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

public class KitchenSet3BarChairBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public KitchenSet3BarChairBlock() {
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
                    box(4.0, 0.0, 4.0, 12.0, 1.0, 12.0),
                    box(5.0, 12.0, 5.0, 11.0, 13.0, 11.0),
                    box(3.0, 13.0, 3.0, 13.0, 14.0, 13.0),
                    box(7.25, 1.0, 7.25, 8.75, 12.0, 8.75),
                    box(3.25, 14.0, 3.25, 12.75, 15.0, 12.75),
                    box(3.5, 15.0, 3.5, 12.5, 15.25, 12.5)
                );
                    case EAST -> Shapes.or(
                    box(4.0, 0.0, 4.0, 12.0, 1.0, 12.0),
                    box(5.0, 12.0, 5.0, 11.0, 13.0, 11.0),
                    box(3.0, 13.0, 3.0, 13.0, 14.0, 13.0),
                    box(7.25, 1.0, 7.25, 8.75, 12.0, 8.75),
                    box(3.25, 14.0, 3.25, 12.75, 15.0, 12.75),
                    box(3.5, 15.0, 3.5, 12.5, 15.25, 12.5)
                );
                    case WEST -> Shapes.or(
                    box(4.0, 0.0, 4.0, 12.0, 1.0, 12.0),
                    box(5.0, 12.0, 5.0, 11.0, 13.0, 11.0),
                    box(3.0, 13.0, 3.0, 13.0, 14.0, 13.0),
                    box(7.25, 1.0, 7.25, 8.75, 12.0, 8.75),
                    box(3.25, 14.0, 3.25, 12.75, 15.0, 12.75),
                    box(3.5, 15.0, 3.5, 12.5, 15.25, 12.5)
                );
                    default -> Shapes.or(
                    box(4.0, 0.0, 4.0, 12.0, 1.0, 12.0),
                    box(5.0, 12.0, 5.0, 11.0, 13.0, 11.0),
                    box(3.0, 13.0, 3.0, 13.0, 14.0, 13.0),
                    box(7.25, 1.0, 7.25, 8.75, 12.0, 8.75),
                    box(3.25, 14.0, 3.25, 12.75, 15.0, 12.75),
                    box(3.5, 15.0, 3.5, 12.5, 15.25, 12.5)
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
