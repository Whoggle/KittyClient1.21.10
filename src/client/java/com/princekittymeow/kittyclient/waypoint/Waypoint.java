package com.princekittymeow.kittyclient.waypoint;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * A data class for a waypoint, it's position, and it's color.
 */
public class Waypoint {

    public static final List<Waypoint> waypoints = new ArrayList<>();

    public final BlockPos pos;
    public final String label;
    public final int color;

    /**
     * Creates a waypoint and adds it to waypoints.
     * @param pos The BlockPos position of the waypoint
     * @param label The label of the waypoint.
     * @param color The color of the waypoint.
     */
    public Waypoint(BlockPos pos, String label, int color) {
        this.pos = pos;
        this.label = label;
        this.color = color;
        waypoints.add(this);
    }

}
