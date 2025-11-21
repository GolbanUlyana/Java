package ua.model;

import java.time.LocalDate;
import ua.validators.PersonValidator;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;
    
    protected Person(String firstName, String lastName, LocalDate birthDate) {
        PersonValidator.validatePersonData(firstName, lastName, birthDate);  
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.birthDate = birthDate;
    }
    
    
    protected final void setFirstName(String firstName) {
        if (!PersonValidator.isValidFirstName(firstName)) {  
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName.trim();
    }
    
    protected final void setLastName(String lastName) {
        if (!PersonValidator.isValidLastName(lastName)) {  
            throw new IllegalArgumentException("Invalid last name");
        }
        this.lastName = lastName.trim();
    }
    
    protected final void setBirthDate(LocalDate birthDate) {
        if (!PersonValidator.isValidBirthDate(birthDate)) {  
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