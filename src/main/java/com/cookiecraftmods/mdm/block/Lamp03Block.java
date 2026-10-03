package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Lamp03Block extends FurnitureBlock {
    private static final VoxelShape SHAPE = box(3.0, 0.0, 3.0, 13.0, 32.0, 13.0);

    public Lamp03Block() {
        super(Properties.of().sound(SoundType.WOOL).strength(1.0F, 10.0F).lightLevel(blockstate -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

}
