package com.princekittymeow.kittyclient.waypoint;

import com.princekittymeow.kittyclient.dungeon.DungeonPlayer;
import com.princekittymeow.kittyclient.dungeon.DungeonSession;

public class WaypointManager {

    /**
     * Handles incoming messages to check if they trigger waypoints.
     * @param message The incoming message being handled
     */
    public static void handleMessage(String message) {
        for (WaypointTrigger trigger : WaypointTriggerRegistry.getTriggers()) {
            if (message.equals(trigger.activationMessage)) {
                clearById(trigger.id);

                if (trigger.requiredClass != null) {
                    DungeonPlayer self = DungeonSession.INSTANCE.getSelf().orElse(null);
                    if (self == null || self.dungeonClass != trigger.requiredClass) return;
                }

                new Waypoint(trigger.pos, trigger.id, trigger.color);
            }

            for (String removalMessage : trigger.removalMessages) {
                if (message.equals(removalMessage)) {
                    clearById(trigger.id);
                }
            }
        }
    }

    /**
     * Removes a waypoint from Waypoint's waypoints by id.
     * @param id The id to remove
     */
    public static void clearById(String id) {
        Waypoint.waypoints.removeIf(w -> w.label.equals(id));
    }

    /**
     * Removes all waypoints from Waypoint's waypoints.
     */
    public static void clearAll() {
        Waypoint.waypoints.clear();
    }
}
