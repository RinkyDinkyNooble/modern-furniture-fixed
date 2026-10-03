package com.cookiecraftmods.mdm.block;

import com.rinkynooble.modernfurniturefixed.FurnitureData;
import com.rinkynooble.modernfurniturefixed.FurnitureStorage;
import com.rinkynooble.modernfurniturefixed.FurnitureStorageBlockEntity;
import com.rinkynooble.modernfurniturefixed.SplitFurniture;
import com.rinkynooble.modernfurniturefixed.SplitPart;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Every piece of furniture. Its hitboxes, its split into parts (for furniture larger than one block) and its
 * storage come from the block's furniture data.
 */
public class FurnitureBlock extends Block implements EntityBlock {
    private final FurnitureData furniture;
    private final Map<BlockState, VoxelShape> shapes;
    private final Map<BlockState, Vec3i> offsets;

    protected FurnitureBlock(Properties properties) {
        super(properties);
        this.furniture = FurnitureData.forClass(this.getClass());
        this.shapes = this.furniture.shapes(this.stateDefinition);
        this.offsets = this.furniture.offsets(this.stateDefinition);
    }

    public FurnitureData furniture() {
        return this.furniture;
    }

    /** Where this part sits relative to the main part (zero for the main part and unsplit furniture). */
    public Vec3i offset(BlockState state) {
        return this.offsets.getOrDefault(state, Vec3i.ZERO);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        FurnitureData data = FurnitureData.forClass(this.getClass());
        if (data.split()) {
            builder.add(data.partProperty());
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes.getOrDefault(state, Shapes.block());
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public final BlockState getStateForPlacement(BlockPlaceContext context) {
        return SplitFurniture.placedPart(this, context, this.basePlacement(context));
    }

    /** The state to place, before the part is chosen. Subclasses set their facing and other properties here. */
    @Nullable
    protected BlockState basePlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state != null && this.furniture.split() ? state.setValue(this.furniture.partProperty(), SplitPart.MAIN) : state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return SplitFurniture.partsFit(this, state, level, pos) && super.canSurvive(state, level, pos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        SplitFurniture.placeParts(this, level, pos, state);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        SplitFurniture.breakPiece(this, level, pos, state, player);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return SplitFurniture.dropsItem(this, state) ? super.getDrops(state, params) : List.of();
    }

    /** Swaps a split piece's left and right parts after a mirror, so they stay in line. */
    protected BlockState mirrorPart(BlockState mirrored) {
        return SplitFurniture.mirrorPart(this, mirrored);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return FurnitureStorage.holdsItems(this, state) ? new FurnitureStorageBlockEntity(pos, state) : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return FurnitureStorage.use(this, state, level, pos, player, hit.getDirection());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        FurnitureStorage.onRemove(state, level, pos, newState);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof MenuProvider provider ? provider : null;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return this.furniture.storage() != null;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof FurnitureStorageBlockEntity storage ? AbstractContainerMenu.getRedstoneSignalFromContainer(storage) : 0;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof FurnitureStorageBlockEntity storage) {
            storage.recheckOpen();
        }
    }
}
