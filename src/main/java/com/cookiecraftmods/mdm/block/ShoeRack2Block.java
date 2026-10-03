package com.cookiecraftmods.mdm.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShoeRack2Block extends TransparentHorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public ShoeRack2Block() {
        super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(state -> {
            return switch ((Direction)state.getValue(FACING)) {
                case NORTH -> box(0.0, 0.0, 6.0, 16.0, 16.0, 16.0);
                case EAST -> box(0.0, 0.0, 0.0, 10.0, 16.0, 16.0);
                case WEST -> box(6.0, 0.0, 0.0, 16.0, 16.0, 16.0);
                default -> box(0.0, 0.0, 0.0, 16.0, 16.0, 10.0);
            };
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.shapes.get(state);
    }
}
