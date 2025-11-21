package ua.validators;

public class TeacherValidator {
    
    public static boolean isValidExperienceYears(int experienceYears) {
        return experienceYears >= 0 && experienceYears <= 60;
    }
    
    public static void validateTeacherData(int experienceYears) {
        if (!isValidExperienceYears(experienceYears)) {
            throw new IllegalArgumentException("Experience years must be between 0 and 60: " + experienceYears);
        }
    }
}