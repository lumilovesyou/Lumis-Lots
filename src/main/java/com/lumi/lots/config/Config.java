package com.lumi.lots.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

import static com.lumi.lots.LumisCore.MOD_ID;

public class Config {
    public boolean displayTrackName = false;
    public boolean doSpongeBackport = true;
    public boolean invMovement = true;
    public boolean musicCooldown = false;
    public boolean metaKeyPasting = true;
    public boolean firstPersonModel = false;
    public int etFuturumDeepslateYLevel = 22;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File configFile = new File(String.format("config/%s.json", MOD_ID));

    public static Config load() {
        if (!configFile.exists()) {
            Config defaultConfig = new Config();
            defaultConfig.save();
            return defaultConfig;
        }
        try (Reader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, Config.class);
        } catch (Exception ignore) {
            return new Config();
        }
    }

    public void save() {
        try (Writer writer = new FileWriter(configFile)) {
            gson.toJson(this, writer);
        } catch (Exception ignore) {}
    }
}
