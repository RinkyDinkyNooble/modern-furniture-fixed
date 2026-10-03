package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class Set1Island1Block extends HorizontalFurnitureBlock {
    public Set1Island1Block() {
        super(Properties.of().strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
    }
}
