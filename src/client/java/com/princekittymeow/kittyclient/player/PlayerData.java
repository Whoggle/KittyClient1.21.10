package com.princekittymeow.kittyclient.player;

import com.princekittymeow.kittyclient.dungeon.DungeonCommands;
import net.minecraft.client.MinecraftClient;

/**
 * A data class for the user.
 */
public class PlayerData {
    private static boolean partyLeader = false;

    /**
     * Retrieves the player's name.
     * @return the player's name
     */
    public static String getPlayerName() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return null;
        return client.player.getName().getString();
    }

    /**
     * Sets the value of partyLeader.
     * @param state the value to set partyLeader to
     */
    public static void setIsPartyLeader(boolean state) {
        partyLeader = state;
        if (!partyLeader  && DungeonCommands.willRequeue()) {
            DungeonCommands.toggleRequeue();
        }
    }

    /**
     * Returns the value of partyLeader.
     * @return partyLeader
     */
    public static boolean isPartyLeader() {
        return partyLeader;
    }

    /**
     * Resets all player data.
     */
    public static void reset() {
        partyLeader = false;
    }
}