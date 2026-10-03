package com.rinkynooble.modernfurniturefixed;

import java.util.HashMap;
import java.util.Map;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Server settings, saved per world in {@code serverconfig/mdm-server.toml}, and client settings in
 * {@code config/mdm-client.toml}.
 */
public final class MffConfig {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;
    private static final ForgeConfigSpec.BooleanValue BREAK_WHOLE_PIECE;
    private static final Map<String, ForgeConfigSpec.IntValue> STORAGE_ROWS = new HashMap<>();
    private static final ForgeConfigSpec.BooleanValue FURNITURE_SOUNDS;

    static {
        ForgeConfigSpec.Builder server = new ForgeConfigSpec.Builder();
        BREAK_WHOLE_PIECE = server
                .comment(
                        "Breaking any part of furniture that fills more than one block breaks the whole piece.",
                        "When false, each part breaks on its own, and only the part the furniture was placed from",
                        "drops the item.")
                .define("breakWholePiece", true);
        server.comment(
                "Storage size of each piece of furniture with storage, in rows of 9 slots (1 to 6).",
                "When a size is lowered, items in slots that no longer exist drop on the ground the next time the block loads.")
                .push("storageRows");
        for (String id : FurnitureData.ids()) {
            FurnitureData.Storage storage = FurnitureData.forId(id).storage();
            if (storage != null) {
                STORAGE_ROWS.put(id, server.defineInRange(id, storage.rows(), 1, 6));
            }
        }
        server.pop();
        SERVER_SPEC = server.build();

        ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();
        FURNITURE_SOUNDS = client
                .comment("Plays a sound when furniture storage opens and closes.")
                .define("furnitureSounds", true);
        CLIENT_SPEC = client.build();
    }

    private MffConfig() {
    }

    public static boolean breakWholePiece() {
        return !SERVER_SPEC.isLoaded() || BREAK_WHOLE_PIECE.get();
    }

    public static int storageRows(FurnitureData furniture) {
        ForgeConfigSpec.IntValue rows = STORAGE_ROWS.get(furniture.id());
        if (rows != null && SERVER_SPEC.isLoaded()) {
            return rows.get();
        }
        return furniture.storage() != null ? furniture.storage().rows() : 3;
    }

    public static boolean furnitureSounds() {
        return !CLIENT_SPEC.isLoaded() || FURNITURE_SOUNDS.get();
    }
}
