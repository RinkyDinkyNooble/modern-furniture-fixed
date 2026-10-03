package com.cookiecraftmods.mdm.init;

import com.rinkynooble.modernfurniturefixed.FurnitureStorageBlockEntity;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MdmModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "mdm");
    private static final List<Storage> STORAGE = new ArrayList<>();
    private static volatile Map<Block, BlockEntityType<FurnitureStorageBlockEntity>> byBlock;

    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BATHROOM_SHELF_02 = register("bathroom_shelf_02", MdmModBlocks.BATHROOM_SHELF_02);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BATHROOM_SINK_WITH_SHELF = register("bathroom_sink_with_shelf", MdmModBlocks.BATHROOM_SINK_WITH_SHELF);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BATHROOM_STAND = register("bathroom_stand", MdmModBlocks.BATHROOM_STAND);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREY_BUNK_BED = register("bedroom_set_1grey_bunk_bed", MdmModBlocks.BEDROOM_SET_1GREY_BUNK_BED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREY_DOUBLE_BED = register("bedroom_set_1grey_double_bed", MdmModBlocks.BEDROOM_SET_1GREY_DOUBLE_BED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREY_NIGHTSTAND_1 = register("bedroom_set_1grey_nightstand_1", MdmModBlocks.BEDROOM_SET_1GREY_NIGHTSTAND_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREY_NIGHTSTAND_2 = register("bedroom_set_1grey_nightstand_2", MdmModBlocks.BEDROOM_SET_1GREY_NIGHTSTAND_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREY_SINGLE_BED = register("bedroom_set_1grey_single_bed", MdmModBlocks.BEDROOM_SET_1GREY_SINGLE_BED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREYDRESSERLEFT = register("bedroom_set_1greydresserleft", MdmModBlocks.BEDROOM_SET_1GREYDRESSERLEFT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREYDRESSERMID = register("bedroom_set_1greydressermid", MdmModBlocks.BEDROOM_SET_1GREYDRESSERMID);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREYDRESSERRIGHT = register("bedroom_set_1greydresserright", MdmModBlocks.BEDROOM_SET_1GREYDRESSERRIGHT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1GREYWARDROBE = register("bedroom_set_1greywardrobe", MdmModBlocks.BEDROOM_SET_1GREYWARDROBE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGE_DOUBLE_BED = register("bedroom_set_1wenge_double_bed", MdmModBlocks.BEDROOM_SET_1WENGE_DOUBLE_BED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGE_SINGLE_BED = register("bedroom_set_1wenge_single_bed", MdmModBlocks.BEDROOM_SET_1WENGE_SINGLE_BED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGEBUNKBED = register("bedroom_set_1wengebunkbed", MdmModBlocks.BEDROOM_SET_1WENGEBUNKBED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGEDRESSERLEFT = register("bedroom_set_1wengedresserleft", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERLEFT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGEDRESSERMID = register("bedroom_set_1wengedressermid", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERMID);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGEDRESSERRIGHT = register("bedroom_set_1wengedresserright", MdmModBlocks.BEDROOM_SET_1WENGEDRESSERRIGHT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGENIGHSTAND_2 = register("bedroom_set_1wengenighstand_2", MdmModBlocks.BEDROOM_SET_1WENGENIGHSTAND_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGENIGHTSTAND_1 = register("bedroom_set_1wengenightstand_1", MdmModBlocks.BEDROOM_SET_1WENGENIGHTSTAND_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WENGEWARDROBE = register("bedroom_set_1wengewardrobe", MdmModBlocks.BEDROOM_SET_1WENGEWARDROBE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITE_DOUBLEBED = register("bedroom_set_1white_doublebed", MdmModBlocks.BEDROOM_SET_1WHITE_DOUBLEBED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITE_SINGLEBED = register("bedroom_set_1white_singlebed", MdmModBlocks.BEDROOM_SET_1WHITE_SINGLEBED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITEBUNKBED = register("bedroom_set_1whitebunkbed", MdmModBlocks.BEDROOM_SET_1WHITEBUNKBED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITEDRESSERLEFT = register("bedroom_set_1whitedresserleft", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERLEFT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITEDRESSERMID = register("bedroom_set_1whitedressermid", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERMID);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITEDRESSERRIGHT = register("bedroom_set_1whitedresserright", MdmModBlocks.BEDROOM_SET_1WHITEDRESSERRIGHT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITENIGHSTAND_2 = register("bedroom_set_1whitenighstand_2", MdmModBlocks.BEDROOM_SET_1WHITENIGHSTAND_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITENIGHTSTAND_1 = register("bedroom_set_1whitenightstand_1", MdmModBlocks.BEDROOM_SET_1WHITENIGHTSTAND_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BEDROOM_SET_1WHITEWARDROBE = register("bedroom_set_1whitewardrobe", MdmModBlocks.BEDROOM_SET_1WHITEWARDROBE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BLACK_DESK_EXTENSION_1 = register("black_desk_extension_1", MdmModBlocks.BLACK_DESK_EXTENSION_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BLACK_DESK_EXTENSION_2 = register("black_desk_extension_2", MdmModBlocks.BLACK_DESK_EXTENSION_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BLACK_DESK_EXTENSION_3 = register("black_desk_extension_3", MdmModBlocks.BLACK_DESK_EXTENSION_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BLACK_FOYER_WALL_UNIT = register("black_foyer_wall_unit", MdmModBlocks.BLACK_FOYER_WALL_UNIT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BLACK_MODERN_FRIDGE_1 = register("black_modern_fridge_1", MdmModBlocks.BLACK_MODERN_FRIDGE_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BLACK_OFFICE_DESK = register("black_office_desk", MdmModBlocks.BLACK_OFFICE_DESK);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> BOXES = register("boxes", MdmModBlocks.BOXES);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> FOYER_BENCH = register("foyer_bench", MdmModBlocks.FOYER_BENCH);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> FOYER_BENCH_DARK = register("foyer_bench_dark", MdmModBlocks.FOYER_BENCH_DARK);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> FOYER_BENCH_WENGE = register("foyer_bench_wenge", MdmModBlocks.FOYER_BENCH_WENGE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> FOYER_BENCH_WHITE = register("foyer_bench_white", MdmModBlocks.FOYER_BENCH_WHITE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> FOYER_WALL_UNIT_BOTTOM = register("foyer_wall_unit_bottom", MdmModBlocks.FOYER_WALL_UNIT_BOTTOM);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> HANGING_SHELF = register("hanging_shelf", MdmModBlocks.HANGING_SHELF);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_CORNER_COUNTER = register("kitchen_set_3_corner_counter", MdmModBlocks.KITCHEN_SET_3_CORNER_COUNTER);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_CORNER_COUNTER_WITH_SINK = register("kitchen_set_3_corner_counter_with_sink", MdmModBlocks.KITCHEN_SET_3_CORNER_COUNTER_WITH_SINK);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_COUNTER = register("kitchen_set_3_counter", MdmModBlocks.KITCHEN_SET_3_COUNTER);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_COUNTER_2 = register("kitchen_set_3_counter_2", MdmModBlocks.KITCHEN_SET_3_COUNTER_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_COUNTER_3 = register("kitchen_set_3_counter_3", MdmModBlocks.KITCHEN_SET_3_COUNTER_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_DISHWASHER = register("kitchen_set_3_dishwasher", MdmModBlocks.KITCHEN_SET_3_DISHWASHER);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_FRIDGE_BOTTOM = register("kitchen_set_3_fridge_bottom", MdmModBlocks.KITCHEN_SET_3_FRIDGE_BOTTOM);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_FRIDGE_TOP = register("kitchen_set_3_fridge_top", MdmModBlocks.KITCHEN_SET_3_FRIDGE_TOP);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_HANGING_CABINET = register("kitchen_set_3_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_HANGING_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_HANGING_CORNER_CABINET = register("kitchen_set_3_hanging_corner_cabinet", MdmModBlocks.KITCHEN_SET_3_HANGING_CORNER_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_OVEN_BOTTOM = register("kitchen_set_3_oven_bottom", MdmModBlocks.KITCHEN_SET_3_OVEN_BOTTOM);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_OVEN_TOP = register("kitchen_set_3_oven_top", MdmModBlocks.KITCHEN_SET_3_OVEN_TOP);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_SMALL_CORNER_HANGING_CABINET = register("kitchen_set_3_small_corner_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_SMALL_CORNER_HANGING_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> KITCHEN_SET_3_SMALL_HANGING_CABINET = register("kitchen_set_3_small_hanging_cabinet", MdmModBlocks.KITCHEN_SET_3_SMALL_HANGING_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_CABINET_1 = register("office_cabinet_1", MdmModBlocks.OFFICE_CABINET_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_CABINET_2 = register("office_cabinet_2", MdmModBlocks.OFFICE_CABINET_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_CABINET_3 = register("office_cabinet_3", MdmModBlocks.OFFICE_CABINET_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_CABINET = register("office_set_1_desk_1_cabinet", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_CABINET_VAR_2 = register("office_set_1_desk_1_cabinet_var_2", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET_VAR_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_CABINET_VAR_3 = register("office_set_1_desk_1_cabinet_var_3", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET_VAR_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_CABINET_VAR_4 = register("office_set_1_desk_1_cabinet_var_4", MdmModBlocks.OFFICE_SET_1_DESK_1_CABINET_VAR_4);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_LEFT = register("office_set_1_desk_1_left", MdmModBlocks.OFFICE_SET_1_DESK_1_LEFT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_LEFT_VAR_2 = register("office_set_1_desk_1_left_var_2", MdmModBlocks.OFFICE_SET_1_DESK_1_LEFT_VAR_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_LEFT_VAR_3 = register("office_set_1_desk_1_left_var_3", MdmModBlocks.OFFICE_SET_1_DESK_1_LEFT_VAR_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_MIDDLE = register("office_set_1_desk_1_middle", MdmModBlocks.OFFICE_SET_1_DESK_1_MIDDLE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_RIGHT = register("office_set_1_desk_1_right", MdmModBlocks.OFFICE_SET_1_DESK_1_RIGHT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_RIGHT_VAR_2 = register("office_set_1_desk_1_right_var_2", MdmModBlocks.OFFICE_SET_1_DESK_1_RIGHT_VAR_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_1_RIGHT_VAR_3 = register("office_set_1_desk_1_right_var_3", MdmModBlocks.OFFICE_SET_1_DESK_1_RIGHT_VAR_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_DESK_2_SIDE_VAR_2 = register("office_set_1_desk_2_side_var_2", MdmModBlocks.OFFICE_SET_1_DESK_2_SIDE_VAR_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_SHELF_VAR_1_BOTTOM = register("office_set_1_shelf_var_1_bottom", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_1_BOTTOM);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_SHELF_VAR_1_TOP = register("office_set_1_shelf_var_1_top", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_1_TOP);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_SHELF_VAR_2_BOTTOM = register("office_set_1_shelf_var_2_bottom", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_2_BOTTOM);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SET_1_SHELF_VAR_2_TOP = register("office_set_1_shelf_var_2_top", MdmModBlocks.OFFICE_SET_1_SHELF_VAR_2_TOP);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> OFFICE_SHELF = register("office_shelf", MdmModBlocks.OFFICE_SHELF);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SEASONING_RACK = register("seasoning_rack", MdmModBlocks.SEASONING_RACK);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_CABINET = register("set_1_cabinet", MdmModBlocks.SET_1_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_CABINET_2 = register("set_1_cabinet_2", MdmModBlocks.SET_1_CABINET_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_CABINET_WITH_DRAWERS = register("set_1_cabinet_with_drawers", MdmModBlocks.SET_1_CABINET_WITH_DRAWERS);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_CORNER_CABINET_1 = register("set_1_corner_cabinet_1", MdmModBlocks.SET_1_CORNER_CABINET_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_CORNER_CABINET_2 = register("set_1_corner_cabinet_2", MdmModBlocks.SET_1_CORNER_CABINET_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_CORNER_HANGING_CABINET = register("set_1_corner_hanging_cabinet", MdmModBlocks.SET_1_CORNER_HANGING_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_FRIDGE = register("set_1_fridge", MdmModBlocks.SET_1_FRIDGE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_HANGING_CABINET = register("set_1_hanging_cabinet", MdmModBlocks.SET_1_HANGING_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_LIVINGROOM_CABINET = register("set_1_livingroom_cabinet", MdmModBlocks.SET_1_LIVINGROOM_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_LIVINGROOM_CABINET_2 = register("set_1_livingroom_cabinet_2", MdmModBlocks.SET_1_LIVINGROOM_CABINET_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_LIVINGROOM_HANGING_CABINET = register("set_1_livingroom_hanging_cabinet", MdmModBlocks.SET_1_LIVINGROOM_HANGING_CABINET);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_NIGHT_TABLE = register("set_1_night_table", MdmModBlocks.SET_1_NIGHT_TABLE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_OVEN_MICROWAVE = register("set_1_oven_microwave", MdmModBlocks.SET_1_OVEN_MICROWAVE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_TV_STAND = register("set_1_tv_stand", MdmModBlocks.SET_1_TV_STAND);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_1_TV_STAND_MIDDLE = register("set_1_tv_stand_middle", MdmModBlocks.SET_1_TV_STAND_MIDDLE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_2_CABINET_1 = register("set_2_cabinet_1", MdmModBlocks.SET_2_CABINET_1);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_2_CABINET_2 = register("set_2_cabinet_2", MdmModBlocks.SET_2_CABINET_2);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_2_CABINET_3 = register("set_2_cabinet_3", MdmModBlocks.SET_2_CABINET_3);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_2_CABINET_WITH_DRAWERS = register("set_2_cabinet_with_drawers", MdmModBlocks.SET_2_CABINET_WITH_DRAWERS);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_3_COFFE_TABLE = register("set_3_coffe_table", MdmModBlocks.SET_3_COFFE_TABLE);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_3_COFFE_TABLE_DECORATED = register("set_3_coffe_table_decorated", MdmModBlocks.SET_3_COFFE_TABLE_DECORATED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SET_3TV_STAND = register("set_3tv_stand", MdmModBlocks.SET_3TV_STAND);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> SHELF = register("shelf", MdmModBlocks.SHELF);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> STEEL_SHELF = register("steel_shelf", MdmModBlocks.STEEL_SHELF);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> TV_STAND_LEFT = register("tv_stand_left", MdmModBlocks.TV_STAND_LEFT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> TV_STAND_MID = register("tv_stand_mid", MdmModBlocks.TV_STAND_MID);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> TV_STAND_MID_DECORATED = register("tv_stand_mid_decorated", MdmModBlocks.TV_STAND_MID_DECORATED);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> TV_STAND_RIGHT = register("tv_stand_right", MdmModBlocks.TV_STAND_RIGHT);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> WHITE_BATHROOM_SHELF = register("white_bathroom_shelf", MdmModBlocks.WHITE_BATHROOM_SHELF);
    public static final RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> WHITE_WOOD_FOYER_WALL_UNIT = register("white_wood_foyer_wall_unit", MdmModBlocks.WHITE_WOOD_FOYER_WALL_UNIT);

    private record Storage(RegistryObject<Block> block, RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> type) {
    }

    private static RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> register(String name, RegistryObject<Block> block) {
        RegistryObject<BlockEntityType<FurnitureStorageBlockEntity>> type = REGISTRY.register(
            name, () -> BlockEntityType.Builder.of(FurnitureStorageBlockEntity::new, block.get()).build(null)
        );
        STORAGE.add(new Storage(block, type));
        return type;
    }

    /** The storage block entity type of a piece of furniture. */
    public static BlockEntityType<FurnitureStorageBlockEntity> typeFor(Block block) {
        Map<Block, BlockEntityType<FurnitureStorageBlockEntity>> map = byBlock;
        if (map == null) {
            map = new IdentityHashMap<>();
            for (Storage storage : STORAGE) {
                map.put(storage.block().get(), storage.type().get());
            }
            byBlock = map;
        }
        BlockEntityType<FurnitureStorageBlockEntity> type = map.get(block);
        if (type == null) {
            throw new IllegalStateException("No storage for " + block);
        }
        return type;
    }
}
