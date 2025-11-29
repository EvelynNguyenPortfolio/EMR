package main.service;

import java.util.List;
import main.dao.DoctorDAO;
import main.dao.PatientDAO;
import main.dao.PatientHistoryDAO;
import main.dao.ProcedureDAO;
import main.exception.DatabaseException;
import main.exception.EntityNotFoundException;
import main.exception.ValidationException;
import main.model.PatientHistory;
import main.util.Database;
import main.validation.PatientHistoryValidator;

/**
 * Service layer for PatientHistory operations.
 * <p>
 * This class provides business logic for managing patient history records,
 * including validation, foreign key verification, and CRUD operations.
 * It acts as an intermediary between the CLI layer and the DAO layer.
 * </p>
 *
 */
public class PatientHistoryService {

    private final PatientHistoryDAO patientHistoryDAO;
    private final PatientDAO patientDAO;
    private final ProcedureDAO procedureDAO;
    private final DoctorDAO doctorDAO;

    /**
     * Constructs a new PatientHistoryService with the specified database connection.
     *
     * @param db the database connection to use
     */
    public PatientHistoryService(Database db) {
        this.patientHistoryDAO = new PatientHistoryDAO(db);
        this.patientDAO = new PatientDAO(db);
        this.procedureDAO = new ProcedureDAO(db);
        this.doctorDAO = new DoctorDAO(db);
    }

    /**
     * Creates a new patient history record.
     * <p>
     * This method validates the patient history data and verifies that all
     * referenced entities (patient, procedure, doctor) exist before creating
     * the record.
     * </p>
     *
     * @param patientHistory the patient history record to create
     * @return true if the record was created successfully
     * @throws ValidationException if the patient history data is invalid
     * @throws DatabaseException   if a database error occurs
     * @throws EntityNotFoundException if a referenced entity does not exist
     */
    public boolean createPatientHistory(PatientHistory patientHistory)
        throws ValidationException, DatabaseException, EntityNotFoundException {
        // Validate the patient history data
        PatientHistoryValidator.validateForCreate(
            patientHistory.getId(),
            patientHistory.getPatientId(),
            patientHistory.getProcedureId(),
            patientHistory.getDate(),
            patientHistory.getBilling(),
            patientHistory.getDoctorId()
        );

        // Verify foreign key references exist
        verifyPatientExists(patientHistory.getPatientId());
        verifyProcedureExists(patientHistory.getProcedureId());
        verifyDoctorExists(patientHistory.getDoctorId());

        // Attempt to create the patient history record in the database
        return patientHistoryDAO.create(patientHistory);
    }

    /**
     * Retrieves a patient history record by its ID.
     *
     * @param id the ID of the patient history record to retrieve
     * @return the patient history record
     * @throws EntityNotFoundException if the record is not found
     * @throws DatabaseException       if a database error occurs
     */
    public PatientHistory getPatientHistory(String id)
        throws EntityNotFoundException, DatabaseException {
        PatientHistory history = patientHistoryDAO.read(id);
        if (history == null) {
            throw new EntityNotFoundException("PatientHistory", id);
        }
        return history;
    }

    /**
     * Retrieves all patient history records.
     *
     * @return a list of all patient history records
     * @throws DatabaseException if a database error occurs
     */
    public List<PatientHistory> getAllPatientHistories()
        throws DatabaseException {
        return patientHistoryDAO.readAll();
    }

    /**
     * Retrieves all patient history records for a specific patient by their MRN.
     *
     * @param patientId the patient's MRN
     * @return a list of patient history records for the patient
     * @throws EntityNotFoundException if the patient does not exist
     * @throws DatabaseException       if a database error occurs
     */
    public List<PatientHistory> getPatientHistoriesByPatientId(int patientId)
        throws EntityNotFoundException, DatabaseException {
        // Verify the patient exists
        verifyPatientExists(patientId);

        // Retrieve the patient histories
        return patientHistoryDAO.readByPatientId(patientId);
    }

    /**
     * Updates an existing patient history record.
     * <p>
     * This method validates the updated data and verifies that all
     * referenced entities still exist before performing the update.
     * </p>
     *
     * @param patientHistory the patient history record with updated values
     * @return true if the record was updated successfully
     * @throws ValidationException     if the patient history data is invalid
     * @throws DatabaseException       if a database error occurs
     * @throws EntityNotFoundException if the record or a referenced entity does not exist
     */
    public boolean updatePatientHistory(PatientHistory patientHistory)
        throws ValidationException, DatabaseException, EntityNotFoundException {
        // Validate the patient history data
        PatientHistoryValidator.validateForCreate(
            patientHistory.getId(),
            patientHistory.getPatientId(),
            patientHistory.getProcedureId(),
            patientHistory.getDate(),
            patientHistory.getBilling(),
            patientHistory.getDoctorId()
        );

        // Verify the record exists
        if (!patientHistoryDAO.exists(patientHistory.getId())) {
            throw new EntityNotFoundException(
                "PatientHistory",
                patientHistory.getId()
            );
        }

        // Verify foreign key references exist
        verifyPatientExists(patientHistory.getPatientId());
        verifyProcedureExists(patientHistory.getProcedureId());
        verifyDoctorExists(patientHistory.getDoctorId());

        // Attempt to update the patient history record in the database
        return patientHistoryDAO.update(patientHistory);
    }

    /**
     * Deletes a patient history record by its ID.
     *
     * @param id the ID of the patient history record to delete
     * @return true if the record was deleted successfully
     * @throws EntityNotFoundException if the record is not found
     * @throws DatabaseException       if a database error occurs
     */
    public boolean deletePatientHistory(String id)
        throws EntityNotFoundException, DatabaseException {
        // Verify the record exists before attempting to delete
        if (!patientHistoryDAO.exists(id)) {
            throw new EntityNotFoundException("PatientHistory", id);
        }

        // Perform the delete operation in the database
        return patientHistoryDAO.delete(id);
    }

    /**
     * Checks if a patient history record exists.
     *
     * @param id the ID to check
     * @return true if the record exists, false otherwise
     * @throws DatabaseException if a database error occurs
     */
    public boolean exists(String id) throws DatabaseException {
        return patientHistoryDAO.exists(id);
    }

    /**
     * Verifies that a patient with the given ID exists.
     *
     * @param patientId the patient ID (MRN) to verify
     * @throws EntityNotFoundException if the patient does not exist
     * @throws DatabaseException       if a database error occurs
     */
    private void verifyPatientExists(int patientId)
        throws EntityNotFoundException, DatabaseException {
        if (!patientDAO.exists(patientId)) {
            throw new EntityNotFoundException("Patient", patientId);
        }
    }

    /**
     * Verifies that a procedure with the given ID exists.
     *
     * @param procedureId the procedure ID to verify
     * @throws EntityNotFoundException if the procedure does not exist
     * @throws DatabaseException       if a database error occurs
     */
    private void verifyProcedureExists(String procedureId)
        throws EntityNotFoundException, DatabaseException {
        if (!procedureDAO.exists(procedureId)) {
            throw new EntityNotFoundException("Procedure", procedureId);
        }
    }

    /**
     * Verifies that a doctor with the given ID exists.
     *
     * @param doctorId the doctor ID to verify
     * @throws EntityNotFoundException if the doctor does not exist
     * @throws DatabaseException       if a database error occurs
     */
    private void verifyDoctorExists(String doctorId)
        throws EntityNotFoundException, DatabaseException {
        if (!doctorDAO.exists(doctorId)) {
            throw new EntityNotFoundException("Doctor", doctorId);
        }
    }
}
