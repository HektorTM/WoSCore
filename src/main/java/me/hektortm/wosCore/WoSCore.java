package me.hektortm.wosCore;

import me.hektortm.wosCore.essentials.*;
import me.hektortm.wosCore.guis.ChatListener;
import me.hektortm.wosCore.guis.GuiCommand;
import me.hektortm.wosCore.guis.GuiListener;
import me.hektortm.wosCore.guis.GuiManager;
import me.hektortm.wosCore.pvpsystem.PvPCommands;
import me.hektortm.wosCore.pvpsystem.PvPListeners;
import me.hektortm.wosCore.pvpsystem.PvPManager;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;


@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class WoSCore extends JavaPlugin {
    private PlaytimeManager playtimeManager;
    private LangManager lang;
    private GuiManager guiManager;
    private File langDirectory;
    private File playerDataFolder;

    public WoSCore() {
    }


    @Override
    public void onEnable() {
        lang = new LangManager(this);
        guiManager = new GuiManager(this);

        this.langDirectory = new File(getDataFolder(), "lang");
        if(!langDirectory.exists()) {
            langDirectory.mkdirs();
        }
        this.playerDataFolder = new File(getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        int langFileCount = lang.getActiveLangFileCount();
        int guiFileCount = guiManager.getActiveGuiFileCount();

        getLogger().info("Active Language Files: " + langFileCount);
        getLogger().info("Active GUI Files: "+ guiFileCount);

        playtimeManager = new PlaytimeManager(getDataFolder());
        this.lang = new LangManager(this);
        GuiManager guiManager = new GuiManager(this);

        this.getCommand("gui").setExecutor(new GuiCommand(guiManager, lang));
        this.getServer().getPluginManager().registerEvents(new GuiListener(guiManager), this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(guiManager), this);

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent e) {
                playtimeManager.playerLogin(e.getPlayer());
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent e) {
                playtimeManager.playerLogout(e.getPlayer());
            }

        }, this);


        saveDefaultConfig();
        Utils.init(lang);

        Playtime playtimeExe = new Playtime(playtimeManager);
        Teleport teleportExecutor = new Teleport();
        Gamemode gamemodeExe = new Gamemode();
        Time timeExe = new Time();
        Weather weatherExe = new Weather();
        Broadcast broadcastExe = new Broadcast(lang);
        CoreCommands coreExe = new CoreCommands(lang, this);
        GuiCommand guiExe = new GuiCommand(new GuiManager(this), lang);

        PvPManager pvpManager = new PvPManager(getDataFolder());
        //noinspection DataFlowIssue
        getCommand("pvp").setExecutor(new PvPCommands(pvpManager, lang));
        tabcompReg("pvp");
        getServer().getPluginManager().registerEvents(new PvPListeners(pvpManager, lang), this);

        commandReg("tp", teleportExecutor);
        commandReg("tphere", teleportExecutor);
        commandReg("gmc", gamemodeExe);
        commandReg("gms", gamemodeExe);
        commandReg("gma", gamemodeExe);
        commandReg("gmsp", gamemodeExe);
        commandReg("fly", gamemodeExe);
        commandReg("speed", gamemodeExe);
        commandReg("day", timeExe);
        commandReg("night", timeExe);
        commandReg("ptime", timeExe);
        commandReg("sun", weatherExe);
        commandReg("rain", weatherExe);
        commandReg("storm", weatherExe);
        commandReg("playtime", playtimeExe);
        commandReg("broadcast", broadcastExe);
        commandReg("shout", broadcastExe);
        commandReg("core", coreExe);

        tabcompReg("broadcast");
        tabcompReg("ptime");
        tabcompReg("core");
        tabcompReg("gui");


    }



    @Override
    public void onDisable() {
        reloadConfig();
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
            getCommand(name).setTabCompleter(new CommandTabComplete(new GuiManager(this), lang));
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

}


