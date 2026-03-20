package com.princekittymeow.kittyclient.display;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.Identifier;

/**
 * Registers and managers the HudOverlay for imgur images.
 */
public class HudOverlay {

    private static int maxSize = 256;
    public static final Identifier LAYER_ID = Identifier.of("kittyclient", "imgur_hud_layer");
    public static boolean shouldShow = false;

    /**
     * Registers the section for images to appear in to the HudElementRegistry.
     */
    public static void register() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.BOSS_BAR,
                LAYER_ID,
                (drawContext, tickCounter) -> {
                    if (!shouldShow || !ImgurHudTexture.isLoaded) return;
                    if (!(MinecraftClient.getInstance().currentScreen instanceof ChatScreen)) return;
                    renderImage(drawContext);
                }
        );
    }

    /**
     * Renders an image.
     * @param context The context being drawn in
     */
    private static void renderImage(DrawContext context) {
        int x = 10, y = 10;

        int w = ImgurHudTexture.imageWidth;
        int h = ImgurHudTexture.imageHeight;

        if (w > h) {
            h = (int) ((float) h / w * maxSize);
            w = maxSize;
        } else {
            w = (int) ((float) w / h * maxSize);
            h = maxSize;
        }

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                ImgurHudTexture.HUD_TEXTURE_ID,
                x, y,
                0, 0,
                w, h,
                w, h
        );
    }

    /**
     * Toggles the image display.
     */
    public static void toggleHudOverlay() {
        shouldShow = !shouldShow;
        System.out.println("Show: " + Boolean.toString(shouldShow));
    }

    /**
     * Changes the max size of the image
     * @param size new max size
     */
    public static void changeMaxSize(int size) {
        maxSize = size;
    }
}
