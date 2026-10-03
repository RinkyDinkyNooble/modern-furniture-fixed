package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class BlackLampBlock extends HorizontalFurnitureBlock {

    public BlackLampBlock() {
        super(
            Properties.of()
                .strength(1.0F, 10.0F)
                .lightLevel(blockstate -> 15)
                .noOcclusion()
                .hasPostProcess((bs, br, bp) -> true)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
    }

}
