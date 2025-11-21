package ua.model;

import java.time.LocalDate;
import ua.validators.TeacherValidator;

public class Teacher extends Person {
    private int experienceYears;
    
    public Teacher(String firstName, String lastName, LocalDate birthDate, int experienceYears) {
        super(firstName, lastName, birthDate);
        TeacherValidator.validateTeacherData(experienceYears);
        this.experienceYears = experienceYears;
    }
    
    // ДОДАЙТЕ ЦЕЙ FACTORY МЕТОД
    public static Teacher createTeacher(String firstName, String lastName, 
                                       LocalDate birthDate, int experienceYears) {
        return new Teacher(firstName, lastName, birthDate, experienceYears);
    }
    
    public int getExperienceYears() { return experienceYears; }
    
    public void setExperienceYears(int experienceYears) {
        TeacherValidator.validateTeacherData(experienceYears);
        this.experienceYears = experienceYears;
    }
    
    @Override
    public String toString() {
        return String.format("Викладач: %s %s, досвід: %d років", 
                           firstName, lastName, experienceYears);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return experienceYears == teacher.experienceYears &&
               firstName.equals(teacher.firstName) &&
               lastName.equals(teacher.lastName) &&
               birthDate.equals(teacher.birthDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstName, lastName, birthDate, experienceYears);
    }
}