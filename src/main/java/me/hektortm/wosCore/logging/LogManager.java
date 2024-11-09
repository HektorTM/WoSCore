package me.hektortm.wosCore.logging;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.WoSCore;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
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
    private final File logFolder;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    private final DateTimeFormatter logEntryFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LogManager(LangManager lang, WoSCore plugin) {
        this.lang = lang;
        this.plugin = plugin;
        this.logFolder = new File(plugin.getDataFolder(), "logs");

        // Ensure the log folder exists
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }
    }

    /**
     * Writes a log entry to the daily log file.
     * @param executor The player who executed the action
     * @param message The message to log
     */
    public void writeLog(Player executor, String message) {
        String date = dateFormatter.format(LocalDateTime.now());
        File logFile = new File(logFolder, date + "-log.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String time = logEntryFormatter.format(LocalDateTime.now());
            String logEntry = String.format("[%s] %s: %s",
                    time, executor.getName(), message);

            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to write log entry: " + e.getMessage());
        }
    }

    public List<String> getLogsForPlayer(String playerName) {
        List<String> playerLogs = new ArrayList<>();

        // Loop through all files in the log directory
        File[] logFiles = logFolder.listFiles();
        if (logFiles == null) return playerLogs;

        for (File file : logFiles) {
            if (file.isFile() && file.getName().endsWith("-log.txt")) {
                try (Stream<String> lines = Files.lines(file.toPath())) {
                    List<String> filteredLines = lines
                            .filter(line -> line.contains(playerName))
                            .collect(Collectors.toList());
                    playerLogs.addAll(filteredLines);
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to read log file: " + file.getName());
                }
            }
        }
        return playerLogs;
    }

    public void sendWarning(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PermUtil.hasPermissionNoMsg(player, Permissions.WARNING_USER)) {
                String msg = lang.getMessage("general", "prefix.warning") + message;
                player.sendMessage(msg);
            }
        }
    }

}