package library.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/librarydb";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "1776";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", System.getenv().getOrDefault("DB_USER", DEFAULT_USER));
        props.setProperty("password", System.getenv().getOrDefault("DB_PASSWORD", DEFAULT_PASSWORD));
        String url = System.getenv().getOrDefault("DB_URL", DEFAULT_URL);
        return DriverManager.getConnection(url, props);
    }
}

