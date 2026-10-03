package com.cookiecraftmods.mdm.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlueCurtainBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<AttachFace> FACE = FaceAttachedHorizontalDirectionalBlock.FACE;
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

    public BlueCurtainBlock() {
        super(
            Properties.of()
                .sound(SoundType.WOOL)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
    }

    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(state -> {
            return switch ((Direction)state.getValue(FACING)) {
                case NORTH -> {
                    switch ((AttachFace)state.getValue(FACE)) {
                        case FLOOR:
                            yield box(-4.0, -16.0, 14.0, 20.0, 32.0, 16.0);
                        case WALL:
                            yield box(-4.0, 14.0, -16.0, 20.0, 16.0, 32.0);
                        case CEILING:
                            yield box(-4.0, -16.0, 14.0, 20.0, 32.0, 16.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
                case EAST -> {
                    switch ((AttachFace)state.getValue(FACE)) {
                        case FLOOR:
                            yield box(0.0, -16.0, -4.0, 2.0, 32.0, 20.0);
                        case WALL:
                            yield box(-16.0, 14.0, -4.0, 32.0, 16.0, 20.0);
                        case CEILING:
                            yield box(0.0, -16.0, -4.0, 2.0, 32.0, 20.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
                case WEST -> {
                    switch ((AttachFace)state.getValue(FACE)) {
                        case FLOOR:
                            yield box(14.0, -16.0, -4.0, 16.0, 32.0, 20.0);
                        case WALL:
                            yield box(-16.0, 14.0, -4.0, 32.0, 16.0, 20.0);
                        case CEILING:
                            yield box(14.0, -16.0, -4.0, 16.0, 32.0, 20.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
                default -> {
                    switch ((AttachFace)state.getValue(FACE)) {
                        case FLOOR:
                            yield box(-4.0, -16.0, 0.0, 20.0, 32.0, 2.0);
                        case WALL:
                            yield box(-4.0, 14.0, -16.0, 20.0, 16.0, 32.0);
                        case CEILING:
                            yield box(-4.0, -16.0, 0.0, 20.0, 32.0, 2.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
            };
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.shapes.get(state);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, FACE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
            .setValue(FACE, this.faceForDirection(context.getNearestLookingDirection()))
            .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    private AttachFace faceForDirection(Direction direction) {
        if (direction.getAxis() == Axis.Y) {
            return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
        } else {
            return AttachFace.WALL;
        }
    }
}
