"""Writes FIXES.md, the list of what MFF changes in MDM, with its tables made from the furniture data.

Run after tools/furniture_data.py:
    python tools/write_fixes.py
"""
import glob
import json
import os

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..")
DATA = os.path.join(ROOT, "src", "main", "resources", "mff", "blocks")
LANG = os.path.join(ROOT, "src", "main", "resources", "assets", "mdm", "lang", "en_us.json")

INTRO = """# Fixes

What Modern Furniture Fixed changes in MDM 26.9. MFF replaces MDM: it keeps MDM's mod id and every block id, so worlds and buildings made with MDM load with MFF.

## Every block
- **Hitboxes follow the model.** Each block state's hitbox is made from the model's own boxes, leaving out small details such as handles and feet, unless leaving them out would lose most of the object (a lantern made of thin glass, a lamp shade). Tilted parts get a staircase of boxes that follows the diagonal. Before, many hitboxes were full cubes or a single box, and some sat beside the model.
- **Mined faster with an axe or a pickaxe**, either one. Hands still work.

## Furniture larger than one block
{split_count} pieces are split into one block per space they fill. Each part draws its own slice of the model and has its own hitbox, so lighting, culling and collision work per space, and the furniture no longer pokes through walls. A space only becomes a part when the furniture reaches at least 4 pixels into it; shallower bits are drawn by the part next to them.
- **Placing** the item places every part. It is refused if a space the piece needs isn't free. A piece that reaches below the block it is placed from (a tall mirror, window blinds, curtains) is raised when it doesn't fit, so its lowest part takes the space you clicked: placed on the floor, it stands on the floor.
- **Breaking** any part breaks the whole piece and drops one item. The server setting `breakWholePiece` turns this off; then each part breaks on its own and only the part the piece was placed from drops the item.
- **Furniture placed before this mod** stays one block that draws the whole model (`part=whole`), so existing worlds and buildings look the same. Its hitbox follows the whole model, as far as one block's hitbox can reach. Breaking it and placing it again splits it.
- **Pistons** can't push furniture.
- **Mirroring** (for example WorldEdit's flip) keeps a piece whole only if it has parts on both sides.

| Block | Parts |
|---|---|
"""

STORAGE = """
## Storage
{storage_count} pieces have storage. It opens as a vanilla chest, so sorting buttons and inventory mods treat it as one, and it works with hoppers, loot tables and LootJS. Each piece has one inventory; on split furniture, only the parts listed below open it, and the first of them holds the items. Beds open from their long sides only.
- **Size** is set per block in rows of 9 slots (1 to 6) in `serverconfig/mdm-server.toml`, under `storageRows`. When a size is lowered, items in slots that no longer exist drop on the ground the next time the block loads.
- **Titles:** the storage screen shows a short name that fits the chest screen (for example "Wenge Dresser Right").
- **Sounds:** storage plays Minecraft's barrel sounds when it opens and closes. Each player can turn them off with `furnitureSounds` in `config/mdm-client.toml`.
- **Items in MDM storage** keep their slots. MDM's own storage screens are gone.
- **No more storage** on `mdm:bedroom_set_1whiteloveseat`, `mdm:set_1_island_1`, `mdm:set_1_island_2`, `mdm:set_2_corner_counter` and `mdm:set_2_hanging_shelf`. Items stored in them in an existing world are lost.

| Block | Title | Slots | Opens from |
|---|---|---|---|
"""

OUTRO = """
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
"""


def code_list(items):
    return ", ".join(f"`{i}`" for i in items)


def main():
    blocks = {}
    for path in sorted(glob.glob(os.path.join(DATA, "*.json"))):
        with open(path, encoding="utf-8") as fh:
            blocks[os.path.basename(path)[:-5]] = json.load(fh)
    with open(LANG, encoding="utf-8") as fh:
        lang = json.load(fh)

    split = [(b, d["parts"]) for b, d in blocks.items() if "parts" in d]
    storage = [(b, d["storage"]) for b, d in blocks.items() if d.get("storage")]
    out = INTRO.format(split_count=len(split))
    out += "".join(f"| `mdm:{b}` | {code_list(parts)} |\n" for b, parts in split)
    out += STORAGE.format(storage_count=len(storage))
    for b, s in storage:
        opens = code_list(s["parts"]) if "parts" in blocks[b] else "the block"
        if s.get("faces"):
            opens += " (" + " and ".join(s["faces"]) + " sides)"
        out += f"| `mdm:{b}` | {lang.get('container.mdm.' + b, '')} | {s['rows'] * 9} | {opens} |\n"
    out += OUTRO
    with open(os.path.join(ROOT, "FIXES.md"), "w", encoding="utf-8", newline="\n") as fh:
        fh.write(out)
    print(f"FIXES.md: {len(split)} split, {len(storage)} with storage")


if __name__ == "__main__":
    main()
