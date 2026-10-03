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

public class ChlothesbinBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public ChlothesbinBlock() {
        super(
            Properties.of()
                .sound(SoundType.SCAFFOLDING)
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
                    box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), box(4.75, 10.0, 4.75, 11.25, 11.0, 11.25), box(5.25, 8.0, 4.85, 6.25, 10.0, 4.9)
                );
                    case EAST -> Shapes.or(
                    box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), box(4.75, 10.0, 4.75, 11.25, 11.0, 11.25), box(11.1, 8.0, 5.25, 11.15, 10.0, 6.25)
                );
                    case WEST -> Shapes.or(
                    box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), box(4.75, 10.0, 4.75, 11.25, 11.0, 11.25), box(4.85, 8.0, 9.75, 4.9, 10.0, 10.75)
                );
                    default -> Shapes.or(
                    box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0), box(4.75, 10.0, 4.75, 11.25, 11.0, 11.25), box(9.75, 8.0, 11.1, 10.75, 10.0, 11.15)
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
