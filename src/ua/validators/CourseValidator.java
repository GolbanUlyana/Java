package ua.validators;

import java.time.LocalDate;

public class CourseValidator {
    
    public static boolean isValidCourseName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    public static boolean isValidDescription(String description) {
        return description != null;
    }
    
    public static boolean isValidCredits(int credits) {
        return credits > 0 && credits <= 10;
    }
    
    public static boolean isValidStartDate(LocalDate startDate) {
        return startDate != null && !startDate.isAfter(LocalDate.now());
    }
    
    public static void validateCourseData(String name, String description, int credits, LocalDate startDate) {
        if (!isValidCourseName(name)) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (!isValidDescription(description)) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (!isValidCredits(credits)) {
            throw new IllegalArgumentException("Credits must be between 1 and 10: " + credits);
        }
        if (!isValidStartDate(startDate)) {
            throw new IllegalArgumentException("Invalid start date: " + startDate);
        }
    }
}