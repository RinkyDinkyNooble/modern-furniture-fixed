package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WallLamp2Block extends HorizontalFurnitureBlock {

    public WallLamp2Block() {
        super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).lightLevel(blockstate -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

}
