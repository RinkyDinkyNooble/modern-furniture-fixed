package com.rinkynooble.modernfurniturefixed;

import com.cookiecraftmods.mdm.MdmMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;

/**
 * The parts of one piece of split furniture: where each part sits relative to the main part, and its hitbox,
 * for each facing. Read from {@code mff/parts/<block>.json}, which gives offsets and boxes for facing north.
 */
public final class SplitLayout {
    private static final Direction[] FACINGS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private final Map<SplitPart, Vec3i> offsets = new EnumMap<>(SplitPart.class);
    private final Map<SplitPart, VoxelShape[]> shapes = new EnumMap<>(SplitPart.class);
    private final EnumProperty<SplitPart> property;

    private SplitLayout(JsonObject json) {
        Map<SplitPart, List<double[]>> boxes = new EnumMap<>(SplitPart.class);
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject("parts").entrySet()) {
            SplitPart part = SplitPart.byName(entry.getKey());
            JsonObject data = entry.getValue().getAsJsonObject();
            JsonArray offset = data.getAsJsonArray("offset");
            this.offsets.put(part, new Vec3i(offset.get(0).getAsInt(), offset.get(1).getAsInt(), offset.get(2).getAsInt()));
            List<double[]> list = new ArrayList<>();
            for (JsonElement element : data.getAsJsonArray("boxes")) {
                JsonArray array = element.getAsJsonArray();
                double[] box = new double[6];
                for (int i = 0; i < 6; i++) {
                    box[i] = array.get(i).getAsDouble();
                }
                list.add(box);
            }
            boxes.put(part, list);
        }

        List<SplitPart> values = new ArrayList<>();
        values.add(SplitPart.WHOLE);
        values.addAll(this.offsets.keySet());
        this.property = EnumProperty.create("part", SplitPart.class, values);

        VoxelShape[] whole = new VoxelShape[4];
        for (int turns = 0; turns < 4; turns++) {
            VoxelShape all = Shapes.empty();
            for (Map.Entry<SplitPart, List<double[]>> entry : boxes.entrySet()) {
                VoxelShape shape = Shapes.empty();
                for (double[] box : entry.getValue()) {
                    double[] b = rotateBox(box, turns);
                    shape = Shapes.or(shape, Shapes.box(b[0] / 16, b[1] / 16, b[2] / 16, b[3] / 16, b[4] / 16, b[5] / 16));
                }
                shape = shape.optimize();
                this.shapes.computeIfAbsent(entry.getKey(), part -> new VoxelShape[4])[turns] = shape;
                Vec3i offset = rotate(this.offsets.get(entry.getKey()), turns);
                all = Shapes.or(all, shape.move(offset.getX(), offset.getY(), offset.getZ()));
            }
            whole[turns] = all.optimize();
        }
        this.shapes.put(SplitPart.WHOLE, whole);
    }

    public static SplitLayout load(String blockName) {
        Path path = ModList.get().getModFileById(MdmMod.MODID).getFile().findResource("mff", "parts", blockName + ".json");
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return new SplitLayout(JsonParser.parseReader(reader).getAsJsonObject());
        } catch (IOException e) {
            throw new UncheckedIOException("Can't read " + path, e);
        }
    }

    /** The block state property that holds the part, with WHOLE first so it is the default. */
    public EnumProperty<SplitPart> property() {
        return this.property;
    }

    /** Every part of the split piece, without WHOLE. */
    public Set<SplitPart> parts() {
        return this.offsets.keySet();
    }

    public VoxelShape shape(Direction facing, SplitPart part) {
        return this.shapes.get(part)[turns(facing)];
    }

    /** Where the main part is, for a part at {@code pos}. */
    public BlockPos mainPos(BlockPos pos, Direction facing, SplitPart part) {
        if (part == SplitPart.WHOLE) {
            return pos;
        }
        return pos.subtract(rotate(this.offsets.get(part), turns(facing)));
    }

    /** Where a part goes, for a main part at {@code main}. */
    public BlockPos partPos(BlockPos main, Direction facing, SplitPart part) {
        return main.offset(rotate(this.offsets.get(part), turns(facing)));
    }

    private static int turns(Direction facing) {
        for (int i = 0; i < 4; i++) {
            if (FACINGS[i] == facing) {
                return i;
            }
        }
        return 0;
    }

    /** Turns an offset clockwise (seen from above) a quarter at a time, as the blockstate's y rotation turns the model. */
    private static Vec3i rotate(Vec3i offset, int turns) {
        int x = offset.getX();
        int z = offset.getZ();
        for (int i = 0; i < turns; i++) {
            int t = x;
            x = -z;
            z = t;
        }
        return new Vec3i(x, offset.getY(), z);
    }

    /** Turns a box (in pixels, 0 to 16) the same way. */
    private static double[] rotateBox(double[] box, int turns) {
        double x1 = box[0], z1 = box[2], x2 = box[3], z2 = box[5];
        for (int i = 0; i < turns; i++) {
            double nx1 = 16 - z2, nx2 = 16 - z1;
            z1 = x1;
            z2 = x2;
            x1 = nx1;
            x2 = nx2;
        }
        return new double[] {x1, box[1], z1, x2, box[4], z2};
    }
}
