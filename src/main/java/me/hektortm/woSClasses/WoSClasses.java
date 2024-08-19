package me.hektortm.woSClasses;

import me.hektortm.woSClasses.essentials.gamemode;
import me.hektortm.woSClasses.essentials.teleport;
import me.hektortm.woSClasses.essentials.time;
import me.hektortm.woSClasses.essentials.weather;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoSClasses extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        utils.initPrefix(this);

        teleport teleportExecutor = new teleport();
        gamemode gamemodeExe = new gamemode();
        time timeExe = new time();
        weather weatherExe = new weather();

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
