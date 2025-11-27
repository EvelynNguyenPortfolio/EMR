package main.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Abstract base class for all CLI (Command Line Interface) handlers in the EMR system.
 * <p>
 * This class provides common functionality for user input handling, including:
 * <ul>
 *   <li>Numeric input with validation</li>
 *   <li>String input with optional required validation</li>
 *   <li>Date input with format validation</li>
 * </ul>
 * </p>
 *
 * <p>All CLI subclasses should extend this class to ensure consistent
 * user interaction patterns across the application.</p>
 *
 */
public abstract class CLI {

    /**
     * Shared scanner instance for all CLI classes.
     * Using a shared scanner prevents issues with multiple Scanner instances
     * reading from System.in.
     */
    private static final Scanner SHARED_SCANNER = new Scanner(System.in);

    /**
     * The scanner instance used by this CLI for reading user input.
     */
    protected final Scanner scanner;

    /**
     * Date formatter for parsing and formatting dates in yyyy-MM-dd format.
     */
    protected static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a new CLI instance with the shared scanner.
     */
    protected CLI() {
        this.scanner = SHARED_SCANNER;
    }

    /**
     * Prompts the user for an integer input and validates it.
     * <p>
     * This method will continue prompting until a valid integer is entered.
     * </p>
     *
     * @param prompt the message to display to the user
     * @return the validated integer input
     */
    protected int getIntInput(String prompt) {
        while (true) {
            // Display the prompt to the user
            System.out.print(prompt);
            // Read the input from the user and trim whitespace
            String input = scanner.nextLine().trim();
            try {
                // Attempt to parse the input as an integer
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Display error message for invalid input and retry
                System.out.println(
                    "[ERROR] Invalid input. Please enter a valid number."
                );
            }
        }
    }

    /**
     * Prompts the user for a double input and validates it.
     * <p>
     * This method will continue prompting until a valid double is entered.
     * </p>
     *
     * @param prompt the message to display to the user
     * @return the validated double input
     */
    protected double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(
                    "[ERROR] Invalid input. Please enter a valid number."
                );
            }
        }
    }

    /**
     * Prompts the user for a positive double input and validates it.
     * <p>
     * This method will continue prompting until a valid positive double is entered.
     * </p>
     *
     * @param prompt the message to display to the user
     * @return the validated positive double input
     */
    protected double getPositiveDoubleInput(String prompt) {
        while (true) {
            double value = getDoubleInput(prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println(
                "[ERROR] Value cannot be negative. Please enter a positive number."
            );
        }
    }

    /**
     * Prompts the user for a string input.
     * <p>
     * This method accepts any input including empty strings.
     * </p>
     *
     * @param prompt the message to display to the user
     * @return the trimmed string input
     */
    protected String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Prompts the user for a required (non-empty) string input.
     * <p>
     * This method will continue prompting until a non-empty string is entered.
     * </p>
     *
     * @param prompt the message to display to the user
     * @return the validated non-empty string input
     */
    protected String getRequiredStringInput(String prompt) {
        // Declare variable to store user input
        String input;
        // Loop until a non-empty input is provided
        do {
            // Get input from the user
            input = getStringInput(prompt);
            // Check if the input is empty
            if (input.isEmpty()) {
                // Display error message for required field
                System.out.println(
                    "[ERROR] This field is required. Please enter a value."
                );
            }
        } while (input.isEmpty());
        // Return the valid non-empty input
        return input;
    }

    /**
     * Prompts the user for a date input in yyyy-MM-dd format.
     * <p>
     * This method will continue prompting until a valid date is entered.
     * </p>
     *
     * @param prompt the message to display to the user
     * @return the validated LocalDate input
     */
    protected LocalDate getDateInput(String prompt) {
        // Loop until a valid date is entered
        while (true) {
            // Get required string input from the user
            String input = getRequiredStringInput(prompt);
            try {
                // Attempt to parse the input as a LocalDate using the formatter
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                // Display error message for invalid date format and retry
                System.out.println(
                    "[ERROR] Invalid date format. Please use yyyy-MM-dd format (e.g., 2024-01-15)."
                );
            }
        }
    }

    /**
     * Prompts the user for confirmation (yes/no).
     *
     * @param prompt the message to display to the user
     * @return true if the user confirms, false otherwise
     */
    protected boolean getConfirmation(String prompt) {
        String input = getStringInput(prompt);
        return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
    }

    /**
     * Displays an error message with consistent formatting.
     *
     * @param message the error message to display
     */
    protected void showError(String message) {
        System.out.println("[ERROR] " + message);
    }

    /**
     * Displays a success message with consistent formatting.
     *
     * @param message the success message to display
     */
    protected void showSuccess(String message) {
        System.out.println("[OK] " + message);
    }

    /**
     * Displays an info message with consistent formatting.
     *
     * @param message the info message to display
     */
    protected void showInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    /**
     * Displays a warning message with consistent formatting.
     *
     * @param message the warning message to display
     */
    protected void showWarning(String message) {
        System.out.println("[WARN] " + message);
    }

    /**
     * Displays a "not found" message with consistent formatting.
     *
     * @param entityType the type of entity that was not found
     * @param id the ID that was searched for
     */
    protected void showNotFound(String entityType, Object id) {
        System.out.println(
            "[NOT FOUND] " + entityType + " with ID '" + id + "' not found"
        );
    }

    /**
     * Displays an "empty" message with consistent formatting.
     *
     * @param message the message to display
     */
    protected void showEmpty(String message) {
        System.out.println("[EMPTY] " + message);
    }

    /**
     * Displays a "cancelled" message with consistent formatting.
     *
     * @param message the message to display
     */
    protected void showCancelled(String message) {
        System.out.println("[CANCELLED] " + message);
    }

    /**
     * Prints a separator line for visual organization.
     */
    protected void printSeparator() {
        System.out.println("-----");
    }

    /**
     * Starts the CLI interface.
     * <p>
     * Subclasses must implement this method to provide their specific
     * menu and functionality.
     * </p>
     */
    public abstract void start();
}
