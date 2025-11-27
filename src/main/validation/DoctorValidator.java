package main.validation;

import main.exception.ValidationException;

/**
 * Validator class for Doctor entities.
 * <p>
 * Provides validation methods to ensure doctor data meets
 * the required criteria before database operations.
 * </p>
 *
 */
public class DoctorValidator {

    private static final int MAX_ID_LENGTH = 25;
    private static final int MAX_NAME_LENGTH = 45;

    /**
     * Private constructor to prevent instantiation.
     */
    private DoctorValidator() {}

    /**
     * Validates doctor data for creation or update.
     *
     * @param id   the doctor ID to validate
     * @param name the doctor name to validate
     * @throws ValidationException if validation fails
     */
    public static void validate(String id, String name)
        throws ValidationException {
        validateId(id);
        validateName(name);
    }

    /**
     * Validates a doctor ID.
     *
     * @param id the doctor ID to validate
     * @throws ValidationException if the ID is invalid
     */
    public static void validateId(String id) throws ValidationException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("id", "Doctor ID is required");
        }

        if (id.length() > MAX_ID_LENGTH) {
            throw new ValidationException(
                "id",
                "Doctor ID must not exceed " + MAX_ID_LENGTH + " characters"
            );
        }
    }

    /**
     * Validates a doctor name.
     *
     * @param name the doctor name to validate
     * @throws ValidationException if the name is invalid
     */
    public static void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("name", "Doctor name is required");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException(
                "name",
                "Doctor name must not exceed " + MAX_NAME_LENGTH + " characters"
            );
        }
    }
}
