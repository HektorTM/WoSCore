package me.hektortm.wosCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class LangManager {
    private final FileConfiguration config;

    public LangManager(JavaPlugin plugin) {
        File langFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!langFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(langFile);
    }

    public String getMessage(String path) {
        return config.getString(path, "§cMessage not found");
    }

    // TODO: use for reload command
    public void reload() {
        try {
            config.load(new File("messages.yml"));
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
