package ua;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import ua.exceptions.InvalidDataException;
import ua.model.*;
import ua.repository.*;
import ua.tests.CourseTest;
import ua.tests.GenericRepositoryTest;
import ua.tests.SortingTest;
import ua.tests.SpecializedRepositoryTest;
import ua.tests.StudentTest;
import ua.tests.TeacherTest;
import ua.util.*;

public class Main {
    public static void main(String[] args) {
        Logger.info("=== ПРОГРАМА ОНЛАЙН-КУРСІВ З СПЕЦІАЛІЗОВАНИМИ РЕПОЗИТОРІЯМИ ЗАПУЩЕНА ===");
        
        try {
            // 1. Демонстрація читання даних з файлів
            System.out.println("\n1. ЧИТАННЯ ДАНИХ З ФАЙЛІВ:");
            List<Teacher> teachers = demonstrateFileReading();
            List<Student> students = readStudentsFromFile();
            List<Course> courses = readCoursesFromFile();
            
            // 2. Демонстрація спеціалізованих репозиторіїв
            System.out.println("\n2. СПЕЦІАЛІЗОВАНІ РЕПОЗИТОРІЇ ТА STREAM API:");
            demonstrateSpecializedRepositories(teachers, students, courses);
            
            // 3. Демонстрація порівняння stream vs parallelStream
            System.out.println("\n3. ПОРІВНЯННЯ STREAM VS PARALLELSTREAM:");
            demonstrateStreamPerformance();
            
            // 4. Демонстрація enum
            System.out.println("\n4. ДЕМОНСТРАЦІЯ ENUM:");
            demonstrateEnums();
            
            // 5. Демонстрація record
            System.out.println("\n5. ДЕМОНСТРАЦІЯ RECORD:");
            demonstrateRecords();
            
            // 6. Демонстрація switch expressions
            System.out.println("\n6. SWITCH EXPRESSIONS:");
            demonstrateSwitchExpressions();
            
            // 7. Демонстрація роботи з структурованими даними
            System.out.println("\n7. СТРУКТУРОВАНІ ДАНІ:");
            demonstrateStructuredData();
            
            // 8. Демонстрація обробки винятків
            System.out.println("\n8. ОБРОБКА ВИНЯТКІВ:");
            demonstrateExceptionHandling();
            
            // 9. Демонстрація тестування
            System.out.println("\n9. ДЕМОНСТРАЦІЯ ТЕСТУВАННЯ:");
            demonstrateTesting();
            
            // 10. Демонстрація логування
            System.out.println("\n10. ДЕМОНСТРАЦІЯ ЛОГУВАННЯ:");
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
    
    private static void demonstrateSpecializedRepositories(List<Teacher> teachers, 
                                                          List<Student> students, 
                                                          List<Course> courses) {
        Logger.info("Демонстрація спеціалізованих репозиторіїв");
        
        // Створення спеціалізованих репозиторіїв
        TeacherRepository teacherRepo = new TeacherRepository();
        StudentRepository studentRepo = new StudentRepository();
        CourseRepository courseRepo = new CourseRepository();
        
        // Додавання даних
        teacherRepo.addAll(teachers);
        studentRepo.addAll(students);
        courseRepo.addAll(courses);
        
        System.out.println("✅ Репозиторії створені та заповнені:");
        System.out.println("   - Викладачі: " + teacherRepo.size());
        System.out.println("   - Студенти: " + studentRepo.size());
        System.out.println("   - Курси: " + courseRepo.size());
        
        // Демонстрація методів TeacherRepository
        System.out.println("\n--- TeacherRepository методи ---");
        demonstrateTeacherRepositoryMethods(teacherRepo);
        
        // Демонстрація методів StudentRepository
        System.out.println("\n--- StudentRepository методи ---");
        demonstrateStudentRepositoryMethods(studentRepo);
        
        // Демонстрація методів CourseRepository
        System.out.println("\n--- CourseRepository методи ---");
        demonstrateCourseRepositoryMethods(courseRepo);
        
        // Демонстрація Stream API операцій
        System.out.println("\n--- Stream API операції ---");
        demonstrateStreamAPIOperations(teacherRepo, studentRepo, courseRepo);
        
        // Демонстрація операцій сортування
        System.out.println("\n--- Операції сортування ---");
        demonstrateSortingOperations(teacherRepo, studentRepo, courseRepo);
    }
    
    private static void demonstrateTeacherRepositoryMethods(TeacherRepository repo) {
        // Пошук за досвідом
        List<Teacher> experiencedTeachers = repo.findByExperienceRange(10, 20);
        System.out.println("✅ Викладачі з досвідом 10-20 років: " + experiencedTeachers.size());
        experiencedTeachers.forEach(t -> 
            System.out.println("   - " + t.getFullName() + " (" + t.experienceYears() + " років)"));
        
        // Пошук за прізвищем
        List<Teacher> kovals = repo.findByLastNameContains("кова");
        System.out.println("✅ Викладачі з прізвищем, що містить 'кова': " + kovals.size());
        
        // Статистика
        repo.displayExperienceStatistics();
    }
    
    private static void demonstrateStudentRepositoryMethods(StudentRepository repo) {
        // Пошук за доменом email
        List<Student> exampleStudents = repo.findByEmailDomain("example.com");
        System.out.println("✅ Студенти з доменом example.com: " + exampleStudents.size());
        
        // Пошук активних студентів
        List<Student> activeStudents = repo.findActiveStudents();
        System.out.println("✅ Активних студентів: " + activeStudents.size());
        
        // Групування за роком зарахування
        Map<Integer, List<Student>> studentsByYear = repo.groupStudentsByEnrollmentYear();
        System.out.println("✅ Студенти за роками зарахування:");
        studentsByYear.forEach((year, studentList) -> 
            System.out.println("   - " + year + ": " + studentList.size() + " студентів"));
        
        // Статистика
        repo.displayStudentStatistics();
    }
    
    private static void demonstrateCourseRepositoryMethods(CourseRepository repo) {
        // Пошук за рівнем
        List<Course> advancedCourses = repo.findByLevel(CourseLevel.ADVANCED);
        System.out.println("✅ Просунутих курсів: " + advancedCourses.size());
        
        // Пошук за кредитами
        List<Course> midLevelCourses = repo.findByCreditsRange(4, 6);
        System.out.println("✅ Курсів з 4-6 кредитами: " + midLevelCourses.size());
        
        // Пошук за ключовим словом
        List<Course> programmingCourses = repo.findByKeyword("програмування");
        System.out.println("✅ Курсів з ключовим словом 'програмування': " + programmingCourses.size());
        
        // Групування за рівнем
        Map<CourseLevel, List<Course>> coursesByLevel = repo.groupCoursesByLevel();
        System.out.println("✅ Курси за рівнями:");
        coursesByLevel.forEach((level, courseList) -> 
            System.out.println("   - " + level.getUkrainianName() + ": " + courseList.size() + " курсів"));
        
        // Комбінації курсів
        List<String> combinations = repo.generateCourseCombinations();
        System.out.println("✅ Приклади комбінацій курсів:");
        combinations.stream().limit(3).forEach(comb -> 
            System.out.println("   - " + comb));
        
        // Статистика
        repo.displayCourseStatistics();
    }
    
    private static void demonstrateStreamAPIOperations(TeacherRepository teacherRepo,
                                                      StudentRepository studentRepo,
                                                      CourseRepository courseRepo) {
        System.out.println("📊 Демонстрація Stream API операцій:");
        
        // Використання collect
        List<String> teacherNames = teacherRepo.getAll().stream()
                .map(Teacher::getFullName)
                .collect(java.util.stream.Collectors.toList());
        System.out.println("✅ collect - імена викладачів: " + teacherNames.size() + " імен");
        
        // Використання forEach
        System.out.println("✅ forEach - курси:");
        courseRepo.getAll().stream()
                .limit(3)
                .forEach(course -> System.out.println("   - " + course.name() + " (" + course.level().getUkrainianName() + ")"));
        
        // Використання reduce
        int totalStudents = studentRepo.getAll().stream()
                .mapToInt(s -> 1)
                .reduce(0, Integer::sum);
        System.out.println("✅ reduce - загальна кількість студентів: " + totalStudents);
        
        // Використання filter + map
        List<String> experiencedTeacherNames = teacherRepo.getAll().stream()
                .filter(Teacher::isExperienced)
                .map(Teacher::getFullName)
                .collect(java.util.stream.Collectors.toList());
        System.out.println("✅ filter+map - досвідчені викладачі: " + experiencedTeacherNames.size() + " викладачів");
        
        // Використання flatMap
        List<String> allEmails = studentRepo.getAllEmailAddresses();
        System.out.println("✅ flatMap - всі email адреси: " + allEmails.size() + " адрес");
    }
    
    private static void demonstrateSortingOperations(TeacherRepository teacherRepo,
                                                    StudentRepository studentRepo,
                                                    CourseRepository courseRepo) {
        Logger.info("Демонстрація операцій сортування");
        
        // Сортування викладачів
        System.out.println("📋 Викладачі відсортовані за досвідом (спадання):");
        List<Teacher> sortedTeachers = teacherRepo.getAll().stream()
                .sorted((t1, t2) -> Integer.compare(t2.experienceYears(), t1.experienceYears()))
                .toList();
        sortedTeachers.stream()
                .limit(3)
                .forEach(t -> System.out.println("   - " + t.getFullName() + " (" + t.experienceYears() + " років)"));
        
        // Сортування студентів
        System.out.println("📋 Студенти відсортовані за датою зарахування (спадання):");
        List<Student> sortedStudents = studentRepo.getAll().stream()
                .sorted((s1, s2) -> s2.enrollmentDate().compareTo(s1.enrollmentDate()))
                .toList();
        sortedStudents.stream()
                .limit(3)
                .forEach(s -> System.out.println("   - " + s.getFormattedName() + " (" + s.enrollmentDate() + ")"));
        
        // Сортування курсів
        System.out.println("📋 Курси відсортовані за кредитами (спадання):");
        List<Course> sortedCourses = courseRepo.getAll().stream()
                .sorted((c1, c2) -> Integer.compare(c2.credits(), c1.credits()))
                .toList();
        sortedCourses.stream()
                .limit(3)
                .forEach(c -> System.out.println("   - " + c.name() + " (" + c.credits() + " кредитів)"));
        
        // Сортування за рівнем
        System.out.println("📋 Курси відсортовані за рівнем складності:");
        List<Course> coursesByLevel = courseRepo.getAll().stream()
                .sorted((c1, c2) -> c1.level().compareTo(c2.level()))
                .toList();
        coursesByLevel.forEach(c -> System.out.println("   - " + c.name() + " (" + c.level().getUkrainianName() + ")"));
    }
    
    private static void demonstrateStreamPerformance() {
        Logger.info("Демонстрація порівняння stream vs parallelStream");
        
        // Створення великого набору даних для тестування
        CourseRepository repo = new CourseRepository();
        for (int i = 0; i < 1000; i++) {
            try {
                Course course = Course.createCourse("Course " + i, "Description " + i,
                    (i % 10) + 1, LocalDate.now().plusDays(i), 
                    i % 3 == 0 ? CourseLevel.BEGINNER : 
                    i % 3 == 1 ? CourseLevel.INTERMEDIATE : CourseLevel.ADVANCED);
                repo.add(course);
            } catch (Exception e) {
                // Ігноруємо помилки для демонстрації
            }
        }
        
        System.out.println("Створено " + repo.size() + " курсів для тестування продуктивності");
        
        // Звичайний stream
        long startTime = System.nanoTime();
        List<Course> streamResult = repo.getAll().stream()
                .filter(c -> c.credits() > 3)
                .filter(c -> c.level() != CourseLevel.BEGINNER)
                .toList();
        long streamTime = System.nanoTime() - startTime;
        
        // Паралельний stream
        startTime = System.nanoTime();
        List<Course> parallelResult = repo.getAll().parallelStream()
                .filter(c -> c.credits() > 3)
                .filter(c -> c.level() != CourseLevel.BEGINNER)
                .toList();
        long parallelTime = System.nanoTime() - startTime;
        
        System.out.println("⚡ Порівняння продуктивності:");
        System.out.println("   - Звичайний stream: " + streamTime / 1000000.0 + " мс");
        System.out.println("   - Паралельний stream: " + parallelTime / 1000000.0 + " мс");
        System.out.println("   - Знайдено курсів: " + streamResult.size());
        System.out.println("   - Результати ідентичні: " + streamResult.equals(parallelResult));
        
        if (parallelTime < streamTime) {
            System.out.println("   ✅ Паралельний stream швидший на " + 
                             (streamTime - parallelTime) / 1000000.0 + " мс");
        } else {
            System.out.println("   ⚠️ Для малих наборів даних звичайний stream може бути швидшим");
        }
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
            System.out.println("Запуск всіх тестів...");
            
            // Запуск базових тестів
            TeacherTest.testTeacherCreation();
            StudentTest.testStudentCreation();
            CourseTest.testCourseCreation();
            
            // Запуск тестів GenericRepository
            GenericRepositoryTest.testRepositoryOperations();
            
            // Запуск тестів спеціалізованих репозиторіїв
            SpecializedRepositoryTest.testSpecializedRepositories();
            
            // Запуск тестів сортування
            SortingTest.testSortingOperations();
            
            System.out.println("✅ Всі тести пройдено успішно!");
            
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
            CourseRepository testRepo = new CourseRepository();
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
                Teacher.createTeacher("Марія", "Коваль", LocalDate.of(1985, 12, 20), 8),
                Teacher.createTeacher("Андрій", "Мельник", LocalDate.of(1978, 3, 25), 12),
                Teacher.createTeacher("Наталія", "Шевченко", LocalDate.of(1982, 7, 30), 10)
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
                Student.createStudent("Петро", "Коваленko", LocalDate.of(1999, 12, 5),
                    "petro@example.com", LocalDate.of(2023, 9, 1)),
                Student.createStudent("Олександр", "Бондаренko", LocalDate.of(2001, 7, 15),
                    "olexandr@example.com", LocalDate.of(2024, 1, 15)),
                Student.createStudent("Софія", "Ткаченko", LocalDate.of(2002, 1, 10),
                    "sofia@test.org", LocalDate.of(2023, 9, 1)),
                Student.createStudent("Дмитро", "Лисенko", LocalDate.of(2000, 11, 25),
                    "dmitro@test.org", LocalDate.of(2024, 1, 15)),
                Student.createStudent("Анна", "Петренko", LocalDate.of(2001, 4, 18),
                    "anna@example.com", LocalDate.of(2023, 9, 1))
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
                    LocalDate.now().plusMonths(3), CourseLevel.ADVANCED),
                Course.createCourse("Бази даних", "SQL та робота з базами даних", 4,
                    LocalDate.now().plusMonths(4), CourseLevel.INTERMEDIATE),
                Course.createCourse("Мобільна розробка", "Розробка додатків для iOS та Android", 6,
                    LocalDate.now().plusMonths(5), CourseLevel.ADVANCED)
            );
        } catch (Exception e) {
            Logger.error("Помилка створення тестових курсів: " + e.getMessage());
            return List.of();
        }
    }
}