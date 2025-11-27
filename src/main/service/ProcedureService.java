package main.service;

import java.util.List;
import main.dao.DoctorDAO;
import main.dao.ProcedureDAO;
import main.exception.DatabaseException;
import main.exception.EntityNotFoundException;
import main.exception.ValidationException;
import main.model.Procedure;
import main.util.Database;
import main.validation.ProcedureValidator;

/**
 * Service class for managing Procedure entities.
 * <p>
 * This class provides business logic operations for procedures,
 * including validation, foreign key checks, and CRUD operations.
 * It acts as an intermediary between the CLI layer and the DAO layer.
 * </p>
 *
 */
public class ProcedureService {

    private final ProcedureDAO procedureDAO;
    private final DoctorDAO doctorDAO;

    /**
     * Constructs a new ProcedureService with the specified database connection.
     *
     * @param db the database connection to use
     */
    public ProcedureService(Database db) {
        this.procedureDAO = new ProcedureDAO(db);
        this.doctorDAO = new DoctorDAO(db);
    }

    /**
     * Creates a new procedure after validating all fields and checking foreign key constraints.
     *
     * @param procedure the procedure to create
     * @return true if the procedure was created successfully
     * @throws ValidationException if the procedure data is invalid
     * @throws DatabaseException   if a database error occurs
     */
    public boolean createProcedure(Procedure procedure)
        throws ValidationException, DatabaseException {
        // Validate the procedure
        ProcedureValidator.validateForCreate(
            procedure.getId(),
            procedure.getName(),
            procedure.getDescription(),
            procedure.getDuration(),
            procedure.getDoctorId()
        );

        // Check if doctor exists
        if (!doctorDAO.exists(procedure.getDoctorId())) {
            throw new ValidationException(
                "doctorId",
                "Doctor with ID '" +
                    procedure.getDoctorId() +
                    "' does not exist"
            );
        }

        // Check if procedure ID already exists
        if (procedureDAO.exists(procedure.getId())) {
            throw new ValidationException(
                "id",
                "Procedure with ID '" + procedure.getId() + "' already exists"
            );
        }

        // Attempt to create the procedure in the database
        return procedureDAO.create(procedure);
    }

    /**
     * Retrieves a procedure by its ID.
     *
     * @param id the procedure ID
     * @return the procedure if found
     * @throws EntityNotFoundException if the procedure is not found
     * @throws DatabaseException       if a database error occurs
     */
    public Procedure getProcedure(String id)
        throws EntityNotFoundException, DatabaseException {
        Procedure procedure = procedureDAO.read(id);
        if (procedure == null) {
            throw new EntityNotFoundException("Procedure", id);
        }
        return procedure;
    }

    /**
     * Retrieves all procedures from the database.
     *
     * @return a list of all procedures
     * @throws DatabaseException if a database error occurs
     */
    public List<Procedure> getAllProcedures() throws DatabaseException {
        return procedureDAO.readAll();
    }

    /**
     * Updates an existing procedure.
     *
     * @param procedure the procedure with updated values
     * @return true if the procedure was updated successfully
     * @throws ValidationException     if the procedure data is invalid
     * @throws EntityNotFoundException if the procedure does not exist
     * @throws DatabaseException       if a database error occurs
     */
    public boolean updateProcedure(Procedure procedure)
        throws ValidationException, EntityNotFoundException, DatabaseException {
        // Validate the updated procedure data to ensure it meets all requirements
        ProcedureValidator.validateForCreate(
            procedure.getId(),
            procedure.getName(),
            procedure.getDescription(),
            procedure.getDuration(),
            procedure.getDoctorId()
        );

        // Verify procedure exists
        if (!procedureDAO.exists(procedure.getId())) {
            throw new EntityNotFoundException("Procedure", procedure.getId());
        }

        // Verify doctor exists if doctorId is provided
        if (procedure.getDoctorId() != null) {
            if (!doctorDAO.exists(procedure.getDoctorId())) {
                throw new ValidationException(
                    "doctorId",
                    "Doctor with ID '" +
                        procedure.getDoctorId() +
                        "' does not exist"
                );
            }
        }

        // Perform the update operation in the database
        return procedureDAO.update(procedure);
    }

    /**
     * Deletes a procedure by its ID.
     *
     * @param id the ID of the procedure to delete
     * @return true if the procedure was deleted successfully
     * @throws EntityNotFoundException if the procedure does not exist
     * @throws DatabaseException       if a database error occurs
     */
    public boolean deleteProcedure(String id)
        throws EntityNotFoundException, DatabaseException {
        // Check if procedure exists
        if (!procedureDAO.exists(id)) {
            throw new EntityNotFoundException("Procedure", id);
        }

        // Perform the delete operation in the database
        return procedureDAO.delete(id);
    }

    /**
     * Checks if a procedure with the given ID exists.
     *
     * @param id the procedure ID to check
     * @return true if the procedure exists, false otherwise
     * @throws DatabaseException if a database error occurs
     */
    public boolean exists(String id) throws DatabaseException {
        return procedureDAO.exists(id);
    }
}
