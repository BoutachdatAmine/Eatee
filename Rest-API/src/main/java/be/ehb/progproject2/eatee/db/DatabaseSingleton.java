package be.ehb.progproject2.eatee.db;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {
    private static DatabaseSingleton instance = null;
    //private Connection connection;
    private static BasicDataSource ds = new BasicDataSource();
    private static final Dotenv env = Dotenv.configure().load();

    static {
        ds.setUrl(env.get("DB_URL") + env.get("DB_USERNAME"));
        ds.setUsername(env.get("DB_USERNAME"));
        ds.setPassword(env.get("DB_PASSWORD"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(200);
    }

    private DatabaseSingleton() {
    }

    public static DatabaseSingleton getInstance() {
        if(instance == null)
            instance = new DatabaseSingleton();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        /*if(connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    env.get("DB_URL") + env.get("DB_USERNAME") + "?autoReconnect=true&allowMultiQueries=true", // URL
                    env.get("DB_USERNAME"),                         // Username
                    env.get("DB_PASSWORD")                          // Password
            );
            connection.setAutoCommit(true);
        }

        return connection;*/
        return ds.getConnection();
    }
}
