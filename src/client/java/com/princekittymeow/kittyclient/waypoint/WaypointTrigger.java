package com.princekittymeow.kittyclient.waypoint;

import com.princekittymeow.kittyclient.dungeon.DungeonClass;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * A Data class for waypoint information.
 */
public class WaypointTrigger {

    public final String id;
    public final String activationMessage;
    public final List<String> removalMessages;
    public final BlockPos pos;
    public final int color;
    public final DungeonClass requiredClass;

    /**
     * Create a waypoint with no required class
     * @param id The id of the waypoint
     * @param activationMessage The message that activates the waypoint
     * @param removalMessages A list of messages that remove the waypoint
     * @param pos The position of the waypoint
     * @param color The color of the waypoint
     */
    public WaypointTrigger(String id, String activationMessage, List<String> removalMessages, BlockPos pos, int color) {
        this(id, activationMessage, removalMessages, pos, color, null);
    }

    /**
     * Create a waypoint with a required class
     * @param id The id of the waypoint
     * @param activationMessage The message that activates the waypoint
     * @param removalMessages A list of messages that remove the waypoint
     * @param pos The position of the waypoint
     * @param color The color of the waypoint
     * @param requiredClass The required class to activate the waypoint
     */
    public WaypointTrigger(String id, String activationMessage, List<String> removalMessages, BlockPos pos, int color, DungeonClass requiredClass) {
        this.id = id;
        this.activationMessage = activationMessage;
        this.removalMessages = removalMessages;
        this.pos = pos;
        this.color = color;
        this.requiredClass = requiredClass;
    }
}
