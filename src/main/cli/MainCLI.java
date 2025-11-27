package main.cli;

import main.util.Database;

/**
 * Main CLI handler for the EMR Management System.
 * <p>
 * This class serves as the entry point for the command-line interface,
 * providing navigation to all entity management subsystems including:
 * <ul>
 *   <li>Patient management</li>
 *   <li>Patient history management</li>
 *   <li>Procedure management</li>
 *   <li>Doctor management</li>
 * </ul>
 * </p>
 *
 */
public class MainCLI extends CLI {

    private static final String WELCOME_MESSAGE =
        "Welcome to EMR Management System\n";

    private static final String GOODBYE_MESSAGE =
        "Thank you for using EMR Management System. Goodbye!\n";

    private final Database db;

    /**
     * Constructs a new MainCLI with the specified database connection.
     *
     * @param db the database connection to use for all operations
     */
    public MainCLI(Database db) {
        super();
        this.db = db;
    }

    /**
     * Starts the main EMR Management System CLI interface.
     * <p>
     * This method displays the main menu and handles navigation to
     * the various entity management subsystems. It runs in a loop
     * until the user chooses to exit.
     * </p>
     */
    @Override
    public void start() {
        System.out.println(WELCOME_MESSAGE);

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");

            System.out.println();

            switch (choice) {
                case 1:
                    // Navigate to patient management
                    new PatientsCLI(db).start();
                    System.out.println();
                    break;
                case 2:
                    // Navigate to patient history management
                    new PatientHistoryCLI(db).start();
                    System.out.println();
                    break;
                case 3:
                    // Navigate to procedure management
                    new ProceduresCLI(db).start();
                    System.out.println();
                    break;
                case 4:
                    // Navigate to doctor management
                    new DoctorsCLI(db).start();
                    System.out.println();
                    break;
                case 5:
                    // Exit the application
                    running = false;
                    System.out.println(GOODBYE_MESSAGE);
                    break;
                default:
                    // Handle invalid menu choice
                    showError("Invalid choice. Please try again.");
                    System.out.println();
            }
        }
    }

    /**
     * Displays the main menu options.
     */
    private void showMainMenu() {
        System.out.println("EMR Management System");
        System.out.println();
        System.out.println("1. Patients");
        System.out.println("2. Patient History");
        System.out.println("3. Procedures");
        System.out.println("4. Doctors");
        System.out.println("5. Exit");
    }
}
