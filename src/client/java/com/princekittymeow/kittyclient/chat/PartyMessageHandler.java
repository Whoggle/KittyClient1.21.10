package com.princekittymeow.kittyclient.chat;

import com.princekittymeow.kittyclient.command.PartyCommandHandler;
import com.princekittymeow.kittyclient.display.ImgurParser;

public class PartyMessageHandler {

    /**
     * Handles incoming party messages, ignoring messages starting with "[KC]" or "[KittyClient]"
     * @param message A party message formatted by name and message
     */
    public static void handlePartyMessage(String message) {
        String[] splitMessage = message.split(": ", 2);
        if (splitMessage.length < 2) return; // no content after name
        String name = MessageStripper.stripRank(splitMessage[0]); // Name extraction handled
        String messageContent = splitMessage[1];

        if (messageContent.startsWith("[KC]") || messageContent.startsWith("[KittyClient]")) return;

        // Party commands
        if (messageContent.startsWith("!")) {
            PartyCommandHandler.handlePartyCommand(name, messageContent.substring(1));
        }

        // Image parsing
        String imgur = ImgurParser.extract(messageContent);
        if (imgur != null) {
            System.out.println(imgur);
            ImgurChatInjector.inject(name, imgur);
        }
    }

}
