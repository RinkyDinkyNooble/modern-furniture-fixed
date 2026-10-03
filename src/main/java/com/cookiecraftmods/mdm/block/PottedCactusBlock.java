package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class PottedCactusBlock extends TransparentFurnitureBlock {
    public PottedCactusBlock() {
        super(Properties.of().sound(SoundType.DECORATED_POT).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }
}
