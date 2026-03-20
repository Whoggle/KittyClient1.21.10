package com.princekittymeow.kittyclient.command.utility;

import com.princekittymeow.kittyclient.command.CommandSender;
import com.princekittymeow.kittyclient.player.PlayerData;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PartyWarper {

    private static boolean active = false;
    private static int ticksRemaining = 0;
    private static final int WARP_DELAY = 20 * 5;  // 5 seconds

    /**
     * Registers the PartyWarper into ClientTickEvents.END_CLIENT_TICK
     */
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!active) return;

            if (--ticksRemaining <= 0) {
                active = false;
                CommandSender.warp();
            }
        });
    }

    /**
     * Handles when someone calls the command !w.
     */
    public static void start() {
        if (!PlayerData.isPartyLeader()) return;
        if (active) {
            CommandSender.sendPartyMessage("[KC] A warp is already in progress! Use !fw to force warp or !c to cancel.");
            return;
        }
        active = true;
        ticksRemaining = WARP_DELAY;
        CommandSender.sendPartyMessage("[KC] Warping in 5 seconds, !c to cancel, !fw to force warp");
    }

    /**
     * Handles when someone calls the command !c.
     */
    public static void cancel() {
        if (!PlayerData.isPartyLeader()) return;
        if (!active) {
            CommandSender.sendPartyMessage("[KC] There is no warp currently in progress.");
            return;
        }
        active = false;
        ticksRemaining = 0;
        CommandSender.sendPartyMessage("[KC] Warp cancelled!");
    }
}
