package me.hektortm.wosCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangManager {
    private FileConfiguration config;
    private final WoSCore plugin;

    public LangManager(WoSCore plugin) {
        this.plugin = plugin;
        reload();
    }

    public String getMessage(String path) {
        return config.getString(path, "§cMessage not found");
    }

    // TODO: use for reload command
    public void reload() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        try {
            config = YamlConfiguration.loadConfiguration(messagesFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
