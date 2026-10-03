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
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;

/**
 * One block's furniture data, read from {@code mff/blocks/<id>.json}: a hitbox per block state, and for split
 * furniture its parts and where each part sits relative to the main part. Storage settings too, if it has storage.
 */
public final class FurnitureData {
    private static final Map<String, String> INDEX = loadIndex();
    private static final Map<String, FurnitureData> BY_ID = new ConcurrentHashMap<>();

    private final String id;
    private final List<Variant> variants = new ArrayList<>();
    @Nullable
    private final EnumProperty<SplitPart> part;
    @Nullable
    private final Storage storage;

    private record Variant(Map<String, String> properties, VoxelShape shape, Vec3i offset) {
    }

    /** The parts that open the storage; the first one holds the items. */
    public record Storage(int rows, String sound, List<SplitPart> parts) {
    }

    private FurnitureData(String id, JsonObject json) {
        this.id = id;
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject("variants").entrySet()) {
            Map<String, String> properties = new LinkedHashMap<>();
            for (String pair : entry.getKey().split(",")) {
                if (!pair.isEmpty()) {
                    String[] kv = pair.split("=", 2);
                    properties.put(kv[0], kv[1]);
                }
            }
            JsonObject data = entry.getValue().getAsJsonObject();
            VoxelShape shape = Shapes.empty();
            for (JsonElement element : data.getAsJsonArray("shape")) {
                JsonArray b = element.getAsJsonArray();
                shape = Shapes.or(shape, Shapes.box(
                        b.get(0).getAsDouble() / 16, b.get(1).getAsDouble() / 16, b.get(2).getAsDouble() / 16,
                        b.get(3).getAsDouble() / 16, b.get(4).getAsDouble() / 16, b.get(5).getAsDouble() / 16));
            }
            Vec3i offset = Vec3i.ZERO;
            if (data.has("offset")) {
                JsonArray o = data.getAsJsonArray("offset");
                offset = new Vec3i(o.get(0).getAsInt(), o.get(1).getAsInt(), o.get(2).getAsInt());
            }
            this.variants.add(new Variant(properties, shape.optimize(), offset));
        }

        if (json.has("parts")) {
            List<SplitPart> values = new ArrayList<>();
            values.add(SplitPart.WHOLE);
            for (JsonElement element : json.getAsJsonArray("parts")) {
                values.add(SplitPart.byName(element.getAsString()));
            }
            this.part = EnumProperty.create("part", SplitPart.class, values);
        } else {
            this.part = null;
        }

        if (json.has("storage")) {
            JsonObject s = json.getAsJsonObject("storage");
            List<SplitPart> parts = new ArrayList<>();
            if (s.has("parts")) {
                for (JsonElement element : s.getAsJsonArray("parts")) {
                    parts.add(SplitPart.byName(element.getAsString()));
                }
            }
            this.storage = new Storage(s.get("rows").getAsInt(), s.get("sound").getAsString(), Collections.unmodifiableList(parts));
        } else {
            this.storage = null;
        }
    }

    public static FurnitureData forClass(Class<?> blockClass) {
        String id = INDEX.get(blockClass.getSimpleName());
        if (id == null) {
            throw new IllegalStateException("No furniture data for " + blockClass.getName());
        }
        return forId(id);
    }

    public static FurnitureData forId(String id) {
        return BY_ID.computeIfAbsent(id, key -> new FurnitureData(key, read("blocks", key + ".json")));
    }

    /** Every block id, in registry order. */
    public static List<String> ids() {
        return new ArrayList<>(INDEX.values());
    }

    public String id() {
        return this.id;
    }

    public boolean split() {
        return this.part != null;
    }

    /** The part property, or null if the furniture isn't split. Its first value, WHOLE, is the default. */
    @Nullable
    public EnumProperty<SplitPart> partProperty() {
        return this.part;
    }

    @Nullable
    public Storage storage() {
        return this.storage;
    }

    /** The hitbox of every block state. */
    public Map<BlockState, VoxelShape> shapes(StateDefinition<Block, BlockState> definition) {
        Map<BlockState, VoxelShape> out = new IdentityHashMap<>();
        for (BlockState state : definition.getPossibleStates()) {
            Variant variant = this.match(state, definition);
            out.put(state, variant != null ? variant.shape() : Shapes.block());
        }
        return out;
    }

    /** For each part's block state, where it sits relative to the main part. */
    public Map<BlockState, Vec3i> offsets(StateDefinition<Block, BlockState> definition) {
        Map<BlockState, Vec3i> out = new IdentityHashMap<>();
        for (BlockState state : definition.getPossibleStates()) {
            Variant variant = this.match(state, definition);
            out.put(state, variant != null ? variant.offset() : Vec3i.ZERO);
        }
        return out;
    }

    /** The first variant whose properties all match the state, the way blockstate files are matched. */
    @Nullable
    private Variant match(BlockState state, StateDefinition<Block, BlockState> definition) {
        for (Variant variant : this.variants) {
            boolean matches = true;
            for (Map.Entry<String, String> entry : variant.properties().entrySet()) {
                Property<?> property = definition.getProperty(entry.getKey());
                if (property == null || !valueName(state, property).equals(entry.getValue())) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return variant;
            }
        }
        return null;
    }

    private static <T extends Comparable<T>> String valueName(BlockState state, Property<T> property) {
        return property.getName(state.getValue(property));
    }

    private static Map<String, String> loadIndex() {
        JsonObject json = read("index.json");
        Map<String, String> index = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            index.put(entry.getKey(), entry.getValue().getAsString());
        }
        return index;
    }

    private static JsonObject read(String... path) {
        String[] full = new String[path.length + 1];
        full[0] = "mff";
        System.arraycopy(path, 0, full, 1, path.length);
        Path file = ModList.get().getModFileById(MdmMod.MODID).getFile().findResource(full);
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new UncheckedIOException("Can't read " + file, e);
        }
    }
}
