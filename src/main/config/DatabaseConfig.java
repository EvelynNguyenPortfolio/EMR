package main.config;

/**
 * Configuration class for database connection settings.
 * <p>
 * This class manages the database connection parameters for the EMR application.
 * It supports loading configuration from environment variables with fallback
 * to default values for development purposes.
 * </p>
 *
 * <p>The following environment variables are supported:</p>
 * <ul>
 *   <li>{@code EMR_DB_URL} - The JDBC connection URL</li>
 *   <li>{@code EMR_DB_USER} - The database username</li>
 *   <li>{@code EMR_DB_PASSWORD} - The database password</li>
 * </ul>
 *
 * <p><strong>Security Note:</strong> For production environments, always use
 * environment variables or a secure configuration management system.
 * Never commit credentials to version control.</p>
 *
 */
public class DatabaseConfig {

    /** Default JDBC URL for local development. */
    private static final String DEFAULT_URL =
        "jdbc:mysql://localhost:3306/emr_db";

    /** Default username for local development. */
    private static final String DEFAULT_USER = "root";

    /** Default password for local development (empty for security). */
    private static final String DEFAULT_PASSWORD = "";

    /** The JDBC connection URL. */
    private final String url;

    /** The database username. */
    private final String user;

    /** The database password. */
    private final String password;

    /**
     * Constructs a new DatabaseConfig using environment variables.
     * <p>
     * This constructor reads configuration from the following environment variables:
     * <ul>
     *   <li>{@code EMR_DB_URL} - defaults to {@code jdbc:mysql://localhost:3306/emr_db}</li>
     *   <li>{@code EMR_DB_USER} - defaults to {@code root}</li>
     *   <li>{@code EMR_DB_PASSWORD} - defaults to empty string</li>
     * </ul>
     * </p>
     */
    public DatabaseConfig() {
        this.url = getEnvOrDefault("EMR_DB_URL", DEFAULT_URL);
        this.user = getEnvOrDefault("EMR_DB_USER", DEFAULT_USER);
        this.password = getEnvOrDefault("EMR_DB_PASSWORD", DEFAULT_PASSWORD);
    }

    /**
     * Constructs a new DatabaseConfig with explicit connection parameters.
     * <p>
     * Use this constructor when you need to override the default or
     * environment-based configuration, such as in testing scenarios.
     * </p>
     *
     * @param url      the JDBC connection URL
     * @param user     the database username
     * @param password the database password
     */
    public DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Gets an environment variable value or returns a default if not set.
     *
     * @param key          the environment variable name
     * @param defaultValue the default value to use if the variable is not set
     * @return the environment variable value or the default value
     */
    private String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Gets the JDBC connection URL.
     *
     * @return the database URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the database username.
     *
     * @return the database user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the database password.
     *
     * @return the database password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns a string representation of this configuration.
     * <p>
     * Note: The password is masked for security purposes.
     * </p>
     *
     * @return a string representation of the configuration
     */
    @Override
    public String toString() {
        return (
            "DatabaseConfig{" +
            "url='" +
            url +
            '\'' +
            ", user='" +
            user +
            '\'' +
            ", password='****'" +
            '}'
        );
    }
}
