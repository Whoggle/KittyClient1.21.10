package com.princekittymeow.kittyclient.chat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A helper class containing methods to strip common patterns into parsed data.
 */
public class MessageStripper {

    /**
     * Strips a rare drop and magic find from a magic find message.
     * @param message The message being parsed
     * @return A list of the drop and the magic find the drop was obtained with
     */
    public static List<String> stripRareDrop(String message) {
        if (!message.contains("RARE DROP!")) return List.of();
        if (message.length() < message.indexOf("RARE DROP!") + 11) List.of();

        String dropMessage = message.substring(message.indexOf("RARE DROP!") + 11);

        if (!dropMessage.contains("Magic Find!")) {
            dropMessage = dropMessage.replace("(", "");
            dropMessage = dropMessage.replace(")", "");
            return List.of(dropMessage);
        }

        Pattern pattern = Pattern.compile("\\(([^)]+)\\).*\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(dropMessage);

        if (matcher.find()) {
            String drop = matcher.group(1);
            String magicFind = matcher.group(2);

            return List.of(drop, magicFind);
        }

        return List.of();
    }

    /**
     * Strips a rank from a players name
     * Example: [MVP+] PrinceKittyMeow -> PrinceKittyMeow
     * @param name A '[RANK] Name' formatted player name
     * @return The name of the player with the rank removed if there was one
     */
    public static String stripRank(String name) {
        if (!name.contains("]")) return name;
        return name.substring(name.indexOf("]") + 1).trim();
    }
}
