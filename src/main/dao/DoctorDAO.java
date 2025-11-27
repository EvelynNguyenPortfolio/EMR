package main.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.exception.DatabaseException;
import main.model.Doctor;
import main.util.Database;

/**
 * Data Access Object for Doctor entities.
 * <p>
 * This class provides CRUD operations for the doctors table in the database.
 * It implements the BaseDAO interface to ensure consistent operation signatures
 * across all DAO classes.
 * </p>
 *
 */
public class DoctorDAO implements BaseDAO<Doctor, String> {

    private final Database db;

    /**
     * Constructs a new DoctorDAO with the specified database connection.
     *
     * @param db the database connection to use
     */
    public DoctorDAO(Database db) {
        this.db = db;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Doctor doctor) throws DatabaseException {
        String sql = "INSERT INTO doctors (id, name) VALUES (?, ?)";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, doctor.getId());
            stmt.setString(2, doctor.getName());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to create doctor: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Doctor read(String id) throws DatabaseException {
        String sql = "SELECT * FROM doctors WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToDoctor(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to read doctor: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Doctor> readAll() throws DatabaseException {
        String sql = "SELECT * FROM doctors";
        List<Doctor> doctors = new ArrayList<>();

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                doctors.add(mapResultSetToDoctor(resultSet));
            }
            return doctors;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to read all doctors: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Doctor doctor) throws DatabaseException {
        String sql = "UPDATE doctors SET name = ? WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to update doctor: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String id) throws DatabaseException {
        String sql = "DELETE FROM doctors WHERE id = ?";

        try (
            PreparedStatement stmt = db.getConnection().prepareStatement(sql)
        ) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException(
                "Failed to delete doctor: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String id) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM doctors WHERE id = ?";

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
                "Failed to check doctor existence: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * Maps a ResultSet row to a Doctor object.
     *
     * @param resultSet the ResultSet positioned at a valid row
     * @return a Doctor object populated with data from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private Doctor mapResultSetToDoctor(ResultSet resultSet)
        throws SQLException {
        return new Doctor(
            resultSet.getString("id"),
            resultSet.getString("name")
        );
    }
}
