package com.princekittymeow.kittyclient.waypoint;

import com.princekittymeow.kittyclient.dungeon.DungeonClass;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * A registry of all the waypoints that can be triggered by this mod.
 */
public class WaypointTriggerRegistry {

    private static final List<WaypointTrigger> TRIGGERS = new ArrayList<>();

    /**
     * Add all of the waypoints saved.
     */
    public static void register() {
        TRIGGERS.add(new WaypointTrigger(
                "m6 mage1",
                "[BOSS] Sadan: So you made it all the way here... Now you wish to defy me? Sadan?!",
                List.of(
                        "[BOSS] Sadan: ENOUGH!"
                ),
                new BlockPos(-8, 69, 33),
                0x3399FF,
                DungeonClass.MAGE
        ));
        TRIGGERS.add(new WaypointTrigger(
                "m6 mage2",
                "[BOSS] Sadan: You did it. I understand now, you have earned my respect.",
                List.of(
                        "[BOSS] Sadan: NOOOOOOOOO!!! THIS IS IMPOSSIBLE!!"
                ),
                new BlockPos(-8, 69, 87),
                0x3399FF,
                DungeonClass.MAGE
        ));
    }

    /**
     * Returns the List of waypoints
     * @return TRIGGERS
     */
    public static List<WaypointTrigger> getTriggers() {
        return TRIGGERS;
    }
}
