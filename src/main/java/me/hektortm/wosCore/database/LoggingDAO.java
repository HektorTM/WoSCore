package me.hektortm.wosCore.database;

import me.hektortm.wosCore.WoSCore;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class LoggingDAO implements IDAO{
    private final DatabaseManager db;
    private final WoSCore plugin = WoSCore.getPlugin(WoSCore.class);
    private final String logName = "LoggingDAO";

    public LoggingDAO(DatabaseManager db) {
        this.db = db;
    }

    @Override
    public void initializeTable() throws SQLException {
        try (Connection conn = db.getConnection(); Statement statement = conn.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS logs (
                    time TIMESTAMP DEFAULT CURRENT TIMESTAMP,
                    username VARCHAR(36) NOT NULL,
                    log TEXT NOT NULL,
                )
            """);
        }
    }

    public void writeLog(Player player, String message) {
        String sql = "INSERT INTO logs (username, log) VALUES (?, ?)";
        try (Connection conn = db.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setString(2, message);
            statement.execute();
        } catch (SQLException e) {
            plugin.writeLog(logName, Level.SEVERE, "Failed to write Log");
        }
    }

}
