package main.validation;

import java.time.LocalDate;
import java.util.regex.Pattern;
import main.exception.ValidationException;

/**
 * Validator for Patient entity data.
 * <p>
 * This class provides validation methods for all Patient fields
 * to ensure data integrity before database operations.
 * </p>
 *
 */
public class PatientValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    /**
     * Private constructor to prevent instantiation.
     */
    private PatientValidator() {}

    /**
     * Validates all patient data for creation.
     *
     * @param mrn       the Medical Record Number
     * @param fname     the patient's first name
     * @param lname     the patient's last name
     * @param dob       the patient's date of birth
     * @param address   the patient's street address
     * @param state     the patient's state of residence
     * @param city      the patient's city of residence
     * @param zip       the patient's ZIP code
     * @param insurance the patient's insurance provider
     * @param email     the patient's email address
     * @throws ValidationException if any field is invalid
     */
    public static void validate(
        int mrn,
        String fname,
        String lname,
        LocalDate dob,
        String address,
        String state,
        String city,
        int zip,
        String insurance,
        String email
    ) throws ValidationException {
        validateMrn(mrn);
        validateName(fname, "First name");
        validateName(lname, "Last name");
        validateDob(dob);
        validateRequired(address, "Address");
        validateRequired(city, "City");
        validateState(state);
        validateZip(zip);
        validateRequired(insurance, "Insurance");
        validateEmail(email);
    }

    /**
     * Validates the Medical Record Number (MRN).
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
     * Validates a name field (first name or last name).
     *
     * @param name      the name to validate
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the name is invalid
     */
    public static void validateName(String name, String fieldName)
        throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException(
                fieldName,
                fieldName + " is required"
            );
        }
        if (name.length() > 100) {
            throw new ValidationException(
                fieldName,
                fieldName + " must be 100 characters or less"
            );
        }
    }

    /**
     * Validates the date of birth.
     *
     * @param dob the date of birth to validate
     * @throws ValidationException if the date of birth is invalid
     */
    public static void validateDob(LocalDate dob) throws ValidationException {
        if (dob == null) {
            throw new ValidationException("DOB", "Date of birth is required");
        }
        if (dob.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "DOB",
                "Date of birth cannot be in the future"
            );
        }
        if (dob.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new ValidationException(
                "DOB",
                "Date of birth must be after 1900"
            );
        }
    }

    /**
     * Validates a required string field.
     *
     * @param value     the value to validate
     * @param fieldName the name of the field for error messages
     * @throws ValidationException if the value is null or empty
     */
    public static void validateRequired(String value, String fieldName)
        throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(
                fieldName,
                fieldName + " is required"
            );
        }
    }

    /**
     * Validates the state field.
     *
     * @param state the state to validate
     * @throws ValidationException if the state is invalid
     */
    public static void validateState(String state) throws ValidationException {
        if (state == null || state.trim().isEmpty()) {
            throw new ValidationException("State", "State is required");
        }
        if (state.length() > 50) {
            throw new ValidationException(
                "State",
                "State must be 50 characters or less"
            );
        }
    }

    /**
     * Validates the ZIP code.
     *
     * @param zip the ZIP code to validate
     * @throws ValidationException if the ZIP code is invalid
     */
    public static void validateZip(int zip) throws ValidationException {
        if (zip <= 0 || zip > 99999) {
            throw new ValidationException(
                "ZIP",
                "ZIP code must be a valid 5-digit number"
            );
        }
    }

    /**
     * Validates an email address.
     *
     * @param email the email to validate
     * @throws ValidationException if the email is invalid
     */
    public static void validateEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email", "Email is required");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Email", "Email format is invalid");
        }
    }
}
