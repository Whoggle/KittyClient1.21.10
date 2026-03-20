package com.princekittymeow.kittyclient.command;

import com.princekittymeow.kittyclient.player.PlayerData;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayDeque;
import java.util.Queue;

public class CommandSender {

    private final static Queue<CommandEntry> COMMANDS = new ArrayDeque<>();

    /**
     * A command to be called on every game tick.
     */
    public static void tick() {
        if (COMMANDS.isEmpty()) return;
        if (--COMMANDS.peek().ticks <= 0) {
            sendRawCommand(COMMANDS.poll().command);
        }
    }

    /**
     * Sends a command from the player.
     * This method is private to avoid any accidental commands from being sent.
     * @param command The command being sent
     */
    private static void sendRawCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        client.getNetworkHandler().sendChatCommand(command);
    }

    /**
     * Adds a command to the command queue to send a party message with a delay of 10 ticks.
     * @param message The message being sent to the party
     */
    public static void sendPartyMessage(String message) {
        COMMANDS.add(new CommandEntry(("pc " + message), 10));
    }

    /**
     * Adds a command to the command queue to send a party message with a specified tick delay.
     * @param message The message being sent to the party
     * @param delay The amount of ticks to delay the message by
     */
    public static void sendPartyMessage(String message, int delay) {
        COMMANDS.add(new CommandEntry(("pc " + message), delay));
    }

    /**
     * Sends the /p warp command.
     */
    public static void warp() {
        COMMANDS.add(new CommandEntry("p warp", 10));
    }

    /**
     * Joins a dungeon using the /joindungeon command.
     * @param floorName The instance name to use in the /joindungeon command's parameters
     */
    public static void joinDungeon(String floorName) {
        COMMANDS.add(new CommandEntry("joindungeon " + floorName, 10));
    }

    /**
     * Handles !coords party command calls.
     * This will send a message if the player's name follows "!coords"
     * Example: "!coords PrinceKittyMeow:"
     * @param content The content of the message being read
     */
    public static void coords(String content) {
        content = content.replace("coords","").replace("cords","").trim();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (!content.equals(PlayerData.getPlayerName())) return;
        int x = client.player.getBlockX();
        int y = client.player.getBlockY();
        int z = client.player.getBlockZ();
        sendPartyMessage("x: " + x + "y: " + y + "z: " + z + " | Location Requested");
    }
}

/**
 * A helper class to hold a command String and tick delay int.
 */
class CommandEntry {
    public String command;
    public int ticks;
    public CommandEntry(String command, int ticks) {
        this.command = command;
        this.ticks = ticks;
    }
}
