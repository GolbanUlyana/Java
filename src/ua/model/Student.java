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
) {
    // Factory метод
    public static Student createStudent(String firstName, String lastName, LocalDate birthDate,
                                      String email, LocalDate enrollmentDate) {
        validateData(firstName, lastName, birthDate, email, enrollmentDate);
        return new Student(firstName, lastName, birthDate, email, enrollmentDate);
    }
    
    // Новий метод для створення з обробкою InvalidDataException
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
    
    // Валідація
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
    
    // Додаткові методи - ВИПРАВЛЕНО
    public String getFormattedName() {
        return Utils.formatName(firstName, lastName);
    }
    
    public int getStudyDurationMonths() {
        return java.time.Period.between(enrollmentDate, java.time.LocalDate.now()).getMonths();
    }
    
    public boolean isActiveStudent() {
        return getStudyDurationMonths() < 24; // Активний якщо навчається менше 2 років
    }
    
    // Додатковий метод для зручності
    public String getFullName() {
        return firstName + " " + lastName;
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