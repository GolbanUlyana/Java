package ua.model;

import ua.util.Utils;
import java.time.LocalDate;

public class Course {
    private String name;
    private String description;
    private int credits;
    private LocalDate startDate;
    
    // Конструктор
    public Course(String name, String description, int credits, LocalDate startDate) {
        setName(name);
        setDescription(description);
        setCredits(credits);
        setStartDate(startDate);
    }
    
    // Factory метод
    public static Course createCourse(String name, String description, 
                                    int credits, LocalDate startDate) {
        return new Course(name, description, credits, startDate);
    }
    
    // Getter/Setter
    public String getName() { return name; }
    
    public void setName(String name) {
        if (!ua.util.ValidationHelper.isNotEmpty(name)) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        this.name = name.trim();
    }
    
    public String getDescription() { return description; }
    
    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }
    
    public int getCredits() { return credits; }
    
    public void setCredits(int credits) {
        if (!ua.util.ValidationHelper.isValidCredits(credits)) {
            throw new IllegalArgumentException("Credits must be between 1 and 10");
        }
        this.credits = credits;
    }
    
    public LocalDate getStartDate() { return startDate; }
    
    public void setStartDate(LocalDate startDate) {
        if (!ua.util.ValidationHelper.isValidDate(startDate)) {
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
