package main.model;

import java.util.Objects;

/**
 * Represents a medical procedure in the EMR system.
 * <p>
 * A Procedure entity contains information about a specific medical procedure,
 * including its name, description, duration, and the doctor who performs it.
 * </p>
 *
 * <p>Corresponds to the 'procedures' table in the database with the following schema:</p>
 * <ul>
 *   <li>id - varchar(25), Primary Key</li>
 *   <li>name - text, NOT NULL</li>
 *   <li>description - text, NOT NULL</li>
 *   <li>duration - int, NOT NULL (in minutes)</li>
 *   <li>doctorId - varchar(25), Foreign Key to doctors table</li>
 * </ul>
 *
 */
public class Procedure {

    /** Unique identifier for the procedure (max 25 characters). */
    private String id;

    /** Name of the procedure. */
    private String name;

    /** Detailed description of the procedure. */
    private String description;

    /** Duration of the procedure in minutes. */
    private int duration;

    /** ID of the doctor who performs this procedure. */
    private String doctorId;

    /**
     * Constructs a new Procedure with all fields.
     *
     * @param id          the unique identifier for the procedure
     * @param name        the name of the procedure
     * @param description the detailed description of the procedure
     * @param duration    the duration of the procedure in minutes
     * @param doctorId    the ID of the doctor who performs this procedure
     */
    public Procedure(
        String id,
        String name,
        String description,
        int duration,
        String doctorId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.doctorId = doctorId;
    }

    /**
     * Default constructor for Procedure.
     * Creates an empty Procedure instance.
     */
    public Procedure() {}

    /**
     * Gets the procedure ID.
     *
     * @return the procedure ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the procedure ID.
     *
     * @param id the procedure ID to set (max 25 characters)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the procedure name.
     *
     * @return the procedure name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the procedure name.
     *
     * @param name the procedure name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the procedure description.
     *
     * @return the procedure description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the procedure description.
     *
     * @param description the procedure description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the procedure duration in minutes.
     *
     * @return the procedure duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the procedure duration in minutes.
     *
     * @param duration the procedure duration to set (must be positive)
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the ID of the doctor who performs this procedure.
     *
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the ID of the doctor who performs this procedure.
     *
     * @param doctorId the doctor ID to set
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Returns a string representation of the Procedure.
     *
     * @return a formatted string containing all procedure details
     */
    @Override
    public String toString() {
        return String.format(
            "Procedure ID: %s, Name: %s, Description: %s, Duration: %d minutes, Doctor ID: %s",
            id,
            name,
            description,
            duration,
            doctorId
        );
    }

    /**
     * Compares this Procedure to another object for equality.
     * Two procedures are considered equal if they have the same ID.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Procedure)) return false;
        Procedure procedure = (Procedure) o;
        return Objects.equals(id, procedure.id);
    }

    /**
     * Returns the hash code for this Procedure.
     *
     * @return the hash code based on the procedure ID
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
