package main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private Connection connection;

    public Database() {
        this.URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:mysql://localhost:3306/dbname";
        this.USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "your_username";
        this.PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "your_password";
        connect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("=== Connected to MySQL database ===\n");
        } catch (SQLException e) {
            System.err.println(
                    "!!! Connection failed: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")!!!\n");
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("=== Database connection closed ===");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt.executeQuery();
    }

    public int executeUpdate(String query) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            return stmt.executeUpdate();
        }
    }
}
