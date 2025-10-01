package ua.model;

import ua.util.Utils;
import ua.util.ValidationHelper;
import java.time.LocalDate;

public class Student extends Person {
    private String email;
    private LocalDate enrollmentDate;
    
    // Конструктор
    public Student(String firstName, String lastName, LocalDate birthDate, 
                  String email, LocalDate enrollmentDate) {
        super(firstName, lastName, birthDate);
        setEmail(email);
        setEnrollmentDate(enrollmentDate);
    }
    
    // Factory метод
    public static Student createStudent(String firstName, String lastName, LocalDate birthDate,
                                       String email, LocalDate enrollmentDate) {
        return new Student(firstName, lastName, birthDate, email, enrollmentDate);
    }
    
    // Getter/Setter
    public String getEmail() { return email; }
    
    public void setEmail(String email) {
        if (!ua.util.ValidationHelper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }
    
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        if (!ua.util.ValidationHelper.isValidDate(enrollmentDate)) {
            throw new IllegalArgumentException("Invalid enrollment date");
        }
        this.enrollmentDate = enrollmentDate;
    }
    
    public String getFormattedName() {
        return Utils.formatName(firstName, lastName);
    }
    
    @Override
    public String toString() {
        return String.format("Студент: %s %s, email: %s, дата зарахування: %s", 
                           firstName, lastName, email, enrollmentDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return email.equals(student.email) &&
               firstName.equals(student.firstName) &&
               lastName.equals(student.lastName) &&
               birthDate.equals(student.birthDate) &&
               enrollmentDate.equals(student.enrollmentDate);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstName, lastName, birthDate, email, enrollmentDate);
    }
}