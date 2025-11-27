package main.exception;

/**
 * Exception thrown when a database operation fails.
 * This exception wraps SQL exceptions and provides more context
 * about the operation that failed.
 */
public class DatabaseException extends EMRException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DatabaseException with the specified detail message.
     *
     * @param message the detail message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new DatabaseException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DatabaseException with the specified cause.
     *
     * @param cause the cause of this exception
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
