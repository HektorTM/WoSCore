package me.hektortm.wosCore;

import me.hektortm.wosCore.database.DatabaseManager;
import me.hektortm.wosCore.discord.DiscordListener;
import me.hektortm.wosCore.discord.command.DiscordCommand;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.logging.command.DebugCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class WoSCore extends JavaPlugin {
    private static WoSCore instance;
    private DatabaseManager dbManager;
    private LogManager logManager;
    private LangManager lang;
    private File langDirectory;
    private File playerDataFolder;
    public static JDA jda;
    public static final String REQUIRED_ROLE_ID = "1277276951056482346";

    @Override
    public void onEnable() {
        instance = this;
        try {
            dbManager = new DatabaseManager(this.getDataFolder().getAbsolutePath() + "WoS.db");
        } catch (SQLException e) {
            writeLog("WoSCore", Level.SEVERE, "Failed to initialize database: " + e.getMessage());
        }
        lang = new LangManager(this);
        logManager = new LogManager(lang, this);

        this.langDirectory = new File(getDataFolder(), "lang");
        if(!langDirectory.exists()) {
            langDirectory.mkdirs();
        }
        this.playerDataFolder = new File(getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        int langFileCount = lang.getActiveLangFileCount();

        getLogger().info("Active Language Files: " + langFileCount);

        this.lang = new LangManager(this);

        saveDefaultConfig();
        Utils.init(lang);

        loadConfig();
        try {
            jda = JDABuilder.createDefault(getConfig().getString("BOT-TOKEN"))
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing("Minecraft"))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT) // Enable the MESSAGE_CONTENT intent
                    .addEventListeners(new DiscordListener()) // Register the command listener
                    .build().awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        commandReg("debug", new DebugCommand(logManager, lang, this));
        commandReg("discord", new DiscordCommand(this));
    }



    @Override
    public void onDisable() {
        reloadConfig();
        if (jda != null) {
            jda.shutdown();
            try {
                // Allow at most 10 seconds for remaining requests to finish
                if (!jda.awaitShutdown(Duration.ofSeconds(10))) {
                    // Cancel all remaining requests if shutdown is not complete
                    jda.shutdownNow();
                    jda.awaitShutdown(); // Wait until shutdown is complete (indefinitely)
                }
            } catch (InterruptedException e) {
                // Handle the exception if the thread is interrupted during shutdown
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void writeLog(String name, Level level, String message) {
        Logger LOGGER = Logger.getLogger(name);
        LOGGER.log(level, message);
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public FileConfiguration getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId(), player.getName());
    }

    public FileConfiguration getPlayerData(UUID uuid, String username) {
        File playerFile = new File(playerDataFolder, uuid.toString()+ ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

        if(!playerFile.exists()) {
            playerData.set("UUID", uuid.toString());
            playerData.set("Username", username);
            playerData.set("Joindate", new Date().toString());
            playerData.set("Lastonline", new Date().toString());
            savePlayerData(playerData, playerFile);
        }
        return playerData;
    }

    public void savePlayerData(FileConfiguration playerData, File playerFile) {
        try {
            playerData.save(playerFile);
        } catch (IOException e) {
            getLogger().severe("Could not save player data file for "+playerFile.getName());
        }
    }


    private void commandReg(String name, CommandExecutor exe) {
        if (getCommand(name) != null) {
            //noinspection DataFlowIssue
            getCommand(name).setExecutor(exe);
        } else {
            getLogger().severe("Command '"+ name + "' was not found in plugin.yml");
        }
    }



    public void addLangFile(Plugin plugin, String fileName) {
        try (InputStream inputStream = plugin.getResource("lang/" + fileName)) {
            if (inputStream == null) {
                plugin.getLogger().warning("Language file not found: " + fileName);
                return; // Exit the method if the file is not found
            }
            File targetFile = new File(langDirectory, fileName);
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to copy language file: " + fileName);
            e.printStackTrace();
        }
    }

    public boolean checkUnlockable(UUID uuid, String id) {
        File playerFile = new File(playerDataFolder, uuid.toString()+ ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

        String path = "unlockables";
        List<String> unlockableList = playerData.getStringList(path);
        if (unlockableList.contains(id)) {
            return true;
        }
        return false;
    }

    public LangManager getLang() {
        return lang;
    }
    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }

}


