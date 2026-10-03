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

public class Set1CoffeTable1TallBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public Set1CoffeTable1TallBlock() {
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
                    box(0.0, 13.0, 1.0, 16.0, 16.0, 15.0),
                    box(8.0, 0.0, 0.25, 10.0, 1.0, 15.75),
                    box(8.0, 9.5, 1.0, 10.0, 10.0, 15.0),
                    box(8.0, 1.0, 0.25, 10.0, 15.0, 1.0),
                    box(8.0, 1.0, 15.0, 10.0, 15.0, 15.75)
                );
                    case EAST -> Shapes.or(
                    box(1.0, 13.0, 0.0, 15.0, 16.0, 16.0),
                    box(0.25, 0.0, 8.0, 15.75, 1.0, 10.0),
                    box(1.0, 9.5, 8.0, 15.0, 10.0, 10.0),
                    box(15.0, 1.0, 8.0, 15.75, 15.0, 10.0),
                    box(0.25, 1.0, 8.0, 1.0, 15.0, 10.0)
                );
                    case WEST -> Shapes.or(
                    box(1.0, 13.0, 0.0, 15.0, 16.0, 16.0),
                    box(0.25, 0.0, 6.0, 15.75, 1.0, 8.0),
                    box(1.0, 9.5, 6.0, 15.0, 10.0, 8.0),
                    box(0.25, 1.0, 6.0, 1.0, 15.0, 8.0),
                    box(15.0, 1.0, 6.0, 15.75, 15.0, 8.0)
                );
                    default -> Shapes.or(
                    box(0.0, 13.0, 1.0, 16.0, 16.0, 15.0),
                    box(6.0, 0.0, 0.25, 8.0, 1.0, 15.75),
                    box(6.0, 9.5, 1.0, 8.0, 10.0, 15.0),
                    box(6.0, 1.0, 15.0, 8.0, 15.0, 15.75),
                    box(6.0, 1.0, 0.25, 8.0, 15.0, 1.0)
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
