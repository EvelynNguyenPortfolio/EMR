package main.exception;

/**
 * Exception thrown when validation of input data fails.
 * <p>
 * This exception is used to indicate that user-provided data
 * does not meet the required validation criteria.
 * </p>
 */
public class ValidationException extends EMRException {

    private static final long serialVersionUID = 1L;

    private final String fieldName;

    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param message the detail message
     */
    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
    }

    /**
     * Constructs a new ValidationException with the specified field name and detail message.
     *
     * @param fieldName the name of the field that failed validation
     * @param message   the detail message
     */
    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    /**
     * Constructs a new ValidationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
    }

    /**
     * Returns the name of the field that failed validation.
     *
     * @return the field name, or null if not specified
     */
    public String getFieldName() {
        return fieldName;
    }
}
