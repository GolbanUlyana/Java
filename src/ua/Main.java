package ua;

import java.time.LocalDate;
import ua.model.Course;
import ua.model.Student;
import ua.model.Teacher;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦІЯ РОБОТИ СИСТЕМИ ОНЛАЙН-КУРСІВ ===\n");
        
        // Тестування успішних сценаріїв
        System.out.println("1. Успішні сценарії:");
        
        // Створення об'єктів через конструктори
        Teacher teacher1 = new Teacher("Іван", "Петров", 
                                      LocalDate.of(1980, 5, 15), 15);
        Student student1 = new Student("Марія", "Іванова", 
                                      LocalDate.of(2000, 3, 20),
                                      "maria@example.com", LocalDate.of(2023, 9, 1));
        Course course1 = new Course("Java Програмування", 
                                   "Основи програмування на Java", 5, 
                                   LocalDate.of(2024, 1, 15));
        
        // Створення об'єктів через factory методи
        Teacher teacher2 = Teacher.createTeacher("Олена", "Сидорова", 
                                               LocalDate.of(1975, 8, 10), 20);
        Student student2 = Student.createStudent("Петро", "Коваленко", 
                                               LocalDate.of(1999, 12, 5),
                                               "petro@example.com", LocalDate.of(2023, 9, 1));
        Course course2 = Course.createCourse("Web Розробка", 
                                           "Сучасна веб-розробка", 6, 
                                           LocalDate.of(2024, 2, 1));
        
        System.out.println(teacher1);
        System.out.println(teacher2);
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(course1);
        System.out.println(course2);
        
        // Використання утиліт
        System.out.println("\n2. Використання утиліт:");
        System.out.println("Форматування імені студента: " + student1.getFormattedName());
        System.out.println("Інформація про курс: " + course1.getFormattedInfo());
        
        // Перевірка equals та hashCode
        System.out.println("\n3. Перевірка equals та hashCode:");
        Teacher teacher3 = Teacher.createTeacher("Іван", "Петров", 
                                               LocalDate.of(1980, 5, 15), 15);
        System.out.println("teacher1.equals(teacher3): " + teacher1.equals(teacher3));
        System.out.println("Хеш-коди: " + teacher1.hashCode() + " vs " + teacher3.hashCode());
        
        // Тестування неуспішних сценаріїв
        System.out.println("\n4. Неуспішні сценарії (валідація):");
        
        try {
            Student invalidStudent = new Student("", "Тест", 
                                               LocalDate.of(2000, 1, 1),
                                               "invalid-email", LocalDate.now());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації студента: " + e.getMessage());
        }
        
        try {
            Course invalidCourse = Course.createCourse("", "Опис", 15, LocalDate.now());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації курсу: " + e.getMessage());
        }
        
        try {
            Teacher invalidTeacher = new Teacher("Іван123", "Петров", 
                                               LocalDate.of(1980, 5, 15), 15);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації викладача: " + e.getMessage());
        }
        
        // Демонстрація protected полів
        System.out.println("\n5. Доступ до protected полів через public методи:");
        System.out.println("Ім'я викладача: " + teacher1.getFirstName());
        System.out.println("Прізвище студента: " + student1.getLastName());
        
        // Демонстрація роботи з датами
        System.out.println("\n6. Робота з датами:");
        LocalDate futureDate = LocalDate.now().plusDays(5);
        System.out.println("Завдання з дедлайном " + futureDate + " скоро: " + 
                          ua.util.Utils.isAssignmentDueSoon(futureDate));
    }
}
