package me.hektortm.wosCore;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.hektortm.wosCore.essentials.gamemode;
import me.hektortm.wosCore.essentials.teleport;
import me.hektortm.wosCore.essentials.time;
import me.hektortm.wosCore.essentials.weather;
import me.hektortm.wosCore.pvpsystem.PvPCommands;
import me.hektortm.wosCore.pvpsystem.PvPListeners;
import me.hektortm.wosCore.pvpsystem.PvPManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoSCore extends JavaPlugin {
    private PvPManager pvpManager;

    @Override
    public void onEnable() {
        try {
            // Attempt to get ProtocolLib's ProtocolManager
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        } catch (NoClassDefFoundError | Exception e) {
            getLogger().severe("ProtocolLib is not found. Please install ProtocolLib.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        saveDefaultConfig();
        utils.initPrefix(this);

        teleport teleportExecutor = new teleport();
        gamemode gamemodeExe = new gamemode();
        time timeExe = new time();
        weather weatherExe = new weather();

        pvpManager = new PvPManager(getDataFolder());
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


    }



    @Override
    public void onDisable() {
        reloadConfig();
        utils.initPrefix(this);
    }

    private void commandReg(String name, CommandExecutor exe) {
        if (getCommand(name) != null) {
            getCommand(name).setExecutor(exe);
        } else {
            getLogger().severe("Command '"+ name + "' was not found in plugin.yml");
        }
    }
}
