package ua.tests;

import ua.repository.*;
import ua.model.*;
import java.time.LocalDate;
import java.util.List;

public class SortingTest {
    
    public static void testAllSorting() {
        System.out.println("=== ТЕСТУВАННЯ СОРТУВАНЬ ===");
        
        testTeacherSorting();
        testStudentSorting();
        testCourseSorting();
        testModuleSorting();
        testGenericRepositorySorting();
    }
    
    private static void testTeacherSorting() {
        System.out.println("\n--- Тестування сортування викладачів ---");
        
        TeacherRepository repo = new TeacherRepository();
        
        Teacher t1 = Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
        Teacher t2 = Teacher.createTeacher("Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
        Teacher t3 = Teacher.createTeacher("Марія", "Коваль", LocalDate.of(1985, 12, 20), 8);
        
        repo.addAll(List.of(t1, t2, t3));
        
        // Тест сортування за досвідом
        List<Teacher> byExp = repo.sortByExperienceDesc();
        assert byExp.get(0).experienceYears() == 20 : "Сортування за досвідом не працює";
        System.out.println("✅ Сортування викладачів за досвідом працює");
        
        // Тест сортування за іменами
        List<Teacher> byName = repo.sortByFullName();
        assert byName.get(0).lastName().equals("Коваль") : "Сортування за іменами не працює";
        System.out.println("✅ Сортування викладачів за іменами працює");
    }
    
    private static void testStudentSorting() {
        System.out.println("\n--- Тестування сортування студентів ---");
        
        StudentRepository repo = new StudentRepository();
        
        Student s1 = Student.createStudent("Марія", "Іванова", LocalDate.of(2000, 3, 20),
            "maria@example.com", LocalDate.of(2023, 9, 1));
        Student s2 = Student.createStudent("Петро", "Коваленко", LocalDate.of(1999, 12, 5),
            "petro@example.com", LocalDate.of(2023, 9, 1));
        Student s3 = Student.createStudent("Олександр", "Бондаренко", LocalDate.of(2001, 7, 15),
            "olexandr@example.com", LocalDate.of(2024, 1, 15));
        
        repo.addAll(List.of(s1, s2, s3));
        
        // Тест сортування за email
        List<Student> byEmail = repo.sortByEmail();
        assert byEmail.get(0).email().equals("maria@example.com") : "Сортування за email не працює";
        System.out.println("✅ Сортування студентів за email працює");
        
        // Тест сортування за датою зарахування
        List<Student> byEnrollment = repo.sortByEnrollmentDateDesc();
        System.out.println("✅ Сортування студентів за датою зарахування працює");
    }
    
    private static void testCourseSorting() {
        System.out.println("\n--- Тестування сортування курсів ---");
        
        CourseRepository repo = new CourseRepository();
        
        Course c1 = Course.createCourse("Java", "Основи Java", 5, 
            LocalDate.of(2024, 1, 15), CourseLevel.BEGINNER);
        Course c2 = Course.createCourse("Web", "Веб розробка", 6,
            LocalDate.of(2024, 2, 1), CourseLevel.INTERMEDIATE);
        Course c3 = Course.createCourse("Data Science", "Аналіз даних", 7,
            LocalDate.of(2024, 3, 1), CourseLevel.ADVANCED);
        
        repo.addAll(List.of(c1, c2, c3));
        
        // Тест сортування за кредитами
        List<Course> byCredits = repo.sortByCreditsDesc();
        assert byCredits.get(0).credits() == 7 : "Сортування за кредитами не працює";
        System.out.println("✅ Сортування курсів за кредитами працює");
        
        // Тест сортування за рівнем
        List<Course> byLevel = repo.sortByLevel();
        assert byLevel.get(0).level() == CourseLevel.BEGINNER : "Сортування за рівнем не працює";
        System.out.println("✅ Сортування курсів за рівнем працює");
    }
    
    private static void testModuleSorting() {
        System.out.println("\n--- Тестування сортування модулів ---");
        
        CourseModuleRepository repo = new CourseModuleRepository();
        
        CourseModule m1 = CourseModule.createModule("Java Basics", "Основи Java", 10);
        CourseModule m2 = CourseModule.createModule("OOP", "Об'єктно-орієнтоване програмування", 15);
        CourseModule m3 = CourseModule.createModule("Вступ", "Вступ до програмування", 5);
        
        repo.addAll(List.of(m1, m2, m3));
        
        // Тест сортування за тривалістю
        List<CourseModule> byDuration = repo.sortByDurationAsc();
        assert byDuration.get(0).durationHours() == 5 : "Сортування за тривалістю не працює";
        System.out.println("✅ Сортування модулів за тривалістю працює");
    }
    
    private static void testGenericRepositorySorting() {
        System.out.println("\n--- Тестування базового сортування GenericRepository ---");
        
        GenericRepository<Teacher> repo = new GenericRepository<>(
            new TeacherIdentityExtractor(), "TestRepo");
        
        Teacher t1 = Teacher.createTeacher("Богдан", "Антоненко", LocalDate.of(1980, 5, 15), 15);
        Teacher t2 = Teacher.createTeacher("Андрій", "Броваренко", LocalDate.of(1975, 8, 10), 20);
        Teacher t3 = Teacher.createTeacher("Віктор", "Василенко", LocalDate.of(1985, 12, 20), 8);
        
        repo.addAll(List.of(t1, t2, t3));
        
        // Тест сортування за identity
        List<Teacher> byIdentityAsc = repo.sortByIdentity("asc");
        assert byIdentityAsc.get(0).firstName().equals("Андрій") : "Сортування за identity не працює";
        System.out.println("✅ Сортування за identity працює");
        
        // Тест натурального сортування
        List<Teacher> natural = repo.sortNatural();
        assert natural.get(0).lastName().equals("Антоненко") : "Натуральне сортування не працює";
        System.out.println("✅ Натуральне сортування працює");
    }
}