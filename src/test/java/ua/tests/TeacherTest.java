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

import ua.model.Teacher;
import ua.exceptions.InvalidDataException;

import java.time.LocalDate;
import java.util.stream.Stream;

class TeacherTest {

    private Teacher validTeacher;

    @BeforeEach
    void setUp() throws InvalidDataException {
        validTeacher = Teacher.createTeacherWithValidation(
            "Іван", "Петров", LocalDate.of(1980, 5, 15), 15);
    }

    @ParameterizedTest
    @CsvSource({
        "Іван, Петров, 1980-05-15, 15, Іван Петров, true",
        "Олена, Сидорова, 1975-08-10, 20, Олена Сидорова, true",
        "Марія, Коваль, 1985-12-20, 3, Марія Коваль, false"
    })
    @DisplayName("Тестування коректного створення викладача")
    void testValidTeacherCreation(String firstName, String lastName, String birthDate, 
                                 int experienceYears, String expectedFullName, boolean expectedExperienced) 
                                 throws InvalidDataException {
        SoftAssertions softly = new SoftAssertions();

        Teacher teacher = Teacher.createTeacherWithValidation(firstName, lastName, 
            LocalDate.parse(birthDate), experienceYears);

        softly.assertThat(teacher.getFullName()).isEqualTo(expectedFullName);
        softly.assertThat(teacher.isExperienced()).isEqualTo(expectedExperienced);
        softly.assertThat(teacher.firstName()).isEqualTo(firstName);
        softly.assertThat(teacher.lastName()).isEqualTo(lastName);

        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "Іван123", "John@Doe"})
    @DisplayName("Тестування некоректного імені викладача")
    void testInvalidFirstName(String invalidFirstName) {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Teacher.createTeacherWithValidation(invalidFirstName, "Петров", 
                LocalDate.of(1980, 5, 15), 15);
        });
        assertTrue(exception.getMessage().contains("Некоректні дані викладача"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, 61, 100})
    @DisplayName("Тестування некоректного стажу викладача")
    void testInvalidExperienceYears(int invalidExperience) {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Teacher.createTeacherWithValidation("Іван", "Петров", 
                LocalDate.of(1980, 5, 15), invalidExperience);
        });
        assertTrue(exception.getMessage().contains("Некоректні дані викладача"));
    }

    @Test
    @DisplayName("Тестування equals() та hashCode()")
    void testEqualsAndHashCode() throws InvalidDataException {
        Teacher teacher1 = Teacher.createTeacherWithValidation("Іван", "Петров", 
            LocalDate.of(1980, 5, 15), 15);
        Teacher teacher2 = Teacher.createTeacherWithValidation("Іван", "Петров", 
            LocalDate.of(1980, 5, 15), 15);
        Teacher teacher3 = Teacher.createTeacherWithValidation("Олена", "Сидорова", 
            LocalDate.of(1975, 8, 10), 20);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(teacher1).isEqualTo(teacher2);
        softly.assertThat(teacher1).isNotEqualTo(teacher3);
        softly.assertThat(teacher1.hashCode()).isEqualTo(teacher2.hashCode());
        softly.assertThat(teacher1.hashCode()).isNotEqualTo(teacher3.hashCode());
        softly.assertAll();
    }

    @ParameterizedTest
    @MethodSource("provideExperiencedTeacherData")
    @DisplayName("Тестування методу isExperienced()")
    void testIsExperienced(int experienceYears, boolean expectedExperienced) throws InvalidDataException {
        Teacher teacher = Teacher.createTeacherWithValidation("Тест", "Викладач", 
            LocalDate.of(1980, 1, 1), experienceYears);

        assertEquals(expectedExperienced, teacher.isExperienced());
    }

    private static Stream<Arguments> provideExperiencedTeacherData() {
        return Stream.of(
            Arguments.of(0, false),
            Arguments.of(4, false),
            Arguments.of(5, true),
            Arguments.of(10, true),
            Arguments.of(20, true)
        );
    }

    @Test
    @DisplayName("Тестування toString() методу")
    void testToString() {
        String toStringResult = validTeacher.toString();
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(toStringResult).contains("Іван");
        softly.assertThat(toStringResult).contains("Петров");
        softly.assertThat(toStringResult).contains("15");
        softly.assertAll();
    }
}