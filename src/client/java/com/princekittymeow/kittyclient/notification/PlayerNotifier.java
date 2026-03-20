package com.princekittymeow.kittyclient.notification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Contains a bunch of different ways to notify the player.
 */
public class PlayerNotifier {

    /**
     * Pings the player with a sound.
     */
    public static void ping() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        client.player.playSound(SoundEvents.ENTITY_CAT_AMBIENT, 1f, 1.5f);
    }

    /**
     * Shows a title (and subtitle) to the player and calls ping().
     * @param title  The title to show
     * @param subtitle The subtitle to show
     */
    public static void titleNotify(String title, String subtitle) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        client.inGameHud.setTitle(Text.literal(title));
        client.inGameHud.setSubtitle(Text.literal(subtitle));

        ping();
    }

}
