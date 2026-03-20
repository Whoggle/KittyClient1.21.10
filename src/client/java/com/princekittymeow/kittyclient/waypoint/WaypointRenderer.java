package com.princekittymeow.kittyclient.waypoint;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

/**
 * Renders the Waypoints.
 */
public class WaypointRenderer {

    /**
     * Registers the renderer as a WorldRenderEvent
     */
    public static void register() {
        WorldRenderEvents.BEFORE_DEBUG_RENDER.register(WaypointRenderer::render);
    }

    /**
     * Renders the waypoints.
     * @param context The WorldRenderContext to render in
     */
    private static void render(WorldRenderContext context) {
        if (Waypoint.waypoints.isEmpty()) return;

        CameraRenderState camera = context.worldState().cameraRenderState;
        Vec3d cameraPos = camera.pos;

        for (Waypoint waypoint : Waypoint.waypoints) {
            renderWaypoint(context, waypoint, cameraPos);
        }
    }

    /**
     * The actual rendering of the waypoint.
     * @param context The WorldRenderContext to render in
     * @param waypoint The position to render the waypoint at
     * @param cameraPos The camera position
     */
    private static void renderWaypoint(WorldRenderContext context, Waypoint waypoint, Vec3d cameraPos) {
        MatrixStack matrices = context.matrices();

        matrices.push();
        matrices.translate(
                waypoint.pos.getX() - cameraPos.x,
                waypoint.pos.getY() - cameraPos.y,
                waypoint.pos.getZ() - cameraPos.z
        );

        context.commandQueue().submitCustom(
                matrices,
                RenderLayer.getLines(),
                (entry, vertexConsumer) -> drawBlockOutline(vertexConsumer, entry, waypoint.color)
        );

        matrices.pop();
    }

    /**
     * Draws a block outline
     * @param consumer A vertex consumer to create the shape
     * @param entry An entry to use in consumer.vertex()
     * @param color The color of the outline
     */
    private static void drawBlockOutline(VertexConsumer consumer, MatrixStack.Entry entry, int color) {
        com.mojang.blaze3d.systems.RenderSystem.lineWidth(3.0f);

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        float[][] edges = {
                {0,0,0, 1,0,0}, {0,1,0, 1,1,0}, {0,0,1, 1,0,1}, {0,1,1, 1,1,1},
                {0,0,0, 0,1,0}, {1,0,0, 1,1,0}, {0,0,1, 0,1,1}, {1,0,1, 1,1,1},
                {0,0,0, 0,0,1}, {1,0,0, 1,0,1}, {0,1,0, 0,1,1}, {1,1,0, 1,1,1}
        };

        float[][] normals = {
                {0,1,0}, {0,1,0}, {0,1,0}, {0,1,0},
                {1,0,0}, {1,0,0}, {1,0,0}, {1,0,0},
                {0,0,1}, {0,0,1}, {0,0,1}, {0,0,1}
        };

        for (int i = 0; i < edges.length; i++) {
            float[] edge = edges[i];
            float[] normal = normals[i];
            consumer.vertex(entry, edge[0], edge[1], edge[2]).color(r, g, b, 255).normal(entry, normal[0], normal[1], normal[2]);
            consumer.vertex(entry, edge[3], edge[4], edge[5]).color(r, g, b, 255).normal(entry, normal[0], normal[1], normal[2]);
        }
    }
}