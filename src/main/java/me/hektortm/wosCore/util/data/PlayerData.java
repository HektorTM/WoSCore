package me.hektortm.wosCore.util.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerData {

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




