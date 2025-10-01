package ua.model;

import java.time.LocalDate;
import ua.exceptions.InvalidDataException;

public record Teacher(
    String firstName,
    String lastName, 
    LocalDate birthDate,
    int experienceYears
) implements Comparable<Teacher> {
    
    public static Teacher createTeacher(String firstName, String lastName, 
                                      LocalDate birthDate, int experienceYears) {
        validateData(firstName, lastName, birthDate, experienceYears);
        return new Teacher(firstName, lastName, birthDate, experienceYears);
    }
    
    public static Teacher createTeacherWithValidation(String firstName, String lastName, 
                                                     LocalDate birthDate, int experienceYears) 
                                                     throws InvalidDataException {
        try {
            validateData(firstName, lastName, birthDate, experienceYears);
            return new Teacher(firstName, lastName, birthDate, experienceYears);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Некоректні дані викладача: " + e.getMessage(), e);
        }
    }
    
    private static void validateData(String firstName, String lastName, 
                                   LocalDate birthDate, int experienceYears) {
        if (!ua.util.ValidationHelper.isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (!ua.util.ValidationHelper.isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name");
        }
        if (!ua.util.ValidationHelper.isValidDate(birthDate)) {
            throw new IllegalArgumentException("Invalid birth date");
        }
        if (experienceYears < 0 || experienceYears > 60) {
            throw new IllegalArgumentException("Experience years must be between 0 and 60");
        }
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public boolean isExperienced() {
        return experienceYears >= 5;
    }
    
    @Override
    public int compareTo(Teacher other) {
        int lastNameCompare = this.lastName.compareTo(other.lastName);
        if (lastNameCompare != 0) {
            return lastNameCompare;
        }
        return this.firstName.compareTo(other.firstName);
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public int getExperienceYears() {
        return experienceYears;
    }
    
    @Override
    public String toString() {
        return String.format("Викладач: %s %s, досвід: %d років", 
                           firstName, lastName, experienceYears);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return experienceYears == teacher.experienceYears &&
               firstName.equals(teacher.firstName) &&
               lastName.equals(teacher.lastName) &&
               birthDate.equals(teacher.birthDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstName, lastName, birthDate, experienceYears);
    }
}