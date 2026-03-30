package com.princekittymeow.kittyclient.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "kittyclient.json");

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(PartyCommandConfig.toMap(), writer);
        } catch (IOException e) {
            System.out.println("[KC] Failed to save config: " + e.getMessage());
        }
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) return;
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            java.util.Map<?, ?> map = GSON.fromJson(reader, java.util.Map.class);
            if (map != null) PartyCommandConfig.fromMap(map);
        } catch (IOException e) {
            System.out.println("[KC] Failed to load config: " + e.getMessage());
        }
    }
}
