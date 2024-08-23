package me.hektortm.wosCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FilenameFilter;
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

    private void loadLangFileExternal(Plugin plug, String filename, WoSCore plugin){
        File file = new File(plug.getDataFolder(), "lang/" +filename+".yml");
        if (!file.exists()) {
            plugin.saveResource("lang/"+filename+".yml", false);
        }
        langFiles.put(filename, YamlConfiguration.loadConfiguration(file));
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
