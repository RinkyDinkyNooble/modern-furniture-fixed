package com.cookiecraftmods.mdm.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RingLampBlock extends FurnitureBlock {
    private static final VoxelShape SHAPE = Shapes.or(
        box(4.0, 15.0, 4.0, 12.0, 16.0, 12.0),
        box(4.25, 14.0, 4.25, 11.75, 15.0, 5.25),
        box(4.25, 14.0, 10.75, 11.75, 15.0, 11.75),
        box(4.25, 14.0, 5.25, 5.25, 15.0, 10.75),
        box(10.75, 14.0, 5.25, 11.75, 15.0, 10.75),
        box(5.5, 14.5, 5.5, 10.5, 15.0, 10.5)
    );

    public RingLampBlock() {
        super(
            Properties.of()
                .sound(SoundType.GLASS)
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
