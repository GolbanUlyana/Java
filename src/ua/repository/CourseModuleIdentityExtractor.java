package ua.repository;

import ua.model.CourseModule;

public class CourseModuleIdentityExtractor implements IdentityExtractor<CourseModule> {
    @Override
    public String extractIdentity(CourseModule module) {
        return module.name();
    }
}