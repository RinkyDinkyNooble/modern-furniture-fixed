package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BowlBlock extends TransparentHorizontalFurnitureBlock {
    public BowlBlock() {
        super(Properties.of().sound(SoundType.DECORATED_POT).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }
}
