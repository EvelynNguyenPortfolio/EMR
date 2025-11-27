package main.service;

import java.util.List;
import main.dao.PatientDAO;
import main.exception.DatabaseException;
import main.exception.EntityNotFoundException;
import main.exception.ValidationException;
import main.model.Patient;
import main.util.Database;
import main.validation.PatientValidator;

/**
 * Service layer for Patient entity operations.
 * <p>
 * This service provides business logic and validation for patient-related operations,
 * acting as an intermediary between the CLI layer and the DAO layer.
 * </p>
 *
 */
public class PatientService {

    private final PatientDAO patientDAO;

    /**
     * Constructs a new PatientService with the specified database connection.
     *
     * @param db the database connection to use
     */
    public PatientService(Database db) {
        this.patientDAO = new PatientDAO(db);
    }

    /**
     * Creates a new patient in the system.
     *
     * @param patient the patient to create
     * @return true if the patient was created successfully
     * @throws ValidationException if the patient data is invalid
     * @throws DatabaseException   if a database error occurs
     */
    public boolean createPatient(Patient patient)
        throws ValidationException, DatabaseException {
        PatientValidator.validate(
            patient.getMrn(),
            patient.getFname(),
            patient.getLname(),
            patient.getDob(),
            patient.getAddress(),
            patient.getState(),
            patient.getCity(),
            patient.getZip(),
            patient.getInsurance(),
            patient.getEmail()
        );

        // Check if MRN already exists
        if (patientDAO.exists(patient.getMrn())) {
            throw new ValidationException(
                "MRN",
                "A patient with this MRN already exists"
            );
        }
        return patientDAO.create(patient);
    }

    /**
     * Retrieves a patient by their Medical Record Number (MRN).
     *
     * @param mrn the MRN of the patient to retrieve
     * @return the patient with the specified MRN
     * @throws EntityNotFoundException if no patient with the given MRN exists
     * @throws DatabaseException       if a database error occurs
     */
    public Patient getPatient(int mrn)
        throws EntityNotFoundException, DatabaseException {
        Patient patient = patientDAO.read(mrn);
        if (patient == null) {
            throw new EntityNotFoundException("Patient", mrn);
        }
        return patient;
    }

    /**
     * Retrieves a patient by their MRN, returning null if not found.
     *
     * @param mrn the MRN of the patient to retrieve
     * @return the patient with the specified MRN, or null if not found
     * @throws DatabaseException if a database error occurs
     */
    public Patient findPatient(int mrn) throws DatabaseException {
        return patientDAO.read(mrn);
    }

    /**
     * Retrieves all patients in the system.
     *
     * @return a list of all patients
     * @throws DatabaseException if a database error occurs
     */
    public List<Patient> getAllPatients() throws DatabaseException {
        return patientDAO.readAll();
    }

    /**
     * Updates an existing patient's information.
     *
     * @param patient the patient with updated information
     * @return true if the update was successful
     * @throws ValidationException     if the patient data is invalid
     * @throws EntityNotFoundException if the patient does not exist
     * @throws DatabaseException       if a database error occurs
     */
    public boolean updatePatient(Patient patient)
        throws ValidationException, EntityNotFoundException, DatabaseException {
        // Validate the updated patient data to ensure it meets all requirements
        PatientValidator.validate(
            patient.getMrn(),
            patient.getFname(),
            patient.getLname(),
            patient.getDob(),
            patient.getAddress(),
            patient.getState(),
            patient.getCity(),
            patient.getZip(),
            patient.getInsurance(),
            patient.getEmail()
        );

        // Verify patient exists
        if (!patientDAO.exists(patient.getMrn())) {
            throw new EntityNotFoundException("Patient", patient.getMrn());
        }
        // Perform the update operation in the database
        return patientDAO.update(patient);
    }

    /**
     * Deletes a patient from the system.
     *
     * @param mrn the MRN of the patient to delete
     * @return true if the deletion was successful
     * @throws EntityNotFoundException if the patient does not exist
     * @throws DatabaseException       if a database error occurs
     */
    public boolean deletePatient(int mrn)
        throws EntityNotFoundException, DatabaseException {
        // Verify patient exists
        if (!patientDAO.exists(mrn)) {
            throw new EntityNotFoundException("Patient", mrn);
        }
        return patientDAO.delete(mrn);
    }

    /**
     * Checks if a patient exists in the system.
     *
     * @param mrn the MRN to check
     * @return true if a patient with the given MRN exists
     * @throws DatabaseException if a database error occurs
     */
    public boolean patientExists(int mrn) throws DatabaseException {
        return patientDAO.exists(mrn);
    }
}
