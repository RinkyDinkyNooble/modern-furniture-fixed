package com.cookiecraftmods.mdm.block;

import com.rinkynooble.modernfurniturefixed.SplitFurniture;
import com.rinkynooble.modernfurniturefixed.SplitLayout;
import com.rinkynooble.modernfurniturefixed.SplitPart;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlackOfficeDeskBlock extends HorizontalFurnitureBlock {
    private static final SplitLayout LAYOUT = SplitLayout.load("black_office_desk");
    public static final EnumProperty<SplitPart> PART = LAYOUT.property();

    public BlackOfficeDeskBlock() {
        super(
            Properties.of()
                .sound(SoundType.WOOD)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .pushReaction(PushReaction.BLOCK)
        );
        this.registerDefaultState(this.defaultBlockState().setValue(PART, SplitPart.WHOLE));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return LAYOUT.shape(state.getValue(FACING), state.getValue(PART));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return SplitFurniture.placementState(LAYOUT, context, super.getStateForPlacement(context));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        SplitFurniture.placeParts(LAYOUT, level, pos, state);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        SplitFurniture.breakPiece(LAYOUT, level, pos, state, player);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return SplitFurniture.holdsItems(LAYOUT, state) ? super.getDrops(state, params) : List.of();
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return SplitFurniture.mirror(LAYOUT, super.mirror(state, mirror));
    }
}
