package ua.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.assertj.core.api.SoftAssertions;
import static org.junit.jupiter.api.Assertions.*;

import ua.util.FileDataReader;
import ua.exceptions.InvalidDataException;
import ua.model.Teacher;
import ua.model.Student;
import ua.model.Course;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

class FileDataReaderTest {

    @TempDir
    Path tempDir;

    private Path teachersFile;
    private Path studentsFile;
    private Path coursesFile;

    @BeforeEach
    void setUp() {
        teachersFile = tempDir.resolve("test_teachers.csv");
        studentsFile = tempDir.resolve("test_students.csv");
        coursesFile = tempDir.resolve("test_courses.csv");
    }

    @ParameterizedTest
    @MethodSource("provideValidTeachersData")
    @DisplayName("Тестування читання коректних даних викладачів")
    void testReadValidTeachers(String csvContent, int expectedSize) throws IOException, InvalidDataException {
        Files.writeString(teachersFile, csvContent);

        List<Teacher> teachers = FileDataReader.readTeachersFromFile(teachersFile.toString());

        assertEquals(expectedSize, teachers.size());
    }

    private static Stream<Arguments> provideValidTeachersData() {
        return Stream.of(
            Arguments.of("Іван;Петров;1980-05-15;15\nОлена;Сидорова;1975-08-10;20", 2),
            Arguments.of("Марія;Коваль;1985-12-20;8", 1),
            Arguments.of("# Коментар\nАндрій;Мельник;1978-03-25;12\n\nНаталія;Шевченко;1982-07-30;10", 2),
            Arguments.of("", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTeachersData")
    @DisplayName("Тестування читання некоректних даних викладачів")
    void testReadInvalidTeachers(String csvContent) throws IOException {
        Files.writeString(teachersFile, csvContent);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            FileDataReader.readTeachersFromFile(teachersFile.toString());
        });
        assertTrue(exception.getMessage().contains("Некоректні дані"));
    }

    private static Stream<Arguments> provideInvalidTeachersData() {
        return Stream.of(
            Arguments.of("Іван;Петров;некоректна-дата;15"),
            Arguments.of("Іван;Петров;1980-05-15;нечисло"),
            Arguments.of("Іван;Петров;1980-05-15"),
            Arguments.of(";Петров;1980-05-15;15")
        );
    }

    @Test
    @DisplayName("Тестування читання з неіснуючого файлу")
    void testFileNotFound() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            FileDataReader.readTeachersFromFile("nonexistent_file.csv");
        });
        assertTrue(exception.getMessage().contains("не знайдено"));
    }

    @Test
    @DisplayName("Тестування комплексного читання всіх файлів")
    void testComplexFileReading() throws IOException, InvalidDataException {
        // Створення тестових файлів
        Files.writeString(teachersFile, "Іван;Петров;1980-05-15;15");
        Files.writeString(studentsFile, "Марія;Іванова;2000-03-20;maria@example.com;2023-09-01");
        Files.writeString(coursesFile, "Java;Основи;5;2024-09-01;BEGINNER");

        // Читання даних
        List<Teacher> teachers = FileDataReader.readTeachersFromFile(teachersFile.toString());
        List<Student> students = FileDataReader.readStudentsFromFile(studentsFile.toString());
        List<Course> courses = FileDataReader.readCoursesFromFile(coursesFile.toString());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(teachers).hasSize(1);
        softly.assertThat(students).hasSize(1);
        softly.assertThat(courses).hasSize(1);
        softly.assertThat(teachers.get(0).getFullName()).isEqualTo("Іван Петров");
        softly.assertThat(students.get(0).getFormattedName()).isEqualTo("ІВАНОВА М.");
        softly.assertThat(courses.get(0).name()).isEqualTo("Java");
        softly.assertAll();
    }
}