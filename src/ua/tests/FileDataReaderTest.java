package ua.tests;

import ua.util.FileDataReader;
import ua.exceptions.InvalidDataException;
import ua.model.Teacher;
import ua.model.Student;
import ua.model.Course;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileDataReaderTest {
    
    public static void testFileReading() {
        System.out.println("=== Тестування читання з файлів ===");
        
        try {
            // Створення тестових файлів
            createTestFiles();
            
            // Тест 1: Читання викладачів
            List<Teacher> teachers = FileDataReader.readTeachersFromFile("test_teachers.csv");
            assert teachers.size() == 2 : "Очікувалось 2 викладача, отримано: " + teachers.size();
            System.out.println("✅ Тест 1 пройдено: викладачі прочитані коректно");
            
            // Тест 2: Читання студентів
            List<Student> students = FileDataReader.readStudentsFromFile("test_students.csv");
            assert students.size() == 2 : "Очікувалось 2 студента, отримано: " + students.size();
            System.out.println("✅ Тест 2 пройдено: студенти прочитані коректно");
            
            // Тест 3: Читання курсів
            List<Course> courses = FileDataReader.readCoursesFromFile("test_courses.csv");
            assert courses.size() == 2 : "Очікувалось 2 курси, отримано: " + courses.size();
            System.out.println("✅ Тест 3 пройдено: курси прочитані коректно");
            
            // Очищення тестових файлів
            cleanupTestFiles();
            
        } catch (InvalidDataException e) {
            System.out.println("❌ Тест провалено: " + e.getMessage());
            cleanupTestFiles();
        } catch (Exception e) {
            System.out.println("❌ Неочікувана помилка: " + e.getMessage());
            cleanupTestFiles();
        }
    }
    
    public static void testInvalidFileReading() {
        System.out.println("\n=== Тестування обробки помилок файлів ===");
        
        // Тест 1: Неіснуючий файл
        try {
            FileDataReader.readTeachersFromFile("nonexistent_file.csv");
            System.out.println("❌ Тест 1 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 1 пройдено: " + e.getMessage());
        }
        
        // Тест 2: Файл з некоректними даними
        try {
            createInvalidTestFile();
            FileDataReader.readTeachersFromFile("invalid_data.csv");
            System.out.println("❌ Тест 2 провалено: очікувалась InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("✅ Тест 2 пройдено: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Неочікувана помилка: " + e.getMessage());
        } finally {
            try { Files.deleteIfExists(Paths.get("invalid_data.csv")); } catch (IOException e) {}
        }
    }
    
    private static void createTestFiles() throws IOException {
        // Тестовий файл викладачів
        Files.write(Paths.get("test_teachers.csv"), List.of(
            "Іван;Петров;1980-05-15;15",
            "Олена;Сидорова;1975-08-10;20"
        ));
        
        // Тестовий файл студентів
        Files.write(Paths.get("test_students.csv"), List.of(
            "Марія;Іванова;2000-03-20;maria@example.com;2023-09-01",
            "Петро;Коваленко;1999-12-05;petro@example.com;2023-09-01"
        ));
        
        // Тестовий файл курсів
        Files.write(Paths.get("test_courses.csv"), List.of(
            "Java;Основи;5;2024-01-15;BEGINNER",
            "Web;Розробка;6;2024-02-01;INTERMEDIATE"
        ));
    }
    
    private static void createInvalidTestFile() throws IOException {
        // Файл з некоректними даними
        Files.write(Paths.get("invalid_data.csv"), List.of(
            "Іван;Петров;некоректна-дата;15"  // Некоректна дата
        ));
    }
    
    private static void cleanupTestFiles() {
        try {
            Files.deleteIfExists(Paths.get("test_teachers.csv"));
            Files.deleteIfExists(Paths.get("test_students.csv"));
            Files.deleteIfExists(Paths.get("test_courses.csv"));
        } catch (IOException e) {
            System.out.println("Попередження: не вдалося видалити тестові файли");
        }
    }
}