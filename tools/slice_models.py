"""Splits MDM furniture that is larger than one block into one model and one hitbox per block space.

For each block id, reads its blockstate and model chain from MDM's jar, cuts every model element at the
block-space boundaries (scaling face UVs to match), and writes:
  - assets/mdm/blockstates/<id>.json   the original variants for part=whole, plus one per part and facing
  - assets/mdm/models/block/split/<id>/<part>.json   one model per part, moved into its own block space
  - mff/parts/<id>.json   each part's offset and hitbox boxes (facing north), read by the mod; the
    unsplit piece's hitbox is all of them together

Part names describe where a part sits as seen by someone looking at the front of the furniture:
right/left (x), top/bottom (y), front/back (z), with a 2 for two spaces away. "main" is the space the
item is placed in, and "whole" is the unsplit piece, the default for blocks placed before the split.

    python tools/slice_models.py libs/mdm-26.9.jar out_dir mdm:black_office_desk mdm:bedroom_set_1grey_bunk_bed
"""
import json
import math
import os
import re
import sys
import zipfile

import numpy as np

# Vertex order of each face, as (axis value is max?) per axis x, y, z. Matches Minecraft's FaceInfo.
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
    # Each in-plane axis maps linearly onto (u, v); find the vertex pair that differs on it.
    def lerp_uv(pos):
        base = next(i for i, vtx in enumerate(verts) if all(vtx[a] == 0 for a in plane))
        res = np.array(old[base], dtype=float)
        for a in plane:
            j = next(i for i, vtx in enumerate(verts) if vtx[a] == 1 and all(vtx[b] == 0 for b in plane if b != a))
            span = t[a] - f[a]
            k = (pos[a] - f[a]) / span if span else 0.0
            res += (np.array(old[j], dtype=float) - np.array(old[base], dtype=float)) * k
        return res
    new = []
    for vtx in verts:
        pos = [nt[a] if vtx[a] else nf[a] for a in range(3)]
        new.append(lerp_uv(pos))
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


class Jar:
    def __init__(self, path):
        self.z = zipfile.ZipFile(path)

    def json(self, path):
        txt = self.z.read(path).decode("utf-8")
        return json.loads(re.sub(r"(?m)^\s*//.*$", "", txt))

    def model_chain(self, ref):
        textures, elements, extra = {}, None, {}
        chain, cur = [], ref
        while cur:
            ns, p = cur.split(":", 1) if ":" in cur else ("minecraft", cur)
            m = self.json(f"assets/{ns}/models/{p}.json")
            chain.append(m)
            if elements is None and "elements" in m:
                elements = m["elements"]
            cur = m.get("parent")
        for m in reversed(chain):
            textures.update(m.get("textures", {}))
            for k in ("render_type", "ambientocclusion"):
                if k in m:
                    extra[k] = m[k]
        return elements, textures, extra


def cells_of(el):
    f, t = el["from"], el["to"]
    ranges = []
    for a in range(3):
        lo = math.floor(f[a] / 16)
        hi = math.ceil(t[a] / 16) - 1 if t[a] > f[a] else lo
        ranges.append(range(lo, max(lo, hi) + 1))
    return [(x, y, z) for x in ranges[0] for y in ranges[1] for z in ranges[2]]


def clip_element(el, cell, occupied):
    """The element cut to one block space and moved into 0-16, or None if nothing of it is there."""
    f, t = el["from"], el["to"]
    lo = [c * 16 for c in cell]
    hi = [c * 16 + 16 for c in cell]
    nf = [max(f[a], lo[a]) for a in range(3)]
    nt = [min(t[a], hi[a]) for a in range(3)]
    for a in range(3):
        if f[a] == t[a]:
            # A flat element belongs to the space its plane is in. On a boundary it goes to the upper
            # space if that space is part of the furniture, otherwise to the lower one.
            upper = list(cell)
            upper[a] += 1
            inside = lo[a] <= f[a] < hi[a] or (f[a] == hi[a] and tuple(upper) not in occupied)
            if not inside:
                return None
        elif nt[a] <= nf[a]:
            return None
    rot = el.get("rotation")
    if rot and rot.get("angle", 0) != 0:
        raise ValueError("rotated elements are not split yet")
    out = {k: v for k, v in el.items() if k not in ("from", "to", "faces", "rotation")}
    out["from"] = [round(nf[a] - lo[a], 4) for a in range(3)]
    out["to"] = [round(nt[a] - lo[a], 4) for a in range(3)]
    if rot:
        out["rotation"] = dict(rot, origin=[rot["origin"][a] - lo[a] for a in range(3)])
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
            on_edge = (side_new - lo[axis]) == (16 if is_max else 0)
            if not on_edge or cull != d:
                del nfc["cullface"]
        faces[d] = nfc
    out["faces"] = faces
    return out


def hitbox_boxes(elements):
    """The part's hitbox: its elements snapped to whole pixels, leaving out small details (handles, feet,
    thin trim) and boxes that sit inside other boxes."""
    boxes = []
    for el in elements:
        f, t = el["from"], el["to"]
        size = sorted(t[a] - f[a] for a in range(3))
        if size[0] * size[1] * size[2] < 8 and size[0] >= 1:
            continue
        if size[0] < 1 and size[1] * size[2] < 64:
            continue
        lo = [max(0, math.floor(f[a])) for a in range(3)]
        hi = [min(16, max(math.ceil(t[a]), lo[a] + 1)) for a in range(3)]
        boxes.append(lo + hi)
    boxes = sorted({tuple(b) for b in boxes}, key=lambda b: -(b[3] - b[0]) * (b[4] - b[1]) * (b[5] - b[2]))
    kept = []
    for b in boxes:
        if not any(all(k[a] <= b[a] and b[a + 3] <= k[a + 3] for a in range(3)) for k in kept):
            kept.append(b)
    return [list(b) for b in kept]


def split_block(jar, block_id):
    ns, name = block_id.split(":", 1)
    bs = jar.json(f"assets/{ns}/blockstates/{name}.json")
    north = bs["variants"]["facing=north"]
    elements, textures, extra = jar.model_chain(north["model"])
    occupied = {c for el in elements for c in cells_of(el)}
    cells = sorted(occupied | {(0, 0, 0)}, key=lambda c: (c[1], c[2], -c[0]))
    parts, models = {}, {}
    for cell in cells:
        clipped = [c for c in (clip_element(el, cell, occupied) for el in elements) if c]
        if not clipped and cell != (0, 0, 0):
            continue
        pname = part_name(cell)
        parts[pname] = {"offset": list(cell), "boxes": hitbox_boxes(clipped)}
        models[pname] = dict({"textures": textures, "elements": clipped}, **extra)
    new_bs = {"variants": {}}
    for key, v in bs["variants"].items():
        new_bs["variants"][key + ",part=whole"] = v
        for pname in parts:
            nv = {k: val for k, val in v.items() if k != "model"}
            new_bs["variants"][f"{key},part={pname}"] = dict({"model": f"{ns}:block/split/{name}/{pname}"}, **nv)
    data = {"block": block_id, "parts": parts}
    return new_bs, models, data


def write_json(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8", newline="\n") as fh:
        json.dump(obj, fh, indent=2)
        fh.write("\n")


def main(jar_path, out, block_ids):
    jar = Jar(jar_path)
    for bid in block_ids:
        ns, name = bid.split(":", 1)
        new_bs, models, data = split_block(jar, bid)
        write_json(os.path.join(out, f"assets/{ns}/blockstates/{name}.json"), new_bs)
        for pname, m in models.items():
            write_json(os.path.join(out, f"assets/{ns}/models/block/split/{name}/{pname}.json"), m)
        write_json(os.path.join(out, f"mff/parts/{name}.json"), data)
        print(bid, {p: (d["offset"], len(d["boxes"])) for p, d in data["parts"].items()})


if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2], sys.argv[3:])
