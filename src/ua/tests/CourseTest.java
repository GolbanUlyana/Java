package ua.tests;

import ua.model.Course;
import ua.model.CourseLevel;
import ua.exceptions.InvalidDataException;
import java.time.LocalDate;

public class CourseTest {
    
    public static void testCourseCreation() {
        System.out.println("=== Тестування створення курсу ===");
        
        try {
            // Тест 1: Коректні дані
            Course course = Course.createCourseWithValidation(
                "Java Програмування", "Основи Java", 5, 
                LocalDate.of(2024, 1, 15), CourseLevel.BEGINNER);
            System.out.println("✅ Тест 1 пройдено: коректний курс створений");
            
            // Тест 2: Перевірка методів
            String description = course.getLevelDescription();
            assert description != null && !description.isEmpty() : "Помилка в getLevelDescription()";
            System.out.println("✅ Тест 2 пройдено: методи працюють коректно");
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Тест 1 провалено: " + e.getMessage());
        }
    }
    
    public static void testInvalidCourseData() {
        System.out.println("\n=== Тестування некоректних даних курсу ===");
        
        // Тест 1: Забагато кредитів
        try {
            Course.createCourseWithValidation(
                "Java", "Опис", 15, LocalDate.of(2024, 1, 15), CourseLevel.BEGINNER);
            System.out.println("❌ Тест 1 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 1 пройдено: " + e.getMessage());
        }
        
        // Тест 2: Порожня назва
        try {
            Course.createCourseWithValidation(
                "", "Опис", 5, LocalDate.of(2024, 1, 15), CourseLevel.BEGINNER);
            System.out.println("❌ Тест 2 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 2 пройдено: " + e.getMessage());
        }
        
        // Тест 3: Минула дата початку
        try {
            Course.createCourseWithValidation(
                "Java", "Опис", 5, LocalDate.of(2020, 1, 15), CourseLevel.BEGINNER);
            System.out.println("❌ Тест 3 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 3 пройдено: " + e.getMessage());
        }
    }
    
    public static void testCourseLevelMethods() {
        System.out.println("\n=== Тестування методів CourseLevel ===");
        
        // Тест рекомендованих кредитів
        assert CourseLevel.BEGINNER.getRecommendedCredits() == 3 : "Помилка для BEGINNER";
        assert CourseLevel.INTERMEDIATE.getRecommendedCredits() == 5 : "Помилка для INTERMEDIATE";
        assert CourseLevel.ADVANCED.getRecommendedCredits() == 7 : "Помилка для ADVANCED";
        
        // Тест українських назв
        assert CourseLevel.BEGINNER.getUkrainianName().equals("Початковий") : "Помилка української назви BEGINNER";
        assert CourseLevel.INTERMEDIATE.getUkrainianName().equals("Середній") : "Помилка української назви INTERMEDIATE";
        assert CourseLevel.ADVANCED.getUkrainianName().equals("Просунутий") : "Помилка української назви ADVANCED";
        
        System.out.println("✅ Тест пройдено: методи CourseLevel працюють коректно");
    }
}