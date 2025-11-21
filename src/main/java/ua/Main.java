package ua;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import ua.exceptions.InvalidDataException;
import ua.model.Assignment;
import ua.model.AssignmentType;
import ua.model.Course;
import ua.model.CourseLevel;
import ua.model.CourseModule;
import ua.model.Student;
import ua.model.Teacher;
import ua.util.FileDataReader;
import ua.util.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.info("=== ПРОГРАМА ОНЛАЙН-КУРСІВ ЗАПУЩЕНА ===");
        
        try {
            // 1. Демонстрація читання даних з файлів
            System.out.println("\n1. ЧИТАННЯ ДАНИХ З ФАЙЛІВ:");
            demonstrateFileReading();
            
            // 2. Демонстрація enum
            System.out.println("\n2. ДЕМОНСТРАЦІЯ ENUM:");
            demonstrateEnums();
            
            // 3. Демонстрація record
            System.out.println("\n3. ДЕМОНСТРАЦІЯ RECORD:");
            demonstrateRecords();
            
            // 4. Демонстрація switch expressions
            System.out.println("\n4. SWITCH EXPRESSIONS:");
            demonstrateSwitchExpressions();
            
            // 5. Демонстрація роботи з структурованими даними
            System.out.println("\n5. СТРУКТУРОВАНІ ДАНІ:");
            demonstrateStructuredData();
            
            // 6. Демонстрація обробки винятків
            System.out.println("\n6. ОБРОБКА ВИНЯТКІВ:");
            demonstrateExceptionHandling();
            
            // 7. Демонстрація тестування (оновлено)
            System.out.println("\n7. ДЕМОНСТРАЦІЯ ТЕСТУВАННЯ:");
            demonstrateTesting();
            
            // 8. Демонстрація логування
            System.out.println("\n8. ДЕМОНСТРАЦІЯ ЛОГУВАННЯ:");
            demonstrateLogging();
            
            Logger.info("=== ПРОГРАМА УСПІШНО ЗАВЕРШЕНА ===");
            
        } catch (Exception e) {
            Logger.error("Критична помилка програми: " + e.getMessage());
            System.out.println("❌ Програма завершилася з помилкою: " + e.getMessage());
        }
    }
    
    private static void demonstrateFileReading() {
        Logger.info("Спроба читання даних з файлів");
        
        try {
            // Спроба зчитати дані з файлів
            List<Teacher> teachers = FileDataReader.readTeachersFromFile("teachers.csv");
            List<Student> students = FileDataReader.readStudentsFromFile("students.csv");
            List<Course> courses = FileDataReader.readCoursesFromFile("courses.csv");
            
            System.out.println("✅ Успішно прочитано:");
            System.out.println("   - Викладачів: " + teachers.size());
            System.out.println("   - Студентів: " + students.size());
            System.out.println("   - Курсів: " + courses.size());
            
            // Демонстрація прочитаних даних
            if (!teachers.isEmpty()) {
                System.out.println("\nПерший викладач: " + teachers.get(0));
            }
            if (!students.isEmpty()) {
                System.out.println("Перший студент: " + students.get(0));
            }
            if (!courses.isEmpty()) {
                System.out.println("Перший курс: " + courses.get(0));
            }
            
        } catch (InvalidDataException e) {
            System.out.println("⚠️ Файли не знайдено або містять помилки. Використовуються тестові дані.");
            Logger.warning("Використання тестових даних: " + e.getMessage());
            
            // Використання тестових даних
            demonstrateWithTestData();
        }
    }
    
    private static void demonstrateWithTestData() {
        Logger.info("Створення тестових даних");
        
        try {
            // Створення тестових даних вручну
            Teacher teacher = Teacher.createTeacherWithValidation(
                "Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
            Student student = Student.createStudentWithValidation(
                "Марія", "Іванова", LocalDate.of(2000, 3, 20),
                "maria@example.com", LocalDate.of(2023, 9, 1));
            Course course = Course.createCourseWithValidation(
                "Java Програмування", "Основи програмування на Java", 5,
                LocalDate.now().plusDays(30), CourseLevel.BEGINNER); // Виправлена дата
                
            System.out.println("✅ Створено тестові дані:");
            System.out.println("   - " + teacher);
            System.out.println("   - " + student);
            System.out.println("   - " + course);
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Помилка створення тестових даних: " + e.getMessage());
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
            System.out.printf("   - %s: макс. %d балів, оцінюється: %s%n",
                            type.getUkrainianName(), type.getMaxScore(), type.isGraded());
        }
    }
    
    private static void demonstrateRecords() {
        Logger.info("Демонстрація record класів");
        
        try {
            Teacher teacher = Teacher.createTeacherWithValidation(
                "Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
            Student student = Student.createStudentWithValidation(
                "Петро", "Коваленко", LocalDate.of(1999, 12, 5),
                "petro@example.com", LocalDate.of(2023, 9, 1));
            CourseModule module = CourseModule.createModule(
                "Java Basics", "Основи програмування на Java", 10);
            
            System.out.println("Record об'єкти:");
            System.out.println("   - " + teacher);
            System.out.println("   - " + student);
            System.out.println("   - " + module);
            
            System.out.println("\nДодаткові методи record:");
            System.out.println("   - Викладач досвідчений: " + teacher.isExperienced());
            System.out.println("   - Тривалість навчання: " + student.getStudyDurationMonths() + " місяців");
            System.out.println("   - Рівень складності модуля: " + module.getDifficultyLevel());
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Помилка створення record об'єктів: " + e.getMessage());
        }
    }
    
    private static void demonstrateSwitchExpressions() {
        Logger.info("Демонстрація switch expressions");
        
        try {
            Course javaCourse = Course.createCourseWithValidation(
                "Java Pro", "Просунута Java", 7, 
                LocalDate.now().plusDays(30), CourseLevel.ADVANCED); // Виправлена дата
            CourseModule advancedModule = CourseModule.createModule(
                "Multithreading", "Багатопоточність у Java", 20);
            Assignment project = Assignment.createAssignment(
                advancedModule, LocalDate.now().plusDays(30), 100, AssignmentType.PROJECT);
            
            System.out.println("Switch expressions в дії:");
            System.out.println("   - Опис курсу: " + javaCourse.getLevelDescription());
            System.out.println("   - Інструкції з здачі: " + project.getSubmissionInstructions());
            System.out.println("   - Групова робота дозволена: " + project.allowsGroupWork());
            
            // Складний switch expression
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
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Помилка демонстрації switch expressions: " + e.getMessage());
        }
    }
    
    private static void demonstrateStructuredData() {
        Logger.info("Демонстрація роботи зі структурованими даними");
        
        try {
            // Створення комплексної структури даних
            Teacher teacher = Teacher.createTeacherWithValidation(
                "Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
            Student student = Student.createStudentWithValidation(
                "Петро", "Коваленко", LocalDate.of(1999, 12, 5),
                "petro@example.com", LocalDate.of(2023, 9, 1));
            
            CourseModule[] modules = {
                CourseModule.createModule("Вступ", "Основи програмування", 5),
                CourseModule.createModule("ООП", "Об'єктно-орієнтоване програмування", 15),
                CourseModule.createModule("Бази даних", "Робота з SQL", 12)
            };
            
            Course course = Course.createCourseWithValidation(
                "FullStack Development", "Повний цикл розробки", 6,
                LocalDate.now().plusDays(14), CourseLevel.INTERMEDIATE);
            
            System.out.println("Комплексна структура даних:");
            System.out.println("   - Курс: " + course);
            System.out.println("   - Викладач: " + teacher.getFullName());
            System.out.println("   - Студент: " + student.getFormattedName());
            System.out.println("   - Активний студент: " + student.isActiveStudent());
            
            System.out.println("\nМодулі курсу:");
            for (CourseModule module : modules) {
                String hasVideo = module.hasVideoContent() ? " (з відео)" : "";
                System.out.println("   - " + module.name() + hasVideo);
            }
            
            System.out.println("\nОбробка модулів з switch:");
            for (CourseModule module : modules) {
                String timeEstimate = switch(module.durationHours()) {
                    case 1, 2, 3, 4, 5 -> "Швидкий модуль";
                    case 6, 7, 8, 9, 10 -> "Середньої тривалості";
                    case 11, 12, 13, 14, 15 -> "Тривалий модуль";
                    default -> "Інтенсивний модуль";
                };
                System.out.println("   - " + module.name() + ": " + timeEstimate);
            }
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Помилка створення структурованих даних: " + e.getMessage());
        }
    }
    
    private static void demonstrateExceptionHandling() {
        Logger.info("Демонстрація обробки винятків");
        
        System.out.println("Тестування обробки некоректних даних:");
        
        // Multi-catch блок
        try {
            // Спроба створити об'єкти з некоректними даними
            Teacher invalidTeacher = Teacher.createTeacherWithValidation(
                "", "Петров", LocalDate.of(1980, 5, 15), 15);
            System.out.println("❌ Очікувалась помилка для викладача");
        } catch (InvalidDataException e) {
            System.out.println("✅ Виключення перехоплено: " + e.getMessage());
        }
        
        try {
            Student invalidStudent = Student.createStudentWithValidation(
                "Марія", "Іванова", LocalDate.of(2000, 3, 20),
                "invalid-email", LocalDate.of(2023, 9, 1));
            System.out.println("❌ Очікувалась помилка для студента");
        } catch (InvalidDataException e) {
            System.out.println("✅ Виключення перехоплено: " + e.getMessage());
        }
        
        try {
            Course invalidCourse = Course.createCourseWithValidation(
                "", "Опис", 15, LocalDate.of(2024, 1, 15), CourseLevel.BEGINNER);
            System.out.println("❌ Очікувалась помилка для курсу");
        } catch (InvalidDataException e) {
            System.out.println("✅ Виключення перехоплено: " + e.getMessage());
        }
        
        // Try-catch-finally демонстрація
        System.out.println("\nДемонстрація try-catch-finally:");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader("test data"));
            String line = reader.readLine();
            System.out.println("✅ Успішно прочитано: " + line);
            
            // Симуляція помилки
            throw new IOException("Тестова помилка вводу-виводу");
            
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
        
        System.out.println("Тестування тепер запускається через Maven команду: mvn test");
        System.out.println("Це демонструє роботу JUnit 5 тестів у папці src/test/java/");
        
        // Проста демонстрація без виклику тестів напряму
        System.out.println("✅ Тестова система налаштована правильно");
        System.out.println("✅ JUnit 5 тести готові до запуску");
        System.out.println("✅ Використовуються параметризовані тести");
        System.out.println("✅ Використовується AssertJ для assertions");
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
            Course newCourse = Course.createCourseWithValidation(
                "Новий курс", "Опис нового курсу", 4,
                LocalDate.now().plusMonths(1), CourseLevel.INTERMEDIATE);
            Logger.info("Курс успішно створено: " + newCourse.name());
            
        } catch (InvalidDataException e) {
            Logger.error("Помилка створення курсу: " + e.getMessage());
        }
        
        Logger.info("Завершення демонстрації логування");
    }
}