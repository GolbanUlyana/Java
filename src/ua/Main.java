package ua;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ua.exceptions.InvalidDataException;
import ua.model.*;
import ua.repository.*;
import ua.tests.CourseTest;
import ua.tests.GenericRepositoryTest;
import ua.tests.StudentTest;
import ua.tests.TeacherTest;
import ua.util.*;

public class Main {
    public static void main(String[] args) {
        Logger.info("=== ПРОГРАМА ОНЛАЙН-КУРСІВ З GENERIC REPOSITORY ЗАПУЩЕНА ===");
        
        try {
            // 1. Демонстрація читання даних з файлів
            System.out.println("\n1. ЧИТАННЯ ДАНИХ З ФАЙЛІВ:");
            List<Teacher> teachers = demonstrateFileReading();
            List<Student> students = readStudentsFromFile();
            List<Course> courses = readCoursesFromFile();
            
            // 2. Демонстрація GenericRepository
            System.out.println("\n2. GENERIC REPOSITORY ДЕМОНСТРАЦІЯ:");
            demonstrateGenericRepository(teachers, students, courses);
            
            // 3. Демонстрація enum
            System.out.println("\n3. ДЕМОНСТРАЦІЯ ENUM:");
            demonstrateEnums();
            
            // 4. Демонстрація record
            System.out.println("\n4. ДЕМОНСТРАЦІЯ RECORD:");
            demonstrateRecords();
            
            // 5. Демонстрація switch expressions
            System.out.println("\n5. SWITCH EXPRESSIONS:");
            demonstrateSwitchExpressions();
            
            // 6. Демонстрація роботи з структурованими даними
            System.out.println("\n6. СТРУКТУРОВАНІ ДАНІ:");
            demonstrateStructuredData();
            
            // 7. Демонстрація обробки винятків
            System.out.println("\n7. ОБРОБКА ВИНЯТКІВ:");
            demonstrateExceptionHandling();
            
            // 8. Демонстрація тестування
            System.out.println("\n8. ДЕМОНСТРАЦІЯ ТЕСТУВАННЯ:");
            demonstrateTesting();
            
            // 9. Демонстрація логування
            System.out.println("\n9. ДЕМОНСТРАЦІЯ ЛОГУВАННЯ:");
            demonstrateLogging();
            
            Logger.info("=== ПРОГРАМА УСПІШНО ЗАВЕРШЕНА ===");
            
        } catch (Exception e) {
            Logger.error("Критична помилка програми: " + e.getMessage());
            System.out.println("❌ Програма завершилася з помилкою: " + e.getMessage());
        }
    }
    
    private static List<Teacher> demonstrateFileReading() {
        Logger.info("Спроба читання даних з файлів");
        
        try {
            List<Teacher> teachers = FileDataReader.readTeachersFromFile("teachers.csv");
            System.out.println("✅ Успішно прочитано викладачів: " + teachers.size());
            
            if (!teachers.isEmpty()) {
                System.out.println("   Перший викладач: " + teachers.get(0));
            }
            return teachers;
            
        } catch (InvalidDataException e) {
            System.out.println("⚠️ Файли не знайдено або містять помилки. Використовуються тестові дані.");
            Logger.warning("Використання тестових даних для викладачів: " + e.getMessage());
            return createTestTeachers();
        }
    }
    
    private static List<Student> readStudentsFromFile() {
        try {
            List<Student> students = FileDataReader.readStudentsFromFile("students.csv");
            System.out.println("✅ Успішно прочитано студентів: " + students.size());
            
            if (!students.isEmpty()) {
                System.out.println("   Перший студент: " + students.get(0));
            }
            return students;
            
        } catch (InvalidDataException e) {
            System.out.println("⚠️ Використання тестових даних для студентів");
            return createTestStudents();
        }
    }
    
    private static List<Course> readCoursesFromFile() {
        try {
            List<Course> courses = FileDataReader.readCoursesFromFile("courses.csv");
            System.out.println("✅ Успішно прочитано курсів: " + courses.size());
            
            if (!courses.isEmpty()) {
                System.out.println("   Перший курс: " + courses.get(0));
            }
            return courses;
            
        } catch (InvalidDataException e) {
            System.out.println("⚠️ Використання тестових даних для курсів");
            return createTestCourses();
        }
    }
    
    private static void demonstrateGenericRepository(List<Teacher> teachers, List<Student> students, List<Course> courses) {
        Logger.info("Демонстрація роботи GenericRepository");
        
        // Створення репозиторіїв з різними identity extractors
        GenericRepository<Teacher> teacherRepo = new GenericRepository<>(
            new TeacherIdentityExtractor(), "Викладачі");
        
        GenericRepository<Student> studentRepo = new GenericRepository<>(
            new StudentIdentityExtractor(), "Студенти");
        
        GenericRepository<Course> courseRepo = new GenericRepository<>(
            new CourseIdentityExtractor(), "Курси");
        
        GenericRepository<CourseModule> moduleRepo = new GenericRepository<>(
            new CourseModuleIdentityExtractor(), "Модулі");
        
        // Додавання даних у репозиторії
        System.out.println("Додавання даних до репозиторіїв:");
        teacherRepo.addAll(teachers);
        studentRepo.addAll(students);
        courseRepo.addAll(courses);
        
        // Додавання модулів
        CourseModule module1 = CourseModule.createModule("Java Basics", "Основи Java", 10);
        CourseModule module2 = CourseModule.createModule("OOP", "Об'єктно-орієнтоване програмування", 15);
        moduleRepo.add(module1);
        moduleRepo.add(module2);
        
        System.out.println("✅ Розміри репозиторіїв:");
        System.out.println("   - Викладачі: " + teacherRepo.size());
        System.out.println("   - Студенти: " + studentRepo.size());
        System.out.println("   - Курси: " + courseRepo.size());
        System.out.println("   - Модулі: " + moduleRepo.size());
        
        // Демонстрація пошуку за identity
        System.out.println("\n--- Пошук за identity ---");
        demonstrateIdentitySearch(teacherRepo, studentRepo, courseRepo, moduleRepo);
        
        // Демонстрація роботи з дублікатами
        System.out.println("\n--- Обробка дублікатів ---");
        demonstrateDuplicateHandling(teacherRepo, studentRepo);
        
        // Демонстрація операцій видалення
        System.out.println("\n--- Операції видалення ---");
        demonstrateRemovalOperations(studentRepo, courseRepo);
        
        // Демонстрація пошуку за предикатом
        System.out.println("\n--- Пошук за предикатом ---");
        demonstratePredicateSearch(courseRepo, teacherRepo);
        
        // Демонстрація отримання всіх елементів
        System.out.println("\n--- Отримання всіх елементів ---");
        demonstrateGetAllOperations(teacherRepo, studentRepo, courseRepo);
    }
    
    private static void demonstrateIdentitySearch(GenericRepository<Teacher> teacherRepo, 
                                                 GenericRepository<Student> studentRepo,
                                                 GenericRepository<Course> courseRepo,
                                                 GenericRepository<CourseModule> moduleRepo) {
        // Пошук викладача
        Optional<Teacher> teacher = teacherRepo.findByIdentity("Іван_Петров_1980-05-15");
        teacher.ifPresentOrElse(
            t -> System.out.println("✅ Знайдено викладача: " + t.getFullName()),
            () -> System.out.println("❌ Викладача не знайдено")
        );
        
        // Пошук студента
        Optional<Student> student = studentRepo.findByIdentity("maria@example.com");
        student.ifPresentOrElse(
            s -> System.out.println("✅ Знайдено студента: " + s.getFormattedName()),
            () -> System.out.println("❌ Студента не знайдено")
        );
        
        // Пошук курсу
        Optional<Course> course = courseRepo.findByIdentity("Java Програмування_2024-01-15");
        course.ifPresentOrElse(
            c -> System.out.println("✅ Знайдено курс: " + c.name()),
            () -> System.out.println("❌ Курсу не знайдено")
        );
        
        // Пошук модуля
        Optional<CourseModule> module = moduleRepo.findByIdentity("Java Basics");
        module.ifPresentOrElse(
            m -> System.out.println("✅ Знайдено модуль: " + m.name()),
            () -> System.out.println("❌ Модуля не знайдено")
        );
        
        // Пошук неіснуючого
        Optional<Teacher> nonExistent = teacherRepo.findByIdentity("Неіснуючий_Викладач");
        if (nonExistent.isEmpty()) {
            System.out.println("✅ Пошук неіснуючого коректно оброблений");
        }
    }
    
    private static void demonstrateDuplicateHandling(GenericRepository<Teacher> teacherRepo, 
                                                    GenericRepository<Student> studentRepo) {
        try {
            // Спроба додати дублікат викладача
            Teacher duplicateTeacher = Teacher.createTeacher("Іван", "Петров", 
                LocalDate.of(1980, 5, 15), 15);
            
            boolean teacherAdded = teacherRepo.add(duplicateTeacher);
            System.out.println(teacherAdded ? "❌ Дублікат викладача не повинен бути доданий" : 
                                            "✅ Дублікат викладача коректно відхилено");
            
            // Спроба додати дублікат студента
            Student duplicateStudent = Student.createStudent("Марія", "Іванова",
                LocalDate.of(2000, 3, 20), "maria@example.com", LocalDate.of(2023, 9, 1));
            
            boolean studentAdded = studentRepo.add(duplicateStudent);
            System.out.println(studentAdded ? "❌ Дублікат студента не повинен бути доданий" : 
                                            "✅ Дублікат студента коректно відхилено");
            
        } catch (Exception e) {
            System.out.println("⚠️ Помилка створення дубліката: " + e.getMessage());
        }
    }
    
    private static void demonstrateRemovalOperations(GenericRepository<Student> studentRepo,
                                                    GenericRepository<Course> courseRepo) {
        System.out.println("Розмір репозиторію студентів до видалення: " + studentRepo.size());
        
        // Видалення за identity
        boolean removed = studentRepo.removeByIdentity("maria@example.com");
        System.out.println(removed ? "✅ Студент успішно видалений" : "❌ Помилка видалення студента");
        System.out.println("Розмір після видалення: " + studentRepo.size());
        
        // Видалення безпосередньо об'єкта
        Optional<Course> courseToRemove = courseRepo.findByIdentity("Web Розробка_2024-02-01");
        if (courseToRemove.isPresent()) {
            boolean courseRemoved = courseRepo.remove(courseToRemove.get());
            System.out.println(courseRemoved ? "✅ Курс успішно видалений" : "❌ Помилка видалення курсу");
        }
        
        // Спроба видалити неіснуючого
        boolean removedNonExistent = studentRepo.removeByIdentity("nonexistent@example.com");
        System.out.println(!removedNonExistent ? "✅ Спроба видалити неіснуючого коректно оброблена" : 
                                              "❌ Неіснуючий студент не повинен бути видалений");
    }
    
    private static void demonstratePredicateSearch(GenericRepository<Course> courseRepo,
                                                  GenericRepository<Teacher> teacherRepo) {
        // Пошук курсів для початківців
        List<Course> beginnerCourses = courseRepo.findByPredicate(
            course -> course.level() == CourseLevel.BEGINNER);
        System.out.println("✅ Знайдено курсів для початківців: " + beginnerCourses.size());
        
        // Пошук курсів з більше ніж 5 кредитами
        List<Course> advancedCreditCourses = courseRepo.findByPredicate(
            course -> course.credits() > 5);
        System.out.println("✅ Знайдено курсів з >5 кредитами: " + advancedCreditCourses.size());
        
        // Пошук досвідчених викладачів
        List<Teacher> experiencedTeachers = teacherRepo.findByPredicate(
            teacher -> teacher.experienceYears() >= 10);
        System.out.println("✅ Знайдено досвідчених викладачів (≥10 років): " + experiencedTeachers.size());
    }
    
    private static void demonstrateGetAllOperations(GenericRepository<Teacher> teacherRepo,
                                                   GenericRepository<Student> studentRepo,
                                                   GenericRepository<Course> courseRepo) {
        System.out.println("Отримання всіх елементів з репозиторіїв:");
        
        List<Teacher> allTeachers = teacherRepo.getAll();
        List<Student> allStudents = studentRepo.getAll();
        List<Course> allCourses = courseRepo.getAll();
        
        System.out.println("✅ Всі викладачі (" + allTeachers.size() + "):");
        allTeachers.forEach(t -> System.out.println("   - " + t.getFullName()));
        
        System.out.println("✅ Всі студенти (" + allStudents.size() + "):");
        allStudents.forEach(s -> System.out.println("   - " + s.getFormattedName()));
        
        System.out.println("✅ Всі курси (" + allCourses.size() + "):");
        allCourses.forEach(c -> System.out.println("   - " + c.name() + " (" + c.level().getUkrainianName() + ")"));
    }
    
    private static void demonstrateEnums() {
        Logger.info("Демонстрація роботи з enum");
        
        System.out.println("Рівні курсів:");
        for (CourseLevel level : CourseLevel.values()) {
            System.out.printf("   - %s: %s (рекомендовано %d кредитів)%n",
                            level, level.getUkrainianName(), level.getRecommendedCredits());
        }
        
        System.out.println("\nТипи завдань:");
        for (AssignmentType type : AssignmentType.values()) {
            System.out.printf("   - %s: макс. %d балів, оцінюється: %s, групова робота: %s%n",
                            type.getUkrainianName(), type.getMaxScore(), type.isGraded(),
                            type == AssignmentType.PROJECT ? "дозволена" : "заборонена");
        }
    }
    
    private static void demonstrateRecords() {
        Logger.info("Демонстрація record класів");
        
        try {
            Teacher teacher = Teacher.createTeacher("Олена", "Сидорова", 
                LocalDate.of(1975, 8, 10), 20);
            Student student = Student.createStudent("Петро", "Коваленко", 
                LocalDate.of(1999, 12, 5), "petro@example.com", LocalDate.of(2023, 9, 1));
            CourseModule module = CourseModule.createModule("Java Basics", 
                "Основи програмування на Java", 10);
            
            System.out.println("Record об'єкти:");
            System.out.println("   - " + teacher);
            System.out.println("   - " + student);
            System.out.println("   - " + module);
            
            System.out.println("\nДодаткові методи record:");
            System.out.println("   - Викладач досвідчений: " + teacher.isExperienced());
            System.out.println("   - Тривалість навчання: " + student.getStudyDurationMonths() + " місяців");
            System.out.println("   - Активний студент: " + student.isActiveStudent());
            System.out.println("   - Рівень складності модуля: " + module.getDifficultyLevel());
            System.out.println("   - Модуль містить відео: " + module.hasVideoContent());
            
        } catch (Exception e) {
            System.out.println("❌ Помилка створення record об'єктів: " + e.getMessage());
        }
    }
    
    private static void demonstrateSwitchExpressions() {
        Logger.info("Демонстрація switch expressions");
        
        try {
            Course javaCourse = Course.createCourse("Java Pro", "Просунута Java", 7, 
                LocalDate.now().plusMonths(1), CourseLevel.ADVANCED);
            CourseModule advancedModule = CourseModule.createModule("Multithreading", 
                "Багатопоточність у Java", 20);
            Assignment project = Assignment.createAssignment(advancedModule, 
                LocalDate.now().plusDays(30), 100, AssignmentType.PROJECT);
            
            System.out.println("Switch expressions в дії:");
            System.out.println("   - Опис курсу: " + javaCourse.getLevelDescription());
            System.out.println("   - Інструкції з здачі: " + project.getSubmissionInstructions());
            System.out.println("   - Групова робота дозволена: " + project.allowsGroupWork());
            
            // Складний switch expression для рекомендацій
            String courseRecommendation = switch(javaCourse.level()) {
                case BEGINNER -> "Рекомендується початківцям без попереднього досвіду";
                case INTERMEDIATE -> "Потрібні базові знання програмування";
                case ADVANCED -> {
                    String msg = "Необхідний досвід роботи з Java";
                    msg += " та знання ООП принципів";
                    yield msg;
                }
            };
            System.out.println("   - Рекомендація: " + courseRecommendation);
            
            // Switch expression для тривалості модуля
            String moduleDuration = switch(advancedModule.durationHours()) {
                case 1, 2, 3, 4, 5 -> "Короткий модуль (1-5 годин)";
                case 6, 7, 8, 9, 10 -> "Середній модуль (6-10 годин)";
                case 11, 12, 13, 14, 15 -> "Тривалий модуль (11-15 годин)";
                default -> "Інтенсивний модуль (16+ годин)";
            };
            System.out.println("   - Тривалість модуля: " + moduleDuration);
            
        } catch (Exception e) {
            System.out.println("❌ Помилка демонстрації switch expressions: " + e.getMessage());
        }
    }
    
    private static void demonstrateStructuredData() {
        Logger.info("Демонстрація роботи зі структурованими даними");
        
        try {
            // Створення комплексної структури даних
            Teacher teacher = Teacher.createTeacher("Олена", "Сидорова", 
                LocalDate.of(1975, 8, 10), 20);
            Student student = Student.createStudent("Петро", "Коваленко", 
                LocalDate.of(1999, 12, 5), "petro@example.com", LocalDate.of(2023, 9, 1));
            
            CourseModule[] modules = {
                CourseModule.createModule("Вступ", "Основи програмування, включаючи відео матеріали", 5),
                CourseModule.createModule("ООП", "Об'єктно-орієнтоване програмування", 15),
                CourseModule.createModule("Бази даних", "Робота з SQL та NoSQL", 12),
                CourseModule.createModule("Веб розробка", "HTML, CSS, JavaScript", 18)
            };
            
            Course course = Course.createCourse("FullStack Development", 
                "Повний цикл розробки веб-додатків", 6,
                LocalDate.now().plusMonths(2), CourseLevel.INTERMEDIATE);
            
            System.out.println("Комплексна структура даних:");
            System.out.println("   - Курс: " + course);
            System.out.println("   - Викладач: " + teacher.getFullName() + 
                             " (досвід: " + teacher.experienceYears() + " років)");
            System.out.println("   - Студент: " + student.getFormattedName() + 
                             " (навчається: " + student.getStudyDurationMonths() + " місяців)");
            System.out.println("   - Активний студент: " + student.isActiveStudent());
            
            System.out.println("\nМодулі курсу:");
            for (CourseModule module : modules) {
                String hasVideo = module.hasVideoContent() ? " ✅ (з відео)" : " ❌ (без відео)";
                System.out.println("   - " + module.name() + " (" + module.durationHours() + " год.)" + hasVideo);
            }
            
            System.out.println("\nКласифікація модулів за тривалістю:");
            for (CourseModule module : modules) {
                String classification = switch(module.durationHours()) {
                    case 1, 2, 3, 4, 5 -> "🟢 Легкий";
                    case 6, 7, 8, 9, 10 -> "🟡 Середній";
                    case 11, 12, 13, 14, 15 -> "🟠 Складний";
                    default -> "🔴 Дуже складний";
                };
                System.out.println("   - " + module.name() + ": " + classification + 
                                 " (" + module.durationHours() + " год.)");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Помилка створення структурованих даних: " + e.getMessage());
        }
    }
    
    private static void demonstrateExceptionHandling() {
        Logger.info("Демонстрація обробки винятків");
        
        System.out.println("Тестування обробки некоректних даних:");
        
        // Multi-catch блок для різних типів помилок
        try {
            Teacher invalidTeacher = Teacher.createTeacher("", "Петров", 
                LocalDate.of(1980, 5, 15), 15);
            System.out.println("❌ Очікувалась помилка для викладача");
        } catch (Exception e) {
            System.out.println("✅ Виключення перехоплено: " + e.getMessage());
        }
        
        try {
            Student invalidStudent = Student.createStudent("Марія", "Іванова", 
                LocalDate.of(2000, 3, 20), "invalid-email", LocalDate.of(2023, 9, 1));
            System.out.println("❌ Очікувалась помилка для студента");
        } catch (Exception e) {
            System.out.println("✅ Виключення перехоплено: " + e.getMessage());
        }
        
        try {
            Course invalidCourse = Course.createCourse("", "Опис", 15, 
                LocalDate.of(2020, 1, 15), CourseLevel.BEGINNER);
            System.out.println("❌ Очікувалась помилка для курсу");
        } catch (Exception e) {
            System.out.println("✅ Виключення перехоплено: " + e.getMessage());
        }
        
        // Try-catch-finally демонстрація
        System.out.println("\nДемонстрація try-catch-finally:");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader("тестові дані для демонстрації"));
            String line = reader.readLine();
            System.out.println("✅ Успішно прочитано: " + line);
            
            // Симуляція помилки
            throw new IOException("Тестова помилка вводу-виводу для демонстрації");
            
        } catch (IOException e) {
            System.out.println("✅ Перехоплено IOException: " + e.getMessage());
        } finally {
            // Гарантоване виконання
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("✅ Ресурс успішно закрито в finally блоці");
                } catch (IOException e) {
                    System.out.println("⚠️ Помилка закриття ресурсу: " + e.getMessage());
                }
            }
        }
    }
    
    private static void demonstrateTesting() {
        Logger.info("Демонстрація тестування");
        
        try {
            System.out.println("Запуск базових тестів...");
            
            // Запуск окремих тестів
            TeacherTest.testTeacherCreation();
            StudentTest.testStudentCreation();
            CourseTest.testCourseCreation();
            
            // Запуск тестів GenericRepository
            GenericRepositoryTest.testRepositoryOperations();
            
            System.out.println("✅ Всі базові тести пройдено успішно!");
            
        } catch (Exception e) {
            System.out.println("❌ Помилка тестування: " + e.getMessage());
            Logger.error("Помилка під час тестування: " + e.getMessage());
        }
    }
    
    private static void demonstrateLogging() {
        Logger.info("Демонстрація системи логування");
        
        System.out.println("Рівні логування в дії:");
        
        Logger.debug("Це debug повідомлення (для розробників)");
        Logger.info("Це info повідомлення (інформаційне)");
        Logger.warning("Це warning повідомлення (попередження)");
        Logger.error("Це error повідомлення (помилка)");
        
        // Демонстрація логування різних подій
        Logger.info("Створення нового курсу");
        try {
            Course newCourse = Course.createCourse("Новий курс", "Опис нового курсу", 4,
                LocalDate.now().plusMonths(3), CourseLevel.INTERMEDIATE);
            Logger.info("Курс успішно створено: " + newCourse.name());
            
            // Логування операцій з репозиторієм
            GenericRepository<Course> testRepo = new GenericRepository<>(
                new CourseIdentityExtractor(), "Тестовий репозиторій");
            testRepo.add(newCourse);
            Logger.info("Курс додано до репозиторію");
            
        } catch (Exception e) {
            Logger.error("Помилка створення курсу: " + e.getMessage());
        }
        
        Logger.info("Завершення демонстрації логування");
    }
    
    private static List<Teacher> createTestTeachers() {
        try {
            return List.of(
                Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15),
                Teacher.createTeacher("Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20),
                Teacher.createTeacher("Марія", "Коваль", LocalDate.of(1985, 12, 20), 8)
            );
        } catch (Exception e) {
            Logger.error("Помилка створення тестових викладачів: " + e.getMessage());
            return List.of();
        }
    }
    
    private static List<Student> createTestStudents() {
        try {
            return List.of(
                Student.createStudent("Марія", "Іванова", LocalDate.of(2000, 3, 20),
                    "maria@example.com", LocalDate.of(2023, 9, 1)),
                Student.createStudent("Петро", "Коваленко", LocalDate.of(1999, 12, 5),
                    "petro@example.com", LocalDate.of(2023, 9, 1)),
                Student.createStudent("Олександр", "Бондаренко", LocalDate.of(2001, 7, 15),
                    "olexandr@example.com", LocalDate.of(2024, 1, 15))
            );
        } catch (Exception e) {
            Logger.error("Помилка створення тестових студентів: " + e.getMessage());
            return List.of();
        }
    }
    
    private static List<Course> createTestCourses() {
        try {
            return List.of(
                Course.createCourse("Java Програмування", "Основи програмування на Java", 5, 
                    LocalDate.now().plusMonths(1), CourseLevel.BEGINNER),
                Course.createCourse("Web Розробка", "Сучасна веб-розробка", 6,
                    LocalDate.now().plusMonths(2), CourseLevel.INTERMEDIATE),
                Course.createCourse("Data Science", "Аналіз даних з Python", 7,
                    LocalDate.now().plusMonths(3), CourseLevel.ADVANCED)
            );
        } catch (Exception e) {
            Logger.error("Помилка створення тестових курсів: " + e.getMessage());
            return List.of();
        }
    }
}