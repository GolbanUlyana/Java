package ua.tests;

import ua.repository.TeacherRepository;
import ua.repository.StudentRepository;
import ua.repository.CourseRepository;
import ua.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SpecializedRepositoryTest {
    
    public static void testSpecializedRepositories() {
        System.out.println("=== ТЕСТУВАННЯ СПЕЦІАЛІЗОВАНИХ РЕПОЗИТОРІЇВ ===");
        
        testTeacherRepository();
        testStudentRepository();
        testCourseRepository();
        testStreamOperations();
    }
    
    private static void testTeacherRepository() {
        System.out.println("\n--- Тестування TeacherRepository ---");
        
        TeacherRepository repo = new TeacherRepository();
        
        Teacher t1 = Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
        Teacher t2 = Teacher.createTeacher("Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
        Teacher t3 = Teacher.createTeacher("Петро", "Петренко", LocalDate.of(1985, 3, 20), 5);
        
        repo.add(t1);
        repo.add(t2);
        repo.add(t3);
        
        // Тест пошуку за досвідом
        List<Teacher> experienced = repo.findByExperienceRange(10, 25);
        assert experienced.size() == 2 : "Пошук за досвідом не працює";
        
        // Тест пошуку за прізвищем
        List<Teacher> petrovs = repo.findByLastNameContains("Петр");
        assert petrovs.size() == 2 : "Пошук за прізвищем не працює";
        
        System.out.println("✅ TeacherRepository тести пройдено");
    }
    
    private static void testStudentRepository() {
        System.out.println("\n--- Тестування StudentRepository ---");
        
        StudentRepository repo = new StudentRepository();
        
        Student s1 = Student.createStudent("Марія", "Іванова", 
            LocalDate.of(2000, 3, 20), "maria@example.com", LocalDate.of(2023, 9, 1));
        Student s2 = Student.createStudent("Петро", "Коваленко", 
            LocalDate.of(1999, 12, 5), "petro@test.org", LocalDate.of(2024, 1, 15));
        
        repo.add(s1);
        repo.add(s2);
        
        // Тест пошуку за доменом email
        List<Student> exampleStudents = repo.findByEmailDomain("example.com");
        assert exampleStudents.size() == 1 : "Пошук за доменом не працює";
        
        // Тест групування
        Map<Integer, List<Student>> byYear = repo.groupStudentsByEnrollmentYear();
        assert byYear.size() == 2 : "Групування за роком не працює";
        
        System.out.println("✅ StudentRepository тести пройдено");
    }
    
    private static void testCourseRepository() {
        System.out.println("\n--- Тестування CourseRepository ---");
        
        CourseRepository repo = new CourseRepository();
        
        Course c1 = Course.createCourse("Java", "Програмування на Java", 5, 
            LocalDate.of(2024, 6, 1), CourseLevel.BEGINNER);
        Course c2 = Course.createCourse("Advanced Java", "Просунута Java", 7, 
            LocalDate.of(2024, 7, 1), CourseLevel.ADVANCED);
        
        repo.add(c1);
        repo.add(c2);
        
        // Тест пошуку за рівнем
        List<Course> beginnerCourses = repo.findByLevel(CourseLevel.BEGINNER);
        assert beginnerCourses.size() == 1 : "Пошук за рівнем не працює";
        
        // Тест пошуку за ключовим словом
        List<Course> javaCourses = repo.findByKeyword("java");
        assert javaCourses.size() == 2 : "Пошук за ключовим словом не працює";
        
        System.out.println("✅ CourseRepository тести пройдено");
    }
    
    private static void testStreamOperations() {
        System.out.println("\n--- Тестування Stream API операцій ---");
        
        TeacherRepository teacherRepo = new TeacherRepository();
        
        Teacher t1 = Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
        Teacher t2 = Teacher.createTeacher("Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
        teacherRepo.add(t1);
        teacherRepo.add(t2);
        
        // Тест термінальних операцій
        List<Teacher> allTeachers = teacherRepo.getAll();
        
        // collect
        List<String> names = allTeachers.stream()
                .map(Teacher::getFullName)
                .collect(java.util.stream.Collectors.toList());
        assert names.size() == 2 : "Операція collect не працює";
        
        // forEach
        allTeachers.forEach(teacher -> {
            assert teacher != null : "forEach не працює";
        });
        
        // reduce
        int totalExperience = allTeachers.stream()
                .mapToInt(Teacher::experienceYears)
                .reduce(0, Integer::sum);
        assert totalExperience == 35 : "Операція reduce не працює";
        
        System.out.println("✅ Stream API тести пройдено");
    }
}