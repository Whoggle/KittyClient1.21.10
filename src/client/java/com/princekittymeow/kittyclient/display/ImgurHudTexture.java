package com.princekittymeow.kittyclient.display;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class ImgurHudTexture {

    public static final Identifier HUD_TEXTURE_ID = Identifier.of("kittyclient", "imgur_hud_image");
    public static String loadedUrl = null;
    public static boolean isLoaded = false;
    public static boolean isLoading = false;
    public static int imageWidth = 0;
    public static int imageHeight = 0;

    /**
     * Loads an imgur image from a url.
     * @param imgurUrl String representation of an imgur URL
     */
    public static void load(String imgurUrl) {
        isLoading = true;
        isLoaded = false;
        CompletableFuture.runAsync(() -> {
            try {
                InputStream stream = URI.create(imgurUrl).toURL().openStream();
                NativeImage image = NativeImage.read(stream);

                MinecraftClient.getInstance().execute(() -> {
                    MinecraftClient.getInstance()
                            .getTextureManager()
                            .destroyTexture(HUD_TEXTURE_ID);

                    imageWidth = image.getWidth();
                    imageHeight = image.getHeight();

                    MinecraftClient.getInstance()
                            .getTextureManager()
                            .registerTexture(HUD_TEXTURE_ID, new NativeImageBackedTexture(() -> "kittyclient:imgur_hud_image", image));

                    loadedUrl = imgurUrl;
                    isLoaded = true;
                    isLoading = false;
                });
            } catch (Exception e) {
                System.out.println("[KittyClient] Failed to load Imgur texture: " + e.getMessage());
                isLoading = false;
            }
        });

    }
}
