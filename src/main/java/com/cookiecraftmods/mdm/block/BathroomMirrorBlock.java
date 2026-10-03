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

public class BathroomMirrorBlock extends HorizontalFurnitureBlock {
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public BathroomMirrorBlock() {
        super(
            Properties.of()
                .sound(SoundType.GLASS)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .hasPostProcess((bs, br, bp) -> true)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(
            state -> {
                return switch ((Direction)state.getValue(FACING)) {
                    case NORTH -> Shapes.or(
                    box(1.0, 1.0, 14.0, 15.0, 3.0, 16.0),
                    box(1.0, 1.5, 13.0, 2.0, 2.5, 15.0),
                    box(14.0, 1.5, 12.0, 15.0, 2.5, 14.0),
                    box(0.5, 0.7, 12.3, 2.5, 2.7, 13.3),
                    box(13.5, 0.7, 12.3, 15.5, 2.7, 13.3),
                    box(3.0, -12.0, 15.5, 13.0, -1.0, 15.75),
                    box(4.0, -11.0, 15.75, 12.0, -2.0, 16.0),
                    box(3.0, -12.0, 13.0, 13.0, -11.75, 15.5)
                );
                    case EAST -> Shapes.or(
                    box(0.0, 1.0, 1.0, 2.0, 3.0, 15.0),
                    box(1.0, 1.5, 1.0, 3.0, 2.5, 2.0),
                    box(2.0, 1.5, 14.0, 4.0, 2.5, 15.0),
                    box(2.7, 0.7, 0.5, 3.7, 2.7, 2.5),
                    box(2.7, 0.7, 13.5, 3.7, 2.7, 15.5),
                    box(0.25, -12.0, 3.0, 0.5, -1.0, 13.0),
                    box(0.0, -11.0, 4.0, 0.25, -2.0, 12.0),
                    box(0.5, -12.0, 3.0, 3.0, -11.75, 13.0)
                );
                    case WEST -> Shapes.or(
                    box(14.0, 1.0, 1.0, 16.0, 3.0, 15.0),
                    box(13.0, 1.5, 14.0, 15.0, 2.5, 15.0),
                    box(12.0, 1.5, 1.0, 14.0, 2.5, 2.0),
                    box(12.3, 0.7, 13.5, 13.3, 2.7, 15.5),
                    box(12.3, 0.7, 0.5, 13.3, 2.7, 2.5),
                    box(15.5, -12.0, 3.0, 15.75, -1.0, 13.0),
                    box(15.75, -11.0, 4.0, 16.0, -2.0, 12.0),
                    box(13.0, -12.0, 3.0, 15.5, -11.75, 13.0)
                );
                    default -> Shapes.or(
                    box(1.0, 1.0, 0.0, 15.0, 3.0, 2.0),
                    box(14.0, 1.5, 1.0, 15.0, 2.5, 3.0),
                    box(1.0, 1.5, 2.0, 2.0, 2.5, 4.0),
                    box(13.5, 0.7, 2.7, 15.5, 2.7, 3.7),
                    box(0.5, 0.7, 2.7, 2.5, 2.7, 3.7),
                    box(3.0, -12.0, 0.25, 13.0, -1.0, 0.5),
                    box(4.0, -11.0, 0.0, 12.0, -2.0, 0.25),
                    box(3.0, -12.0, 0.5, 13.0, -11.75, 3.0)
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
