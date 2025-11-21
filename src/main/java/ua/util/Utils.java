package ua.util;

public class Utils {
    
    public static String formatName(String firstName, String lastName) {
        if (!ValidationHelper.isValidName(firstName) || !ValidationHelper.isValidName(lastName)) {
            throw new IllegalArgumentException("Invalid name parameters");
        }
        return lastName.toUpperCase() + " " + firstName.substring(0, 1).toUpperCase() + ".";
    }
    
    public static String formatCourseInfo(String courseName, int credits) {
        if (!ValidationHelper.isNotEmpty(courseName) || !ValidationHelper.isValidCredits(credits)) {
            throw new IllegalArgumentException("Invalid course parameters");
        }
        return String.format("%s (%d кредитів)", courseName, credits);
    }
    
    public static boolean isAssignmentDueSoon(java.time.LocalDate dueDate) {
        if (!ValidationHelper.isValidDate(dueDate)) {
            return false;
        }
        java.time.LocalDate now = java.time.LocalDate.now();
        java.time.Period period = java.time.Period.between(now, dueDate);
        return period.getDays() <= 7 && period.getDays() >= 0;
    }
}