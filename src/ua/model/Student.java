package ua.model;

import java.time.LocalDate;
import ua.exceptions.InvalidDataException;
import ua.util.Utils;

public record Student(
    String firstName,
    String lastName,
    LocalDate birthDate,
    String email,
    LocalDate enrollmentDate
) implements Comparable<Student> {
    
    public static Student createStudent(String firstName, String lastName, LocalDate birthDate,
                                      String email, LocalDate enrollmentDate) {
        validateData(firstName, lastName, birthDate, email, enrollmentDate);
        return new Student(firstName, lastName, birthDate, email, enrollmentDate);
    }
    
    public static Student createStudentWithValidation(String firstName, String lastName, LocalDate birthDate,
                                                    String email, LocalDate enrollmentDate) 
                                                    throws InvalidDataException {
        try {
            validateData(firstName, lastName, birthDate, email, enrollmentDate);
            return new Student(firstName, lastName, birthDate, email, enrollmentDate);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Некоректні дані студента: " + e.getMessage(), e);
        }
    }
    
    private static void validateData(String firstName, String lastName, LocalDate birthDate,
                                   String email, LocalDate enrollmentDate) {
        if (!ua.util.ValidationHelper.isValidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (!ua.util.ValidationHelper.isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid last name");
        }
        if (!ua.util.ValidationHelper.isValidDate(birthDate)) {
            throw new IllegalArgumentException("Invalid birth date");
        }
        if (!ua.util.ValidationHelper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!ua.util.ValidationHelper.isValidDate(enrollmentDate)) {
            throw new IllegalArgumentException("Invalid enrollment date");
        }
    }
    
    public String getFormattedName() {
        return Utils.formatName(firstName, lastName);
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public int getStudyDurationMonths() {
        return java.time.Period.between(enrollmentDate, java.time.LocalDate.now()).getMonths();
    }
    
    public boolean isActiveStudent() {
        return getStudyDurationMonths() < 24;
    }
    
    @Override
    public int compareTo(Student other) {
        int lastNameCompare = this.lastName.compareTo(other.lastName);
        if (lastNameCompare != 0) {
            return lastNameCompare;
        }
        return this.firstName.compareTo(other.firstName);
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return String.format("Студент: %s %s, email: %s, дата зарахування: %s", 
                           firstName, lastName, email, enrollmentDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return email.equals(student.email) &&
               firstName.equals(student.firstName) &&
               lastName.equals(student.lastName) &&
               birthDate.equals(student.birthDate) &&
               enrollmentDate.equals(student.enrollmentDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstName, lastName, birthDate, email, enrollmentDate);
    }
}