package ua.tests;

import java.time.LocalDate;
import java.util.List;
import ua.model.*;
import ua.repository.CourseRepository;
import ua.repository.StudentRepository;
import ua.repository.TeacherRepository;

public class SortingTest {
    
    public static void testSortingOperations() {
        System.out.println("=== ТЕСТУВАННЯ ОПЕРАЦІЙ СОРТУВАННЯ ===");
        
        testTeacherSorting();
        testStudentSorting();
        testCourseSorting();
    }
    
    private static void testTeacherSorting() {
        System.out.println("\n--- Тестування сортування викладачів ---");
        
        TeacherRepository repo = new TeacherRepository();
        
        Teacher t1 = Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
        Teacher t2 = Teacher.createTeacher("Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
        Teacher t3 = Teacher.createTeacher("Марія", "Коваль", LocalDate.of(1985, 3, 20), 5);
        
        repo.add(t1);
        repo.add(t2);
        repo.add(t3);
        
        // Тест сортування за досвідом
        List<Teacher> byExperience = repo.sortByExperienceDesc();
        assert byExperience.get(0).experienceYears() == 20 : "Сортування за досвідом не працює";
        System.out.println("✅ Сортування за досвідом: " + byExperience.get(0).getFullName() + " (" + byExperience.get(0).experienceYears() + " років)");
        
        // Тест сортування за іменем
        List<Teacher> byName = repo.sortByFullName();
        assert byName.get(0).getFullName().contains("Іван") : "Сортування за іменем не працює";
        System.out.println("✅ Сортування за іменем: " + byName.get(0).getFullName());
        
        System.out.println("✅ Сортування викладачів пройдено");
    }
    
    private static void testStudentSorting() {
        System.out.println("\n--- Тестування сортування студентів ---");
        
        StudentRepository repo = new StudentRepository();
        
        Student s1 = Student.createStudent("Марія", "Іванова", 
            LocalDate.of(2000, 3, 20), "maria@example.com", LocalDate.of(2023, 9, 1));
        Student s2 = Student.createStudent("Петро", "Коваленко", 
            LocalDate.of(1999, 12, 5), "petro@test.org", LocalDate.of(2024, 1, 15));
        
        repo.add(s1);
        repo.add(s2);
        
        // Тест сортування за email
        List<Student> byEmail = repo.sortByEmail();
        assert byEmail.get(0).email().contains("maria") : "Сортування за email не працює";
        System.out.println("✅ Сортування за email: " + byEmail.get(0).email());
        
        // Тест сортування за датою зарахування
        List<Student> byEnrollment = repo.sortByEnrollmentDateDesc();
        assert byEnrollment.get(0).enrollmentDate().getYear() == 2024 : "Сортування за датою не працює";
        System.out.println("✅ Сортування за датою зарахування: " + byEnrollment.get(0).enrollmentDate());
        
        System.out.println("✅ Сортування студентів пройдено");
    }
    
    private static void testCourseSorting() {
        System.out.println("\n--- Тестування сортування курсів ---");
        
        CourseRepository repo = new CourseRepository();
        
        Course c1 = Course.createCourse("Java", "Програмування на Java", 5, 
            LocalDate.of(2024, 6, 1), CourseLevel.BEGINNER);
        Course c2 = Course.createCourse("Advanced Java", "Просунута Java", 7, 
            LocalDate.of(2024, 7, 1), CourseLevel.ADVANCED);
        
        repo.add(c1);
        repo.add(c2);
        
        // Тест сортування за кредитами
        List<Course> byCredits = repo.sortByCreditsDesc();
        assert byCredits.get(0).credits() == 7 : "Сортування за кредитами не працює";
        System.out.println("✅ Сортування за кредитами: " + byCredits.get(0).name() + " (" + byCredits.get(0).credits() + " кредитів)");
        
        // Тест сортування за рівнем
        List<Course> byLevel = repo.sortByLevel();
        assert byLevel.get(0).level() == CourseLevel.BEGINNER : "Сортування за рівнем не працює";
        System.out.println("✅ Сортування за рівнем: " + byLevel.get(0).name() + " (" + byLevel.get(0).level().getUkrainianName() + ")");
        
        System.out.println("✅ Сортування курсів пройдено");
    }
}