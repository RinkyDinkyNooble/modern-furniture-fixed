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

public class Set1CoffeTable1ShortBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public Set1CoffeTable1ShortBlock() {
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
                    box(1.0, 5.0, 1.0, 16.0, 8.0, 15.0),
                    box(5.5, 0.0, 0.25, 7.5, 1.0, 15.75),
                    box(5.5, 4.5, 1.0, 7.5, 5.0, 15.0),
                    box(5.5, 1.0, 15.0, 7.5, 7.0, 15.75),
                    box(5.5, 1.0, 0.25, 7.5, 7.0, 1.0)
                );
                    case EAST -> Shapes.or(
                    box(1.0, 5.0, 1.0, 15.0, 8.0, 16.0),
                    box(0.25, 0.0, 5.5, 15.75, 1.0, 7.5),
                    box(1.0, 4.5, 5.5, 15.0, 5.0, 7.5),
                    box(0.25, 1.0, 5.5, 1.0, 7.0, 7.5),
                    box(15.0, 1.0, 5.5, 15.75, 7.0, 7.5)
                );
                    case WEST -> Shapes.or(
                    box(1.0, 5.0, 0.0, 15.0, 8.0, 15.0),
                    box(0.25, 0.0, 8.5, 15.75, 1.0, 10.5),
                    box(1.0, 4.5, 8.5, 15.0, 5.0, 10.5),
                    box(15.0, 1.0, 8.5, 15.75, 7.0, 10.5),
                    box(0.25, 1.0, 8.5, 1.0, 7.0, 10.5)
                );
                    default -> Shapes.or(
                    box(0.0, 5.0, 1.0, 15.0, 8.0, 15.0),
                    box(8.5, 0.0, 0.25, 10.5, 1.0, 15.75),
                    box(8.5, 4.5, 1.0, 10.5, 5.0, 15.0),
                    box(8.5, 1.0, 0.25, 10.5, 7.0, 1.0),
                    box(8.5, 1.0, 15.0, 10.5, 7.0, 15.75)
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
