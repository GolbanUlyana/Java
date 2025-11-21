package ua.model;

import ua.util.ValidationHelper;
import java.time.LocalDate;


public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;
    
    protected Person(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
    }
    
    // Protected методи для наслідування
    protected final void setFirstName(String firstName) {
        if (!ValidationHelper.isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName.trim();
    }
    
    protected final void setLastName(String lastName) {
        if (!ValidationHelper.isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name");
        }
        this.lastName = lastName.trim();
    }
    
    protected final void setBirthDate(LocalDate birthDate) {
        if (!ValidationHelper.isValidDate(birthDate)) {
            throw new IllegalArgumentException("Invalid birth date");
        }
        this.birthDate = birthDate;
    }
    
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}