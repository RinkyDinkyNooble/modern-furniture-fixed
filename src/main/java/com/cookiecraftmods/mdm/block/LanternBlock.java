package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class LanternBlock extends TransparentHorizontalFurnitureBlock {
    public LanternBlock() {
        super(
            Properties.of()
                .sound(SoundType.METAL)
                .strength(1.0F, 10.0F)
                .lightLevel(blockstate -> 15)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
        );
    }
}
