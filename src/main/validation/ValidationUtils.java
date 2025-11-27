package main.validation;

import main.exception.ValidationException;

/**
 * Utility class providing common validation methods for the EMR application.
 * <p>
 * This class contains static methods for validating various types of input data
 * including strings, numbers, dates, and specific field formats like email and IDs.
 * </p>
 */
public final class ValidationUtils {

    private static final String EMAIL_REGEX =
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final int MAX_ID_LENGTH = 25;
    private static final int MAX_DOCTOR_NAME_LENGTH = 45;

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ValidationUtils() {
        throw new UnsupportedOperationException(
            "Utility class cannot be instantiated"
        );
    }

    /**
     * Validates that a string is not null or empty.
     *
     * @param value     the string to validate
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the string is null or empty
     */
    public static void validateNotEmpty(String value, String fieldName)
        throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(
                fieldName,
                fieldName + " cannot be empty"
            );
        }
    }

    /**
     * Validates that a string does not exceed the maximum length.
     *
     * @param value     the string to validate
     * @param maxLength the maximum allowed length
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the string exceeds the maximum length
     */
    public static void validateMaxLength(
        String value,
        int maxLength,
        String fieldName
    ) throws ValidationException {
        if (value != null && value.length() > maxLength) {
            throw new ValidationException(
                fieldName,
                fieldName + " cannot exceed " + maxLength + " characters"
            );
        }
    }

    /**
     * Validates that a number is positive.
     *
     * @param value     the number to validate
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the number is not positive
     */
    public static void validatePositive(int value, String fieldName)
        throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(
                fieldName,
                fieldName + " must be positive"
            );
        }
    }

    /**
     * Validates that a number is non-negative.
     *
     * @param value     the number to validate
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the number is negative
     */
    public static void validateNonNegative(double value, String fieldName)
        throws ValidationException {
        if (value < 0) {
            throw new ValidationException(
                fieldName,
                fieldName + " cannot be negative"
            );
        }
    }

    /**
     * Validates that a zip code is valid (5-digit positive number).
     *
     * @param zip the zip code to validate
     * @throws ValidationException if the zip code is invalid
     */
    public static void validateZipCode(int zip) throws ValidationException {
        if (zip < 10000 || zip > 99999) {
            throw new ValidationException(
                "Zip Code",
                "Zip code must be a 5-digit number"
            );
        }
    }

    /**
     * Validates that an email address has a valid format.
     *
     * @param email the email address to validate
     * @throws ValidationException if the email format is invalid
     */
    public static void validateEmail(String email) throws ValidationException {
        validateNotEmpty(email, "Email");
        if (!email.matches(EMAIL_REGEX)) {
            throw new ValidationException("Email", "Invalid email format");
        }
    }

    /**
     * Validates that an ID string has a valid length.
     *
     * @param id        the ID to validate
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the ID is invalid
     */
    public static void validateId(String id, String fieldName)
        throws ValidationException {
        validateNotEmpty(id, fieldName);
        validateMaxLength(id, MAX_ID_LENGTH, fieldName);
    }

    /**
     * Validates that an MRN (Medical Record Number) is valid.
     *
     * @param mrn the MRN to validate
     * @throws ValidationException if the MRN is invalid
     */
    public static void validateMrn(int mrn) throws ValidationException {
        if (mrn <= 0) {
            throw new ValidationException(
                "MRN",
                "MRN must be a positive number"
            );
        }
    }

    /**
     * Validates a doctor's name.
     *
     * @param name the doctor's name to validate
     * @throws ValidationException if the name is invalid
     */
    public static void validateDoctorName(String name)
        throws ValidationException {
        validateNotEmpty(name, "Doctor Name");
        validateMaxLength(name, MAX_DOCTOR_NAME_LENGTH, "Doctor Name");
    }

    /**
     * Validates that a duration is positive.
     *
     * @param duration the duration in minutes
     * @throws ValidationException if the duration is not positive
     */
    public static void validateDuration(int duration)
        throws ValidationException {
        if (duration <= 0) {
            throw new ValidationException(
                "Duration",
                "Duration must be positive (in minutes)"
            );
        }
    }

    /**
     * Validates that a billing amount is valid.
     *
     * @param billing the billing amount
     * @throws ValidationException if the billing amount is invalid
     */
    public static void validateBilling(double billing)
        throws ValidationException {
        validateNonNegative(billing, "Billing");
    }

    /**
     * Validates a US state abbreviation or name.
     *
     * @param state the state to validate
     * @throws ValidationException if the state is invalid
     */
    public static void validateState(String state) throws ValidationException {
        validateNotEmpty(state, "State");
        // Allow both abbreviations (2 chars) and full names
        if (state.length() > 50) {
            throw new ValidationException("State", "State name is too long");
        }
    }
}
