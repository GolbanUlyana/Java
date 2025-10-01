package ua.model;

import java.time.LocalDate;
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
    
    // Додаткові методи
    public String getFormattedName() {
        return Utils.formatName(firstName, lastName);
    }
    
    public int getStudyDurationMonths() {
        return java.time.Period.between(enrollmentDate, java.time.LocalDate.now()).getMonths();
    }
    
    public boolean isActiveStudent() {
        return getStudyDurationMonths() < 24; // Активний якщо навчається менше 2 років
    }
    
    @Override
    public String toString() {
        return String.format("Студент: %s %s, email: %s, дата зарахування: %s", 
                           firstName, lastName, email, enrollmentDate);
    }
}