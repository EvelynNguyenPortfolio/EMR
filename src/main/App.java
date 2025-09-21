package main;

import main.cli.MainCLI;
import main.util.Database;

/**
 * Main application class for the Electronic Medical Records (EMR) system.
 * This class serves as the entry point for the CLI-based EMR application.
 *
 * The application provides functionality to manage:
 * - Patient records
 * - Patient history/procedure records
 * - Medical procedures
 */
public class App {

    /**
     * Main method - entry point for the EMR application.
     * Initializes database connection and starts the main CLI interface.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Database db = null;

        try {
            // Initialize database connection using environment variables
            db = new Database();
        } catch (RuntimeException e) {
            // Exit if database connection fails
            System.exit(1);
        }

        if (db != null) {
            // Start the main CLI interface and close database connection when done
            new MainCLI(db).start();
            db.close();
        }
    }
}