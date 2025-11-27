package main.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a patient in the EMR system.
 * <p>
 * This class holds all personal and medical information for a patient,
 * including identification, contact details, and insurance information.
 * </p>
 */
public class Patient {

    /** The Medical Record Number - unique identifier for the patient */
    private int mrn;

    /** Patient's first name */
    private String fname;

    /** Patient's last name */
    private String lname;

    /** Patient's date of birth */
    private LocalDate dob;

    /** Patient's street address */
    private String address;

    /** Patient's state of residence */
    private String state;

    /** Patient's city of residence */
    private String city;

    /** Patient's ZIP code */
    private int zip;

    /** Patient's insurance provider name */
    private String insurance;

    /** Patient's email address */
    private String email;

    /**
     * Constructs a new Patient with all required fields.
     *
     * @param mrn       the Medical Record Number (unique identifier)
     * @param fname     the patient's first name
     * @param lname     the patient's last name
     * @param dob       the patient's date of birth
     * @param address   the patient's street address
     * @param state     the patient's state of residence
     * @param city      the patient's city of residence
     * @param zip       the patient's ZIP code
     * @param insurance the patient's insurance provider
     * @param email     the patient's email address
     */
    public Patient(
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
    ) {
        this.mrn = mrn;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.address = address;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.insurance = insurance;
        this.email = email;
    }

    /**
     * Default constructor for Patient.
     * Creates an empty patient object.
     */
    public Patient() {}

    /**
     * Gets the Medical Record Number.
     *
     * @return the MRN
     */
    public int getMrn() {
        return mrn;
    }

    /**
     * Sets the Medical Record Number.
     *
     * @param mrn the MRN to set
     */
    public void setMrn(int mrn) {
        this.mrn = mrn;
    }

    /**
     * Gets the patient's first name.
     *
     * @return the first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets the patient's first name.
     *
     * @param fname the first name to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Gets the patient's last name.
     *
     * @return the last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets the patient's last name.
     *
     * @param lname the last name to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Gets the patient's date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Sets the patient's date of birth.
     *
     * @param dob the date of birth to set
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Gets the patient's street address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the patient's street address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the patient's state of residence.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the patient's state of residence.
     *
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the patient's city of residence.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the patient's city of residence.
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the patient's ZIP code.
     *
     * @return the ZIP code
     */
    public int getZip() {
        return zip;
    }

    /**
     * Sets the patient's ZIP code.
     *
     * @param zip the ZIP code to set
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * Gets the patient's insurance provider.
     *
     * @return the insurance provider name
     */
    public String getInsurance() {
        return insurance;
    }

    /**
     * Sets the patient's insurance provider.
     *
     * @param insurance the insurance provider to set
     */
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    /**
     * Gets the patient's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the patient's email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of this patient.
     *
     * @return a formatted string containing all patient information
     */
    @Override
    public String toString() {
        return (
            "Patient MRN: " +
            mrn +
            ", Name: " +
            fname +
            " " +
            lname +
            ", DOB: " +
            dob +
            ", Address: " +
            address +
            ", City: " +
            city +
            ", State: " +
            state +
            ", Zip: " +
            zip +
            ", Insurance: " +
            insurance +
            ", Email: " +
            email
        );
    }

    /**
     * Compares this patient to another object for equality.
     * Two patients are equal if they have the same MRN.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return mrn == patient.mrn;
    }

    /**
     * Returns a hash code value for this patient.
     *
     * @return the hash code based on the MRN
     */
    @Override
    public int hashCode() {
        return Objects.hash(mrn);
    }
}
