package ua.util;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import ua.exceptions.InvalidDataException; // Додано імпорт
import ua.model.*;

public class FileDataReader {
    private static final String DELIMITER = ";";
    
    public static List<Teacher> readTeachersFromFile(String filename) throws InvalidDataException {
        Logger.info("Читання викладачів з файлу: " + filename);
        List<Teacher> teachers = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    if (line.trim().isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    
                    Teacher teacher = parseTeacher(line, lineNumber);
                    teachers.add(teacher);
                    Logger.info("Успішно створено викладача: " + teacher.getFullName());
                    
                } catch (InvalidDataException e) {
                    Logger.warning("Помилка в рядку " + lineNumber + ": " + e.getMessage());
                    throw e;
                } catch (Exception e) {
                    Logger.error("Неочікувана помилка в рядку " + lineNumber + ": " + e.getMessage());
                    throw new InvalidDataException("Помилка формату даних у рядку " + lineNumber, e);
                }
            }
            
            Logger.info("Прочитано " + teachers.size() + " викладачів з файлу");
            
        } catch (FileNotFoundException e) {
            Logger.error("Файл не знайдено: " + filename);
            throw new InvalidDataException("Файл " + filename + " не знайдено", e);
        } catch (IOException e) {
            Logger.error("Помилка читання файлу: " + filename);
            throw new InvalidDataException("Помилка вводу-виводу при читанні файлу", e);
        }
        
        return teachers;
    }
    
    public static List<Student> readStudentsFromFile(String filename) throws InvalidDataException {
        Logger.info("Читання студентів з файлу: " + filename);
        List<Student> students = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    if (line.trim().isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    
                    Student student = parseStudent(line, lineNumber);
                    students.add(student);
                    Logger.info("Успішно створено студента: " + student.getFullName()); // Виправлено на getFullName()
                    
                } catch (InvalidDataException e) {
                    Logger.warning("Помилка в рядку " + lineNumber + ": " + e.getMessage());
                    throw e;
                }
            }
            
            Logger.info("Прочитано " + students.size() + " студентів з файлу");
            
        } catch (FileNotFoundException e) {
            Logger.error("Файл не знайдено: " + filename);
            throw new InvalidDataException("Файл " + filename + " не знайдено", e);
        } catch (IOException e) {
            Logger.error("Помилка читання файлу: " + filename);
            throw new InvalidDataException("Помилка вводу-виводу при читанні файлу", e);
        }
        
        return students;
    }
    
    public static List<Course> readCoursesFromFile(String filename) throws InvalidDataException {
        Logger.info("Читання курсів з файлу: " + filename);
        List<Course> courses = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    if (line.trim().isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    
                    Course course = parseCourse(line, lineNumber);
                    courses.add(course);
                    Logger.info("Успішно створено курс: " + course.name());
                    
                } catch (InvalidDataException e) {
                    Logger.warning("Помилка в рядку " + lineNumber + ": " + e.getMessage());
                    throw e;
                }
            }
            
            Logger.info("Прочитано " + courses.size() + " курсів з файлу");
            
        } catch (FileNotFoundException e) {
            Logger.error("Файл не знайдено: " + filename);
            throw new InvalidDataException("Файл " + filename + " не знайдено", e);
        } catch (IOException e) {
            Logger.error("Помилка читання файлу: " + filename);
            throw new InvalidDataException("Помилка вводу-виводу при читанні файлу", e);
        }
        
        return courses;
    }
    
    private static Teacher parseTeacher(String line, int lineNumber) throws InvalidDataException {
        try {
            String[] parts = line.split(DELIMITER);
            if (parts.length != 4) {
                throw new InvalidDataException("Невірна кількість полів. Очікувалось 4, отримано " + parts.length);
            }
            
            String firstName = parts[0].trim();
            String lastName = parts[1].trim();
            LocalDate birthDate = LocalDate.parse(parts[2].trim());
            int experienceYears = Integer.parseInt(parts[3].trim());
            
            return Teacher.createTeacherWithValidation(firstName, lastName, birthDate, experienceYears);
            
        } catch (DateTimeParseException e) { // Виправлено на DateTimeParseException
            throw new InvalidDataException("Невірний формат дати у рядку " + lineNumber, e);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Невірний формат числа у рядку " + lineNumber, e);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Некоректні дані у рядку " + lineNumber + ": " + e.getMessage(), e);
        }
    }
    
    private static Student parseStudent(String line, int lineNumber) throws InvalidDataException {
        try {
            String[] parts = line.split(DELIMITER);
            if (parts.length != 5) {
                throw new InvalidDataException("Невірна кількість полів. Очікувалось 5, отримано " + parts.length);
            }
            
            String firstName = parts[0].trim();
            String lastName = parts[1].trim();
            LocalDate birthDate = LocalDate.parse(parts[2].trim());
            String email = parts[3].trim();
            LocalDate enrollmentDate = LocalDate.parse(parts[4].trim());
            
            return Student.createStudentWithValidation(firstName, lastName, birthDate, email, enrollmentDate);
            
        } catch (DateTimeParseException e) { // Виправлено на DateTimeParseException
            throw new InvalidDataException("Невірний формат дати у рядку " + lineNumber, e);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Некоректні дані у рядку " + lineNumber + ": " + e.getMessage(), e);
        }
    }
    
    private static Course parseCourse(String line, int lineNumber) throws InvalidDataException {
        try {
            String[] parts = line.split(DELIMITER);
            if (parts.length != 5) {
                throw new InvalidDataException("Невірна кількість полів. Очікувалось 5, отримано " + parts.length);
            }
            
            String name = parts[0].trim();
            String description = parts[1].trim();
            int credits = Integer.parseInt(parts[2].trim());
            LocalDate startDate = LocalDate.parse(parts[3].trim());
            CourseLevel level = CourseLevel.valueOf(parts[4].trim().toUpperCase());
            
            return Course.createCourseWithValidation(name, description, credits, startDate, level);
            
        } catch (DateTimeParseException e) { // Виправлено на DateTimeParseException
            throw new InvalidDataException("Невірний формат дати у рядку " + lineNumber, e);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Невірний формат числа у рядку " + lineNumber, e);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Некоректні дані у рядку " + lineNumber + ": " + e.getMessage(), e);
        }
    }
}