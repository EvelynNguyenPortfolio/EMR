package main.cli;

import java.util.Scanner;

import main.dao.ProceduresDAO;
import main.models.Procedures;
import main.util.Database;

/**
 * Command Line Interface for managing medical procedures in the EMR system.
 * This class provides a menu-driven interface for performing CRUD operations
 * on medical procedure data, which includes procedure IDs and descriptive
 * names.
 */
public class ProceduresCLI {

    /** Data Access Object for procedures database operations */
    private ProceduresDAO proceduresDAO;

    /** Scanner for reading user input from console */
    private Scanner scanner;

    /**
     * Constructs a new ProceduresCLI with the specified database connection.
     *
     * @param db the Database utility instance for managing connections
     */
    public ProceduresCLI(Database db) {
        this.proceduresDAO = new ProceduresDAO(db);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the procedures management interface.
     * Displays the procedures menu and processes user choices in a loop
     * until the user chooses to return to the main menu.
     */
    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createProcedure();
                    break;
                case 2:
                    readProcedure();
                    break;
                case 3:
                    readAllProcedures();
                    break;
                case 4:
                    updateProcedure();
                    break;
                case 5:
                    deleteProcedure();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nReturning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the procedures management menu options.
     * Shows all available operations for medical procedures management.
     */
    private void showMenu() {
        System.out.println("\n=== Procedures Management ===");
        System.out.println("1. Create Procedure");
        System.out.println("2. Read Procedure by ID");
        System.out.println("3. Read All Procedures");
        System.out.println("4. Update Procedure");
        System.out.println("5. Delete Procedure");
        System.out.println("6. Back to Main Menu");
        System.out.println("============================");
    }

    /**
     * Handles the creation of a new medical procedure record.
     * Prompts the user for procedure ID and name, validates the input,
     * and attempts to save the procedure to the database.
     *
     * Medical procedures represent the various treatments and services
     * that can be performed on patients and billed accordingly.
     */
    private void createProcedure() {
        System.out.println("\n--- Create New Procedure ---");

        // Collect procedure information from user input
        String id = getRequiredStringInput("Enter Procedure ID: ");
        String name = getRequiredStringInput("Enter Procedure Name: ");

        // Create procedure object with collected data
        Procedures procedure = new Procedures(id, name);

        try {
            // Attempt to save procedure record to database
            if (proceduresDAO.createProcedure(procedure)) {
                System.out.println("Procedure created successfully!");
            } else {
                System.out.println("Failed to create procedure.");
            }
        } catch (Exception e) {
            System.out.println("Error creating procedure: " + e.getMessage());
        }
    }

    /**
     * Placeholder method for reading a procedure record by ID.
     * Currently displays a "Work In Progress" message.
     * Future implementation will retrieve and display procedure information.
     */
    private void readProcedure() {
        System.out.println("\n--- Read Procedure (WIP) ---");
    }

    /**
     * Placeholder method for reading all procedure records.
     * Currently displays a "Work In Progress" message.
     * Future implementation will retrieve and display all medical procedures.
     */
    private void readAllProcedures() {
        System.out.println("\n--- All Procedures (WIP) ---");
    }

    /**
     * Placeholder method for updating a procedure record.
     * Currently displays a "Work In Progress" message.
     * Future implementation will allow modification of existing procedure data.
     */
    private void updateProcedure() {
        System.out.println("\n--- Update Procedure (WIP) ---");
    }

    /**
     * Placeholder method for deleting a procedure record.
     * Currently displays a "Work In Progress" message.
     * Future implementation will allow removal of procedure records with
     * confirmation.
     */
    private void deleteProcedure() {
        System.out.println("\n--- Delete Procedure (WIP) ---");
    }

    // ==================== Helper Methods for Input Validation ====================

    /**
     * Reads and validates integer input from the user.
     * Continuously prompts until a valid integer is entered.
     *
     * @param prompt the message to display when asking for input
     * @return the validated integer input
     */
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a string input from the user.
     *
     * @param prompt the message to display when asking for input
     * @return the trimmed string input
     */
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Reads and validates required string input from the user.
     * Ensures the input is not empty by continuously prompting until valid input is
     * provided.
     *
     * @param prompt the message to display when asking for input
     * @return the validated non-empty string input
     */
    private String getRequiredStringInput(String prompt) {
        String input;
        do {
            input = getStringInput(prompt);
            if (input.isEmpty()) {
                System.out.println("This field is required. Please enter a value.");
            }
        } while (input.isEmpty());
        return input;
    }
}