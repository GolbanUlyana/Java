package ua.tests;

import ua.model.Student;
import ua.exceptions.InvalidDataException;
import java.time.LocalDate;

public class StudentTest {
    
    public static void testStudentCreation() {
        System.out.println("=== Тестування створення студента ===");
        
        try {
            // Тест 1: Коректні дані
            Student student = Student.createStudentWithValidation(
                "Марія", "Іванова", LocalDate.of(2000, 3, 20),
                "maria@example.com", LocalDate.of(2023, 9, 1));
            System.out.println("✅ Тест 1 пройдено: коректний студент створений");
            
            // Тест 2: Перевірка методів
            assert student.getFormattedName().equals("ІВАНОВА М.") : "Помилка в getFormattedName()";
            assert student.isActiveStudent() : "Помилка в isActiveStudent()";
            System.out.println("✅ Тест 2 пройдено: методи працюють коректно");
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Тест 1 провалено: " + e.getMessage());
        }
    }
    
    public static void testInvalidStudentData() {
        System.out.println("\n=== Тестування некоректних даних студента ===");
        
        // Тест 1: Некоректний email
        try {
            Student.createStudentWithValidation(
                "Марія", "Іванова", LocalDate.of(2000, 3, 20),
                "invalid-email", LocalDate.of(2023, 9, 1));
            System.out.println("❌ Тест 1 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 1 пройдено: " + e.getMessage());
        }
        
        // Тест 2: Порожнє прізвище
        try {
            Student.createStudentWithValidation(
                "Марія", "", LocalDate.of(2000, 3, 20),
                "maria@example.com", LocalDate.of(2023, 9, 1));
            System.out.println("❌ Тест 2 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 2 пройдено: " + e.getMessage());
        }
    }
    
    public static void testStudentStudyDuration() {
        System.out.println("\n=== Тестування розрахунку тривалості навчання ===");
        
        try {
            // Студент, який навчається 6 місяців
            Student student = Student.createStudent(
                "Петро", "Коваленко", LocalDate.of(1999, 12, 5),
                "petro@example.com", LocalDate.now().minusMonths(6));
            
            int duration = student.getStudyDurationMonths();
            assert duration >= 5 && duration <= 7 : "Невірна тривалість навчання: " + duration;
            System.out.println("✅ Тест пройдено: тривалість навчання = " + duration + " місяців");
            
        } catch (Exception e) {
            System.out.println("❌ Тест провалено: " + e.getMessage());
        }
    }
}