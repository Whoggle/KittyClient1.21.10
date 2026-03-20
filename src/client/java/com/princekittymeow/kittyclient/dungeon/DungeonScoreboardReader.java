package com.princekittymeow.kittyclient.dungeon;

import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.*;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to read the sidebar and detect players in the party and their classes.
 */
public class DungeonScoreboardReader {

    private static final Pattern PLAYER_PATTERN = Pattern.compile("^\\[([A-Z])\\]\\s+(\\S+)");

    /**
     * Attempts to read the scoreboard and parse the floor and all players.
     * Example:
     *  ⏣ The Catacombs (F7)
     *
     *  [M] PrinceKittyMeow [Lv42]
     *  [T] sdihgn [Lv43]
     */
    public static void readAndStart() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        Scoreboard scoreboard = client.world.getScoreboard();
        ScoreboardObjective sidebar = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

        if (sidebar == null) {
            System.out.println("[KittyClient] No sidebar scoreboard found.");
            return;
        }

        Collection<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(sidebar);
        String selfName = client.player.getName().getString();
        List<DungeonPlayer> players = new ArrayList<>();

        for (ScoreboardEntry entry : entries) {
            if (entry.hidden()) continue;

            Team team = scoreboard.getScoreHolderTeam(entry.owner());
            if (team == null) continue;

            String raw = Formatting.strip(
                    team.getPrefix().getString() + team.getSuffix().getString()
            ).trim();

            if (raw.isEmpty()) continue;

            System.out.println("[KittyClient] Read sidebar: " + raw);

            // Floor parsing
            FloorType parsed = parseFloor(raw);
            if (parsed != FloorType.NONE) {
                DungeonCommands.floor = parsed;
            } else if (raw.startsWith("[")) {  // Player parsing
                Matcher matcher = PLAYER_PATTERN.matcher(raw);
                if (matcher.find()) {
                    String classAbbr = matcher.group(1);
                    String playerName = matcher.group(2);
                    DungeonClass dungeonClass = DungeonClass.fromAbbreivation(classAbbr);
                    boolean isSelf = playerName.equalsIgnoreCase(selfName);
                    players.add(new DungeonPlayer(playerName, dungeonClass, isSelf));
                }
            }
        }

        if (!players.isEmpty()) {
            DungeonSession.INSTANCE.start(players);
        } else {
            System.out.println("[KittyClient] No dungeon players found in scoreboard.");
        }
    }

    /**
     * Parses the floor from a scoreboard line
     * @param line The line being parsed for a floor
     * @return The FloorType found, NONE if none found
     */
    private static FloorType parseFloor(String line) {
        if (!line.startsWith("⏣")) return FloorType.NONE;
        int start = line.indexOf("(");
        int end = line.indexOf(")");
        if (start != -1 && end != -1 && end > start) {
            String floorCode = line.substring(start+1, end).trim();
            try {
                return FloorType.valueOf(floorCode);
            } catch (IllegalArgumentException e) {
                return FloorType.NONE;
            }
        }
        return FloorType.NONE;
    }

}
