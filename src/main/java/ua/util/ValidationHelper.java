package ua.util;

public class ValidationHelper {
  
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[A-Za-zА-Яа-яЇїІіЄєҐґ'\\-\\s]+");
    }
    
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    public static boolean isValidDate(java.time.LocalDate date) {
        return date != null && !date.isAfter(java.time.LocalDate.now());
    }
    
    public static boolean isValidCredits(int credits) {
        return credits > 0 && credits <= 10;
    }
    
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }
    
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
}