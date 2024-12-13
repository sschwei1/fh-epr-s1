package at.fhooe.sail.util;

public class Person {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    public Person(String firstname, String lastname, String email, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Formats the person as a string
     *
     * @return the string representation of the person
     */
    public String toString() {
        return String.format(
                "%s %s (%s, %s)",
                this.firstname,
                this.lastname,
                this.email,
                this.phone
        );
    }

    /**
     * Compares two persons
     *
     * @param other - the other person to compare
     * @return true if the persons are equal, false otherwise
     */
    public boolean equals(Person other) {
        if(other == null) {
            return false;
        }

        return this.equals(other.firstname, other.lastname);
    }

    /**
     * Compares a person with a first and last name
     *
     * @param firstname - the first name of the person
     * @param lastname  - the last name of the person
     * @return true if the persons are equal, false otherwise
     */
    public boolean equals(String firstname, String lastname) {
        if(firstname == null || lastname == null) {
            return false;
        }

        return this.firstname.equals(firstname) &&
               this.lastname.equals(lastname);
    }
}
