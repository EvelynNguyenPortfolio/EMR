package main.model;

import java.util.Objects;

/**
 * Represents a Doctor entity in the EMR system.
 * <p>
 * A Doctor is a medical professional who can perform procedures
 * and be associated with patient history records.
 * </p>
 */
public class Doctor {

    /**
     * The unique identifier for the doctor.
     * Maximum length: 25 characters.
     */
    private String id;

    /**
     * The full name of the doctor.
     * Maximum length: 45 characters.
     */
    private String name;

    /**
     * Constructs a new Doctor with the specified ID and name.
     *
     * @param id   the unique identifier for the doctor
     * @param name the full name of the doctor
     */
    public Doctor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Default constructor for Doctor.
     * Creates an empty Doctor instance.
     */
    public Doctor() {}

    /**
     * Gets the doctor's unique identifier.
     *
     * @return the doctor ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the doctor's unique identifier.
     *
     * @param id the doctor ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the doctor's full name.
     *
     * @return the doctor's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the doctor's full name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the Doctor.
     *
     * @return a string containing the doctor's ID and name
     */
    @Override
    public String toString() {
        return "Doctor ID: " + id + ", Name: " + name;
    }

    /**
     * Compares this Doctor to another object for equality.
     * Two doctors are considered equal if they have the same ID.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor d = (Doctor) o;
        return Objects.equals(id, d.id);
    }

    /**
     * Returns a hash code value for the Doctor.
     *
     * @return a hash code based on the doctor's ID
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
