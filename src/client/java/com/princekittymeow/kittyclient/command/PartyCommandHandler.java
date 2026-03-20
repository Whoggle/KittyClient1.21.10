package com.princekittymeow.kittyclient.command;

import com.princekittymeow.kittyclient.command.fun.EightBall;
import com.princekittymeow.kittyclient.command.fun.MeowForHXW3;
import com.princekittymeow.kittyclient.command.fun.Reference;
import com.princekittymeow.kittyclient.command.utility.PartyWarper;
import com.princekittymeow.kittyclient.config.PartyCommandConfig;
import com.princekittymeow.kittyclient.dungeon.DungeonCommands;

/**
 * A class to handle party chat commands.
 */
public class PartyCommandHandler {

    /**
     * A party chat message starting with '!'.
     * @param name The player who sent the party chat message
     * @param command The command (with the '!' already stripped)
     */
    public static void handlePartyCommand(String name, String command) {
        String[] parts = command.toLowerCase().trim().split(" ", 2);
        String cmd = parts[0];

        switch (cmd) {
            case "ping"             -> {
                if (!PartyCommandConfig.pingEnabled) return;
                CommandSender.sendPartyMessage("[KC] Pong!", 10);
            }
            case "c"                ->  {
                if (!PartyCommandConfig.cancelEnabled) return;
                PartyWarper.cancel();
            }
            case "w"                -> {
                if (!PartyCommandConfig.warpEnabled) return;
                PartyWarper.start();
            }
            case "fw"               -> {
                if (!PartyCommandConfig.forceWarpEnabled) return;
                CommandSender.warp();
            }
            case "8ball"            -> {
                if (!PartyCommandConfig.eightBallEnabled) return;
                EightBall.handle(name, command);
            }
            case "rq"               -> {
                if (!PartyCommandConfig.requeueEnabled) return;
                DungeonCommands.toggleRequeue();
            }
            case "dt"               -> {
                if (!PartyCommandConfig.downtimeEnabled) return;
                DungeonCommands.downtimeRequest(name, command);
            }
            case "coords", "cords"  -> {
                if (!PartyCommandConfig.coordsEnabled) return;
                CommandSender.coords(command);
            }
            case "ref"              -> {
                if (!PartyCommandConfig.refEnabled) return;
                Reference.call(name, command);
            }
            case "meow"             -> {
                if (!PartyCommandConfig.meowEnabled) return;
                MeowForHXW3.meow();
            }

        }

    }

}
