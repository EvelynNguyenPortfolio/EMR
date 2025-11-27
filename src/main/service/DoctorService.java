package main.service;

import java.util.List;
import main.dao.DoctorDAO;
import main.exception.DatabaseException;
import main.exception.EntityNotFoundException;
import main.exception.ValidationException;
import main.model.Doctor;
import main.util.Database;
import main.validation.DoctorValidator;

/**
 * Service layer for Doctor entity operations.
 * <p>
 * This class provides business logic and validation for doctor-related
 * operations, acting as an intermediary between the CLI layer and the DAO layer.
 * </p>
 *
 */
public class DoctorService {

    private final DoctorDAO doctorDAO;

    /**
     * Constructs a new DoctorService with the specified database connection.
     *
     * @param db the database connection to use
     */
    public DoctorService(Database db) {
        this.doctorDAO = new DoctorDAO(db);
    }

    /**
     * Creates a new doctor after validation.
     *
     * @param doctor the doctor to create
     * @return true if the doctor was created successfully
     * @throws ValidationException if the doctor data is invalid
     * @throws DatabaseException   if a database error occurs
     */
    public boolean createDoctor(Doctor doctor)
        throws ValidationException, DatabaseException {
        // Validate the doctor data to ensure it meets all requirements
        DoctorValidator.validate(doctor.getId(), doctor.getName());

        try {
            // Check if doctor with same ID already exists
            if (doctorDAO.exists(doctor.getId())) {
                throw new ValidationException(
                    "id",
                    "A doctor with ID '" + doctor.getId() + "' already exists"
                );
            }
            // Attempt to create the doctor in the database
            return doctorDAO.create(doctor);
        } catch (DatabaseException e) {
            throw e;
        }
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id the doctor ID to search for
     * @return the doctor if found
     * @throws EntityNotFoundException if no doctor is found with the given ID
     * @throws DatabaseException       if a database error occurs
     */
    public Doctor getDoctorById(String id)
        throws EntityNotFoundException, DatabaseException {
        Doctor doctor = doctorDAO.read(id);
        if (doctor == null) {
            throw new EntityNotFoundException("Doctor", id);
        }
        return doctor;
    }

    /**
     * Retrieves all doctors from the database.
     *
     * @return a list of all doctors, empty list if none found
     * @throws DatabaseException if a database error occurs
     */
    public List<Doctor> getAllDoctors() throws DatabaseException {
        return doctorDAO.readAll();
    }

    /**
     * Updates an existing doctor after validation.
     *
     * @param doctor the doctor with updated information
     * @return true if the doctor was updated successfully
     * @throws ValidationException     if the doctor data is invalid
     * @throws EntityNotFoundException if the doctor does not exist
     * @throws DatabaseException       if a database error occurs
     */
    public boolean updateDoctor(Doctor doctor)
        throws ValidationException, EntityNotFoundException, DatabaseException {
        // Validate the updated doctor data to ensure it meets all requirements
        DoctorValidator.validate(doctor.getId(), doctor.getName());

        // Verify doctor exists
        if (!doctorDAO.exists(doctor.getId())) {
            throw new EntityNotFoundException("Doctor", doctor.getId());
        }

        // Perform the update operation in the database
        return doctorDAO.update(doctor);
    }

    /**
     * Deletes a doctor by their ID.
     *
     * @param id the ID of the doctor to delete
     * @return true if the doctor was deleted successfully
     * @throws EntityNotFoundException if no doctor is found with the given ID
     * @throws DatabaseException       if a database error occurs
     */
    public boolean deleteDoctor(String id)
        throws EntityNotFoundException, DatabaseException {
        // Verify doctor exists
        if (!doctorDAO.exists(id)) {
            throw new EntityNotFoundException("Doctor", id);
        }

        // Perform the delete operation in the database
        return doctorDAO.delete(id);
    }

    /**
     * Checks if a doctor with the given ID exists.
     *
     * @param id the doctor ID to check
     * @return true if a doctor with the given ID exists
     * @throws DatabaseException if a database error occurs
     */
    public boolean doctorExists(String id) throws DatabaseException {
        return doctorDAO.exists(id);
    }
}
