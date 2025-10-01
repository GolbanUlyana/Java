package ua.tests;

import ua.model.Teacher;
import ua.exceptions.InvalidDataException;
import java.time.LocalDate;
import java.time.Month;

public class TeacherTest {
    
    public static void testTeacherCreation() {
        System.out.println("=== Тестування створення викладача ===");
        
        try {
            // Тест 1: Коректні дані
            Teacher teacher = Teacher.createTeacherWithValidation(
                "Іван", "Петров", LocalDate.of(1980, Month.MAY, 15), 15);
            System.out.println("✅ Тест 1 пройдено: коректний викладач створений");
            
            // Тест 2: Перевірка методів
            assert teacher.getFullName().equals("Іван Петров") : "Помилка в getFullName()";
            assert teacher.isExperienced() : "Помилка в isExperienced()";
            System.out.println("✅ Тест 2 пройдено: методи працюють коректно");
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Тест 1 провалено: " + e.getMessage());
        }
    }
    
    public static void testInvalidTeacherData() {
        System.out.println("\n=== Тестування некоректних даних викладача ===");
        
        // Тест 1: Порожнє ім'я
        try {
            Teacher.createTeacherWithValidation("", "Петров", LocalDate.of(1980, 5, 15), 15);
            System.out.println("❌ Тест 1 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 1 пройдено: " + e.getMessage());
        }
        
        // Тест 2: Некоректний стаж
        try {
            Teacher.createTeacherWithValidation("Іван", "Петров", LocalDate.of(1980, 5, 15), -5);
            System.out.println("❌ Тест 2 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 2 пройдено: " + e.getMessage());
        }
        
        // Тест 3: Майбутня дата народження
        try {
            Teacher.createTeacherWithValidation("Іван", "Петров", LocalDate.now().plusDays(1), 10);
            System.out.println("❌ Тест 3 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 3 пройдено: " + e.getMessage());
        }
    }
    
    public static void testTeacherEqualsAndHashCode() {
        System.out.println("\n=== Тестування equals() та hashCode() ===");
        
        try {
            Teacher teacher1 = Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
            Teacher teacher2 = Teacher.createTeacher("Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
            Teacher teacher3 = Teacher.createTeacher("Олена", "Сидорова", LocalDate.of(1975, 8, 10), 20);
            
            // Тест equals()
            assert teacher1.equals(teacher2) : "Помилка в equals() - однакові об'єкти";
            assert !teacher1.equals(teacher3) : "Помилка в equals() - різні об'єкти";
            
            // Тест hashCode()
            assert teacher1.hashCode() == teacher2.hashCode() : "Помилка в hashCode() - однакові об'єкти";
            assert teacher1.hashCode() != teacher3.hashCode() : "Помилка в hashCode() - різні об'єкти";
            
            System.out.println("✅ Тест пройдено: equals() та hashCode() працюють коректно");
            
        } catch (Exception e) {
            System.out.println("❌ Тест провалено: " + e.getMessage());
        }
    }
}