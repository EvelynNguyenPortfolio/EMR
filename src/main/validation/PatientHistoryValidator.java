package main.validation;

import java.time.LocalDate;
import main.exception.ValidationException;

/**
 * Validator for PatientHistory entities.
 * <p>
 * This class provides validation methods to ensure that PatientHistory data
 * meets the required criteria before being persisted to the database.
 * </p>
 *
 */
public class PatientHistoryValidator {

    private static final int MAX_ID_LENGTH = 25;

    /**
     * Private constructor to prevent instantiation.
     */
    private PatientHistoryValidator() {}

    /**
     * Validates all patient history fields for creation.
     *
     * @param id          the history record ID
     * @param patientId   the patient's MRN
     * @param procedureId the procedure ID
     * @param date        the date of the procedure
     * @param billing     the billing amount
     * @param doctorId    the doctor ID
     * @throws ValidationException if any validation rule is violated
     */
    public static void validateForCreate(
        String id,
        int patientId,
        String procedureId,
        LocalDate date,
        double billing,
        String doctorId
    ) throws ValidationException {
        validateId(id);
        validatePatientId(patientId);
        validateProcedureId(procedureId);
        validateDate(date);
        validateBilling(billing);
        validateDoctorId(doctorId);
    }

    /**
     * Validates the patient history ID.
     *
     * @param id the ID to validate
     * @throws ValidationException if the ID is invalid
     */
    public static void validateId(String id) throws ValidationException {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException(
                "id",
                "Patient history ID is required"
            );
        }
        if (id.length() > MAX_ID_LENGTH) {
            throw new ValidationException(
                "id",
                "Patient history ID cannot exceed " +
                    MAX_ID_LENGTH +
                    " characters"
            );
        }
    }

    /**
     * Validates the patient ID.
     *
     * @param patientId the patient ID to validate
     * @throws ValidationException if the patient ID is invalid
     */
    public static void validatePatientId(int patientId)
        throws ValidationException {
        if (patientId <= 0) {
            throw new ValidationException(
                "patientId",
                "Patient ID must be a positive number"
            );
        }
    }

    /**
     * Validates the procedure ID.
     *
     * @param procedureId the procedure ID to validate
     * @throws ValidationException if the procedure ID is invalid
     */
    public static void validateProcedureId(String procedureId)
        throws ValidationException {
        if (procedureId == null || procedureId.trim().isEmpty()) {
            throw new ValidationException(
                "procedureId",
                "Procedure ID is required"
            );
        }
        if (procedureId.length() > MAX_ID_LENGTH) {
            throw new ValidationException(
                "procedureId",
                "Procedure ID cannot exceed " + MAX_ID_LENGTH + " characters"
            );
        }
    }

    /**
     * Validates the date.
     *
     * @param date the date to validate
     * @throws ValidationException if the date is invalid
     */
    public static void validateDate(LocalDate date) throws ValidationException {
        if (date == null) {
            throw new ValidationException("date", "Date is required");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new ValidationException(
                "date",
                "Date cannot be in the future"
            );
        }
    }

    /**
     * Validates the billing amount.
     *
     * @param billing the billing amount to validate
     * @throws ValidationException if the billing amount is invalid
     */
    public static void validateBilling(double billing)
        throws ValidationException {
        if (billing < 0) {
            throw new ValidationException(
                "billing",
                "Billing amount cannot be negative"
            );
        }
    }

    /**
     * Validates the doctor ID.
     *
     * @param doctorId the doctor ID to validate
     * @throws ValidationException if the doctor ID is invalid
     */
    public static void validateDoctorId(String doctorId)
        throws ValidationException {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new ValidationException("doctorId", "Doctor ID is required");
        }
        if (doctorId.length() > MAX_ID_LENGTH) {
            throw new ValidationException(
                "doctorId",
                "Doctor ID cannot exceed " + MAX_ID_LENGTH + " characters"
            );
        }
    }
}
