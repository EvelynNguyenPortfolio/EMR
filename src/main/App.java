package main;

import main.cli.MainCLI;
import main.util.Database;

/**
 * Main entry point for the EMR (Electronic Medical Records) application.
 * <p>
 * This application provides a command-line interface for managing electronic
 * medical records, including patients, doctors, procedures, and patient history.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Patient management (CRUD operations)</li>
 *   <li>Doctor management (CRUD operations)</li>
 *   <li>Procedure management (CRUD operations)</li>
 *   <li>Patient history tracking with billing</li>
 * </ul>
 *
 * <h2>Configuration:</h2>
 * <p>
 * The application requires the following environment variables for database connection:
 * </p>
 * <ul>
 *   <li>{@code EMR_DB_URL} - JDBC connection URL (e.g., jdbc:mysql://localhost:3306/emr_db)</li>
 *   <li>{@code EMR_DB_USER} - Database username</li>
 *   <li>{@code EMR_DB_PASSWORD} - Database password</li>
 * </ul>
 *
 * @see main.cli.MainCLI
 * @see main.util.Database
 */
public class App {

    /**
     * Main method - entry point of the EMR application.
     * <p>
     * This method:
     * <ol>
     *   <li>Establishes a database connection</li>
     *   <li>Launches the main CLI interface</li>
     *   <li>Ensures proper cleanup of database resources on exit</li>
     * </ol>
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize database connection variable
        Database db = null;

        try {
            // Establish database connection
            db = new Database();

            // Start the main CLI interface
            new MainCLI(db).start();
        } catch (RuntimeException e) {
            // Handle any runtime exceptions and exit with error
            System.err.println("[FATAL] Application error: " + e.getMessage());
            System.exit(1);
        } finally {
            // Ensure database connection is closed
            if (db != null) {
                db.close();
            }
        }
    }
}
