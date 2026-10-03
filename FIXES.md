# Fixes

What Modern Furniture Fixed changes in MDM 26.9. MFF replaces MDM: it keeps MDM's mod id and every block id, so worlds and buildings made with MDM load with MFF.

## Every block
- **Hitboxes follow the model.** Each block state's hitbox is made from the model's own boxes, leaving out small details such as handles and feet, unless leaving them out would lose most of the object (a lantern made of thin glass, a lamp shade). Tilted parts get a staircase of boxes that follows the diagonal. Before, many hitboxes were full cubes or a single box, and some sat beside the model.
- **Mined faster with an axe or a pickaxe**, either one. Hands still work.

## Furniture larger than one block
91 pieces are split into one block per space they fill. Each part draws its own slice of the model and has its own hitbox, so lighting, culling and collision work per space, and the furniture no longer pokes through walls. A space only becomes a part when the furniture reaches at least 4 pixels into it; shallower bits are drawn by the part next to them.
- **Placing** the item places every part. It is refused if a space the piece needs isn't free. A piece that reaches below the block it is placed from (a tall mirror, window blinds, curtains) is raised when it doesn't fit, so its lowest part takes the space you clicked: placed on the floor, it stands on the floor.
- **Breaking** any part breaks the whole piece and drops one item. The server setting `breakWholePiece` turns this off; then each part breaks on its own and only the part the piece was placed from drops the item.
- **Furniture placed before this mod** stays one block that draws the whole model (`part=whole`), so existing worlds and buildings look the same. Its hitbox follows the whole model, as far as one block's hitbox can reach. Breaking it and placing it again splits it.
- **Pistons** can't push furniture.
- **Mirroring** (for example WorldEdit's flip) keeps a piece whole only if it has parts on both sides.

| Block | Parts |
|---|---|
| `mdm:bathroom_mirror` | `bottom`, `main` |
| `mdm:bathroom_radiator` | `main`, `top` |
| `mdm:bathroom_shelf_02` | `left`, `main`, `right`, `top` |
| `mdm:bathroom_stand` | `main`, `top` |
| `mdm:bedroom_set_1grey_bunk_bed` | `main`, `right`, `top`, `top_right` |
| `mdm:bedroom_set_1grey_double_bed` | `front`, `front_right`, `main`, `right` |
| `mdm:bedroom_set_1grey_single_bed` | `front`, `main` |
| `mdm:bedroom_set_1greyloveseat` | `front`, `main` |
| `mdm:bedroom_set_1greywardrobe` | `main`, `top` |
| `mdm:bedroom_set_1wenge_double_bed` | `front`, `front_right`, `main`, `right` |
| `mdm:bedroom_set_1wenge_single_bed` | `front`, `main` |
| `mdm:bedroom_set_1wengebunkbed` | `main`, `right`, `top`, `top_right` |
| `mdm:bedroom_set_1wengewardrobe` | `main`, `top` |
| `mdm:bedroom_set_1white_doublebed` | `front`, `front_right`, `main`, `right` |
| `mdm:bedroom_set_1white_singlebed` | `front`, `main` |
| `mdm:bedroom_set_1whitebunkbed` | `main`, `right`, `top`, `top_right` |
| `mdm:bedroom_set_1whiteloveseat` | `front`, `main` |
| `mdm:bedroom_set_1whitewardrobe` | `main`, `top` |
| `mdm:bedroomset_1_black_loveseat` | `front`, `main` |
| `mdm:big_ceiling_lamp` | `left`, `main`, `right` |
| `mdm:black_armchair` | `main`, `top` |
| `mdm:black_bench` | `left`, `main`, `right` |
| `mdm:black_curtain` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:black_curtain_left` | `bottom_left`, `bottom`, `left`, `main`, `top_left`, `top` |
| `mdm:black_curtain_right` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:black_foyer_wall_unit` | `main`, `right` |
| `mdm:black_foyer_wall_unit_top` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:black_lamp` | `main`, `top` |
| `mdm:black_modern_fridge_1` | `main`, `top` |
| `mdm:black_office_desk` | `left`, `main`, `right` |
| `mdm:blue_curtain` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:blue_curtain_left` | `bottom_left`, `bottom`, `left`, `main`, `top_left`, `top` |
| `mdm:blue_curtain_right` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:carpet_1` | `left`, `main`, `back_left`, `back` |
| `mdm:carpet_2` | `front_left`, `front`, `front_right`, `left`, `main`, `right` |
| `mdm:carpet_3` | `front_left`, `front`, `front_right`, `left`, `main`, `right` |
| `mdm:carpet_4` | `front_left`, `front`, `front_right`, `left`, `main`, `right` |
| `mdm:coffee_table_04` | `left`, `main`, `right` |
| `mdm:cork_board` | `left`, `main`, `right` |
| `mdm:crib` | `main`, `right`, `top`, `top_right` |
| `mdm:entryway_carpet` | `front`, `main`, `back` |
| `mdm:foyer_wall_unit_bottom` | `main`, `right` |
| `mdm:foyer_wall_unit_top` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:green_curtain` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:green_curtain_left` | `bottom_left`, `bottom`, `left`, `main`, `top_left`, `top` |
| `mdm:green_curtain_right` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:grey_curtain` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:grey_curtain_left` | `bottom_left`, `bottom`, `left`, `main`, `top_left`, `top` |
| `mdm:grey_curtain_right` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:hanging_shelf` | `main`, `right` |
| `mdm:kitchen_set_3_corner_counter_with_sink` | `main`, `top` |
| `mdm:lamp_03` | `main`, `top` |
| `mdm:leafless_bamboo_decoration_1` | `main`, `top` |
| `mdm:leafless_bamboo_decoration_2` | `main`, `top` |
| `mdm:leafless_bamboo_decoration_3` | `main`, `top` |
| `mdm:modern_desk_1` | `left`, `main`, `right` |
| `mdm:modern_desk_2` | `left`, `main`, `right` |
| `mdm:modern_lamp_1` | `main`, `top` |
| `mdm:modern_painting` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right` |
| `mdm:modern_painting_2` | `left`, `main`, `right` |
| `mdm:office_set_1_chair` | `main`, `top` |
| `mdm:office_shelf` | `main`, `top` |
| `mdm:painting_03` | `left`, `main`, `right` |
| `mdm:painting_04` | `main`, `top` |
| `mdm:painting_05` | `left`, `main`, `right` |
| `mdm:pool_table` | `main`, `back` |
| `mdm:potted_tree` | `main`, `top` |
| `mdm:red_curtain` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:red_curtain_left` | `bottom_left`, `bottom`, `left`, `main`, `top_left`, `top` |
| `mdm:red_curtain_right` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:set_1_bed` | `main`, `back` |
| `mdm:set_1_chair` | `main`, `top` |
| `mdm:set_1_fridge` | `main`, `top` |
| `mdm:set_1_oven_microwave` | `main`, `top` |
| `mdm:shelf` | `main`, `right` |
| `mdm:shoji_screen` | `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:standing_white_board` | `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:steel_shelf` | `main`, `top` |
| `mdm:tall_mirror` | `bottom`, `main`, `top` |
| `mdm:toilet` | `main`, `top` |
| `mdm:walkin_shower` | `main`, `back`, `top`, `top_back` |
| `mdm:white_armchair` | `main`, `top` |
| `mdm:white_bathtub` | `main`, `back` |
| `mdm:white_bench` | `left`, `main`, `right` |
| `mdm:white_board` | `left`, `main`, `right` |
| `mdm:white_curtain` | `bottom_left`, `bottom`, `bottom_right`, `left`, `main`, `right`, `top_left`, `top`, `top_right` |
| `mdm:white_curtain_left` | `bottom_left`, `bottom`, `left`, `main`, `top_left`, `top` |
| `mdm:white_curtain_right` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:white_wood_foyer_wall_unit` | `main`, `right` |
| `mdm:white_wood_foyer_wall_unit_top` | `bottom`, `bottom_right`, `main`, `right`, `top`, `top_right` |
| `mdm:window_blinds` | `bottom`, `main`, `top` |

## Storage
108 pieces have storage. It opens as a vanilla chest, so sorting buttons and inventory mods treat it as one, and it works with hoppers, loot tables and LootJS. Each piece has one inventory; on split furniture, only the parts listed below open it, and the first of them holds the items. Beds open from their long sides only.
- **Size** is set per block in rows of 9 slots (1 to 6) in `serverconfig/mdm-server.toml`, under `storageRows`. When a size is lowered, items in slots that no longer exist drop on the ground the next time the block loads.
- **Titles:** the storage screen shows a short name that fits the chest screen (for example "Wenge Dresser Right").
- **Sounds:** storage plays Minecraft's barrel sounds when it opens and closes. Each player can turn them off with `furnitureSounds` in `config/mdm-client.toml`.
- **Items in MDM storage** keep their slots. MDM's own storage screens are gone.
- **No more storage** on `mdm:bedroom_set_1whiteloveseat`, `mdm:set_1_island_1`, `mdm:set_1_island_2`, `mdm:set_2_corner_counter` and `mdm:set_2_hanging_shelf`. Items stored in them in an existing world are lost.

| Block | Title | Slots | Opens from |
|---|---|---|---|
| `mdm:bathroom_shelf_02` | Bathroom Shelf 0 2 | 27 | `main`, `right`, `left` |
| `mdm:bathroom_sink_with_shelf` | Bathroom Sink With Shelf | 18 | the block |
| `mdm:bathroom_stand` | Bathroom Stand | 18 | `main`, `top` |
| `mdm:bedroom_set_1grey_bunk_bed` | Grey Bunk Bed | 27 | `main`, `right` |
| `mdm:bedroom_set_1grey_double_bed` | Grey Double Bed | 27 | `front`, `front_right`, `main`, `right` (left and right sides) |
| `mdm:bedroom_set_1grey_nightstand_1` | Grey Nightstand 1 | 27 | the block |
| `mdm:bedroom_set_1grey_nightstand_2` | Grey Nightstand 2 | 27 | the block |
| `mdm:bedroom_set_1grey_single_bed` | Grey Single Bed | 27 | `front`, `main` (left and right sides) |
| `mdm:bedroom_set_1greydresserleft` | Grey Dresser Left | 27 | the block |
| `mdm:bedroom_set_1greydressermid` | Grey Dresser Mid | 27 | the block |
| `mdm:bedroom_set_1greydresserright` | Grey Dresser Right | 27 | the block |
| `mdm:bedroom_set_1greywardrobe` | Grey Wardrobe | 36 | `main`, `top` |
| `mdm:bedroom_set_1wenge_double_bed` | Wenge Double Bed | 27 | `front`, `front_right`, `main`, `right` (left and right sides) |
| `mdm:bedroom_set_1wenge_single_bed` | Wenge Single Bed | 27 | `front`, `main` (left and right sides) |
| `mdm:bedroom_set_1wengebunkbed` | Wenge Bunkbed | 27 | `main`, `right` |
| `mdm:bedroom_set_1wengedresserleft` | Wenge Dresser Left | 27 | the block |
| `mdm:bedroom_set_1wengedressermid` | Wenge Dresser Mid | 27 | the block |
| `mdm:bedroom_set_1wengedresserright` | Wenge Dresser Right | 27 | the block |
| `mdm:bedroom_set_1wengenighstand_2` | Wenge Nightstand 2 | 27 | the block |
| `mdm:bedroom_set_1wengenightstand_1` | Wenge Nightstand 1 | 27 | the block |
| `mdm:bedroom_set_1wengewardrobe` | Wenge Wardrobe | 36 | `main`, `top` |
| `mdm:bedroom_set_1white_doublebed` | White Double Bed | 27 | `front`, `front_right`, `main`, `right` (left and right sides) |
| `mdm:bedroom_set_1white_singlebed` | White Single Bed | 27 | `front`, `main` (left and right sides) |
| `mdm:bedroom_set_1whitebunkbed` | White Bunkbed | 27 | `main`, `right` |
| `mdm:bedroom_set_1whitedresserleft` | White Dresser Left | 27 | the block |
| `mdm:bedroom_set_1whitedressermid` | White Dresser Mid | 27 | the block |
| `mdm:bedroom_set_1whitedresserright` | White Dresser Right | 27 | the block |
| `mdm:bedroom_set_1whitenighstand_2` | White Nighstand 2 | 27 | the block |
| `mdm:bedroom_set_1whitenightstand_1` | White Nightstand 1 | 27 | the block |
| `mdm:bedroom_set_1whitewardrobe` | White Wardrobe | 36 | `main`, `top` |
| `mdm:black_desk_extension_1` | Black Desk Extension 1 | 27 | the block |
| `mdm:black_desk_extension_2` | Black Desk Extension 2 | 27 | the block |
| `mdm:black_desk_extension_3` | Black Desk Extension 3 | 27 | the block |
| `mdm:black_foyer_wall_unit` | Foyer Wall Unit Bottom | 27 | `main`, `right` |
| `mdm:black_modern_fridge_1` | Black Modern Fridge 1 | 36 | `main`, `top` |
| `mdm:black_office_desk` | Black Office Desk | 27 | `left` |
| `mdm:boxes` | Boxes | 9 | the block |
| `mdm:foyer_bench` | Foyer Bench | 18 | the block |
| `mdm:foyer_bench_dark` | Foyer Bench | 18 | the block |
| `mdm:foyer_bench_wenge` | Foyer Bench | 18 | the block |
| `mdm:foyer_bench_white` | Foyer Bench | 18 | the block |
| `mdm:foyer_wall_unit_bottom` | Foyer Wall Unit Bottom | 27 | `main`, `right` |
| `mdm:hanging_shelf` | Hanging Shelf | 18 | `main`, `right` |
| `mdm:kitchen_set_3_corner_counter` | Corner Counter | 27 | the block |
| `mdm:kitchen_set_3_corner_counter_with_sink` | Corner Counter With Sink | 27 | `main` |
| `mdm:kitchen_set_3_counter` | Counter | 27 | the block |
| `mdm:kitchen_set_3_counter_2` | Counter 2 | 27 | the block |
| `mdm:kitchen_set_3_counter_3` | Counter 3 | 27 | the block |
| `mdm:kitchen_set_3_dishwasher` | Dishwasher | 27 | the block |
| `mdm:kitchen_set_3_fridge_bottom` | Fridge Bottom | 27 | the block |
| `mdm:kitchen_set_3_fridge_top` | Fridge Top | 27 | the block |
| `mdm:kitchen_set_3_hanging_cabinet` | Hanging Cabinet | 27 | the block |
| `mdm:kitchen_set_3_hanging_corner_cabinet` | Hanging Corner Cabinet | 27 | the block |
| `mdm:kitchen_set_3_oven_bottom` | Oven Bottom | 27 | the block |
| `mdm:kitchen_set_3_oven_top` | Oven Top | 27 | the block |
| `mdm:kitchen_set_3_small_corner_hanging_cabinet` | Small Corner Hanging Cabinet | 18 | the block |
| `mdm:kitchen_set_3_small_hanging_cabinet` | Small Hanging Cabinet | 18 | the block |
| `mdm:office_cabinet_1` | Office Cabinet 1 | 27 | the block |
| `mdm:office_cabinet_2` | Office Cabinet 2 | 18 | the block |
| `mdm:office_cabinet_3` | Office Cabinet 3 | 18 | the block |
| `mdm:office_set_1_desk_1_cabinet` | Desk 1 Cabinet | 27 | the block |
| `mdm:office_set_1_desk_1_cabinet_var_2` | Desk 1 Cabinet Var 2 | 18 | the block |
| `mdm:office_set_1_desk_1_cabinet_var_3` | Desk 1 Cabinet Var 3 | 18 | the block |
| `mdm:office_set_1_desk_1_cabinet_var_4` | Desk 1 Cabinet Var 4 | 27 | the block |
| `mdm:office_set_1_desk_1_left` | Desk 1 Left | 18 | the block |
| `mdm:office_set_1_desk_1_left_var_2` | Desk 1 Left Var 2 | 27 | the block |
| `mdm:office_set_1_desk_1_left_var_3` | Desk 1 Left Var 3 | 27 | the block |
| `mdm:office_set_1_desk_1_middle` | Desk 1 Middle | 18 | the block |
| `mdm:office_set_1_desk_1_right` | Desk 1 Right | 18 | the block |
| `mdm:office_set_1_desk_1_right_var_2` | Desk 1 Right Var 2 | 27 | the block |
| `mdm:office_set_1_desk_1_right_var_3` | Desk 1 Right Var 3 | 27 | the block |
| `mdm:office_set_1_desk_2_side_var_2` | Desk 2 Side Var 2 | 27 | the block |
| `mdm:office_set_1_shelf_var_1_bottom` | Shelf Var 1 Bottom | 27 | the block |
| `mdm:office_set_1_shelf_var_1_top` | Shelf Var 1 Top | 27 | the block |
| `mdm:office_set_1_shelf_var_2_bottom` | Shelf Var 2 Bottom | 27 | the block |
| `mdm:office_set_1_shelf_var_2_top` | Shelf Var 2 Top | 27 | the block |
| `mdm:office_shelf` | Office Shelf | 27 | `main`, `top` |
| `mdm:seasoning_rack` | Seasoning Rack | 9 | the block |
| `mdm:set_1_cabinet` | Cabinet | 27 | the block |
| `mdm:set_1_cabinet_2` | Cabinet 2 | 27 | the block |
| `mdm:set_1_cabinet_with_drawers` | Cabinet With Drawers | 27 | the block |
| `mdm:set_1_corner_cabinet_1` | Corner Cabinet 1 | 27 | the block |
| `mdm:set_1_corner_cabinet_2` | Corner Cabinet 2 | 27 | the block |
| `mdm:set_1_corner_hanging_cabinet` | Corner Hanging Cabinet | 27 | the block |
| `mdm:set_1_fridge` | Fridge | 36 | `main`, `top` |
| `mdm:set_1_hanging_cabinet` | Hanging Cabinet | 27 | the block |
| `mdm:set_1_livingroom_cabinet` | Livingroom Cabinet | 27 | the block |
| `mdm:set_1_livingroom_cabinet_2` | Livingroom Cabinet 2 | 27 | the block |
| `mdm:set_1_livingroom_hanging_cabinet` | Livingroom Hanging Cabinet | 27 | the block |
| `mdm:set_1_night_table` | Night Table | 27 | the block |
| `mdm:set_1_oven_microwave` | Oven Microwave | 27 | `main`, `top` |
| `mdm:set_1_tv_stand` | TV Stand | 27 | the block |
| `mdm:set_1_tv_stand_middle` | TV Stand Middle | 18 | the block |
| `mdm:set_2_cabinet_1` | Cabinet 1 | 27 | the block |
| `mdm:set_2_cabinet_2` | Cabinet 2 | 27 | the block |
| `mdm:set_2_cabinet_3` | Cabinet 3 | 18 | the block |
| `mdm:set_2_cabinet_with_drawers` | Cabinet With Drawers | 27 | the block |
| `mdm:set_3_coffe_table` | Coffe Table | 18 | the block |
| `mdm:set_3_coffe_table_decorated` | Coffe Table Decorated | 18 | the block |
| `mdm:set_3tv_stand` | Set 3tv Stand | 9 | the block |
| `mdm:shelf` | Shelf | 18 | `main`, `right` |
| `mdm:steel_shelf` | Steel Shelf | 27 | `main`, `top` |
| `mdm:tv_stand_left` | TV Stand Left | 18 | the block |
| `mdm:tv_stand_mid` | TV Stand Mid | 18 | the block |
| `mdm:tv_stand_mid_decorated` | TV Stand Mid Decorated | 18 | the block |
| `mdm:tv_stand_right` | TV Stand Right | 18 | the block |
| `mdm:white_bathroom_shelf` | White Bathroom Shelf | 18 | the block |
| `mdm:white_wood_foyer_wall_unit` | Foyer Wall Unit Bottom | 27 | `main`, `right` |

## Removed
`mdm:pair_of_boots` to `mdm:pair_of_boots_6`, `mdm:shoe_rack` to `mdm:shoe_rack_3`, `mdm:electric_guitar_black` and `mdm:electric_guitar_with_stand`, with their recipes. In an existing world they disappear.

## Repaired models and blocks
- `mdm:pouf`: its model used a newer Blockbench rotation format that Minecraft 1.20.1 rejects, so it drew as a purple and black cube.
- `mdm:office_set_1_desk_1_left_var_2`, `mdm:office_set_1_desk_1_right_var_2`, `mdm:office_set_1_desk_1_left_var_3` and `mdm:office_set_1_desk_1_right_var_3`: the cabinets' doors faced the side; they face the front now.
- `mdm:office_set_1_desk_1_left_var_3`: used the right desk's model; it has its own now.
- `mdm:bedroom_set_1wengewardrobe`: was drawn in grey wood.
- `mdm:bedroom_set_1wengenightstand_1`: always faced the same way; it turns to face the player now, like the other nightstands.
- `mdm:bathroom_shelf_02`: the cabinet stopped short of its base at one end; the top and an end panel now close it into a small open shelf.
- `mdm:set_1_corner_cabinet_2`: the ground next to it showed through (x-ray).

## Known limits
- A few tilted model parts can't be cut at a block boundary, so they are drawn by the part their middle is in and reach into the next space by a few pixels, most of all on the armchairs (7 pixels).
