package main.models;

/**
 * Represents a medical procedure in the Electronic Medical Records system.
 * This model class encapsulates information about available medical procedures
 * that can be performed on patients.
 *
 * Each procedure is uniquely identified by its ID and contains a descriptive
 * name.
 */
public class Procedures {

    /** Unique identifier for this medical procedure */
    private String id;

    /** Descriptive name of the medical procedure */
    private String name;

    /**
     * Constructs a new Procedures object with all required fields.
     *
     * @param id   Unique identifier for the procedure
     * @param name Descriptive name of the procedure
     */
    public Procedures(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Default constructor for creating an empty Procedures object.
     * Used by frameworks and for object initialization before setting fields.
     */
    public Procedures() {
    }

    /**
     * Gets the unique identifier for this procedure.
     *
     * @return the procedure ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this procedure.
     *
     * @param id the procedure ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the descriptive name of this procedure.
     *
     * @return the procedure name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the descriptive name of this procedure.
     *
     * @param name the procedure name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the procedure.
     * Includes the procedure ID and name in a readable format.
     *
     * @return formatted string containing procedure details
     */
    @Override
    public String toString() {
        return "Procedure ID: " + id + ", Name: " + name;
    }

    /**
     * Compares this procedure with another object for equality.
     * Two procedures are considered equal if they have the same ID.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Procedures))
            return false;
        Procedures p = (Procedures) o;
        return id != null && id.equals(p.id);
    }

    /**
     * Returns a hash code value for this procedure.
     * The hash code is based on the ID field.
     *
     * @return hash code value for this procedure
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
