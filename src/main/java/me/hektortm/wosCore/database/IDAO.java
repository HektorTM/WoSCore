package me.hektortm.wosCore.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDAO {
    void initializeTable() throws SQLException;

    void initializeTable(Connection conn) throws SQLException;
}
