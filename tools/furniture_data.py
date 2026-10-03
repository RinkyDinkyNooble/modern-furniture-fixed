"""Generates the furniture data the mod reads, from MDM's models and tools/furniture_spec.json.

For every block, a hitbox for each block state, made from the model's own boxes. Furniture larger than one
block is split into one block per space it fills: each part gets its own slice of the model and its own
hitbox, and the original model stays as part=whole, the default for blocks placed before the split.

Reads src/main/resources (blockstates and models), the block registry (MdmModBlocks.java) and the spec.
Writes, under src/main/resources:
  assets/mdm/blockstates/<id>.json              for split blocks, and blocks the spec gives a facing
  assets/mdm/models/block/split/<id>/<part>.json
  mff/blocks/<id>.json                           per variant: hitbox boxes in pixels, and for parts the
                                                 offset from the main part; the parts; storage settings
  mff/index.json                                 block class name -> block id
  data/minecraft/tags/blocks/mineable/axe.json and pickaxe.json

Part names describe where a part sits as seen by someone looking at the front of the furniture:
right/left (x), top/bottom (y), front/back (z), with a 2 for two spaces away. "main" is the space the
item is placed in.

    python tools/furniture_data.py
"""
import glob
import json
import math
import os
import re
import shutil

import numpy as np

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..")
RES = os.path.join(ROOT, "src", "main", "resources")
ASSETS = os.path.join(RES, "assets")
REGISTRY = os.path.join(ROOT, "src", "main", "java", "com", "cookiecraftmods", "mdm", "init", "MdmModBlocks.java")
SPEC = os.path.join(ROOT, "tools", "furniture_spec.json")

# Vertex order of each face, as (is the max side?) per axis x, y, z. Matches Minecraft's FaceInfo.
FACE_VERTS = {
    "down":  [(0, 0, 1), (0, 0, 0), (1, 0, 0), (1, 0, 1)],
    "up":    [(0, 1, 0), (0, 1, 1), (1, 1, 1), (1, 1, 0)],
    "north": [(1, 1, 0), (1, 0, 0), (0, 0, 0), (0, 1, 0)],
    "south": [(0, 1, 1), (0, 0, 1), (1, 0, 1), (1, 1, 1)],
    "west":  [(0, 1, 0), (0, 0, 0), (0, 0, 1), (0, 1, 1)],
    "east":  [(1, 1, 1), (1, 0, 1), (1, 0, 0), (1, 1, 0)],
}
# The axis a face lies on, and whether it is the element's max side on that axis.
FACE_AXIS = {"down": (1, 0), "up": (1, 1), "north": (2, 0), "south": (2, 1), "west": (0, 0), "east": (0, 1)}


def default_uv(d, f, t):
    x1, y1, z1 = f
    x2, y2, z2 = t
    return {
        "down": [x1, 16 - z2, x2, 16 - z1], "up": [x1, z1, x2, z2],
        "north": [16 - x2, 16 - y2, 16 - x1, 16 - y1], "south": [x1, 16 - y2, x2, 16 - y1],
        "west": [z1, 16 - y2, z2, 16 - y1], "east": [16 - z2, 16 - y2, 16 - z1, 16 - y1],
    }[d]


def vertex_uvs(uv, rotation):
    """The (u, v) Minecraft gives each of a face's four vertices."""
    out = []
    for i in range(4):
        s = (i + rotation // 90) % 4
        out.append((uv[0] if s in (0, 1) else uv[2], uv[1] if s in (0, 3) else uv[3]))
    return out


def clip_face_uv(d, f, t, nf, nt, uv, rotation):
    """UVs for face d after the element [f, t] is cut down to [nf, nt]."""
    verts = FACE_VERTS[d]
    old = vertex_uvs(uv, rotation)
    axis = FACE_AXIS[d][0]
    plane = [a for a in range(3) if a != axis]
    base = next(i for i, vtx in enumerate(verts) if all(vtx[a] == 0 for a in plane))

    def lerp_uv(pos):
        res = np.array(old[base], dtype=float)
        for a in plane:
            j = next(i for i, vtx in enumerate(verts) if vtx[a] == 1 and all(vtx[b] == 0 for b in plane if b != a))
            span = t[a] - f[a]
            k = (pos[a] - f[a]) / span if span else 0.0
            res += (np.array(old[j], dtype=float) - np.array(old[base], dtype=float)) * k
        return res

    new = [lerp_uv([nt[a] if vtx[a] else nf[a] for a in range(3)]) for vtx in verts]
    i0 = next(i for i in range(4) if (i + rotation // 90) % 4 == 0)
    i2 = next(i for i in range(4) if (i + rotation // 90) % 4 == 2)
    return [round(float(v), 4) for v in (new[i0][0], new[i0][1], new[i2][0], new[i2][1])]


def part_name(off):
    x, y, z = off
    if off == (0, 0, 0):
        return "main"
    bits = []
    for v, neg, pos in ((y, "bottom", "top"), (z, "front", "back"), (x, "right", "left")):
        if v:
            bits.append((neg if v < 0 else pos) + (str(abs(v)) if abs(v) > 1 else ""))
    return "_".join(bits)


def load(path):
    with open(path, encoding="utf-8") as fh:
        return json.loads(re.sub(r"(?m)^\s*//.*$", "", fh.read()))


def write(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8", newline="\n") as fh:
        json.dump(obj, fh, indent=2)
        fh.write("\n")


def model_chain(ref):
    """(elements, textures, extra) for a model, following its parents within the mod's files."""
    textures, elements, extra, chain = {}, None, {}, []
    cur = ref
    while cur:
        ns, p = cur.split(":", 1) if ":" in cur else ("minecraft", cur)
        path = os.path.join(ASSETS, ns, "models", p + ".json")
        if not os.path.exists(path):
            break
        m = load(path)
        chain.append(m)
        if elements is None and "elements" in m:
            elements = m["elements"]
        cur = m.get("parent")
    for m in reversed(chain):
        textures.update(m.get("textures", {}))
        for k in ("render_type", "ambientocclusion"):
            if k in m:
                extra[k] = m[k]
    return elements or [], textures, extra


# ---------------------------------------------------------------- rotation --
def rot_point(p, x, y):
    px, py, pz = p
    for _ in range((x // 90) % 4):
        py, pz = pz, 16 - py
    for _ in range((y // 90) % 4):
        px, pz = 16 - pz, px
    return px, py, pz


def rot_box(b, x, y):
    a = rot_point(b[:3], x, y)
    c = rot_point(b[3:], x, y)
    return [round(min(a[i], c[i]), 4) for i in range(3)] + [round(max(a[i], c[i]), 4) for i in range(3)]


def rot_vec(v, x, y):
    vx, vy, vz = v
    for _ in range((x // 90) % 4):
        vy, vz = vz, -vy
    for _ in range((y // 90) % 4):
        vx, vz = -vz, vx
    return [vx, vy, vz]


# ---------------------------------------------------------------- splitting --
def is_rotated(el):
    rot = el.get("rotation")
    return bool(rot) and rot.get("angle", 0) != 0


def center_cell(el):
    return tuple(math.floor((el["from"][a] + el["to"][a]) / 2 / 16) for a in range(3))


AXES = {"x": 0, "y": 1, "z": 2}


def cells_of(el):
    """The block spaces an element reaches. A rotated element is cut only along its rotation axis; across the
    other two it stays in the space its centre is in."""
    f, t = el["from"], el["to"]
    center = center_cell(el)
    axis = AXES[el["rotation"]["axis"]] if is_rotated(el) else None
    ranges = []
    for a in range(3):
        if axis is not None and a != axis:
            ranges.append(range(center[a], center[a] + 1))
            continue
        lo = math.floor(f[a] / 16)
        hi = math.ceil(t[a] / 16) - 1 if t[a] > f[a] else lo
        ranges.append(range(lo, max(lo, hi) + 1))
    return [(x, y, z) for x in ranges[0] for y in ranges[1] for z in ranges[2]]


def bounds(cell, part_cells):
    """Where a part's slice of the model starts and ends on each axis. Towards a neighbouring space that
    is not a part, it doesn't end: shallow bits that reach into such a space stay with this part."""
    lo, hi = [], []
    for a in range(3):
        below, above = list(cell), list(cell)
        below[a] -= 1
        above[a] += 1
        lo.append(cell[a] * 16 if tuple(below) in part_cells else -math.inf)
        hi.append(cell[a] * 16 + 16 if tuple(above) in part_cells else math.inf)
    return lo, hi


def clip_element(el, cell, part_cells):
    """The element cut to one part's slice and moved so the part's space is 0-16, or None if nothing of it
    is there. A rotated element is cut only along its rotation axis (see cells_of)."""
    lo, hi = bounds(cell, part_cells)
    origin = [c * 16 for c in cell]
    f, t = el["from"], el["to"]
    if is_rotated(el):
        axis = AXES[el["rotation"]["axis"]]
        center = center_cell(el)
        if any(cell[a] != center[a] for a in range(3) if a != axis):
            return None
        nf = [max(f[a], lo[a]) if a == axis else f[a] for a in range(3)]
        nt = [min(t[a], hi[a]) if a == axis else t[a] for a in range(3)]
    else:
        nf = [max(f[a], lo[a]) for a in range(3)]
        nt = [min(t[a], hi[a]) for a in range(3)]
    for a in range(3):
        if f[a] == t[a]:
            # A flat element belongs to the space its plane is in. On a boundary it goes to the upper
            # space if that space is part of the furniture, otherwise to the lower one.
            inside = lo[a] <= f[a] < hi[a] or (f[a] == hi[a] and hi[a] != math.inf)
            upper = list(cell)
            upper[a] += 1
            if f[a] == hi[a] and tuple(upper) in part_cells:
                inside = False
            if not inside:
                return None
        elif nt[a] <= nf[a]:
            return None
    out = {k: v for k, v in el.items() if k not in ("from", "to", "faces", "rotation")}
    out["from"] = [round(nf[a] - origin[a], 4) for a in range(3)]
    out["to"] = [round(nt[a] - origin[a], 4) for a in range(3)]
    if "rotation" in el:
        out["rotation"] = dict(el["rotation"], origin=[el["rotation"].get("origin", [8, 8, 8])[a] - origin[a] for a in range(3)])
    faces = {}
    for d, fc in el.get("faces", {}).items():
        axis, is_max = FACE_AXIS[d]
        side_old = t[axis] if is_max else f[axis]
        side_new = nt[axis] if is_max else nf[axis]
        if side_old != side_new:
            continue  # this side was cut away, so the face is inside the furniture now
        if any(nt[a] <= nf[a] for a in range(3) if a != axis):
            continue
        nfc = dict(fc)
        uv = fc.get("uv") or default_uv(d, f, t)
        nfc["uv"] = clip_face_uv(d, f, t, nf, nt, uv, fc.get("rotation", 0))
        cull = fc.get("cullface")
        if cull:
            on_edge = (side_new - origin[axis]) == (16 if is_max else 0)
            if not on_edge or cull != d:
                del nfc["cullface"]
        faces[d] = nfc
    out["faces"] = faces
    return out


# ---------------------------------------------------------------- hitboxes --
def hitbox(elements, lo=0, hi=16):
    """The hitbox: the elements snapped to whole pixels, leaving out small details (handles, feet, thin trim)
    and boxes inside other boxes. If that leaves nothing, every element counts."""
    def boxes(skip_details):
        out = []
        for el in elements:
            f, t = el["from"], el["to"]
            size = sorted(t[a] - f[a] for a in range(3))
            if skip_details:
                if size[0] * size[1] * size[2] < 8 and size[0] >= 1:
                    continue
                if size[0] < 1 and size[1] * size[2] < 64:
                    continue
            b0 = [max(lo, math.floor(f[a])) for a in range(3)]
            b1 = [min(hi, max(math.ceil(t[a]), b0[a] + 1)) for a in range(3)]
            if all(b1[a] > b0[a] for a in range(3)):
                out.append(b0 + b1)
        return out

    found = boxes(True) or boxes(False) or [[0, 0, 0, 16, 16, 16]]
    found = sorted({tuple(b) for b in found}, key=lambda b: -(b[3] - b[0]) * (b[4] - b[1]) * (b[5] - b[2]))
    kept = []
    for b in found:
        if not any(all(k[a] <= b[a] and b[a + 3] <= k[a + 3] for a in range(3)) for k in kept):
            kept.append(b)
    return [list(b) for b in kept]


def filled(boxes):
    """How much of one block space the boxes fill, from 0 to 1."""
    g = np.zeros((16, 16, 16), dtype=bool)
    for b in boxes:
        g[max(0, b[0]):min(16, b[3]), max(0, b[1]):min(16, b[4]), max(0, b[2]):min(16, b[5])] = True
    return float(g.mean())


def rows_for(fraction):
    return 1 if fraction < 0.2 else 2 if fraction < 0.5 else 3


# ---------------------------------------------------------------- blocks --
def join(key, extra):
    return f"{key},{extra}" if key else extra


def base_variants(bs):
    """The variants without the part property: the whole-piece ones of a block that was split before."""
    v = bs["variants"]
    if not any("part=" in k for k in v):
        return dict(v)
    out = {}
    for k, val in v.items():
        props = [p for p in k.split(",") if p]
        if "part=whole" in props:
            out[",".join(p for p in props if p != "part=whole")] = val
    return out


def first(val):
    return val[0] if isinstance(val, list) else val


MIN_DEPTH = 4


def reach(elements, cell):
    """How far, in pixels, the furniture reaches into a neighbouring block space."""
    best = 0.0
    lo = [c * 16 for c in cell]
    for el in elements:
        depth = math.inf
        for a in range(3):
            f, t = el["from"][a], el["to"][a]
            if cell[a] > 0:
                depth = min(depth, t - lo[a])
            elif cell[a] < 0:
                depth = min(depth, lo[a] + 16 - f)
            else:
                depth = min(depth, 16 if min(t, 16) > max(f, 0) or f == t else -1)
        best = max(best, depth)
    return best


def oversized(elements):
    return any(min(e["from"]) < 0 or max(e["to"]) > 16 for e in elements)


def build(bid, spec, report):
    bs_path = os.path.join(ASSETS, "mdm", "blockstates", bid + ".json")
    base = base_variants(load(bs_path))
    rewrite = False
    if bid in spec["add_facing"] and list(base) == [""]:
        v = first(base[""])
        base = {f"facing={f}": dict(v, y=y) if y else dict(v) for f, y in (("north", 0), ("east", 90), ("south", 180), ("west", 270))}
        rewrite = True

    elements, textures, extra = model_chain(first(next(iter(base.values())))["model"])
    data = {"variants": {}}
    part_cells = {(0, 0, 0)}
    if oversized(elements):
        occupied = {c for el in elements for c in cells_of(el)}
        part_cells |= {c for c in occupied if reach(elements, c) >= MIN_DEPTH}
    split = len(part_cells) > 1
    if not split:
        for key, val in base.items():
            v = first(val)
            els = model_chain(v["model"])[0]
            data["variants"][key] = {"shape": [rot_box(b, v.get("x", 0), v.get("y", 0)) for b in hitbox(els)]}
        part_fill = {"": filled(hitbox(elements))}
        if rewrite or base != load(bs_path)["variants"]:
            write(bs_path, {"variants": base})
    else:
        models = {first(val)["model"] for val in base.values()}
        assert len(models) == 1, (bid, models)
        parts = {}
        for cell in sorted(part_cells, key=lambda c: (c[1], c[2], -c[0])):
            clipped = [c for c in (clip_element(el, cell, part_cells) for el in elements) if c]
            parts[part_name(cell)] = (cell, clipped)
        counted = sum(len(c) for _, c in parts.values())
        if counted > len(elements) + sum(len(cells_of(el)) - 1 for el in elements):
            report["double"].append(bid)
        for pname, (cell, clipped) in parts.items():
            for el in clipped:
                if is_rotated(el):
                    over = max(max(-min(el["from"][a], el["to"][a]), max(el["from"][a], el["to"][a]) - 16) for a in range(3))
                    if over > 0:
                        report["overhang"].append((bid, pname, round(over, 2)))
        for pname, (cell, clipped) in parts.items():
            write(os.path.join(ASSETS, "mdm", "models", "block", "split", bid, pname + ".json"),
                  dict({"textures": textures, "elements": clipped}, **extra))
        new_bs = {"variants": {}}
        whole_boxes = hitbox(elements, -16, 32)
        part_boxes = {p: hitbox(clipped) for p, (cell, clipped) in parts.items()}
        for key, val in base.items():
            v = first(val)
            x, y = v.get("x", 0), v.get("y", 0)
            new_bs["variants"][join(key, "part=whole")] = val
            data["variants"][join(key, "part=whole")] = {"shape": [rot_box(b, x, y) for b in whole_boxes]}
            for pname, (cell, clipped) in parts.items():
                nv = {k: val2 for k, val2 in v.items() if k != "model"}
                new_bs["variants"][join(key, "part=" + pname)] = dict({"model": f"mdm:block/split/{bid}/{pname}"}, **nv)
                data["variants"][join(key, "part=" + pname)] = {
                    "shape": [rot_box(b, x, y) for b in part_boxes[pname]],
                    "offset": rot_vec(cell, x, y),
                }
        write(bs_path, new_bs)
        data["parts"] = list(parts)
        part_fill = {p: filled(b) for p, b in part_boxes.items()}
        report["split"].append((bid, list(parts)))

    if bid in spec["storage"]:
        storage = {}
        if split:
            chosen = spec["storage_parts"].get(bid)
            if chosen is None:
                chosen = [p for p, f in part_fill.items() if f >= 0.3] or [max(part_fill, key=part_fill.get)]
            storage["parts"] = chosen
            fill = max(part_fill[p] for p in chosen)
        else:
            fill = part_fill[""]
        storage["rows"] = spec["rows"].get(bid, rows_for(fill))
        storage["sound"] = "metal" if any(s in bid for s in spec["metal_sound"]) else "wood"
        data["storage"] = storage
        report["storage"].append((bid, storage, round(fill, 2)))
    write(os.path.join(RES, "mff", "blocks", bid + ".json"), data)


def main():
    spec = load(SPEC)
    src = open(REGISTRY, encoding="utf-8").read()
    registry = re.findall(r'register\(\s*"([a-z0-9_]+)",\s*(\w+)::new', src)
    ids = [bid for bid, _ in registry]
    missing = [b for b in spec["storage"] if b not in ids]
    assert not missing, missing

    shutil.rmtree(os.path.join(ASSETS, "mdm", "models", "block", "split"), ignore_errors=True)
    shutil.rmtree(os.path.join(RES, "mff"), ignore_errors=True)
    report = {"split": [], "storage": [], "overhang": [], "double": []}
    for bid in ids:
        build(bid, spec, report)
    write(os.path.join(RES, "mff", "index.json"), {cls: bid for bid, cls in registry})
    tag = {"replace": False, "values": [f"mdm:{b}" for b in sorted(ids)]}
    for tool in ("axe", "pickaxe"):
        write(os.path.join(RES, "data", "minecraft", "tags", "blocks", "mineable", tool + ".json"), tag)

    print(f"{len(ids)} blocks, {len(report['split'])} split, {len(report['storage'])} with storage, "
          f"{len(report['overhang'])} rotated elements reaching past their space, {len(report['double'])} blocks to check for elements in two parts")
    return report


if __name__ == "__main__":
    main()
