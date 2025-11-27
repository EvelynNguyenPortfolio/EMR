package main.exception;

/**
 * Exception thrown when an entity is not found in the database.
 * This exception is used across all entity types (Patient, Doctor, Procedure, PatientHistory).
 */
public class EntityNotFoundException extends EMRException {

    private final String entityType;
    private final String entityId;

    /**
     * Constructs a new EntityNotFoundException with the specified entity type and ID.
     *
     * @param entityType the type of entity that was not found (e.g., "Patient", "Doctor")
     * @param entityId   the ID of the entity that was not found
     */
    public EntityNotFoundException(String entityType, String entityId) {
        super(entityType + " with ID '" + entityId + "' not found");
        this.entityType = entityType;
        this.entityId = entityId;
    }

    /**
     * Constructs a new EntityNotFoundException with the specified entity type and numeric ID.
     *
     * @param entityType the type of entity that was not found (e.g., "Patient", "Doctor")
     * @param entityId   the numeric ID of the entity that was not found
     */
    public EntityNotFoundException(String entityType, int entityId) {
        this(entityType, String.valueOf(entityId));
    }

    /**
     * Gets the type of entity that was not found.
     *
     * @return the entity type
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Gets the ID of the entity that was not found.
     *
     * @return the entity ID as a string
     */
    public String getEntityId() {
        return entityId;
    }
}
