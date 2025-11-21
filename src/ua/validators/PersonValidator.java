package ua.validators;

import java.time.LocalDate;

public class PersonValidator {
    
    public static boolean isValidFirstName(String firstName) {
        return firstName != null && !firstName.trim().isEmpty() && 
               firstName.matches("[A-Za-zА-Яа-яЇїІіЄєҐґ'\\-\\s]+");
    }
    
    public static boolean isValidLastName(String lastName) {
        return lastName != null && !lastName.trim().isEmpty() && 
               lastName.matches("[A-Za-zА-Яа-яЇїІіЄєҐґ'\\-\\s]+");
    }
    
    public static boolean isValidBirthDate(LocalDate birthDate) {
        return birthDate != null && !birthDate.isAfter(LocalDate.now());
    }
    
    public static void validatePersonData(String firstName, String lastName, LocalDate birthDate) {
        if (!isValidFirstName(firstName)) {
            throw new IllegalArgumentException("Invalid first name: " + firstName);
        }
        if (!isValidLastName(lastName)) {
            throw new IllegalArgumentException("Invalid last name: " + lastName);
        }
        if (!isValidBirthDate(birthDate)) {
            throw new IllegalArgumentException("Invalid birth date: " + birthDate);
        }
    }
}