package me.hektortm.wosCore.database;

import me.hektortm.wosCore.WoSCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DatabaseManager {
    private final Connection connection;
    private final WoSCore plugin;
    private final List<IDAO> daoList = new ArrayList<>();

    public DatabaseManager(String host, int port, String database, String username, String password) throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
        connection = DriverManager.getConnection(url, username, password);
        plugin = WoSCore.getPlugin(WoSCore.class);
        plugin.writeLog("DatabaseManager", Level.INFO, "Connected to MySQL database.");
    }


    public void registerDAO(IDAO dao){
        daoList.add(dao);
    }

    public void initializeAllDAOs() throws SQLException{
        for (IDAO dao : daoList){
            dao.initializeTable();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()){
            connection.close();
            plugin.writeLog("DatabaseManager", Level.INFO, "Database closed.");
        }
    }

}
