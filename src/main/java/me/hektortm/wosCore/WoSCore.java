package me.hektortm.wosCore;

import me.hektortm.wosCore.essentials.*;
import me.hektortm.wosCore.pvpsystem.PvPCommands;
import me.hektortm.wosCore.pvpsystem.PvPListeners;
import me.hektortm.wosCore.pvpsystem.PvPManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings({"unused", "SpellCheckingInspection"})
public final class WoSCore extends JavaPlugin {
    private me.hektortm.wosCore.essentials.playtimeManager playtimeManager;

    @Override
    public void onEnable() {
        playtimeManager = new playtimeManager(getDataFolder());


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
        utils.initPrefix(this);
        playtime playtimeExe = new playtime(playtimeManager);
        teleport teleportExecutor = new teleport();
        gamemode gamemodeExe = new gamemode();
        time timeExe = new time();
        weather weatherExe = new weather();

        PvPManager pvpManager = new PvPManager(getDataFolder());
        //noinspection DataFlowIssue
        getCommand("pvp").setExecutor(new PvPCommands(pvpManager));
        getServer().getPluginManager().registerEvents(new PvPListeners(pvpManager), this);

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


    }



    @Override
    public void onDisable() {
        reloadConfig();
        utils.initPrefix(this);
    }

    private void commandReg(String name, CommandExecutor exe) {
        if (getCommand(name) != null) {
            //noinspection DataFlowIssue
            getCommand(name).setExecutor(exe);
        } else {
            getLogger().severe("Command '"+ name + "' was not found in plugin.yml");
        }
    }
}
