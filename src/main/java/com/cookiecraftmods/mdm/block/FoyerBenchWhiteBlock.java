package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FoyerBenchWhiteBlock extends TransparentHorizontalFurnitureBlock {
    public FoyerBenchWhiteBlock() {
        super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }
}
