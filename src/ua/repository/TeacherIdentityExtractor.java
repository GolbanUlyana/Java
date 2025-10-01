package ua.repository;

import ua.model.Teacher;

public class TeacherIdentityExtractor implements IdentityExtractor<Teacher> {
    @Override
    public String extractIdentity(Teacher teacher) {
       
        return teacher.firstName() + "_" + teacher.lastName() + "_" + teacher.birthDate();
    }
}