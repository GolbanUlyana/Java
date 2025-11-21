package ua.validators;

import java.time.LocalDate;

public class StudentValidator {
    
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    public static boolean isValidEnrollmentDate(LocalDate enrollmentDate) {
        return enrollmentDate != null && !enrollmentDate.isAfter(LocalDate.now());
    }
    
    public static void validateStudentData(String email, LocalDate enrollmentDate) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        if (!isValidEnrollmentDate(enrollmentDate)) {
            throw new IllegalArgumentException("Invalid enrollment date: " + enrollmentDate);
        }
    }
}