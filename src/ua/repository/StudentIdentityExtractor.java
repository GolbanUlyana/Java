package ua.repository;

import ua.model.Student;

public class StudentIdentityExtractor implements IdentityExtractor<Student> {
    @Override
    public String extractIdentity(Student student) {
        return student.email(); 
    }
}