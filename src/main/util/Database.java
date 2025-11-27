package main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import main.config.DatabaseConfig;

/**
 * Database utility class for managing MySQL database connections.
 * <p>
 * This class provides a centralized way to establish and manage database
 * connections for the EMR application. It uses configuration from
 * {@link DatabaseConfig} to obtain connection parameters.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Database db = new Database();
 * Connection conn = db.getConnection();
 * // Use connection for database operations
 * db.close();
 * }</pre>
 *
 * @see DatabaseConfig
 */
public class Database {

    private final DatabaseConfig config;
    private Connection connection;

    /**
     * Constructs a new Database instance with default configuration.
     * <p>
     * This constructor uses the default {@link DatabaseConfig} which reads
     * connection parameters from environment variables or falls back to
     * default values.
     * </p>
     *
     * @throws RuntimeException if the database connection cannot be established
     */
    public Database() {
        this(new DatabaseConfig());
    }

    /**
     * Constructs a new Database instance with the specified configuration.
     *
     * @param config the database configuration to use for the connection
     * @throws RuntimeException if the database connection cannot be established
     */
    public Database(DatabaseConfig config) {
        this.config = config;
        connect();
    }

    /**
     * Establishes a connection to the MySQL database.
     * <p>
     * This method uses JDBC to connect to the database using the URL,
     * username, and password from the configuration. If the connection
     * fails, a {@link RuntimeException} is thrown.
     * </p>
     *
     * @throws RuntimeException if the connection cannot be established
     */
    private void connect() {
        try {
            connection = DriverManager.getConnection(
                config.getUrl(),
                config.getUser(),
                config.getPassword()
            );
            System.out.println("[INFO] Connected to database\n");
        } catch (SQLException e) {
            System.out.println(
                "[ERROR] Database connection failed: " + e.getMessage() + "\n"
            );
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    /**
     * Closes the database connection.
     * <p>
     * This method should be called when the database connection is no longer
     * needed to release database resources. It is safe to call this method
     * even if the connection is already closed or was never established.
     * </p>
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[INFO] Database connection closed");
            } catch (SQLException e) {
                System.out.println(
                    "\n[WARN] Error closing database connection: " +
                        e.getMessage()
                );
            }
        }
    }

    /**
     * Gets the active database connection.
     * <p>
     * Returns the current JDBC connection that can be used for executing
     * SQL statements. The connection is established during construction
     * of this object.
     * </p>
     *
     * @return the active {@link Connection} to the database
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Checks if the database connection is valid and open.
     *
     * @return true if the connection is valid and open, false otherwise
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
