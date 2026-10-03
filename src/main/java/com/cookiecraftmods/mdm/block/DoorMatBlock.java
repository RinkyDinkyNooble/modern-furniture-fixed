package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DoorMatBlock extends TransparentHorizontalFurnitureBlock {

    public DoorMatBlock() {
        super(Properties.of().sound(SoundType.MOSS).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

}
