package ua.model;

import java.time.LocalDate;

public class Teacher extends Person {
    private int experienceYears;
    
    // Конструктор
    public Teacher(String firstName, String lastName, LocalDate birthDate, int experienceYears) {
        super(firstName, lastName, birthDate);
        setExperienceYears(experienceYears);
    }
    
    // Factory метод
    public static Teacher createTeacher(String firstName, String lastName, 
                                       LocalDate birthDate, int experienceYears) {
        return new Teacher(firstName, lastName, birthDate, experienceYears);
    }
    
    // Getter/Setter
    public int getExperienceYears() { return experienceYears; }
    
    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0 || experienceYears > 60) {
            throw new IllegalArgumentException("Experience years must be between 0 and 60");
        }
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