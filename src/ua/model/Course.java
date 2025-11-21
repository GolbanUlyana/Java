package ua.model;

import java.time.LocalDate;
import ua.util.Utils;
import ua.validators.CourseValidator;

public class Course {
    private String name;
    private String description;
    private int credits;
    private LocalDate startDate;
    
    public Course(String name, String description, int credits, LocalDate startDate) {
        CourseValidator.validateCourseData(name, description, credits, startDate);
        this.name = name.trim();
        this.description = description;
        this.credits = credits;
        this.startDate = startDate;
    }
    
    // ДОДАЙТЕ ЦЕЙ FACTORY МЕТОД
    public static Course createCourse(String name, String description, 
                                    int credits, LocalDate startDate) {
        return new Course(name, description, credits, startDate);
    }
    
    public String getName() { return name; }
    
    public void setName(String name) {
        if (!CourseValidator.isValidCourseName(name)) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        this.name = name.trim();
    }
    
    public String getDescription() { return description; }
    
    public void setDescription(String description) {
        if (!CourseValidator.isValidDescription(description)) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }
    
    public int getCredits() { return credits; }
    
    public void setCredits(int credits) {
        if (!CourseValidator.isValidCredits(credits)) {
            throw new IllegalArgumentException("Credits must be between 1 and 10");
        }
        this.credits = credits;
    }
    
    public LocalDate getStartDate() { return startDate; }
    
    public void setStartDate(LocalDate startDate) {
        if (!CourseValidator.isValidStartDate(startDate)) {
            throw new IllegalArgumentException("Invalid start date");
        }
        this.startDate = startDate;
    }
    
    public String getFormattedInfo() {
        return Utils.formatCourseInfo(name, credits);
    }
    
    @Override
    public String toString() {
        return String.format("Курс: %s, опис: %s, кредити: %d, початок: %s", 
                           name, description, credits, startDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return credits == course.credits &&
               name.equals(course.name) &&
               description.equals(course.description) &&
               startDate.equals(course.startDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, description, credits, startDate);
    }
}