package me.hektortm.wosCore.logging;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.WoSCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogManager {

    private final LangManager lang;
    private final WoSCore plugin;

    public LogManager(LangManager lang, WoSCore plugin) {
        this.lang = lang;
        this.plugin = plugin;
    }

    /**
     * Writes a log entry to the daily log file.
     * @param executor The player who executed the action
     * @param message The message to log
     */
    public void writeLog(Player executor, String message) {

    }

    public void sendWarning(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.checkUnlockable(player.getUniqueId(), "core_warnings")) {
                String msg = lang.getMessage("general", "prefix.warning") + Utils.replaceColorPlaceholders(message);
                player.sendMessage(msg);
            }
        }
    }

}