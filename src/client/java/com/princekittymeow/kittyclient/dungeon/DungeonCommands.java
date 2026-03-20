package com.princekittymeow.kittyclient.dungeon;

import com.princekittymeow.kittyclient.command.CommandSender;
import com.princekittymeow.kittyclient.player.PlayerData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains most the dungeons related methods.
 */
public class DungeonCommands {

    private static boolean requeue = false;
    public static boolean playerLeft = false;
    public static boolean inDungeon = false;
    public static FloorType floor = FloorType.NONE;
    public static Map<String, String> downtimeRequests = new HashMap<>();

    /**
     * Parses a downtime request, storing a reason if one is given
     * @param name The name of the person requesting downtime
     * @param content The message sent
     */
    public static void downtimeRequest(String name, String content) {
        if (content.strip().length() <= 3) {
            downtimeRequests.put(name, "No reason given.");
        } else {
            downtimeRequests.put(name, content.substring(3).trim());
        }
    }

    /**
     * Called when a dungeon run begins to reset stored information.
     */
    public static void onRunStart() {
        downtimeRequests.clear();
        playerLeft = false;
        inDungeon = true;
    }

    /**
     * Called when a dungeon run ends to try and requeue.
     */
    public static void onRunEnd() {
        if (requeue && playerLeft) {
            CommandSender.sendPartyMessage("[KC] Someone has left the party! Cancelling requeue.", 10);
            return;
        }
        if (!downtimeRequests.isEmpty()) {
            int length = downtimeRequests.size();
            StringBuilder message = new StringBuilder("[KC] Downtime request");
            if (length != 1) message.append("s");
            message.append(" from: ");
            message.append(String.join(", ", downtimeRequests.keySet()));
            CommandSender.sendPartyMessage(message.toString(), 10);
            downtimeRequests.clear();
            return;
        }
        if (requeue) {
            if (floor != FloorType.NONE) {
                CommandSender.sendPartyMessage("[KC] Requeueing " + floor.getInternalName(), 10);
            }
            requeue(floor);
        }
    }

    /**
     * Toggles the requeue boolean.
     */
    public static void toggleRequeue() {
        if (!PlayerData.isPartyLeader()) return;
        requeue = !requeue;
        CommandSender.sendPartyMessage(requeue ? "[KC] Toggled Requeue On" : "[KC] Toggled Requeue Off", 15);
    }

    /**
     * Tries to requeue for a given floor
     * @param floor The floor given to requeue
     */
    public static void requeue(FloorType floor) {
        if (floor == null || floor == FloorType.NONE) return;
        // Queue the actual requeue command after a short delay
        CommandSender.joinDungeon(floor.getInternalName());
    }

    /**
     * Returns the value of requeue.
     * @return the value stored in requeue
     */
    public static boolean willRequeue() {
        return requeue;
    }

    /**
     * Resets all dungeon stats.
     */
    public static void reset() {
        requeue = false;
        playerLeft = false;
        inDungeon = false;
        floor = FloorType.NONE;
        downtimeRequests.clear();
    }

    /**
     * A debug message to see party members detected and the class stored with them after a dungeon starts.
     */
    public static void testParty() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.inGameHud == null) return;

        StringBuilder sb = new StringBuilder();
        for (DungeonPlayer player : DungeonSession.INSTANCE.getPlayers()) {
            sb.append(" " + player.name + " (" + player.dungeonClass.toString() + ")");
        }

        Text message = Text.literal("[KittyClient] ")
                .styled(s -> s.withColor(Formatting.AQUA))
                .append(Text.literal("Players: " + sb.toString() + "!")
                        .styled(s -> s
                                .withColor(Formatting.WHITE)
                        )
                );

        client.execute(() -> {
            client.inGameHud.getChatHud().addMessage(message);
        });
    }
}