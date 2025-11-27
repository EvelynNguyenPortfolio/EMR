package main.exception;

/**
 * Base exception class for the EMR application.
 * All custom exceptions in the EMR system should extend this class.
 */
public class EMRException extends Exception {

    /**
     * Constructs a new EMRException with the specified detail message.
     *
     * @param message the detail message
     */
    public EMRException(String message) {
        super(message);
    }

    /**
     * Constructs a new EMRException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public EMRException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EMRException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public EMRException(Throwable cause) {
        super(cause);
    }
}
