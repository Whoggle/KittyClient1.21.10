package com.princekittymeow.kittyclient.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A data class to hold current floor, dungeon party members, and the party members' classes.
 */
public class DungeonSession {

    public static final DungeonSession INSTANCE = new DungeonSession();

    private final List<DungeonPlayer> players = new ArrayList<>();
    private boolean active = false;

    /**
     * Clears past data and adds all new players to a List of DungeonPlayers
     * @param players The new players being added
     */
    public void start(List<DungeonPlayer> players) {
        this.players.clear();
        this.players.addAll(players);
        this.active = true;
    }

    /**
     * Clears the player list.
     */
    public void clear() {
        this.players.clear();
        this.active = false;
    }

    /**
     * Returns the value of active.
     * @return active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Returns the list of players stored.
     * @return the players list
     */
    public List<DungeonPlayer> getPlayers() {
        return players;
    }

    /**
     * Attempts to find the user from the party.
     * @return The player if found
     */
    public Optional<DungeonPlayer> getSelf() {
        return players.stream().filter(p -> p.isSelf).findFirst();
    }

    /**
     * A method to update a party member's class.
     * @param playerName The name of the player who changed their class
     * @param newClass The class they are changing to
     */
    public void updateClass(String playerName, DungeonClass newClass) {
        players.stream()
                .filter(p -> p.name.equalsIgnoreCase(playerName))
                .findFirst()
                .ifPresent(p -> {
                    p.dungeonClass = newClass;
                    System.out.println("[KittyClient] " + playerName + " changed class to " + newClass);
                });
    }

}
