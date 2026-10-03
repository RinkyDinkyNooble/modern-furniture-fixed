package com.rinkynooble.modernfurniturefixed;

import net.minecraftforge.common.ForgeConfigSpec;

/** Server settings, saved per world in {@code serverconfig/mdm-server.toml}. */
public final class MffConfig {
    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.BooleanValue BREAK_WHOLE_PIECE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        BREAK_WHOLE_PIECE = builder
                .comment(
                        "Breaking any part of furniture that fills more than one block breaks the whole piece.",
                        "When false, each part breaks on its own, and only the part the furniture was placed from",
                        "drops the item and its contents.")
                .define("breakWholePiece", true);
        SPEC = builder.build();
    }

    private MffConfig() {
    }

    public static boolean breakWholePiece() {
        return !SPEC.isLoaded() || BREAK_WHOLE_PIECE.get();
    }
}
