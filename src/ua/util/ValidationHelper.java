package ua.util;

import ua.validators.CourseValidator;
import ua.validators.PersonValidator;
import ua.validators.StudentValidator;

public class ValidationHelper {
    
    public static boolean isValidName(String name) {
        return PersonValidator.isValidFirstName(name);
    }
    
    public static boolean isValidEmail(String email) {
        return StudentValidator.isValidEmail(email);
    }
    
    public static boolean isValidDate(java.time.LocalDate date) {
        return PersonValidator.isValidBirthDate(date);
    }
    
    public static boolean isValidCredits(int credits) {
        return CourseValidator.isValidCredits(credits);
    }
    
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }
    
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}