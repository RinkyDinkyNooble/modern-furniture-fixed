package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StreetLanternBottomBlock extends FurnitureBlock {
    private static final VoxelShape SHAPE = Shapes.or(
        box(5.0, 0.0, 5.0, 11.0, 1.0, 11.0), box(6.0, 1.0, 6.0, 10.0, 2.0, 10.0), box(7.0, 2.0, 7.0, 9.0, 16.0, 9.0)
    );

    public StreetLanternBottomBlock() {
        super(Properties.of().strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
