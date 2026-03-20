package com.princekittymeow.kittyclient.command.fun;

import com.princekittymeow.kittyclient.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * A silly freaky 8-ball.
 */
public class EightBall {

    private static final Random RANDOM = new Random();

    private record Response(BiConsumer<String, String> action) {
        static Response simple(String message) {
            return new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] 8-ball: " + message.replace("[name]", name), 10);
            });
        }
    }
    private static final List<Response> RESPONSES = List.of(
            Response.simple("oh yeah... that's the spot."),
            Response.simple("idk yo"),
            Response.simple("¯\\_(ツ)_/¯"),
            Response.simple("Why would I want to tell you... baka (○｀ 3′○)"),
            Response.simple("im busy yo"),
            Response.simple("*zips up pants* huh- what did you say?"),
            Response.simple("*buuuurp*"),
            Response.simple("*cough*"),
            Response.simple("Your answer is- *wizardman flies by* woah!  Did you see that?"),
            Response.simple("*tumbleweed rolls by*"),
            Response.simple("*bounces on it cutely*"),
            Response.simple("*farts cutely*"),
            Response.simple("Do what jesus would do."),
            Response.simple("The outcome was decided before you were born."),
            Response.simple("I asked my boss, he said he doesn't know."),
            Response.simple("..."),
            Response.simple("right..."),
            Response.simple("Only if you don't mess it up."),
            Response.simple("You're holding me tighter than last time."),
            Response.simple("W-why are you so close to me?... (,,>﹏<,,)"),
            Response.simple("idk, flip a coin instead"),
            Response.simple("Ask PrinceKittyMeow, I bet HE knows."),
            Response.simple("You already know the answer, [name].  You just want confirmation."),
            Response.simple("I don't know. I usually ask [name]... Oh-  That's you!"),
            Response.simple("Is that really the best question you could come up with?"),
            Response.simple("[name]!!! Not in public! ₍₍⚞(˶˃ ꒳ ˂˶)⚟⁾⁾"),
            Response.simple("Merry Christmas!"),
            Response.simple("Come closer to your monitor and ask again... I want to feel your breath."),
            Response.simple("稍後再問"),
            Response.simple("idk man why don't you ask an eight ba- ...ooohhhhhh yeaaah, that's me."),

            // Coin flip
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] 8-Ball: Let me flip a coin for you...", 10);
                CommandSender.sendPartyMessage(RANDOM.nextBoolean() ? "[KC] 8-Ball: Tails!" : "[KC] 8-Ball: Heads!", 70);
            }),

            // Wizardman
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] Wizardman: You called, " + name + "?", 10);
                CommandSender.sendPartyMessage("[KC] Wizardman: No?  Oh okay.", 20);
            }),

            // Sluuurp
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] 8-Ball: *sluuuuuuurp*", 10);
                CommandSender.sendPartyMessage("[KC] 8-Ball: huh? me?", 50);
            }),

            // 8-ball lump
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] 8-Ball: mmrrrrph? grrrmmmp?", 10);
                CommandSender.sendPartyMessage("[KC] " + name + ": *suspiciously 8-ball-shaped lump in throat*", 20);
            }),

            // Gurt yo
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] 8-Ball: *eating yogurt* -oh... me?", 10);
                CommandSender.sendPartyMessage("[KC] Gurt: Yo", 80);
            }),

            // Wrong number 7-ball
            new Response((name, content) ->
                    CommandSender.sendPartyMessage("[KC] 7-Ball: Wrong number. *click*", 10)
            ),

            // Mama 8-ball
            new Response((name, content) ->
                    CommandSender.sendPartyMessage("[KC] Mama 8-ball: Don't talk to me or my son ever again!!", 10)
            ),

            // Random caps
            new Response((name, content) ->
                    CommandSender.sendPartyMessage("[KC] 8-Ball: " + randomizeCaps(content), 10)
            )
    );

    /**
     * Handles the incoming !8ball command.
     * @param name The name of the player calling the command
     * @param content The content after !8ball
     */
    public static void handle(String name, String content) {
        content = content.replace("8ball", "").trim();
        RESPONSES.get(RANDOM.nextInt(RESPONSES.size())).action().accept(name, content);
    }

    /**
     * A helper method used to make fun of the player calling the 8ball.
     * @param text The text content of the !8ball command
     * @return The text content with capitalization randomized
     */
    private static String randomizeCaps(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(Character.isLetter(c)
                    ? (RANDOM.nextBoolean() ? Character.toLowerCase(c) : Character.toUpperCase(c))
                    : c);
        }
        return sb.toString();
    }
}
