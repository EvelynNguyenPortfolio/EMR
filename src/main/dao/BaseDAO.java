package main.dao;

import java.util.List;
import main.exception.DatabaseException;

/**
 * Base interface for all Data Access Object (DAO) classes.
 * <p>
 * This interface defines the standard CRUD (Create, Read, Update, Delete) operations
 * that all DAO implementations must provide. It uses generics to support different
 * entity types and ID types.
 * </p>
 *
 * @param <T>  the type of entity this DAO manages
 * @param <ID> the type of the entity's primary key
 */
public interface BaseDAO<T, ID> {
    /**
     * Creates a new entity in the database.
     *
     * @param entity the entity to create
     * @return true if the entity was created successfully, false otherwise
     * @throws DatabaseException if a database error occurs
     */
    boolean create(T entity) throws DatabaseException;

    /**
     * Reads an entity from the database by its ID.
     *
     * @param id the ID of the entity to read
     * @return the entity if found, null otherwise
     * @throws DatabaseException if a database error occurs
     */
    T read(ID id) throws DatabaseException;

    /**
     * Reads all entities from the database.
     *
     * @return a list of all entities, empty list if none found
     * @throws DatabaseException if a database error occurs
     */
    List<T> readAll() throws DatabaseException;

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity with updated values
     * @return true if the entity was updated successfully, false otherwise
     * @throws DatabaseException if a database error occurs
     */
    boolean update(T entity) throws DatabaseException;

    /**
     * Deletes an entity from the database by its ID.
     *
     * @param id the ID of the entity to delete
     * @return true if the entity was deleted successfully, false otherwise
     * @throws DatabaseException if a database error occurs
     */
    boolean delete(ID id) throws DatabaseException;

    /**
     * Checks if an entity with the given ID exists in the database.
     *
     * @param id the ID to check
     * @return true if an entity with the given ID exists, false otherwise
     * @throws DatabaseException if a database error occurs
     */
    boolean exists(ID id) throws DatabaseException;
}
