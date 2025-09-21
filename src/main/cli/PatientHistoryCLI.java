package main.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import main.dao.PatientHistoryDAO;
import main.models.PatientHistory;
import main.util.Database;

/**
 * Command Line Interface for managing patient history records in the EMR
 * system.
 * This class provides a menu-driven interface for performing CRUD operations
 * on patient history data, which links patients to medical procedures with
 * billing information and doctor details.
 */
public class PatientHistoryCLI {

    /** Data Access Object for patient history database operations */
    private PatientHistoryDAO patientHistoryDAO;

    /** Scanner for reading user input from console */
    private Scanner scanner;

    /** Date formatter for parsing and displaying dates in yyyy-MM-dd format */
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a new PatientHistoryCLI with the specified database connection.
     *
     * @param db the Database utility instance for managing connections
     */
    public PatientHistoryCLI(Database db) {
        this.patientHistoryDAO = new PatientHistoryDAO(db);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the patient history management interface.
     * Displays the patient history menu and processes user choices in a loop
     * until the user chooses to return to the main menu.
     */
    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createPatientHistory();
                    break;
                case 2:
                    readPatientHistory();
                    break;
                case 3:
                    readAllPatientHistory();
                    break;
                case 4:
                    updatePatientHistory();
                    break;
                case 5:
                    deletePatientHistory();
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
     * Displays the patient history management menu options.
     * Shows all available operations for patient history management.
     */
    private void showMenu() {
        System.out.println("\n=== Patient History Management ===");
        System.out.println("1. Create Patient History");
        System.out.println("2. Read Patient History by ID");
        System.out.println("3. Read All Patient History");
        System.out.println("4. Update Patient History");
        System.out.println("5. Delete Patient History");
        System.out.println("6. Back to Main Menu");
        System.out.println("==================================");
    }

    /**
     * Handles the creation of a new patient history record.
     * Prompts the user for all required patient history information,
     * validates the input, and attempts to save the record to the database.
     *
     * A patient history record links a patient to a medical procedure,
     * including billing amount and attending physician information.
     */
    private void createPatientHistory() {
        System.out.println("\n--- Create New Patient History ---");

        // Collect all required patient history information from user input
        String id = getRequiredStringInput("Enter History ID: ");
        int patientID = getIntInput("Enter Patient ID: ");
        String procedureID = getRequiredStringInput("Enter Procedure ID: ");
        LocalDate date = getDateInput("Enter Date (YYYY-MM-DD): ");
        double billing = getDoubleInput("Enter Billing Amount: ");
        String doctor = getRequiredStringInput("Enter Doctor Name: ");

        // Create patient history object with collected data
        PatientHistory patientHistory = new PatientHistory(id, patientID, procedureID, date, billing, doctor);

        try {
            // Attempt to save patient history record to database
            if (patientHistoryDAO.createPatientHistory(patientHistory)) {
                System.out.println("Patient history created successfully!");
            } else {
                System.out.println("Failed to create patient history.");
            }
        } catch (Exception e) {
            System.out.println("Error creating patient history: " + e.getMessage());
        }
    }

    /**
     * Placeholder method for reading a patient history record by ID.
     * Currently displays a "Work In Progress" message.
     * Future implementation will retrieve and display patient history information.
     */
    private void readPatientHistory() {
        System.out.println("\n--- Read Patient History (WIP) ---");
    }

    /**
     * Placeholder method for reading all patient history records.
     * Currently displays a "Work In Progress" message.
     * Future implementation will retrieve and display all patient history records.
     */
    private void readAllPatientHistory() {
        System.out.println("\n--- All Patient History (WIP) ---");
    }

    /**
     * Placeholder method for updating a patient history record.
     * Currently displays a "Work In Progress" message.
     * Future implementation will allow modification of existing patient history
     * data.
     */
    private void updatePatientHistory() {
        System.out.println("\n--- Update Patient History (WIP) ---");
    }

    /**
     * Placeholder method for deleting a patient history record.
     * Currently displays a "Work In Progress" message.
     * Future implementation will allow removal of patient history records with
     * confirmation.
     */
    private void deletePatientHistory() {
        System.out.println("\n--- Delete Patient History (WIP) ---");
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
     * Reads and validates double input from the user.
     * Continuously prompts until a valid decimal number is entered.
     *
     * @param prompt the message to display when asking for input
     * @return the validated double input
     */
    private double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
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

    /**
     * Reads and validates date input from the user.
     * Accepts dates in yyyy-MM-dd format and converts them to LocalDate objects.
     * Continuously prompts until a valid date is entered.
     *
     * @param prompt the message to display when asking for input
     * @return the validated LocalDate object
     */
    private LocalDate getDateInput(String prompt) {
        while (true) {
            String input = getRequiredStringInput(prompt);
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
    }
}