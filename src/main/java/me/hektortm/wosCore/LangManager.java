package me.hektortm.wosCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LangManager {
    private FileConfiguration config;
    private final Map<String, FileConfiguration> langFiles = new HashMap<>();
    private final WoSCore plugin;

    public LangManager(WoSCore plugin) {
        this.plugin = plugin;
        loadLangFiles();
    }

    public void loadLangFiles() {
        loadLangFile("broadcast");
        loadLangFile("errors");
        loadLangFile("essentials");
        loadLangFile("general");
        loadLangFile("guis");
        loadLangFile("pvp");
    }

    private void loadLangFile(String filename){
        File file = new File(plugin.getDataFolder(), "lang/" +filename+".yml");
        if (!file.exists()) {
            plugin.saveResource("lang/"+filename+".yml", false);
        }
        langFiles.put(filename, YamlConfiguration.loadConfiguration(file));
    }

    public void loadLangFileExternal(Plugin sourcePlugin, String filename, WoSCore corePlugin) {
        // Attempt to load the resource stream from the source plugin (WoSFriends)
        InputStream resourceStream = sourcePlugin.getResource("lang/" + filename + ".yml");

        if (resourceStream != null) {
            File destinationFile = new File(corePlugin.getDataFolder(), "lang/" + filename + ".yml");
            // Create the directory if it doesn't exist
            if (!destinationFile.getParentFile().exists()) {
                destinationFile.getParentFile().mkdirs();
            }

            try {
                // Copy the file from the resource stream to the destination file
                Files.copy(resourceStream, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                // Load the copied file into the langFiles map
                langFiles.put(filename, YamlConfiguration.loadConfiguration(destinationFile));
                corePlugin.getLogger().info("Successfully loaded and copied " + filename + ".yml from " + sourcePlugin.getName() + " to WoSCore's lang directory.");
            } catch (IOException e) {
                corePlugin.getLogger().severe("Failed to copy " + filename + ".yml to WoSCore data folder: " + e.getMessage());
            }
        } else {
            corePlugin.getLogger().severe("The embedded resource 'lang/" + filename + ".yml' cannot be found in " + sourcePlugin.getName());
        }
    }




    public String getMessage(String fileName, String key) {
        FileConfiguration config = langFiles.get(fileName);
        return config != null ? config.getString(key, "Message not found: "+ key) : "File not found: " + fileName;
    }

    // TODO: use for reload command
    public void reload() {
        reloadFile("broadcast");
        reloadFile("errors");
        reloadFile("essentials");
        reloadFile("general");
        reloadFile("guis");
        reloadFile("pvp");
    }

    public void reloadFile(String file) {
        File langFile = new File(plugin.getDataFolder(), "lang/" + file + ".yml");
        if (!langFile.exists()) {
            plugin.saveResource("lang/" + file + ".yml", false);
        }
        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);
            langFiles.put(file, config); // Store each configuration in the map
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getActiveLangFileCount() {
        return langFiles.size(); // Returns the number of loaded language files
    }

    public FileConfiguration getConfig(String file) {
        return langFiles.get(file);
    }


    public List<String> getAllLangFilenames() {
        File langFolder = new File(plugin.getDataFolder(), "lang");
        List<String> langFiles = new ArrayList<>();

        if (langFolder.exists() && langFolder.isDirectory()) {
            File[] files = langFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".yml");
                }
            });

            if (files != null) {
                for (File file : files) {
                    langFiles.add(file.getName().replace(".yml", ""));
                }
            }
        }

        return langFiles;
    }

}
