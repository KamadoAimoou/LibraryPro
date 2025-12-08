package library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
    // !!! ОБЯЗАТЕЛЬНО ИЗМЕНИТЬ ПАРОЛЬ И БАЗУ ДАННЫХ !!!
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/librarypro";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    public static Connection getConnection() throws SQLException {
        try {
            // Загрузка драйвера (нужно только один раз)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            throw new SQLException("PostgreSQL Driver not available.", e);
        }
        // Установление соединения
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Таблица BOOKS
            String sqlBooks = "CREATE TABLE IF NOT EXISTS books ("
                    + "id SERIAL PRIMARY KEY,"
                    + "title VARCHAR(255) NOT NULL,"
                    + "author VARCHAR(255) NOT NULL,"
                    + "genre VARCHAR(100),"
                    + "year INTEGER,"
                    + "status VARCHAR(50) NOT NULL"
                    + ")";
            stmt.execute(sqlBooks);

            // 2. Таблица BORROW_HISTORY
            String sqlHistory = "CREATE TABLE IF NOT EXISTS borrow_history ("
                    + "id SERIAL PRIMARY KEY,"
                    + "book_id INTEGER NOT NULL REFERENCES books(id),"
                    + "borrower_name VARCHAR(255) NOT NULL,"
                    + "borrow_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,"
                    + "return_date TIMESTAMP WITHOUT TIME ZONE"
                    + ")";
            stmt.execute(sqlHistory);

            System.out.println("PostgreSQL Database initialization complete.");

        } catch (SQLException e) {
            System.err.println("Database initialization failed. Check server status and credentials.");
            e.printStackTrace();
        }
    }
}