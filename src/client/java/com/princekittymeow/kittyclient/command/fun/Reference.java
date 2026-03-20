package com.princekittymeow.kittyclient.command.fun;

import com.princekittymeow.kittyclient.command.CommandSender;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * A silly reference maker.
 */
public class Reference {

    record Response(BiConsumer<String, String> action) {
        static Response simple(String message) {
            return new Response((name, content) ->
                    CommandSender.sendPartyMessage("[KC] " + message.replace("[name]", name), 10));
        }
    }

    private static final List<Response> RESPONSES = List.of(
            Response.simple("Sukuna: Stand proud, [name]. You are strong."),
            Response.simple("Cuddle Team Leader: Only YOU can prevent V-Buck Scams. Do not share your password with third parties."),
            Response.simple("Wizardman: All you have to do is scream \"Help Wizardman!\" and I'll come save you if I'm not too busy."),
            Response.simple("Doctor Strange: I am opening a portal"),
            Response.simple("Whitebeard: The One Piece! The One Piece is REAAAAAL!"),
            Response.simple("Northernlion: I'm Northernlion yo"),
            Response.simple("Gojo: Nah, I'd win."),
            Response.simple("Miles Morales: Nah, Imma do my own thing."),
            Response.simple("Goldilocks: This porridge is juuuust right!"),
            Response.simple("Travis Scott: 9-0-2-1-0  9-0-2-1-0 looking for that alley."),
            Response.simple("Isaac: augh!"),
            Response.simple("Gurt: Yo"),
            Response.simple("Mario: It's a me, Mario!"),
            Response.simple("Jerry: Jerry!"),
            Response.simple("[SECURITY] Sloth: Downloading suspicious mods or visiting untrusted discord servers can put your account at risk."),
            Response.simple("Rusty: You would not believe how many people leave ingots and stones behind them!"),
            Response.simple("Sadan: ENOUGH!"),
            Response.simple("Bonzo: Oh, I'm dead. Sike!"),
            Response.simple("Elle: Bring me 2 Spectre Dust, 3 Blaze Ashes, and 4 Magma Chunks."),
            Response.simple("Kuudra: yo im kuudra yo"),
            Response.simple("Superior Dragon: roar"),
            Response.simple("Shadow Assassin: BOO!"),
            Response.simple("Thorn: Round and round, another wound."),
            Response.simple("Crypt Lurker: Kill [name], they are ugly!"),
            Response.simple("PrinceKittyMeow: I am PrinceKittyMeow"),
            Response.simple("sdihgn: I am sdihgn"),
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] Eren: Beyond the wall...", 20);
                CommandSender.sendPartyMessage("[KC] Eren: there's a sea.", 20);
                CommandSender.sendPartyMessage("[KC] Eren: On the other side of the sea...", 20);
                CommandSender.sendPartyMessage("[KC] Eren: is freedom.", 20);
            }),
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] Todo: The sound of the Gion Shōja bells echoes the impermanence of all things;", 20);
                CommandSender.sendPartyMessage("[KC] Todo: The color of the sāla flowers reveals the truth that the prosperous must decline.", 20);
                CommandSender.sendPartyMessage("[KC] Todo: However! We are the exception.", 20);
            }),
            new Response((name, content) -> {
                CommandSender.sendPartyMessage("[KC] Peter Griffin: This reminds me of that one time I got kicked from the party for failing tic tac toe!", 20);
                CommandSender.sendPartyMessage("[KC] *flashback*", 10);
                CommandSender.sendPartyMessage("[KC] PUZZLE FAIL! peterpumpkineater69 lost Tic Tac Toe! Yikes!", 40);
                CommandSender.sendPartyMessage("[KC] peterpumpkineater69 has been removed from the party.", 20);
            })
    );

    /**
     * Handles incoming !ref calls.
     * @param name The name of the person calling !ref
     * @param content The content after !ref
     */
    public static void call(String name, String content) {
        RESPONSES.get((int) (Math.random() * RESPONSES.size())).action().accept(name, content);
    }
}
