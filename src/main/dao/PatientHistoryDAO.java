package main.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.exception.DatabaseException;
import main.model.PatientHistory;
import main.util.Database;

/**
 * Data Access Object for PatientHistory entities.
 * <p>
 * This class provides CRUD operations for the patient_history table in the database.
 * It manages records that track medical procedures performed on patients, including
 * billing information and the doctor who performed the procedure.
 * </p>
 *
 * <p>The patient_history table has foreign key relationships to:</p>
 * <ul>
 *   <li>patients table (via patientId)</li>
 *   <li>procedures table (via procedureId)</li>
 *   <li>doctors table (via doctorId)</li>
 * </ul>
 *
 * @see PatientHistory
 * @see BaseDAO
 */
public class PatientHistoryDAO implements BaseDAO<PatientHistory, String> {

    private final Database db;

    /**
     * Constructs a new PatientHistoryDAO with the specified database connection.
     *
     * @param db the database connection to use for all operations
     */
    public PatientHistoryDAO(Database db) {
        this.db = db;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates a new patient history record in the database. The record includes
     * references to the patient, procedure, and doctor involved.
     * </p>
     *
     * @throws DatabaseException if the referenced patient, procedure, or doctor
     *                           does not exist, or if a database error occurs
     */
    @Override
    public boolean create(PatientHistory patientHistory)
        throws DatabaseException {
        // Define the SQL insert statement for the patient_history table
        String sql =
            "INSERT INTO patient_history (id, patientId, procedureId, date, billing, doctorId) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        // Prepare the prepared statement with the database connection
        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            // Set the parameters for the prepared statement from the patient history object
            stmt.setString(1, patientHistory.getId());
            stmt.setInt(2, patientHistory.getPatientId());
            stmt.setString(3, patientHistory.getProcedureId());
            stmt.setDate(4, Date.valueOf(patientHistory.getDate()));
            stmt.setDouble(5, patientHistory.getBilling());
            stmt.setString(6, patientHistory.getDoctorId());

            // Execute the insert and return true if at least one row was affected
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle SQL exceptions by throwing a custom database exception
            throw new DatabaseException(
                "Failed to create patient history: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a patient history record by its unique identifier.
     * </p>
     */
    @Override
    public PatientHistory read(String id) throws DatabaseException {
        // Define the SQL select statement to retrieve a patient history by ID
        String sql = "SELECT * FROM patient_history WHERE id = ?";

        // Prepare the prepared statement with the database connection
        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            // Set the ID parameter in the prepared statement
            stmt.setString(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Check if a result was found
                if (resultSet.next()) {
                    // Map the result set to a PatientHistory object and return it
                    return mapResultSetToPatientHistory(resultSet);
                }
                // Return null if no patient history was found
                return null;
            }
        } catch (SQLException e) {
            // Handle SQL exceptions by throwing a custom database exception
            throw new DatabaseException(
                "Failed to read patient history: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all patient history records from the database.
     * </p>
     */
    @Override
    public List<PatientHistory> readAll() throws DatabaseException {
        String sql = "SELECT * FROM patient_history";
        List<PatientHistory> patientHistories = new ArrayList<>();

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                patientHistories.add(mapResultSetToPatientHistory(resultSet));
            }
            return patientHistories;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to read all patient histories: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates an existing patient history record in the database.
     * The record's ID is used to locate the record, and all other fields are updated.
     * </p>
     */
    @Override
    public boolean update(PatientHistory patientHistory)
        throws DatabaseException {
        String sql =
            "UPDATE patient_history SET patientId = ?, procedureId = ?, date = ?, " +
            "billing = ?, doctorId = ? WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setInt(1, patientHistory.getPatientId());
            stmt.setString(2, patientHistory.getProcedureId());
            stmt.setDate(3, Date.valueOf(patientHistory.getDate()));
            stmt.setDouble(4, patientHistory.getBilling());
            stmt.setString(5, patientHistory.getDoctorId());
            stmt.setString(6, patientHistory.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to update patient history: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Deletes a patient history record from the database by its ID.
     * </p>
     */
    @Override
    public boolean delete(String id) throws DatabaseException {
        String sql = "DELETE FROM patient_history WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to delete patient history: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks if a patient history record with the given ID exists in the database.
     * </p>
     */
    @Override
    public boolean exists(String id) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM patient_history WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to check patient history existence: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * Retrieves all patient history records for a specific patient by their MRN.
     *
     * @param patientId the patient's MRN
     * @return a list of patient history records for the patient
     * @throws DatabaseException if a database error occurs
     */
    public List<PatientHistory> readByPatientId(int patientId)
        throws DatabaseException {
        String sql = "SELECT * FROM patient_history WHERE patientId = ?";
        List<PatientHistory> patientHistories = new ArrayList<>();

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setInt(1, patientId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    patientHistories.add(
                        mapResultSetToPatientHistory(resultSet)
                    );
                }
                return patientHistories;
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to read patient histories by patient ID: " +
                    e.getMessage(),
                e
            );
        }
    }

    /**
     * Maps a ResultSet row to a PatientHistory object.
     *
     * @param resultSet the ResultSet positioned at a valid row
     * @return a PatientHistory object populated with data from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private PatientHistory mapResultSetToPatientHistory(ResultSet resultSet)
        throws SQLException {
        // Create and return a new PatientHistory object populated with all fields from the result set
        return new PatientHistory(
            resultSet.getString("id"),
            resultSet.getInt("patientId"),
            resultSet.getString("procedureId"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getDouble("billing"),
            resultSet.getString("doctorId")
        );
    }
}
