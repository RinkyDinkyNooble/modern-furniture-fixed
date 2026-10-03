# Fixes

What Modern Furniture Fixed changes in MDM 26.9, block by block.

## Every split piece
Furniture larger than one block is split into one block per space it fills. Each part draws its own slice of the model and has its own hitbox.
- **Placing** the item places every part. It is refused if a space the piece needs isn't free.
- **Breaking** any part breaks the whole piece and drops one item. The server setting `breakWholePiece` in `serverconfig/mdm-server.toml` turns this off; then each part breaks on its own, and only the part the piece was placed from drops the item and its contents.
- **Furniture placed before this mod** stays one block that draws the whole model (`part=whole`), so existing worlds and buildings look the same. Its hitbox now matches the model, as far as one block's hitbox can reach. Breaking it and placing it again splits it.
- **Pistons** can't push the parts.
- **Mirroring** (for example WorldEdit's flip) keeps a piece whole only if it has parts on both sides.

## Black Office Desk (`mdm:black_office_desk`)
- Split into three parts: `left` (the cabinet), `main` (the middle), `right` (the leg).
- Hitboxes follow the model. Before, the hitbox was three blocks wide at desk height, and the leg's box was 28 pixels away from the leg.

## Bedroom Set 1 Grey Bunk Bed (`mdm:bedroom_set_1grey_bunk_bed`)
- Split into four parts: `main`, `right`, `top` and `top_right`.
- Hitboxes follow the frame and mattresses. Before, it was one box two blocks wide and 28 pixels tall.
- Storage opens as a vanilla chest with 18 slots (two rows), from any part, so sorting buttons and inventory mods treat it as a chest. It had 14 slots in MDM's own screen; items in slots 0 to 13 stay where they were.
