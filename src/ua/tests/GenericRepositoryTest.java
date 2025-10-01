package ua.tests;

import ua.repository.GenericRepository;
import ua.repository.IdentityExtractor;
import ua.model.Teacher;
import ua.model.Student;
import ua.model.Course;
import ua.model.CourseLevel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GenericRepositoryTest {
    
    public static void testRepositoryOperations() {
        System.out.println("=== ТЕСТУВАННЯ GENERIC REPOSITORY ===");
        
        // Тест 1: Репозиторій для викладачів
        testTeacherRepository();
        
        // Тест 2: Репозиторій для студентів
        testStudentRepository();
        
        // Тест 3: Репозиторій для курсів
        testCourseRepository();
        
        // Тест 4: Тестування дублікатів
        testDuplicateHandling();
        
        // Тест 5: Тестування видалення
        testRemovalOperations();
    }
    
    private static void testTeacherRepository() {
        System.out.println("\n--- Тестування репозиторію викладачів ---");
        
        IdentityExtractor<Teacher> extractor = teacher -> 
            teacher.firstName() + "_" + teacher.lastName();
        
        GenericRepository<Teacher> teacherRepo = new GenericRepository<>(extractor, "Teachers");
        
        Teacher teacher1 = Teacher.createTeacher("Іван", "Петров", 
            LocalDate.of(1980, 5, 15), 15);
        Teacher teacher2 = Teacher.createTeacher("Олена", "Сидорова", 
            LocalDate.of(1975, 8, 10), 20);
        
        // Додавання
        assert teacherRepo.add(teacher1) : "Помилка додавання teacher1";
        assert teacherRepo.add(teacher2) : "Помилка додавання teacher2";
        assert teacherRepo.size() == 2 : "Невірна кількість елементів";
        
        // Пошук
        Optional<Teacher> found = teacherRepo.findByIdentity("Іван_Петров");
        assert found.isPresent() : "Пошук teacher1 не вдався";
        assert found.get().equals(teacher1) : "Знайдено невірний об'єкт";
        
        System.out.println("✅ Тестування репозиторію викладачів пройдено");
    }
    
    private static void testStudentRepository() {
        System.out.println("\n--- Тестування репозиторію студентів ---");
        
        IdentityExtractor<Student> extractor = Student::email;
        
        GenericRepository<Student> studentRepo = new GenericRepository<>(extractor, "Students");
        
        Student student1 = Student.createStudent("Марія", "Іванова",
            LocalDate.of(2000, 3, 20), "maria@example.com", LocalDate.of(2023, 9, 1));
        Student student2 = Student.createStudent("Петро", "Коваленко",
            LocalDate.of(1999, 12, 5), "petro@example.com", LocalDate.of(2023, 9, 1));
        
        // Додавання всіх
        studentRepo.addAll(List.of(student1, student2));
        assert studentRepo.size() == 2 : "Невірна кількість студентів";
        
        // Пошук за email
        Optional<Student> found = studentRepo.findByIdentity("petro@example.com");
        assert found.isPresent() : "Пошук студента за email не вдався";
        
        System.out.println("✅ Тестування репозиторію студентів пройдено");
    }
    
    private static void testCourseRepository() {
        System.out.println("\n--- Тестування репозиторію курсів ---");
        
        IdentityExtractor<Course> extractor = course -> course.name();
        
        GenericRepository<Course> courseRepo = new GenericRepository<>(extractor, "Courses");
        
        Course course1 = Course.createCourse("Java", "Основи Java", 5,
            LocalDate.of(2024, 1, 15), CourseLevel.BEGINNER);
        Course course2 = Course.createCourse("Web", "Веб розробка", 6,
            LocalDate.of(2024, 2, 1), CourseLevel.INTERMEDIATE);
        
        courseRepo.add(course1);
        courseRepo.add(course2);
        
        // Отримання всіх
        List<Course> allCourses = courseRepo.getAll();
        assert allCourses.size() == 2 : "Невірна кількість курсів";
        
        // Пошук за предикатом
        List<Course> advancedCourses = courseRepo.findByPredicate(
            course -> course.level() == CourseLevel.INTERMEDIATE);
        assert advancedCourses.size() == 1 : "Помилка пошуку за предикатом";
        
        System.out.println("✅ Тестування репозиторію курсів пройдено");
    }
    
    private static void testDuplicateHandling() {
        System.out.println("\n--- Тестування обробки дублікатів ---");
        
        IdentityExtractor<Teacher> extractor = teacher -> teacher.firstName();
        
        GenericRepository<Teacher> repo = new GenericRepository<>(extractor, "DuplicateTest");
        
        Teacher teacher1 = Teacher.createTeacher("Іван", "Петров", 
            LocalDate.of(1980, 5, 15), 15);
        Teacher teacher2 = Teacher.createTeacher("Іван", "Сидоров", 
            LocalDate.of(1975, 8, 10), 20); // Такий же firstName
        
        assert repo.add(teacher1) : "Не вдалося додати першого викладача";
        assert !repo.add(teacher2) : "Дублікат не повинен бути доданий";
        assert repo.size() == 1 : "Репозиторій повинен містити лише 1 елемент";
        
        System.out.println("✅ Тестування обробки дублікатів пройдено");
    }
    
    private static void testRemovalOperations() {
        System.out.println("\n--- Тестування операцій видалення ---");
        
        IdentityExtractor<Student> extractor = Student::email;
        
        GenericRepository<Student> repo = new GenericRepository<>(extractor, "RemovalTest");
        
        Student student = Student.createStudent("Марія", "Іванова",
            LocalDate.of(2000, 3, 20), "maria@example.com", LocalDate.of(2023, 9, 1));
        
        repo.add(student);
        assert repo.size() == 1 : "Студент не доданий";
        
        // Видалення за identity
        assert repo.removeByIdentity("maria@example.com") : "Видалення за identity не вдалося";
        assert repo.isEmpty() : "Репозиторій повинен бути порожнім";
        
        // Видалення неіснуючого
        assert !repo.removeByIdentity("nonexistent@example.com") : "Видалення неіснуючого не повинно вдатися";
        
        System.out.println("✅ Тестування операцій видалення пройдено");
    }
}