package com.princekittymeow.kittyclient.display;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to parse imgur links from messages
 */
public class ImgurParser {

    private static final Pattern IMGUR_PATTERN = Pattern.compile("I\\[([A-Za-z0-9]+)]");

    /**
     * Parses an Imgur link from a party chat message containing a pattern.
     * Example I[mpaCz9b] -> https://i.imgur.com/mpaCz9b.png
     * @param message The message being handled to find an image format
     * @return The url String if an imgur format is found, null otherwise
     */
    public static String extract(String message) {

        Matcher matcher = IMGUR_PATTERN.matcher(message);

        if (matcher.find()) {
            String id = matcher.group(1);
            return "https://i.imgur.com/" + id + ".png";
        }

        return null;
    }

}
