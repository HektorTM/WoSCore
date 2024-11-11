package me.hektortm.wosCore;

import me.hektortm.wosCore.discord.DiscordListener;
import me.hektortm.wosCore.discord.command.DiscordCommand;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.logging.command.DebugCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
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
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class WoSCore extends JavaPlugin {
    private LogManager logManager;
    private LangManager lang;
    private File langDirectory;
    private File playerDataFolder;
    public static JDA jda;
    public static final String REQUIRED_ROLE_ID = "1277276951056482346";

    @Override
    public void onEnable() {
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

        CoreCommands coreExe = new CoreCommands(lang, this);

        commandReg("core", coreExe);
        tabcompReg("core");
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
    private void tabcompReg(String name) {
        if (getCommand(name) != null) {
            //noinspection DataFlowIssue
            getCommand(name).setTabCompleter(new CommandTabComplete(lang));
        } else {
            getLogger().severe("Tabcompletion '"+name+"' was not found.");
        }

    }

    public LangManager getLang() {
        return lang;
    }

    public void buttonClick(Player p) {
        p.playSound(p, Sound.UI_BUTTON_CLICK, 1, 1);
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

}


