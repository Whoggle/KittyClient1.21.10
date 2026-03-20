package com.princekittymeow.kittyclient.dungeon;

/**
 * Contains all dungeon classes and an unknown type.
 */
public enum DungeonClass {
    BERSERK("B"),
    MAGE("M"),
    ARCHER("A"),
    TANK("T"),
    HEALER("H"),
    UNKNOWN("?");

    public final String abbreviation;

    DungeonClass(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Finds a dungeon class from a character.
     * @param abbr The character being turned into a class
     * @return The class found, UNKNOWN if none found
     */
    public static DungeonClass fromAbbreivation(String abbr) {
        for (DungeonClass c : values()) {
            if (c.abbreviation.equalsIgnoreCase(abbr)) return c;
        }
        return UNKNOWN;
    }

    /**
     * Finds a dungeon class from a class name.
     * @param name The class name being turned into a class object
     * @return The class found, UNKNOWN if none found
     */
    public static DungeonClass fromName(String name) {
        for (DungeonClass c : values()) {
            if (c.name().equalsIgnoreCase(name)) return c;
        }
        return UNKNOWN;
    }
}
