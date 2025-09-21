package main.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import main.dao.PatientDAO;
import main.models.Patients;
import main.util.Database;

/**
 * Command Line Interface for managing patient records in the EMR system.
 * This class provides a menu-driven interface for performing CRUD operations
 * on patient data, including creating new patients and placeholder methods
 * for future read, update, and delete operations.
 */
public class PatientsCLI {

    /** Data Access Object for patient database operations */
    private PatientDAO patientDAO;

    /** Scanner for reading user input from console */
    private Scanner scanner;

    /** Date formatter for parsing and displaying dates in yyyy-MM-dd format */
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a new PatientsCLI with the specified database connection.
     *
     * @param db the Database utility instance for managing connections
     */
    public PatientsCLI(Database db) {
        this.patientDAO = new PatientDAO(db);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the patient management interface.
     * Displays the patient menu and processes user choices in a loop
     * until the user chooses to return to the main menu.
     */
    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createPatient();
                    break;
                case 2:
                    readPatient();
                    break;
                case 3:
                    readAllPatients();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    deletePatient();
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
     * Displays the patient management menu options.
     * Shows all available operations for patient management.
     */
    private void showMenu() {
        System.out.println("\n=== Patient Management System ===");
        System.out.println("1. Create Patient");
        System.out.println("2. Read Patient by MRN");
        System.out.println("3. Read All Patients");
        System.out.println("4. Update Patient");
        System.out.println("5. Delete Patient");
        System.out.println("6. Back to Main Menu");
        System.out.println("=================================");
    }

    /**
     * Handles the creation of a new patient record.
     * Prompts the user for all required patient information,
     * validates the input, and attempts to save the patient to the database.
     */
    private void createPatient() {
        System.out.println("\n--- Create New Patient ---");

        // Collect all required patient information from user input
        int mrn = getIntInput("Enter MRN: ");
        String fname = getRequiredStringInput("Enter First Name: ");
        String lname = getRequiredStringInput("Enter Last Name: ");
        LocalDate dob = getDateInput("Enter Date of Birth (yyyy-MM-dd): ");
        String address = getRequiredStringInput("Enter Address: ");
        String state = getRequiredStringInput("Enter State: ");
        String city = getRequiredStringInput("Enter City: ");
        int zip = getIntInput("Enter Zip Code: ");
        String insurance = getRequiredStringInput("Enter Insurance: ");

        // Create patient object with collected data
        Patients patient = new Patients(mrn, fname, lname, dob, address, state, city, zip, insurance);

        try {
            // Attempt to save patient to database
            if (patientDAO.createPatient(patient)) {
                System.out.println("\nPatient created successfully!");
            } else {
                System.out.println("\n!!! Failed to create patient. !!!");
            }
        } catch (Exception e) {
            System.out.println("\n!!! Error creating patient: " + e.getMessage() + " !!!");
        }
    }

    /**
     * Placeholder method for reading a patient by MRN.
     * Currently displays a "Work In Progress" message.
     * Future implementation will retrieve and display patient information.
     */
    private void readPatient() {
        System.out.println("\n--- Read Patient (WIP) ---");
    }

    /**
     * Placeholder method for reading all patients.
     * Currently displays a "Work In Progress" message.
     * Future implementation will retrieve and display all patient records.
     */
    private void readAllPatients() {
        System.out.println("\n--- All Patients (WIP) ---");
    }

    /**
     * Placeholder method for updating a patient record.
     * Currently displays a "Work In Progress" message.
     * Future implementation will allow modification of existing patient data.
     */
    private void updatePatient() {
        System.out.println("\n--- Update Patient (WIP) ---");
    }

    /**
     * Placeholder method for deleting a patient record.
     * Currently displays a "Work In Progress" message.
     * Future implementation will allow removal of patient records with
     * confirmation.
     */
    private void deletePatient() {
        System.out.println("\n--- Delete Patient (WIP) ---");
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