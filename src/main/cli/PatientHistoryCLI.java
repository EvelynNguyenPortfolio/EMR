package main.cli;

import java.time.LocalDate;
import java.util.List;
import main.dao.DoctorDAO;
import main.dao.PatientDAO;
import main.dao.PatientHistoryDAO;
import main.dao.ProcedureDAO;
import main.exception.DatabaseException;
import main.model.PatientHistory;
import main.util.Database;

/**
 * CLI handler for PatientHistory entity management.
 * <p>
 * This class provides a command-line interface for performing CRUD operations
 * on PatientHistory records in the EMR system. It includes validation for
 * foreign key references to Patient, Procedure, and Doctor entities.
 * </p>
 *
 */
public class PatientHistoryCLI extends CLI {

    private final PatientHistoryDAO patientHistoryDAO;
    private final PatientDAO patientDAO;
    private final ProcedureDAO procedureDAO;
    private final DoctorDAO doctorDAO;

    /**
     * Constructs a new PatientHistoryCLI with the specified database connection.
     *
     * @param db the database connection to use
     */
    public PatientHistoryCLI(Database db) {
        super();
        this.patientHistoryDAO = new PatientHistoryDAO(db);
        this.patientDAO = new PatientDAO(db);
        this.procedureDAO = new ProcedureDAO(db);
        this.doctorDAO = new DoctorDAO(db);
    }

    /**
     * Starts the PatientHistory management CLI interface.
     */
    @Override
    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            System.out.println();

            switch (choice) {
                case 1:
                    // Create a new patient history record
                    createPatientHistory();
                    break;
                case 2:
                    // Read a patient history by ID
                    readPatientHistory();
                    break;
                case 3:
                    // Read all patient histories
                    readAllPatientHistories();
                    break;
                case 4:
                    // Update an existing patient history
                    updatePatientHistory();
                    break;
                case 5:
                    // Delete a patient history
                    deletePatientHistory();
                    break;
                case 6:
                    // Return to main menu
                    running = false;
                    System.out.println("Returning to main menu");
                    break;
                default:
                    // Handle invalid menu choice
                    showError("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the PatientHistory management menu.
     */
    private void showMenu() {
        System.out.println("Patient History Management");
        System.out.println();
        System.out.println("1. Create Patient History");
        System.out.println("2. Read Patient History by ID");
        System.out.println("3. Read All Patient History");
        System.out.println("4. Update Patient History");
        System.out.println("5. Delete Patient History");
        System.out.println("6. Back to Main Menu");
    }

    /**
     * Handles the creation of a new patient history record.
     */
    private void createPatientHistory() {
        printSeparator();
        System.out.println("Create New Patient History");

        // Prompt user for history ID
        String id = getRequiredStringInput("Enter History ID: ");

        // Validate ID length
        if (id.length() > 25) {
            showError("History ID cannot exceed 25 characters");
            System.out.println();
            return;
        }

        // Check if history ID already exists
        try {
            if (patientHistoryDAO.exists(id)) {
                showError(
                    "A patient history with ID '" + id + "' already exists"
                );
                System.out.println();
                return;
            }
        } catch (DatabaseException e) {
            showError("Error checking history ID: " + e.getMessage());
            System.out.println();
            return;
        }

        // Prompt user for patient MRN
        int patientId = getIntInput("Enter Patient MRN: ");

        // Validate patient exists
        try {
            if (!patientDAO.exists(patientId)) {
                showError(
                    "Patient with MRN '" + patientId + "' does not exist"
                );
                System.out.println();
                return;
            }
        } catch (DatabaseException e) {
            showError("Error validating patient: " + e.getMessage());
            System.out.println();
            return;
        }

        // Prompt user for procedure ID
        String procedureId = getRequiredStringInput("Enter Procedure ID: ");

        // Validate procedure ID length
        if (procedureId.length() > 25) {
            showError("Procedure ID cannot exceed 25 characters");
            System.out.println();
            return;
        }

        // Validate procedure exists
        try {
            if (!procedureDAO.exists(procedureId)) {
                showError(
                    "Procedure with ID '" + procedureId + "' does not exist"
                );
                System.out.println();
                return;
            }
        } catch (DatabaseException e) {
            showError("Error validating procedure: " + e.getMessage());
            System.out.println();
            return;
        }

        // Prompt user for date of procedure
        LocalDate date = getDateInput("Enter Date (yyyy-MM-dd): ");

        // Validate date is not in future
        if (date.isAfter(LocalDate.now())) {
            showError("Date cannot be in the future");
            System.out.println();
            return;
        }

        // Prompt user for billing amount
        double billing = getPositiveDoubleInput("Enter Billing Amount: ");

        // Prompt user for doctor ID
        String doctorId = getRequiredStringInput("Enter Doctor ID: ");

        // Validate doctor ID length
        if (doctorId.length() > 25) {
            showError("Doctor ID cannot exceed 25 characters");
            System.out.println();
            return;
        }

        // Validate doctor exists
        try {
            if (!doctorDAO.exists(doctorId)) {
                showError("Doctor with ID '" + doctorId + "' does not exist");
                System.out.println();
                return;
            }
        } catch (DatabaseException e) {
            showError("Error validating doctor: " + e.getMessage());
            System.out.println();
            return;
        }

        // Create a new PatientHistory object with all collected data
        PatientHistory patientHistory = new PatientHistory(
            id,
            patientId,
            procedureId,
            date,
            billing,
            doctorId
        );

        try {
            System.out.println();
            // Attempt to create the patient history in the database
            if (patientHistoryDAO.create(patientHistory)) {
                showSuccess("Patient history created successfully");
            } else {
                showError("Failed to create patient history");
            }
        } catch (DatabaseException e) {
            showError("Error creating patient history: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles reading a single patient history by ID.
     */
    private void readPatientHistory() {
        printSeparator();
        System.out.println("Read Patient History");

        // Prompt user for history ID
        String id = getRequiredStringInput("Enter History ID: ");

        try {
            // Attempt to read the patient history from the database
            PatientHistory history = patientHistoryDAO.read(id);
            System.out.println();
            // Check if history was found and display accordingly
            if (history != null) {
                displayPatientHistory(history);
            } else {
                showNotFound("Patient History", id);
            }
        } catch (DatabaseException e) {
            showError("Error reading Patient History: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles reading all patient histories from the database.
     */
    private void readAllPatientHistories() {
        printSeparator();
        System.out.println("Read All Patient Histories");

        try {
            System.out.println();
            List<PatientHistory> histories = patientHistoryDAO.readAll();
            if (!histories.isEmpty()) {
                int count = 1;
                for (PatientHistory h : histories) {
                    System.out.println("Patient History " + count + ":");
                    displayPatientHistory(h);
                    System.out.println();
                    count++;
                }
            } else {
                showEmpty("No patient histories found");
            }
        } catch (DatabaseException e) {
            showError("Error reading all Patient Histories: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles updating an existing patient history record.
     */
    private void updatePatientHistory() {
        printSeparator();
        System.out.println("Update Patient History");

        // Prompt user for history ID
        String patientHistoryID = getRequiredStringInput("Enter History ID: ");
        PatientHistory history;
        // Fetch the patient history from database
        try {
            history = patientHistoryDAO.read(patientHistoryID);
        } catch (DatabaseException e) {
            showError("Error retrieving record: " + e.getMessage());
            System.out.println();
            return;
        }

        // Check if patient history exists
        if (history == null) {
            showNotFound("Patient History", patientHistoryID);
            System.out.println();
            return;
        }

        System.out.println();
        // Display current patient history details
        showInfo("Current patient history details:");
        displayPatientHistory(history);
        System.out.println();

        String input;

        // Begin updating patient history fields
        // Prompt user to update patient MRN
        input = getStringInput("Update Patient MRN (leave empty to skip): ");
        if (!input.isEmpty()) {
            try {
                int patientId = Integer.parseInt(input);
                // Validate patient exists
                try {
                    if (!patientDAO.exists(patientId)) {
                        showError(
                            "Patient with MRN '" +
                                patientId +
                                "' does not exist"
                        );
                        System.out.println();
                        return;
                    }
                } catch (DatabaseException e) {
                    showError("Error validating patient: " + e.getMessage());
                    System.out.println();
                    return;
                }
                history.setPatientId(patientId);
            } catch (NumberFormatException e) {
                showError("Invalid Patient MRN. Please enter a valid number.");
                System.out.println();
                return;
            }
        }

        // Prompt user to update procedure ID
        input = getStringInput("Update Procedure ID (leave empty to skip): ");
        if (!input.isEmpty()) {
            // Validate procedure ID length
            if (input.length() > 25) {
                showError("Procedure ID cannot exceed 25 characters");
                System.out.println();
                return;
            }
            // Validate procedure exists
            try {
                if (!procedureDAO.exists(input)) {
                    showError(
                        "Procedure with ID '" + input + "' does not exist"
                    );
                    System.out.println();
                    return;
                }
            } catch (DatabaseException e) {
                showError("Error validating procedure: " + e.getMessage());
                System.out.println();
                return;
            }
            history.setProcedureId(input);
        }

        // Prompt user to update date of procedure
        input = getStringInput(
            "Update Date of Procedure (yyyy-MM-dd, leave empty to skip): "
        );
        if (!input.isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(input, DATE_FORMATTER);
                if (date.isAfter(LocalDate.now())) {
                    showError("Date cannot be in the future");
                    System.out.println();
                    return;
                }
                history.setDate(date);
            } catch (Exception e) {
                showError("Invalid date format. Please use yyyy-MM-dd format.");
                System.out.println();
                return;
            }
        }

        // Prompt user to update billing amount
        input = getStringInput("Update Billing Amount (leave empty to skip): ");
        if (!input.isEmpty()) {
            try {
                double billing = Double.parseDouble(input);
                if (billing < 0) {
                    showError("Billing amount cannot be negative");
                    System.out.println();
                    return;
                }
                history.setBilling(billing);
            } catch (NumberFormatException e) {
                showError(
                    "Invalid billing amount. Please enter a valid number."
                );
                System.out.println();
                return;
            }
        }

        // Prompt user to update doctor ID
        input = getStringInput("Update Doctor ID (leave empty to skip): ");
        if (!input.isEmpty()) {
            // Validate doctor ID length
            if (input.length() > 25) {
                showError("Doctor ID cannot exceed 25 characters");
                System.out.println();
                return;
            }
            // Validate doctor exists
            try {
                if (!doctorDAO.exists(input)) {
                    showError("Doctor with ID '" + input + "' does not exist");
                    System.out.println();
                    return;
                }
            } catch (DatabaseException e) {
                showError("Error validating doctor: " + e.getMessage());
                System.out.println();
                return;
            }
            history.setDoctorId(input);
        }

        // Attempt to update the patient history in the database
        try {
            System.out.println();
            boolean update = patientHistoryDAO.update(history);
            if (update) {
                showSuccess("Patient history has been updated");
            } else {
                showError("Update failed");
            }
        } catch (DatabaseException e) {
            showError("Error updating patient history: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles deleting a patient history record.
     */
    private void deletePatientHistory() {
        printSeparator();
        System.out.println("Delete Patient History");

        // Prompt user for history ID
        String id = getRequiredStringInput("Enter History ID: ");
        PatientHistory history;
        // Fetch patient history from database to verify existence
        try {
            history = patientHistoryDAO.read(id);
        } catch (DatabaseException e) {
            showError("Error fetching patient history: " + e.getMessage());
            System.out.println();
            return;
        }

        // Check if patient history exists
        if (history == null) {
            showNotFound("Patient History", id);
            System.out.println();
            return;
        }

        System.out.println();
        // Display patient history details for confirmation
        showInfo("Patient history details:");
        displayPatientHistory(history);
        System.out.println();

        // Ask for user confirmation before deletion
        if (
            getConfirmation(
                "[WARN] Are you sure you want to delete this patient history? (y/n): "
            )
        ) {
            try {
                System.out.println();
                // Attempt to delete the patient history from the database
                boolean deleted = patientHistoryDAO.delete(id);
                if (deleted) {
                    showSuccess("Patient history deleted successfully");
                } else {
                    showError("Failed to delete patient history");
                }
            } catch (DatabaseException e) {
                showError("Error deleting patient history: " + e.getMessage());
            }
        } else {
            // Handle cancellation of deletion
            showCancelled("Deletion cancelled");
        }
        System.out.println();
    }

    /**
     * Displays a patient history's information in a formatted way.
     *
     * @param history the patient history to display
     */
    private void displayPatientHistory(PatientHistory history) {
        System.out.println("ID: " + history.getId());
        System.out.println("Patient MRN: " + history.getPatientId());
        System.out.println("Procedure ID: " + history.getProcedureId());
        System.out.println("Date: " + history.getDate());
        System.out.println(
            String.format("Billing: $%.2f", history.getBilling())
        );
        System.out.println("Doctor ID: " + history.getDoctorId());
    }
}
