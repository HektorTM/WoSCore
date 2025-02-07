package me.hektortm.wosCore.database;

import org.bukkit.entity.Player;

import java.sql.*;

public class PlayerdataDAO implements IDAO {
    private final Connection conn;
    private final DatabaseManager db;

    public PlayerdataDAO(DatabaseManager db) {
        conn = db.getConnection();
        this.db = db;
    }

    @Override
    public void initializeTable() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS playerdata (
                    uuid VARCHAR(36) PRIMARY KEY,
                    username VARCHAR(36) NOT NULL,
                    last_known_name VARCHAR(16) NOT NULL,
                    last_online TIMESTAMP NOT NULL
                )
            """);
        }
    }

    public void addPlayer(Player player) {
        String sql = "INSERT INTO playerdata (uuid, username, last_known_name, last_online) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, player.getUniqueId().toString());
            pstmt.setString(2, player.getName());
            pstmt.setString(3, player.getName());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isInDatabase(Player player) {
        String sql = "SELECT 1 FROM playerdata WHERE uuid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUsername(Player player) {
        String sql = "UPDATE playerdata SET username = ?, last_known_name = ? WHERE uuid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getName());
            pstmt.setString(3, player.getUniqueId().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLastKnownName(Player player) {
        String sql = "SELECT last_known_name FROM playerdata WHERE uuid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("last_known_name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateLastOnline(Player player) {
        String sql = "UPDATE playerdata SET last_online = ? WHERE uuid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(2, player.getUniqueId().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
