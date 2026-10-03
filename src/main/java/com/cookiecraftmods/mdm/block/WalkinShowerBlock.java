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

public class WalkinShowerBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public WalkinShowerBlock() {
        super(
            Properties.of()
                .sound(SoundType.GLASS)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(state -> {
            return switch ((Direction)state.getValue(FACING)) {
                case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 0.25, 32.0), box(0.0, 0.0, 0.0, 16.0, 32.0, 1.0), box(0.0, 0.0, 16.0, 1.0, 32.0, 32.0));
                case EAST -> Shapes.or(box(-16.0, 0.0, 0.0, 16.0, 0.25, 16.0), box(15.0, 0.0, 0.0, 16.0, 32.0, 16.0), box(-16.0, 0.0, 0.0, 0.0, 32.0, 1.0));
                case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 32.0, 0.25, 16.0), box(0.0, 0.0, 0.0, 1.0, 32.0, 16.0), box(16.0, 0.0, 15.0, 32.0, 32.0, 16.0));
                default -> Shapes.or(box(0.0, 0.0, -16.0, 16.0, 0.25, 16.0), box(0.0, 0.0, 15.0, 16.0, 32.0, 16.0), box(15.0, 0.0, -16.0, 16.0, 32.0, 0.0));
            };
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.shapes.get(state);
    }
}
