package me.hektortm.wosCore;

import me.hektortm.wosCore.chatsystem.ChatListeners;
import me.hektortm.wosCore.essentials.*;
import me.hektortm.wosCore.guis.ChatListener;
import me.hektortm.wosCore.guis.GuiCommand;
import me.hektortm.wosCore.guis.GuiListener;
import me.hektortm.wosCore.guis.GuiManager;
import me.hektortm.wosCore.pvpsystem.PvPCommands;
import me.hektortm.wosCore.pvpsystem.PvPListeners;
import me.hektortm.wosCore.pvpsystem.PvPManager;
import org.bukkit.command.CommandExecutor;
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


@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class WoSCore extends JavaPlugin {
    private PlaytimeManager playtimeManager;
    private LangManager lang;
    private GuiManager guiManager;
    private File langDirectory;

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
        getServer().getPluginManager().registerEvents(new PvPListeners(pvpManager), this);
        getServer().getPluginManager().registerEvents(new ChatListeners(), this);

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


