package main.validation;

import main.exception.ValidationException;

/**
 * Validator class for Procedure entities.
 * <p>
 * Provides validation methods for procedure data to ensure
 * data integrity before database operations.
 * </p>
 *
 */
public class ProcedureValidator {

    private static final int MAX_ID_LENGTH = 25;
    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 1440; // 24 hours in minutes

    /**
     * Private constructor to prevent instantiation.
     */
    private ProcedureValidator() {}

    /**
     * Validates all procedure fields for creation.
     *
     * @param id          the procedure ID
     * @param name        the procedure name
     * @param description the procedure description
     * @param duration    the procedure duration in minutes
     * @param doctorId    the doctor ID
     * @throws ValidationException if validation fails
     */
    public static void validateForCreate(
        String id,
        String name,
        String description,
        int duration,
        String doctorId
    ) throws ValidationException {
        validateId(id);
        validateName(name);
        validateDescription(description);
        validateDuration(duration);
        validateDoctorId(doctorId);
    }

    /**
     * Validates the procedure ID.
     *
     * @param id the procedure ID to validate
     * @throws ValidationException if the ID is invalid
     */
    public static void validateId(String id) throws ValidationException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("id", "Procedure ID is required");
        }
        if (id.length() > MAX_ID_LENGTH) {
            throw new ValidationException(
                "id",
                "Procedure ID must not exceed " + MAX_ID_LENGTH + " characters"
            );
        }
    }

    /**
     * Validates the procedure name.
     *
     * @param name the procedure name to validate
     * @throws ValidationException if the name is invalid
     */
    public static void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("name", "Procedure name is required");
        }
    }

    /**
     * Validates the procedure description.
     *
     * @param description the procedure description to validate
     * @throws ValidationException if the description is invalid
     */
    public static void validateDescription(String description)
        throws ValidationException {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException(
                "description",
                "Procedure description is required"
            );
        }
    }

    /**
     * Validates the procedure duration.
     *
     * @param duration the duration in minutes to validate
     * @throws ValidationException if the duration is invalid
     */
    public static void validateDuration(int duration)
        throws ValidationException {
        if (duration < MIN_DURATION) {
            throw new ValidationException(
                "duration",
                "Procedure duration must be at least " +
                    MIN_DURATION +
                    " minute(s)"
            );
        }
        if (duration > MAX_DURATION) {
            throw new ValidationException(
                "duration",
                "Procedure duration must not exceed " +
                    MAX_DURATION +
                    " minutes (24 hours)"
            );
        }
    }

    /**
     * Validates the doctor ID associated with the procedure.
     *
     * @param doctorId the doctor ID to validate
     * @throws ValidationException if the doctor ID is invalid
     */
    public static void validateDoctorId(String doctorId)
        throws ValidationException {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new ValidationException(
                "doctorId",
                "Doctor ID is required for procedure"
            );
        }
        if (doctorId.length() > MAX_ID_LENGTH) {
            throw new ValidationException(
                "doctorId",
                "Doctor ID must not exceed " + MAX_ID_LENGTH + " characters"
            );
        }
    }
}
