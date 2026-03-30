package com.princekittymeow.kittyclient.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data class for toggling the party commands.
 */
public class PartyCommandConfig {
    // Fun
    public static boolean pingEnabled = true;
    public static boolean eightBallEnabled = true;
    public static boolean refEnabled = true;
    public static boolean meowEnabled = true;

    // Util
    public static boolean warpEnabled = true;
    public static boolean cancelEnabled = true;
    public static boolean forceWarpEnabled = true;
    public static boolean coordsEnabled = true;

    // Dungeon
    public static boolean requeueEnabled = true;
    public static boolean downtimeEnabled = true;

    // Player salutation
    public static boolean playerSalutationEnabled = true;

    public static Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("eightBallEnabled", eightBallEnabled);
        map.put("refEnabled", refEnabled);
        map.put("meowEnabled", meowEnabled);
        map.put("pingEnabled", pingEnabled);
        map.put("coordsEnabled", coordsEnabled);
        map.put("warpEnabled", warpEnabled);
        map.put("cancelEnabled", cancelEnabled);
        map.put("forceWarpEnabled", forceWarpEnabled);
        map.put("requeueEnabled", requeueEnabled);
        map.put("downtimeEnabled", downtimeEnabled);
        map.put("playerSalutationEnabled", playerSalutationEnabled);
        return map;
    }

    public static void fromMap(Map<?, ?> map) {
        eightBallEnabled = getBool(map, "eightBallEnabled", true);
        refEnabled = getBool(map, "refEnabled", true);
        meowEnabled = getBool(map, "meowEnabled", true);
        pingEnabled = getBool(map, "pingEnabled", true);
        coordsEnabled = getBool(map, "coordsEnabled", true);
        warpEnabled = getBool(map, "warpEnabled", true);
        cancelEnabled = getBool(map, "cancelEnabled", true);
        forceWarpEnabled = getBool(map, "forceWarpEnabled", true);
        requeueEnabled = getBool(map, "requeueEnabled", true);
        downtimeEnabled = getBool(map, "downtimeEnabled", true);
        playerSalutationEnabled = getBool(map, "playerSalutationEnabled", playerSalutationEnabled);
    }

    private static boolean getBool(Map<?, ?> map, String key, boolean defaultValue) {
        Object val = map.get(key);
        return val instanceof Boolean ? (Boolean) val : defaultValue;
    }
}
