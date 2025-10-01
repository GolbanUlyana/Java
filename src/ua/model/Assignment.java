package ua.model;

import java.time.LocalDate;

public class Assignment {
    private Module module;
    private LocalDate dueDate;
    private int maxScore;
    
    public Assignment(Module module, LocalDate dueDate, int maxScore) {
        setModule(module);
        setDueDate(dueDate);
        setMaxScore(maxScore);
    }
    
    public static Assignment createAssignment(Module module, LocalDate dueDate, int maxScore) {
        return new Assignment(module, dueDate, maxScore);
    }
    
    public Module getModule() { return module; }
    public LocalDate getDueDate() { return dueDate; }
    public int getMaxScore() { return maxScore; }
    
    public void setModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        this.module = module;
    }
    
    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null || dueDate.isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Invalid due date");
        }
        this.dueDate = dueDate;
    }
    
    public void setMaxScore(int maxScore) {
        if (maxScore <= 0 || maxScore > 100) {
            throw new IllegalArgumentException("Max score must be between 1 and 100");
        }
        this.maxScore = maxScore;
    }
    
    @Override
    public String toString() {
        return String.format("Завдання: модуль '%s', дата виконання: %s, макс. балів: %d", 
                           module.getName(), dueDate, maxScore);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment assignment = (Assignment) obj;
        return maxScore == assignment.maxScore &&
               module.equals(assignment.module) &&
               dueDate.equals(assignment.dueDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(module, dueDate, maxScore);
    }
}