package main.cli;

import java.util.List;
import main.dao.DoctorDAO;
import main.dao.ProcedureDAO;
import main.exception.DatabaseException;
import main.model.Procedure;
import main.util.Database;

/**
 * CLI handler for Procedure entity management.
 * <p>
 * This class provides a command-line interface for performing CRUD operations
 * on Procedure records in the EMR system.
 * </p>
 *
 */
public class ProceduresCLI extends CLI {

    private final ProcedureDAO procedureDAO;
    private final DoctorDAO doctorDAO;

    /**
     * Constructs a new ProceduresCLI with the specified database connection.
     *
     * @param db the database connection to use
     */
    public ProceduresCLI(Database db) {
        super();
        this.procedureDAO = new ProcedureDAO(db);
        this.doctorDAO = new DoctorDAO(db);
    }

    /**
     * Starts the Procedure management CLI interface.
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
                    // Create a new procedure
                    createProcedure();
                    break;
                case 2:
                    // Read a procedure by ID
                    readProcedure();
                    break;
                case 3:
                    // Read all procedures
                    readAllProcedures();
                    break;
                case 4:
                    // Update an existing procedure
                    updateProcedure();
                    break;
                case 5:
                    // Delete a procedure
                    deleteProcedure();
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
     * Displays the Procedure management menu.
     */
    private void showMenu() {
        System.out.println("Procedures Management");
        System.out.println();
        System.out.println("1. Create Procedure");
        System.out.println("2. Read Procedure by ID");
        System.out.println("3. Read All Procedures");
        System.out.println("4. Update Procedure");
        System.out.println("5. Delete Procedure");
        System.out.println("6. Back to Main Menu");
    }

    /**
     * Handles the creation of a new procedure.
     */
    private void createProcedure() {
        printSeparator();
        System.out.println("Create New Procedure");

        // Prompt user for procedure ID
        String id = getRequiredStringInput("Enter Procedure ID: ");

        // Validate ID length
        if (id.length() > 25) {
            showError("Procedure ID cannot exceed 25 characters");
            System.out.println();
            return;
        }

        // Check if a procedure with this ID already exists to prevent duplicates
        try {
            if (procedureDAO.exists(id)) {
                showError("A procedure with ID '" + id + "' already exists");
                System.out.println();
                return;
            }
        } catch (DatabaseException e) {
            showError("Error checking procedure ID: " + e.getMessage());
            System.out.println();
            return;
        }

        // Prompt user for procedure name
        String name = getRequiredStringInput("Enter Procedure Name: ");
        // Prompt user for procedure description
        String description = getRequiredStringInput(
            "Enter Procedure Description: "
        );

        // Prompt user for procedure duration in minutes
        int duration = getIntInput("Enter Procedure Duration (minutes): ");
        // Validate that duration is positive
        if (duration <= 0) {
            showError("Duration must be a positive number");
            System.out.println();
            return;
        }
        // Validate that duration does not exceed 24 hours
        if (duration > 1440) {
            showError("Duration cannot exceed 1440 minutes (24 hours)");
            System.out.println();
            return;
        }

        // Prompt user for doctor ID
        String doctorId = getRequiredStringInput("Enter Doctor ID: ");

        // Validate doctor ID length
        if (doctorId.length() > 25) {
            showError("Doctor ID cannot exceed 25 characters");
            System.out.println();
            return;
        }

        // Validate that the doctor exists in the database
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

        // Create a new Procedure object with all collected data
        Procedure procedure = new Procedure(
            id,
            name,
            description,
            duration,
            doctorId
        );

        try {
            System.out.println();
            // Attempt to create the procedure in the database
            if (procedureDAO.create(procedure)) {
                showSuccess("Procedure created successfully");
            } else {
                showError("Failed to create procedure");
            }
        } catch (DatabaseException e) {
            showError("Error creating procedure: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles reading a single procedure by ID.
     */
    private void readProcedure() {
        printSeparator();
        System.out.println("Read Procedure");

        // Prompt user for procedure ID
        String id = getRequiredStringInput("Enter Procedure ID: ");

        try {
            System.out.println();
            // Attempt to read the procedure from the database
            Procedure procedure = procedureDAO.read(id);
            // Check if procedure was found and display accordingly
            if (procedure != null) {
                displayProcedure(procedure);
            } else {
                showNotFound("Procedure", id);
            }
        } catch (DatabaseException e) {
            showError("Error reading procedure: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles reading all procedures from the database.
     */
    private void readAllProcedures() {
        printSeparator();
        System.out.println("Read All Procedures");

        try {
            System.out.println();
            List<Procedure> procedures = procedureDAO.readAll();
            if (!procedures.isEmpty()) {
                int count = 1;
                for (Procedure p : procedures) {
                    System.out.println("Procedure " + count + ":");
                    displayProcedure(p);
                    System.out.println();
                    count++;
                }
            } else {
                showEmpty("No procedures found");
            }
        } catch (DatabaseException e) {
            showError("Error reading all procedures: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles updating an existing procedure.
     */
    private void updateProcedure() {
        printSeparator();
        System.out.println("Update Procedure");

        String id = getRequiredStringInput("Enter Procedure ID: ");
        Procedure procedure;
        try {
            procedure = procedureDAO.read(id);
        } catch (DatabaseException e) {
            showError("Error fetching procedure: " + e.getMessage());
            System.out.println();
            return;
        }

        if (procedure == null) {
            showNotFound("Procedure", id);
            System.out.println();
            return;
        }

        System.out.println();
        showInfo("Current procedure details:");
        displayProcedure(procedure);
        System.out.println();

        // Prompt user to update procedure name
        String input = getStringInput(
            "Update procedure name (leave empty to skip): "
        );
        if (!input.isEmpty()) {
            procedure.setName(input);
        }

        // Prompt user to update procedure description
        input = getStringInput(
            "Update procedure description (leave empty to skip): "
        );
        if (!input.isEmpty()) {
            procedure.setDescription(input);
        }

        // Prompt user to update procedure duration
        input = getStringInput(
            "Update procedure duration in minutes (leave empty to skip): "
        );
        if (!input.isEmpty()) {
            try {
                // Parse the input to integer
                int duration = Integer.parseInt(input);
                if (duration <= 0) {
                    showError("Duration must be a positive number");
                    System.out.println();
                    return;
                }
                if (duration > 1440) {
                    showError("Duration cannot exceed 1440 minutes (24 hours)");
                    System.out.println();
                    return;
                }
                procedure.setDuration(duration);
            } catch (NumberFormatException e) {
                showError("Invalid duration. Please enter a valid number.");
                System.out.println();
                return;
            }
        }

        input = getStringInput(
            "Update procedure Doctor ID (leave empty to skip): "
        );
        if (!input.isEmpty()) {
            // Validate doctor ID length
            if (input.length() > 25) {
                showError("Doctor ID cannot exceed 25 characters");
                System.out.println();
                return;
            }

            // Validate that doctor exists
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
            procedure.setDoctorId(input);
        }

        try {
            System.out.println();
            boolean update = procedureDAO.update(procedure);
            if (update) {
                showSuccess("Procedure information has been updated");
            } else {
                showError("Update failed");
            }
        } catch (DatabaseException e) {
            showError("Error updating procedure: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles deleting a procedure.
     */
    private void deleteProcedure() {
        printSeparator();
        System.out.println("Delete Procedure");

        String id = getRequiredStringInput("Enter Procedure ID: ");
        Procedure procedure;
        try {
            procedure = procedureDAO.read(id);
        } catch (DatabaseException e) {
            showError("Error fetching procedure: " + e.getMessage());
            System.out.println();
            return;
        }

        if (procedure == null) {
            showNotFound("Procedure", id);
            System.out.println();
            return;
        }

        System.out.println();
        showInfo("Procedure details:");
        displayProcedure(procedure);
        System.out.println();

        if (
            getConfirmation(
                "[WARN] Are you sure you want to delete this procedure? (y/n): "
            )
        ) {
            try {
                System.out.println();
                boolean deleted = procedureDAO.delete(id);
                if (deleted) {
                    showSuccess("Procedure deleted successfully");
                } else {
                    showError("Failed to delete procedure");
                }
            } catch (DatabaseException e) {
                showError("Error deleting procedure: " + e.getMessage());
            }
        } else {
            showCancelled("Deletion cancelled");
        }
        System.out.println();
    }

    /**
     * Displays a procedure's information in a formatted way.
     *
     * @param procedure the procedure to display
     */
    private void displayProcedure(Procedure procedure) {
        System.out.println("ID: " + procedure.getId());
        System.out.println("Name: " + procedure.getName());
        System.out.println("Description: " + procedure.getDescription());
        System.out.println("Duration: " + procedure.getDuration() + " minutes");
        System.out.println("Doctor ID: " + procedure.getDoctorId());
    }
}
