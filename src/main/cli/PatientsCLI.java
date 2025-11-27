package main.cli;

import java.time.LocalDate;
import java.util.List;
import main.dao.PatientDAO;
import main.exception.DatabaseException;
import main.model.Patient;
import main.util.Database;

/**
 * CLI handler for Patient entity management.
 * <p>
 * This class provides a command-line interface for performing CRUD operations
 * on Patient records in the EMR system.
 * </p>
 *
 */
public class PatientsCLI extends CLI {

    private final PatientDAO patientDAO;

    /**
     * Constructs a new PatientsCLI with the specified database connection.
     *
     * @param db the database connection to use
     */
    public PatientsCLI(Database db) {
        super();
        this.patientDAO = new PatientDAO(db);
    }

    /**
     * Starts the Patient management CLI interface.
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
                    // Create a new patient
                    createPatient();
                    break;
                case 2:
                    // Read a patient by MRN
                    readPatient();
                    break;
                case 3:
                    // Read all patients
                    readAllPatients();
                    break;
                case 4:
                    // Update an existing patient
                    updatePatient();
                    break;
                case 5:
                    // Delete a patient
                    deletePatient();
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
     * Displays the Patient management menu.
     */
    private void showMenu() {
        System.out.println("Patient Management");
        System.out.println();
        System.out.println("1. Create Patient");
        System.out.println("2. Read Patient by MRN");
        System.out.println("3. Read All Patients");
        System.out.println("4. Update Patient");
        System.out.println("5. Delete Patient");
        System.out.println("6. Back to Main Menu");
    }

    /**
     * Handles the creation of a new patient.
     */
    private void createPatient() {
        printSeparator();
        System.out.println("Create New Patient");

        // Prompt user for patient MRN
        int mrn = getIntInput("Enter MRN: ");

        // Validate that MRN is a positive number
        if (mrn <= 0) {
            showError("MRN must be a positive number");
            System.out.println();
            return;
        }

        // Check if a patient with this MRN already exists to prevent duplicates
        try {
            if (patientDAO.exists(mrn)) {
                showError("A patient with MRN '" + mrn + "' already exists");
                System.out.println();
                return;
            }
        } catch (DatabaseException e) {
            showError("Error checking MRN: " + e.getMessage());
            System.out.println();
            return;
        }

        // Prompt user for patient's first name
        String fname = getRequiredStringInput("Enter First Name: ");
        // Prompt user for patient's last name
        String lname = getRequiredStringInput("Enter Last Name: ");
        // Prompt user for patient's date of birth
        LocalDate dob = getDateInput("Enter Date of Birth (yyyy-MM-dd): ");

        // Validate that date of birth is not in the future
        if (dob.isAfter(LocalDate.now())) {
            showError("Date of birth cannot be in the future");
            System.out.println();
            return;
        }

        // Prompt user for patient's address
        String address = getRequiredStringInput("Enter Address: ");
        // Prompt user for patient's state
        String state = getRequiredStringInput("Enter State: ");
        // Prompt user for patient's city
        String city = getRequiredStringInput("Enter City: ");

        // Prompt user for patient's zip code
        int zip = getIntInput("Enter Zip Code: ");
        // Validate that zip code is a valid 5-digit number
        if (zip <= 0 || zip > 99999) {
            showError("Zip code must be a valid 5-digit number");
            System.out.println();
            return;
        }

        // Prompt user for patient's insurance provider
        String insurance = getRequiredStringInput("Enter Insurance: ");
        // Prompt user for patient's email address
        String email = getRequiredStringInput("Enter Email: ");

        // Perform basic email format validation
        if (!email.contains("@") || !email.contains(".")) {
            showError("Invalid email format");
            System.out.println();
            return;
        }

        // Create a new Patient object with all collected data
        Patient patient = new Patient(
            mrn,
            fname,
            lname,
            dob,
            address,
            state,
            city,
            zip,
            insurance,
            email
        );

        try {
            System.out.println();
            // Attempt to create the patient in the database
            if (patientDAO.create(patient)) {
                showSuccess("Patient created successfully");
            } else {
                showError("Failed to create patient");
            }
        } catch (DatabaseException e) {
            showError("Error creating patient: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles reading a single patient by MRN.
     */
    private void readPatient() {
        printSeparator();
        System.out.println("Read Patient");

        // Prompt user for patient MRN
        int mrn = getIntInput("Enter MRN: ");

        try {
            System.out.println();
            // Attempt to read the patient from the database
            Patient patient = patientDAO.read(mrn);
            // Check if patient was found and display accordingly
            if (patient != null) {
                displayPatient(patient);
            } else {
                showNotFound("Patient", mrn);
            }
        } catch (DatabaseException e) {
            showError("Error reading patient: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles reading all patients from the database.
     */
    private void readAllPatients() {
        printSeparator();
        System.out.println("Read All Patients");

        try {
            System.out.println();
            List<Patient> patients = patientDAO.readAll();
            if (!patients.isEmpty()) {
                int count = 1;
                for (Patient p : patients) {
                    System.out.println("Patient " + count + ":");
                    displayPatient(p);
                    System.out.println();
                    count++;
                }
            } else {
                showEmpty("No patients found");
            }
        } catch (DatabaseException e) {
            showError("Error reading all patients: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles updating an existing patient.
     */
    private void updatePatient() {
        printSeparator();
        System.out.println("Update Patient");

        int mrn = getIntInput("Enter Patient MRN: ");
        Patient patient;
        try {
            patient = patientDAO.read(mrn);
        } catch (DatabaseException e) {
            showError("Error fetching patient: " + e.getMessage());
            System.out.println();
            return;
        }

        if (patient == null) {
            showNotFound("Patient", mrn);
            System.out.println();
            return;
        }

        System.out.println();
        showInfo("Current patient details:");
        displayPatient(patient);
        System.out.println();

        // Begin updating patient fields
        String input;

        input = getStringInput("Update first name (leave empty to skip): ");
        if (!input.isEmpty()) {
            patient.setFname(input);
        }

        // Prompt user to update last name
        input = getStringInput("Update last name (leave empty to skip): ");
        if (!input.isEmpty()) {
            patient.setLname(input);
        }

        // Prompt user to update date of birth
        input = getStringInput(
            "Update Date of Birth (yyyy-MM-dd, leave empty to skip): "
        );
        if (!input.isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(input, DATE_FORMATTER);
                if (dob.isAfter(LocalDate.now())) {
                    showError("Date of birth cannot be in the future");
                    System.out.println();
                    return;
                }
                patient.setDob(dob);
            } catch (Exception e) {
                showError("Invalid date format. Please use yyyy-MM-dd format.");
                System.out.println();
                return;
            }
        }

        // Prompt user to update address
        input = getStringInput("Update Address (leave empty to skip): ");
        if (!input.isEmpty()) {
            patient.setAddress(input);
        }

        // Prompt user to update state
        input = getStringInput("Update State (leave empty to skip): ");
        if (!input.isEmpty()) {
            patient.setState(input);
        }

        // Prompt user to update city
        input = getStringInput("Update City (leave empty to skip): ");
        if (!input.isEmpty()) {
            patient.setCity(input);
        }

        // Prompt user to update zip code
        input = getStringInput("Update Zip Code (leave empty to skip): ");
        if (!input.isEmpty()) {
            try {
                int zip = Integer.parseInt(input);
                if (zip <= 0 || zip > 99999) {
                    showError("Zip code must be a valid 5-digit number");
                    System.out.println();
                    return;
                }
                patient.setZip(zip);
            } catch (NumberFormatException e) {
                showError("Invalid zip code. Please enter a valid number.");
                System.out.println();
                return;
            }
        }

        // Prompt user to update insurance
        input = getStringInput("Update Insurance (leave empty to skip): ");
        if (!input.isEmpty()) {
            patient.setInsurance(input);
        }

        // Prompt user to update email
        input = getStringInput("Update Email (leave empty to skip): ");
        if (!input.isEmpty()) {
            if (!input.contains("@") || !input.contains(".")) {
                showError("Invalid email format");
                System.out.println();
                return;
            }
            patient.setEmail(input);
        }

        try {
            System.out.println();
            // Attempt to update the patient in the database
            boolean update = patientDAO.update(patient);
            if (update) {
                showSuccess("Patient information has been updated");
            } else {
                showError("Update failed");
            }
        } catch (DatabaseException e) {
            showError("Error updating patient: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Handles deleting a patient.
     */
    private void deletePatient() {
        printSeparator();
        System.out.println("Delete Patient");

        // Prompt user for patient MRN
        int mrn = getIntInput("Enter MRN: ");
        Patient patient;
        // Fetch patient from database to verify existence
        try {
            patient = patientDAO.read(mrn);
        } catch (DatabaseException e) {
            showError("Error fetching patient: " + e.getMessage());
            System.out.println();
            return;
        }

        // Check if patient exists
        if (patient == null) {
            showNotFound("Patient", mrn);
            System.out.println();
            return;
        }

        System.out.println();
        // Display patient details for confirmation
        showInfo("Patient details:");
        displayPatient(patient);
        System.out.println();

        // Ask for user confirmation before deletion
        if (
            getConfirmation(
                "[WARN] Are you sure you want to delete this patient? (y/n): "
            )
        ) {
            try {
                System.out.println();
                // Attempt to delete the patient from the database
                boolean deleted = patientDAO.delete(mrn);
                if (deleted) {
                    showSuccess("Patient deleted successfully");
                } else {
                    showError("Failed to delete patient");
                }
            } catch (DatabaseException e) {
                showError("Error deleting patient: " + e.getMessage());
            }
        } else {
            // Handle cancellation of deletion
            showCancelled("Deletion cancelled");
        }
        System.out.println();
    }

    /**
     * Displays a patient's information in a formatted way.
     *
     * @param patient the patient to display
     */
    private void displayPatient(Patient patient) {
        System.out.println("MRN: " + patient.getMrn());
        System.out.println(
            "Name: " + patient.getFname() + " " + patient.getLname()
        );
        System.out.println("DOB: " + patient.getDob());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("City: " + patient.getCity());
        System.out.println("State: " + patient.getState());
        System.out.println("Zip: " + patient.getZip());
        System.out.println("Insurance: " + patient.getInsurance());
        System.out.println("Email: " + patient.getEmail());
    }
}
