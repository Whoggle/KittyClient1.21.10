package com.princekittymeow.kittyclient.dungeon;

/**
 * Contains all dungeon floors and a NONE type.
 */
public enum FloorType {
    NONE(null),
    E("CATACOMBS_ENTRANCE"),
    F1("CATACOMBS_FLOOR_ONE"),
    F2("CATACOMBS_FLOOR_TWO"),
    F3("CATACOMBS_FLOOR_THREE"),
    F4("CATACOMBS_FLOOR_FOUR"),
    F5("CATACOMBS_FLOOR_FIVE"),
    F6("CATACOMBS_FLOOR_SIX"),
    F7("CATACOMBS_FLOOR_SEVEN"),
    M1("MASTER_CATACOMBS_FLOOR_ONE"),
    M2("MASTER_CATACOMBS_FLOOR_TWO"),
    M3("MASTER_CATACOMBS_FLOOR_THREE"),
    M4("MASTER_CATACOMBS_FLOOR_FOUR"),
    M5("MASTER_CATACOMBS_FLOOR_FIVE"),
    M6("MASTER_CATACOMBS_FLOOR_SIX"),
    M7("MASTER_CATACOMBS_FLOOR_SEVEN");

    private final String internalName;

    /**
     * Creates a new FloorType object.
     * @param internalName the String to store with the FloorType value
     */
    FloorType(String internalName) {
        this.internalName = internalName;
    }

    /**
     * Returns the String representation of the floor name.
     * @return the String stored with the FloorType value
     */
    public String getInternalName() {
        return internalName;
    }

}