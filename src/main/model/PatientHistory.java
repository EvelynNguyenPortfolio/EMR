package main.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a patient history record in the EMR system.
 * <p>
 * A PatientHistory entity captures the details of a medical procedure
 * performed on a patient, including the date, billing information,
 * and the doctor who performed the procedure.
 * </p>
 *
 * <p>Corresponds to the 'patient_history' table in the database with the following schema:</p>
 * <ul>
 *   <li>id - varchar(25), Primary Key</li>
 *   <li>patientId - int, Foreign Key to patients table</li>
 *   <li>procedureId - varchar(25), Foreign Key to procedures table</li>
 *   <li>date - date, NOT NULL</li>
 *   <li>billing - double, NOT NULL</li>
 *   <li>doctorId - varchar(25), Foreign Key to doctors table</li>
 * </ul>
 *
 */
public class PatientHistory {

    /** Unique identifier for the patient history record (max 25 characters). */
    private String id;

    /** The MRN (Medical Record Number) of the patient. */
    private int patientId;

    /** The ID of the procedure performed. */
    private String procedureId;

    /** The date when the procedure was performed. */
    private LocalDate date;

    /** The billing amount for the procedure. */
    private double billing;

    /** The ID of the doctor who performed the procedure. */
    private String doctorId;

    /**
     * Constructs a new PatientHistory with all required fields.
     *
     * @param id          the unique identifier for this history record
     * @param patientId   the MRN of the patient
     * @param procedureId the ID of the procedure performed
     * @param date        the date when the procedure was performed
     * @param billing     the billing amount for the procedure
     * @param doctorId    the ID of the doctor who performed the procedure
     */
    public PatientHistory(
        String id,
        int patientId,
        String procedureId,
        LocalDate date,
        double billing,
        String doctorId
    ) {
        this.id = id;
        this.patientId = patientId;
        this.procedureId = procedureId;
        this.date = date;
        this.billing = billing;
        this.doctorId = doctorId;
    }

    /**
     * Default constructor for PatientHistory.
     * Creates an empty PatientHistory instance.
     */
    public PatientHistory() {}

    /**
     * Gets the unique identifier for this history record.
     *
     * @return the history record ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this history record.
     *
     * @param id the history record ID to set (max 25 characters)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the patient's MRN.
     *
     * @return the patient ID (MRN)
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient's MRN.
     *
     * @param patientId the patient ID (MRN) to set
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the ID of the procedure performed.
     *
     * @return the procedure ID
     */
    public String getProcedureId() {
        return procedureId;
    }

    /**
     * Sets the ID of the procedure performed.
     *
     * @param procedureId the procedure ID to set
     */
    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    /**
     * Gets the date when the procedure was performed.
     *
     * @return the procedure date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date when the procedure was performed.
     *
     * @param date the procedure date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the billing amount for the procedure.
     *
     * @return the billing amount
     */
    public double getBilling() {
        return billing;
    }

    /**
     * Sets the billing amount for the procedure.
     *
     * @param billing the billing amount to set (must be non-negative)
     */
    public void setBilling(double billing) {
        this.billing = billing;
    }

    /**
     * Gets the ID of the doctor who performed the procedure.
     *
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the ID of the doctor who performed the procedure.
     *
     * @param doctorId the doctor ID to set
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Returns a string representation of the PatientHistory.
     *
     * @return a formatted string containing all history record details
     */
    @Override
    public String toString() {
        return String.format(
            "PatientHistory ID: %s, Patient ID: %d, Procedure ID: %s, Date: %s, Billing: $%.2f, Doctor ID: %s",
            id,
            patientId,
            procedureId,
            date,
            billing,
            doctorId
        );
    }

    /**
     * Compares this PatientHistory to another object for equality.
     * Two patient history records are considered equal if they have the same ID.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientHistory)) return false;
        PatientHistory that = (PatientHistory) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Returns a hash code value for this PatientHistory.
     *
     * @return a hash code based on the history record ID
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
