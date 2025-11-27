package main.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.exception.DatabaseException;
import main.model.Procedure;
import main.util.Database;

/**
 * Data Access Object for Procedure entities.
 * <p>
 * This class provides CRUD operations for the procedures table in the database.
 * It handles all database interactions related to medical procedures, including
 * creation, retrieval, update, and deletion operations.
 * </p>
 *
 * @see Procedure
 * @see BaseDAO
 */
public class ProcedureDAO implements BaseDAO<Procedure, String> {

    private final Database db;

    /**
     * Constructs a new ProcedureDAO with the specified database connection.
     *
     * @param db the database connection to use for all operations
     */
    public ProcedureDAO(Database db) {
        this.db = db;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates a new procedure record in the database with the given details.
     * The procedure must have a unique ID that does not already exist in the database.
     * </p>
     */
    @Override
    public boolean create(Procedure procedure) throws DatabaseException {
        // Define the SQL insert statement for the procedures table
        String sql =
            "INSERT INTO procedures (id, name, description, duration, doctorId) VALUES (?, ?, ?, ?, ?)";

        // Prepare the prepared statement with the database connection
        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            // Set the parameters for the prepared statement from the procedure object
            stmt.setString(1, procedure.getId());
            stmt.setString(2, procedure.getName());
            stmt.setString(3, procedure.getDescription());
            stmt.setInt(4, procedure.getDuration());
            stmt.setString(5, procedure.getDoctorId());

            // Execute the insert and return true if at least one row was affected
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle SQL exceptions by throwing a custom database exception
            throw new DatabaseException(
                "Failed to create procedure: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a procedure by its unique identifier.
     * </p>
     */
    @Override
    public Procedure read(String id) throws DatabaseException {
        // Define the SQL select statement to retrieve a procedure by ID
        String sql = "SELECT * FROM procedures WHERE id = ?";

        // Prepare the prepared statement with the database connection
        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            // Set the ID parameter in the prepared statement
            stmt.setString(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                // Check if a result was found
                if (resultSet.next()) {
                    // Map the result set to a Procedure object and return it
                    return mapResultSetToProcedure(resultSet);
                }
                // Return null if no procedure was found
                return null;
            }
        } catch (SQLException e) {
            // Handle SQL exceptions by throwing a custom database exception
            throw new DatabaseException(
                "Failed to read procedure: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all procedures from the database.
     * </p>
     */
    @Override
    public List<Procedure> readAll() throws DatabaseException {
        String sql = "SELECT * FROM procedures";
        List<Procedure> procedures = new ArrayList<>();

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                procedures.add(mapResultSetToProcedure(resultSet));
            }
            return procedures;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to read all procedures: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates an existing procedure in the database. The procedure's ID is used
     * to locate the record, and all other fields are updated.
     * </p>
     */
    @Override
    public boolean update(Procedure procedure) throws DatabaseException {
        String sql =
            "UPDATE procedures SET name = ?, description = ?, duration = ?, doctorId = ? WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, procedure.getName());
            stmt.setString(2, procedure.getDescription());
            stmt.setInt(3, procedure.getDuration());
            stmt.setString(4, procedure.getDoctorId());
            stmt.setString(5, procedure.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to update procedure: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Deletes a procedure from the database by its ID. Note that this may fail
     * if there are patient history records referencing this procedure, depending
     * on the database's foreign key constraints.
     * </p>
     */
    @Override
    public boolean delete(String id) throws DatabaseException {
        String sql = "DELETE FROM procedures WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to delete procedure: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks if a procedure with the given ID exists in the database.
     * </p>
     */
    @Override
    public boolean exists(String id) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM procedures WHERE id = ?";

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
                "Failed to check procedure existence: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * Maps a ResultSet row to a Procedure object.
     *
     * @param resultSet the ResultSet positioned at a valid row
     * @return a Procedure object populated with data from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private Procedure mapResultSetToProcedure(ResultSet resultSet)
        throws SQLException {
        // Create and return a new Procedure object populated with all fields from the result set
        return new Procedure(
            resultSet.getString("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getInt("duration"),
            resultSet.getString("doctorId")
        );
    }
}
