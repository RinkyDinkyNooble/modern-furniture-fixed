package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StreetLanternMidBlock extends FurnitureBlock {
    private static final VoxelShape SHAPE = box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);

    public StreetLanternMidBlock() {
        super(Properties.of().strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
    }

}
