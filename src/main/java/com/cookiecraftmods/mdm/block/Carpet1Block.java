package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class Carpet1Block extends HorizontalFurnitureBlock {

    public Carpet1Block() {
        super(Properties.of().sound(SoundType.WOOL).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).ignitedByLava());
    }

}
