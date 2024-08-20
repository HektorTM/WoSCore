package me.hektortm.wosCore.essentials;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaytimeManager {
    private final File dataFile;
    private final Gson gson;
    private final Map<UUID, Long> playtimeMap;
    private final Map<UUID, Long> loginTimes;

    public PlaytimeManager(File dataFolder) {
        this.dataFile = new File(dataFolder, "playtime_data.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.playtimeMap = new HashMap<>();
        this.loginTimes = new HashMap<>();
        loadPlaytimeData();
    }

    public void playerLogin(Player p) {
        UUID playerId = p.getUniqueId();
        loginTimes.put(playerId, System.currentTimeMillis());
        playtimeMap.putIfAbsent(playerId, 0L);
    }

    public void playerLogout(Player p) {
        UUID playerId = p.getUniqueId();
        if (loginTimes.containsKey(playerId)) {
            long loginTime = loginTimes.get(playerId);
            long sessionTime = System.currentTimeMillis() - loginTime;
            playtimeMap.put(playerId, playtimeMap.getOrDefault(playerId, 0L)+ sessionTime);
            loginTimes.remove(playerId);
            savePlaytimeData();
        }
    }
    // TODO:
    public String getPlaytime(Player p) {
        UUID playerId = p.getUniqueId();
        long totalTime = playtimeMap.getOrDefault(playerId, 0L);
        totalTime += loginTimes.getOrDefault(playerId, 0L);

        long seconds = totalTime / 1000; // Total time in seconds
        long hours = seconds / 3600; // Convert seconds to hours
        long minutes = (seconds % 3600) / 60; // Get remaining minutes
        seconds = seconds % 60; // Get remaining seconds

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void loadPlaytimeData() {
        if (dataFile.exists()) {
            try (FileReader reader = new FileReader(dataFile)) {
                Type type = new TypeToken<Map<UUID, Long>>() {}.getType();
                Map<UUID, Long> loadedData = gson.fromJson(reader, type);
                if (loadedData != null) {
                    playtimeMap.putAll(loadedData);
                }
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }
    private void savePlaytimeData() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(playtimeMap, writer);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

}
