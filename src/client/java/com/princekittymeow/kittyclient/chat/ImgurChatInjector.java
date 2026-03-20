package com.princekittymeow.kittyclient.chat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to inject a hoverable message into chat when an imgur link is detected.
 */
public class ImgurChatInjector {

    private static final Map<String, String> URL_MAP = new HashMap<>();

    /**
     * Injects a hoverable message into chat when an imgur link is detected.
     * @param playerName The player who sent the imgur message
     * @param url The url of the imgur image
     */
    public static void inject(String playerName, String url) {
        URL_MAP.put(url, url);

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.inGameHud == null) return;

        Text message = Text.literal("[KittyClient] ")
                .styled(s -> s.withColor(Formatting.AQUA))
                .append(Text.literal("Imgur from " + playerName + "!")
                        .styled(s -> s
                                .withColor(Formatting.WHITE)
                                .withUnderline(true)
                                .withInsertion(url)
                        )
                );

        client.execute(() -> {
            client.inGameHud.getChatHud().addMessage(message);
        });
    }

    /**
     * Returns a url String from a Map
     * @param insertion The key used in the map
     * @return The value stored at that key
     */
    public static String getUrl(String insertion) {
        return URL_MAP.get(insertion);
    }
}
