package ua.model;

import java.time.LocalDate;

public class Assignment {
    private CourseModule module;  
    private LocalDate dueDate;
    private int maxScore;
    private AssignmentType type;
    
    // Конструктор без сеттерів
    public Assignment(CourseModule module, LocalDate dueDate, int maxScore, AssignmentType type) {
        // Валідація без сеттерів
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        if (dueDate == null || dueDate.isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Invalid due date");
        }
        if (maxScore <= 0 || maxScore > 100) {
            throw new IllegalArgumentException("Max score must be between 1 and 100");
        }
        if (type == null) {
            throw new IllegalArgumentException("Assignment type cannot be null");
        }
        
        this.module = module;
        this.dueDate = dueDate;
        this.maxScore = maxScore;
        this.type = type;
    }
    
    public static Assignment createAssignment(CourseModule module, LocalDate dueDate, 
                                            int maxScore, AssignmentType type) {
        return new Assignment(module, dueDate, maxScore, type);
    }
    
    // Тільки гетери (record-style)
    public CourseModule module() { return module; }
    public LocalDate dueDate() { return dueDate; }
    public int maxScore() { return maxScore; }
    public AssignmentType type() { return type; }
    
    // Додаткові методи
    public String getSubmissionInstructions() {
        return switch(type) {
            case HOMEWORK -> "Надіслати у форматі PDF до дедлайну.";
            case PROJECT -> "Завантажити проект на GitHub та надіслати посилання.";
            case QUIZ -> "Пройти тест онлайн протягом виділеного часу.";
            case EXAM -> "З'явитися особисто у аудиторії з документом, що посвідчує особу.";
        };
    }
    
    public boolean allowsGroupWork() {
        return switch(type) {
            case PROJECT -> true;
            case HOMEWORK, QUIZ, EXAM -> false;
        };
    }
    
    @Override
    public String toString() {
        return String.format("Завдання: %s - '%s', дата: %s, макс. балів: %d", 
                           type.getUkrainianName(), module.name(), dueDate, maxScore);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment assignment = (Assignment) obj;
        return maxScore == assignment.maxScore &&
               module.equals(assignment.module) &&
               dueDate.equals(assignment.dueDate) &&
               type == assignment.type;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(module, dueDate, maxScore, type);
    }
}