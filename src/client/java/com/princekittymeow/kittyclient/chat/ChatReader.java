package com.princekittymeow.kittyclient.chat;

import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * Handles incoming chats and reads them correctly according to their type.
 */
public class ChatReader {

    /**
     * Reads incoming chat messaged
     * @param component - The text data
     * @param signatureData
     * @param tag
     */
    public static void readChatMessage(Text component, @Nullable MessageSignatureData signatureData, @Nullable MessageIndicator tag) {
        // Do nothing for now (and probably forever)
    }

    /**
     * Reads incoming system messages
     * @param component - The text data
     * @param tag
     */
    public static void readSystemMessage(Text component, @Nullable MessageIndicator tag) {
        String message = component.getString().replaceAll("§[0-9a-fk-or]", "").trim();
        if (message.isEmpty()) return;
        ChatMessageHandler.handleChat(message);
    }


}
