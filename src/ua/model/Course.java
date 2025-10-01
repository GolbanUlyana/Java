package ua.model;

import java.time.LocalDate;
import ua.util.Utils;

public class Course {
    
    private final String name;
    private final String description;
    private final int credits;
    private final LocalDate startDate;
    private final CourseLevel level;
    
    // Конструктор з валідацією
    public Course(String name, String description, int credits, 
                 LocalDate startDate, CourseLevel level) {
        // Валідація
        if (!ua.util.ValidationHelper.isNotEmpty(name)) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (!ua.util.ValidationHelper.isValidCredits(credits)) {
            throw new IllegalArgumentException("Credits must be between 1 and 10");
        }
        if (!ua.util.ValidationHelper.isValidDate(startDate)) {
            throw new IllegalArgumentException("Invalid start date");
        }
        if (level == null) {
            throw new IllegalArgumentException("Course level cannot be null");
        }
        
        this.name = name.trim();
        this.description = description;
        this.credits = credits;
        this.startDate = startDate;
        this.level = level;
    }
    
    public static Course createCourse(String name, String description, 
                                    int credits, LocalDate startDate, CourseLevel level) {
        return new Course(name, description, credits, startDate, level);
    }
    
    
    public String name() { return name; }
    public String description() { return description; }
    public int credits() { return credits; }
    public LocalDate startDate() { return startDate; }
    public CourseLevel level() { return level; }
    
    
    public String getLevelDescription() {
        return switch(level) {
            case BEGINNER -> "Курс для початківців. Ідеальний для тих, хто тільки починає.";
            case INTERMEDIATE -> "Курс для тих, хто вже має базові знання.";
            case ADVANCED -> "Просунутий курс для досвідчених студентів.";
        };
    }
    
    public String getFormattedInfo() {
        return Utils.formatCourseInfo(name, credits);
    }
    
    @Override
    public String toString() {
        return String.format("Курс: %s (%s), кредити: %d, початок: %s", 
                           name, level.getUkrainianName(), credits, startDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return credits == course.credits &&
               name.equals(course.name) &&
               description.equals(course.description) &&
               startDate.equals(course.startDate) &&
               level == course.level;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, description, credits, startDate, level);
    }
}