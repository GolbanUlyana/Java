package ua.repository;

import ua.model.Course;

public class CourseIdentityExtractor implements IdentityExtractor<Course> {
    @Override
    public String extractIdentity(Course course) {
        return course.name() + "_" + course.startDate();
    }
}