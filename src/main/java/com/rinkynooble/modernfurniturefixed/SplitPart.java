package com.rinkynooble.modernfurniturefixed;

import net.minecraft.util.StringRepresentable;

/**
 * Which block space of a piece of furniture a block is, as seen by someone looking at the furniture's front.
 * WHOLE is a piece that isn't split: it draws the full model from one block.
 */
public enum SplitPart implements StringRepresentable {
    WHOLE("whole"),
    MAIN("main"),
    LEFT("left"),
    RIGHT("right"),
    TOP("top"),
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right");

    private final String name;

    SplitPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    /** The part in the mirror-image position: left and right swap. */
    public SplitPart mirrored() {
        return switch (this) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case TOP_LEFT -> TOP_RIGHT;
            case TOP_RIGHT -> TOP_LEFT;
            default -> this;
        };
    }

    public static SplitPart byName(String name) {
        for (SplitPart part : values()) {
            if (part.name.equals(name)) {
                return part;
            }
        }
        throw new IllegalArgumentException("Unknown furniture part: " + name);
    }
}
