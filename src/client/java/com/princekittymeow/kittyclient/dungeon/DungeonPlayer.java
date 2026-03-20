package com.princekittymeow.kittyclient.dungeon;

/**
 * A data class for a party member (or self) in a dungeon.
 */
public class DungeonPlayer {
    public final String name;
    public DungeonClass dungeonClass;
    public final boolean isSelf;

    public DungeonPlayer(String name, DungeonClass dungeonClass, boolean isSelf) {
        this.name = name;
        this.dungeonClass = dungeonClass;
        this.isSelf = isSelf;
    }

    @Override
    public String toString() {
        return "[" + dungeonClass.abbreviation + "] " + name + (isSelf ? " (you)" : "");
    }
}
