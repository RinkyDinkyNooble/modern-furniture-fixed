# Modern Furniture Fixed [MFF] (Unofficial Patch)

**A replacement for [MDM](https://www.curseforge.com/minecraft/mc-mods/m-d-m): remove MDM, then add this mod.** Worlds made with MDM keep working.

A fixed version of MDM (Modern Decorations Mod) 26.9 by opaleq, for Minecraft 1.20.1 and Forge 47.

- **Hitboxes that follow the furniture**, for every block. No more full-cube couches or hitboxes beside the model.
- **Furniture larger than one block is split into one block per space it fills**, so it collides, lights and hides correctly instead of poking through walls. Placing it places every part, and breaking any part breaks the whole piece and drops one item.
- **Storage that opens as a vanilla chest**, so sorting buttons and inventory mods work. 108 pieces have storage, with a size you can set per block, a title that fits the screen, and barrel sounds when it opens and closes.
- **Repaired models**, such as the pouf that drew as a purple and black cube and desk cabinets whose doors faced the side.
- **Mines faster with an axe or a pickaxe.**
- The boots, shoe racks and electric guitars are removed.

The full list, block by block, is in [FIXES.md](https://github.com/RinkyDinkyNooble/modern-furniture-fixed/blob/main/FIXES.md).

![All the furniture](https://raw.githubusercontent.com/RinkyDinkyNooble/modern-furniture-fixed/main/curseforge/images/all_furniture.png)

**Hitboxes in MDM:**

![Hitboxes in MDM](https://raw.githubusercontent.com/RinkyDinkyNooble/modern-furniture-fixed/main/curseforge/images/Before-Hitbox.png)

**Hitboxes in Modern Furniture Fixed:**

![Hitboxes in Modern Furniture Fixed](https://raw.githubusercontent.com/RinkyDinkyNooble/modern-furniture-fixed/main/curseforge/images/After-Hitbox.png)

Needed on both the client and the server. Furniture placed before installing MFF keeps its old look until it is broken and placed again.

## Settings

- `serverconfig/mdm-server.toml` (per world): `breakWholePiece` (breaking a part breaks the whole piece, default on) and `storageRows` (the storage size of each piece, 1 to 6 rows of 9 slots).
- `config/mdm-client.toml`: `furnitureSounds` (storage open and close sounds, default on).

## Reason For Existing

MDM's furniture looks good, but its hitboxes, storage screens and some models are broken. This fixes them for modpacks that use it.

### Credit

This is an unofficial patch. MDM is made by opaleq ([cookiecraftmods.com](https://cookiecraftmods.com/)). The changes are 0BSD; MDM's own code and assets are under the Academic Free License 3.0.
