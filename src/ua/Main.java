package ua;

import java.time.LocalDate;
import ua.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ОНОВЛЕНА ДЕМОНСТРАЦІЯ З RECORD ТА ENUM ===\n");
        
        // 1. Демонстрація enum
        System.out.println("1. ДЕМОНСТРАЦІЯ ENUM:");
        demonstrateEnums();
        
        // 2. Демонстрація record
        System.out.println("\n2. ДЕМОНСТРАЦІЯ RECORD:");
        demonstrateRecords();
        
        // 3. Демонстрація switch expressions
        System.out.println("\n3. SWITCH EXPRESSIONS:");
        demonstrateSwitchExpressions();
        
        // 4. Демонстрація роботи з структурованими даними
        System.out.println("\n4. СТРУКТУРОВАНІ ДАНІ:");
        demonstrateStructuredData();
    }
    
    private static void demonstrateEnums() {
        for (CourseLevel level : CourseLevel.values()) {
            System.out.printf("%s: %s, рекомендовано кредитів: %d%n",
                            level, level.getUkrainianName(), level.getRecommendedCredits());
        }
        
        System.out.println();
        for (AssignmentType type : AssignmentType.values()) {
            System.out.printf("%s: макс. балів: %d, оцінюється: %s%n",
                            type.getUkrainianName(), type.getMaxScore(), type.isGraded());
        }
    }
    
    private static void demonstrateRecords() {
        // Використовуємо record класи
        Teacher teacher = Teacher.createTeacher("Іван", "Петров", 
                                              LocalDate.of(1980, 5, 15), 15);
        Student student = Student.createStudent("Марія", "Іванова",
                                              LocalDate.of(2000, 3, 20),
                                              "maria@example.com", LocalDate.of(2023, 9, 1));
        CourseModule module = CourseModule.createModule("Java Basics", "Основи програмування на Java", 10);
        
        System.out.println("Teacher record: " + teacher);
        System.out.println("Student record: " + student);
        System.out.println("Module record: " + module);
        
        // Демонстрація методів record
        System.out.println("\nДодаткові методи record:");
        System.out.println("Викладач досвідчений: " + teacher.isExperienced());
        System.out.println("Тривалість навчання студента (місяці): " + student.getStudyDurationMonths());
        System.out.println("Рівень складності модуля: " + module.getDifficultyLevel());
    }
    
   private static void demonstrateSwitchExpressions() {
    Course javaCourse = Course.createCourse("Java Pro", "Просунута Java", 
                                          7, LocalDate.of(2024, 3, 1), CourseLevel.ADVANCED);
    CourseModule advancedModule = CourseModule.createModule("Multithreading", "Багатопоточність у Java", 20);
    
    // Виправте дату на майбутню
    Assignment project = Assignment.createAssignment(advancedModule, 
                                                   LocalDate.now().plusDays(30), 
                                                   100, AssignmentType.PROJECT);
    
    // Switch expressions з enum
    System.out.println("Опис курсу: " + javaCourse.getLevelDescription());
    System.out.println("Інструкції з здачі: " + project.getSubmissionInstructions());
    System.out.println("Групова робота дозволена: " + project.allowsGroupWork());
    
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
    System.out.println("Рекомендація: " + courseRecommendation);
}
    
    private static void demonstrateStructuredData() {
        // Створення комплексної структури даних
        Teacher teacher = Teacher.createTeacher("Олена", "Сидорова",
                                              LocalDate.of(1975, 8, 10), 20);
        
        Student student = Student.createStudent("Петро", "Коваленко",
                                              LocalDate.of(1999, 12, 5),
                                              "petro@example.com", LocalDate.of(2023, 9, 1));
        
        CourseModule[] modules = {
            CourseModule.createModule("Вступ", "Основи програмування", 5),
            CourseModule.createModule("ООП", "Об'єктно-орієнтоване програмування", 15),
            CourseModule.createModule("Бази даних", "Робота з SQL", 12)
        };
        
        Course course = Course.createCourse("FullStack Development", 
                                          "Повний цикл розробки", 6,
                                          LocalDate.of(2024, 2, 1), CourseLevel.INTERMEDIATE);
        
        System.out.println("Комплексна структура даних:");
        System.out.println("Курс: " + course);
        System.out.println("Викладач: " + teacher.getFullName());
        System.out.println("Студент: " + student.getFormattedName());
        System.out.println("Активний студент: " + student.isActiveStudent());
        
        System.out.println("\nМодулі курсу:");
        for (CourseModule module : modules) {
            String hasVideo = module.hasVideoContent() ? " (з відео)" : "";
            System.out.printf("- %s%s%n", module.name(), hasVideo);
        }
        
        // Обробка даних з використанням switch
        System.out.println("\nОбробка модулів:");
        for (CourseModule module : modules) {
            String timeEstimate = switch(module.durationHours()) {
                case 1, 2, 3, 4, 5 -> "Швидкий модуль";
                case 6, 7, 8, 9, 10 -> "Середньої тривалості";
                case 11, 12, 13, 14, 15 -> "Тривалий модуль";
                default -> "Інтенсивний модуль";
            };
            System.out.printf("- %s: %s%n", module.name(), timeEstimate);
        }
    }
}