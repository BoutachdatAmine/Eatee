package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.db.DatabaseSingleton;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseDAO {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed())
            connection = DatabaseSingleton.getInstance().getConnection();

        return connection;
    }
}
