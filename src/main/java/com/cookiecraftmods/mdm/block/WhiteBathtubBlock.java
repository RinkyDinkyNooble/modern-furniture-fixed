package com.cookiecraftmods.mdm.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WhiteBathtubBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public WhiteBathtubBlock() {
        super(
            Properties.of()
                .sound(SoundType.METAL)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(state -> {
            return switch ((Direction)state.getValue(FACING)) {
                case NORTH -> Shapes.join(box(0.0, 0.0, 0.0, 16.0, 16.0, 32.0), box(2.0, 2.0, 2.0, 14.0, 16.0, 30.0), BooleanOp.ONLY_FIRST);
                case EAST -> Shapes.join(box(-16.0, 0.0, 0.0, 16.0, 16.0, 16.0), box(-14.0, 2.0, 2.0, 14.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
                case WEST -> Shapes.join(box(0.0, 0.0, 0.0, 32.0, 16.0, 16.0), box(2.0, 2.0, 2.0, 30.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
                default -> Shapes.join(box(0.0, 0.0, -16.0, 16.0, 16.0, 16.0), box(2.0, 2.0, -14.0, 14.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
            };
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.shapes.get(state);
    }
}
