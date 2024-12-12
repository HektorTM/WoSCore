package me.hektortm.wosCore.util.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerdataManager {

    private final File playerDataFolder;

    public PlayerdataManager(File dataFolder) {
        this.playerDataFolder = new File(dataFolder, "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    /**
     * Retrieves or creates a player data configuration for the given player.
     */
    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId(), player.getName());
    }

    /**
     * Retrieves or creates a player data configuration for the given UUID and username.
     */
    public PlayerData getPlayerData(UUID uuid, String username) {
        File playerFile = new File(playerDataFolder, uuid.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        if (!playerFile.exists()) {
            config.set("UUID", uuid.toString());
            config.set("Username", username);
            config.set("Joindate", new Date().toString());
            config.set("Lastonline", new Date().toString());
            saveConfig(config, playerFile);
        }

        return new PlayerData(uuid, config, playerFile);
    }

    /**
     * Saves the given player's data.
     */
    public void savePlayerData(PlayerData playerData) {
        saveConfig(playerData.getConfig(), playerData.getFile());
    }

    private void saveConfig(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            System.err.println("Could not save player data file: " + file.getName());
            e.printStackTrace();
        }
    }

    /**
     * Represents a player's data with dynamic property support.
     */
    public static class PlayerData {

        private final UUID uuid;
        private final FileConfiguration config;
        private final File file;
        private final Map<String, Object> customProperties;

        public PlayerData(UUID uuid, FileConfiguration config, File file) {
            this.uuid = uuid;
            this.config = config;
            this.file = file;
            this.customProperties = new HashMap<>();
        }

        public UUID getUuid() {
            return uuid;
        }

        public FileConfiguration getConfig() {
            return config;
        }

        public File getFile() {
            return file;
        }

        /**
         * Gets a dynamic property by key.
         */
        public Object getProperty(String key) {
            return customProperties.getOrDefault(key, config.get(key));
        }

        /**
         * Sets a dynamic property. Also persists it to the YAML file.
         */
        public void setProperty(String key, Object value) {
            customProperties.put(key, value);
            config.set(key, value);
        }

        /**
         * Removes a dynamic property by key.
         */
        public void removeProperty(String key) {
            customProperties.remove(key);
            config.set(key, null);
        }

        /**
         * Saves all dynamic properties to the configuration file.
         */
        public void save() {
            for (Map.Entry<String, Object> entry : customProperties.entrySet()) {
                config.set(entry.getKey(), entry.getValue());
            }
            try {
                config.save(file);
            } catch (IOException e) {
                System.err.println("Could not save player data file: " + file.getName());
                e.printStackTrace();
            }
        }
    }
}