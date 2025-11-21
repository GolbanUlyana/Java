package ua.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.assertj.core.api.SoftAssertions;
import static org.junit.jupiter.api.Assertions.*;

import ua.model.Student;
import ua.exceptions.InvalidDataException;

import java.time.LocalDate;
import java.util.stream.Stream;

class StudentTest {

    private Student validStudent;

    @BeforeEach
    void setUp() throws InvalidDataException {
        validStudent = Student.createStudentWithValidation(
            "Марія", "Іванова", LocalDate.of(2000, 3, 20),
            "maria@example.com", LocalDate.of(2023, 9, 1));
    }

    @ParameterizedTest
    @CsvSource({
        "Марія, Іванова, 2000-03-20, maria@example.com, 2023-09-01, ІВАНОВА М., true",
        "Петро, Коваленко, 1999-12-05, petro@example.com, 2024-01-15, КОВАЛЕНКО П., true",
        "Олександр, Бондаренко, 2001-07-15, olexandr@example.com, 2020-09-01, БОНДАРЕНКО О., false"
    })
    @DisplayName("Тестування коректного створення студента")
    void testValidStudentCreation(String firstName, String lastName, String birthDate, 
                                String email, String enrollmentDate, String expectedFormattedName, 
                                boolean expectedActive) throws InvalidDataException {
        SoftAssertions softly = new SoftAssertions();

        Student student = Student.createStudentWithValidation(firstName, lastName, 
            LocalDate.parse(birthDate), email, LocalDate.parse(enrollmentDate));

        softly.assertThat(student.getFormattedName()).isEqualTo(expectedFormattedName);
        softly.assertThat(student.isActiveStudent()).isEqualTo(expectedActive);
        softly.assertThat(student.email()).isEqualTo(email);

        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "email@", "@domain.com", "email@domain"})
    @DisplayName("Тестування некоректного email студента")
    void testInvalidEmail(String invalidEmail) {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Student.createStudentWithValidation("Марія", "Іванова", 
                LocalDate.of(2000, 3, 20), invalidEmail, LocalDate.of(2023, 9, 1));
        });
        assertTrue(exception.getMessage().contains("Некоректні дані студента"));
    }

    @ParameterizedTest
    @MethodSource("provideStudyDurationTestData")
    @DisplayName("Тестування розрахунку тривалості навчання")
    void testGetStudyDurationMonths(String enrollmentDate, int expectedMonths) throws InvalidDataException {
        Student student = Student.createStudentWithValidation("Тест", "Студент", 
            LocalDate.of(2000, 1, 1), "test@example.com", LocalDate.parse(enrollmentDate));

        // Допускаємо похибку ±1 місяць через різницю в днях
        assertTrue(Math.abs(student.getStudyDurationMonths() - expectedMonths) <= 1);
    }

    private static Stream<Arguments> provideStudyDurationTestData() {
        LocalDate now = LocalDate.now();
        return Stream.of(
            Arguments.of(now.minusMonths(6).toString(), 6),
            Arguments.of(now.minusMonths(12).toString(), 12),
            Arguments.of(now.minusMonths(24).toString(), 24),
            Arguments.of(now.plusMonths(1).toString(), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideActiveStudentTestData")
    @DisplayName("Тестування методу isActiveStudent()")
    void testIsActiveStudent(String enrollmentDate, boolean expectedActive) throws InvalidDataException {
        Student student = Student.createStudentWithValidation("Тест", "Студент", 
            LocalDate.of(2000, 1, 1), "test@example.com", LocalDate.parse(enrollmentDate));

        assertEquals(expectedActive, student.isActiveStudent());
    }

    private static Stream<Arguments> provideActiveStudentTestData() {
        LocalDate now = LocalDate.now();
        return Stream.of(
            Arguments.of(now.minusMonths(6).toString(), true),
            Arguments.of(now.minusMonths(12).toString(), true),
            Arguments.of(now.minusMonths(23).toString(), true),
            Arguments.of(now.minusMonths(24).toString(), false),
            Arguments.of(now.minusMonths(36).toString(), false)
        );
    }

    @Test
    @DisplayName("Тестування форматування імені")
    void testGetFormattedName() throws InvalidDataException {
        Student student = Student.createStudentWithValidation("Марія", "Іванова", 
            LocalDate.of(2000, 3, 20), "maria@example.com", LocalDate.of(2023, 9, 1));

        assertEquals("ІВАНОВА М.", student.getFormattedName());
    }
}