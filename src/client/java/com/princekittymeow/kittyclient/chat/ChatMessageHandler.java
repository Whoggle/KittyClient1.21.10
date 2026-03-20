package com.princekittymeow.kittyclient.chat;

import com.princekittymeow.kittyclient.command.CommandSender;
import com.princekittymeow.kittyclient.dungeon.*;
import com.princekittymeow.kittyclient.player.PlayerData;
import com.princekittymeow.kittyclient.waypoint.WaypointManager;

/**
 * A class handling incoming chat messages.
 */
public class ChatMessageHandler {

    private static final int JOIN_DELAY_TICKS = 100; // 100 ticks = 5 seconds
    private static int ticksOnServer = -1;

    private static volatile String lastMessage = null;

    /**
     * Whenever the server is switched, this method is called.
     */
    public static void onJoinServer() {
        ticksOnServer = 0;
    }

    /**
     * This method is called every tick.
     */
    public static void tick() {
        if (ticksOnServer >= 0 && ticksOnServer < JOIN_DELAY_TICKS) {
            ticksOnServer++;
        }
    }

    /**
     * Determines if enough time has passed for the chat reader to start.
     * @return true if enough time has passed for messages to be read
     */
    public static boolean isReady() {
        return ticksOnServer >= JOIN_DELAY_TICKS;
    }

    /**
     * Handles incoming chat messages.
     * @param message The message being handled
     */
    public static void handleChat(String message) {
        if (!isReady()) return;

        if (message.startsWith("Sending to server ")) {
            // Stuff to do on island switch
            WaypointManager.clearAll();
            return;
        }

        if (message.equals("[NPC] Mort: Here, I found this map when I first entered the dungeon.")) {
            DungeonCommands.onRunStart();
        }

        // Dungeon run end
        if (message.strip().equals("> EXTRA STATS <")) {
            DungeonCommands.onRunEnd();
            WaypointManager.clearAll();
        }

        // Player left party
        if (message.contains(" has left the party.")) {
            handlePlayerLeft(message.replace(" has left the party.", "").trim());
        }

        // Player removed from party
        if (message.contains(" has been removed from the party.")) {
            handlePlayerLeft(message.replace(" has been removed from the party.", "").trim());
        }

        // Party leader tracking 1
        if (message.contains("entered The Catacombs") || message.contains("entered MM The Catacombs")) {
            DungeonCommands.inDungeon = true;
            String playerPart = message.replaceAll("^-+|-+$", "").trim().split(" entered")[0];
            String playerName = MessageStripper.stripRank(playerPart);
            PlayerData.setIsPartyLeader(playerName.equals(PlayerData.getPlayerName()));
        }

        // Party leader tracking 2
        if (message.startsWith("Party Leader:")) {
            String leader = MessageStripper.stripRank(
                    message.replace("Party Leader: ", "").replace("●", "").trim()
            );
            PlayerData.setIsPartyLeader(leader.equals(PlayerData.getPlayerName()));
        }

        // Party Transfer 3
        if (message.startsWith("The party was transferred to ")) {
            handlePartyTransfer(message);
        }

        // Island switch - reset everything
        if (message.startsWith("Sending to server")) {
            DungeonCommands.reset();
            WaypointManager.clearAll();
            PlayerData.reset();
        }

        // Send to party handler
        if (message.startsWith("Party >")) {
            if ((message.indexOf(">") + 2) > message.length()) {
                return;
            }
            PartyMessageHandler.handlePartyMessage(message.substring(message.indexOf(">") + 2));
            return;
        }

        // Read the scoreboard while all players (including self) are on the scoreboard
        if (message.equals("Starting in 1 second.")) { // The reason this had to be changed to 1 was because of the 100 tick delay on reading
            DungeonScoreboardReader.readAndStart();
        }

        // Detect class changes during the countdown
        if (DungeonSession.INSTANCE.isActive() && message.contains("selected the") && message.contains("Class!")) {
            int selectedIdx = message.indexOf(" selected the ");
            int classIdx = message.indexOf(" Class!");
            if (selectedIdx > 0 && classIdx > selectedIdx) {
                String playerName = message.substring(0, selectedIdx).trim();
                String className = message.substring(selectedIdx + " selected the ".length(), classIdx).trim();
                DungeonClass dungeonClass = DungeonClass.fromName(className);
                DungeonSession.INSTANCE.updateClass(playerName, dungeonClass);
            }
        }


        // Don't run on duplicate messages
        if (message.equals(lastMessage)) return;
        lastMessage = message;

        // Send message to waypoint handler in case it has a waypoint pairing
        WaypointManager.handleMessage(message);

    }

    /**
     * Sends a message when players leave the party.
     * If a dungeon class can be found, it adds it next to their name.
     * Example: [KC] Goodbye, PrinceKittyMeow (HEALER). ヾ(＾ ∇ ＾).
     * @param rawName The name of the player that left the party
     */
    private static void handlePlayerLeft(String rawName) {
        String name = MessageStripper.stripRank(rawName);
        DungeonCommands.playerLeft = true;

        DungeonPlayer player = DungeonSession.INSTANCE.getPlayers().stream()
                .filter(p -> p.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        String classStr = player != null && player.dungeonClass != DungeonClass.UNKNOWN
                ? " (" + player.dungeonClass.name() + ")"
                : "";

        CommandSender.sendPartyMessage("[KC] Goodbye, " + name + classStr + ". ヾ(＾ ∇ ＾).", 10);
    }

    /**
     * Reads the correct new leader when the party is transferred.
     * Also sends a leave message if the transfer was due to the last party leader leaving.
     * @param message The party transferring message
     */
    private static void handlePartyTransfer(String message) {
        String rest = message.replace("The party was transferred to ", "");
        String leader;
        if (rest.contains(" because ")) {
            String[] parts = rest.split(" because ");
            leader = MessageStripper.stripRank(parts[0].trim());
            String prevLeader = MessageStripper.stripRank(parts[1].replace("left", "").trim());
            handlePlayerLeft(prevLeader);
        } else if (rest.contains(" by ")) {
            leader = MessageStripper.stripRank(rest.split(" by ")[0].trim());
        } else {
            return;
        }
        PlayerData.setIsPartyLeader(leader.equals(PlayerData.getPlayerName()));
    }
}
