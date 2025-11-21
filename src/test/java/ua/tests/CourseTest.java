package ua.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.assertj.core.api.SoftAssertions;
import static org.junit.jupiter.api.Assertions.*;

import ua.model.Course;
import ua.model.CourseLevel;
import ua.exceptions.InvalidDataException;

import java.time.LocalDate;

class CourseTest {

    @ParameterizedTest
    @CsvSource({
        "Java Програмування, Основи Java, 5, 2024-09-01, BEGINNER",
        "Web Розробка, Сучасна веб-розробка, 6, 2024-10-01, INTERMEDIATE", 
        "Data Science, Аналіз даних, 7, 2024-11-01, ADVANCED"
    })
    @DisplayName("Тестування коректного створення курсу")
    void testValidCourseCreation(String name, String description, int credits, 
                               String startDate, CourseLevel level) throws InvalidDataException {
        SoftAssertions softly = new SoftAssertions();

        assertDoesNotThrow(() -> {
            Course course = Course.createCourseWithValidation(name, description, credits, 
                LocalDate.parse(startDate), level);
            
            softly.assertThat(course.name()).isEqualTo(name);
            softly.assertThat(course.description()).isEqualTo(description);
            softly.assertThat(course.credits()).isEqualTo(credits);
            softly.assertThat(course.level()).isEqualTo(level);
        });

        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 11, 20})
    @DisplayName("Тестування некоректної кількості кредитів")
    void testInvalidCredits(int invalidCredits) {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Course.createCourseWithValidation("Java", "Опис", invalidCredits, 
                LocalDate.now().plusDays(30), CourseLevel.BEGINNER);
        });
        assertTrue(exception.getMessage().contains("Некоректні дані курсу"));
    }

    @ParameterizedTest
    @EnumSource(CourseLevel.class)
    @DisplayName("Тестування методів CourseLevel")
    void testCourseLevelMethods(CourseLevel level) {
        SoftAssertions softly = new SoftAssertions();
        
        softly.assertThat(level.getUkrainianName()).isNotBlank();
        softly.assertThat(level.getRecommendedCredits()).isBetween(3, 7);
        softly.assertThat(level.name()).isNotNull();
        
        softly.assertAll();
    }

    @Test
    @DisplayName("Тестування методу getLevelDescription()")
    void testGetLevelDescription() throws InvalidDataException {
        Course beginnerCourse = Course.createCourseWithValidation("Початковий", "Опис", 3, 
            LocalDate.now().plusDays(30), CourseLevel.BEGINNER);
        Course advancedCourse = Course.createCourseWithValidation("Просунутий", "Опис", 7, 
            LocalDate.now().plusDays(30), CourseLevel.ADVANCED);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(beginnerCourse.getLevelDescription()).contains("початківців");
        softly.assertThat(advancedCourse.getLevelDescription()).contains("досвідчених");
        softly.assertAll();
    }

    @Test
    @DisplayName("Тестування equals() та hashCode()")
    void testEqualsAndHashCode() throws InvalidDataException {
        Course course1 = Course.createCourseWithValidation("Java", "Опис", 5, 
            LocalDate.now().plusDays(30), CourseLevel.BEGINNER);
        Course course2 = Course.createCourseWithValidation("Java", "Опис", 5, 
            LocalDate.now().plusDays(30), CourseLevel.BEGINNER);
        Course course3 = Course.createCourseWithValidation("Web", "Інший опис", 6, 
            LocalDate.now().plusDays(30), CourseLevel.INTERMEDIATE);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(course1).isEqualTo(course2);
        softly.assertThat(course1).isNotEqualTo(course3);
        softly.assertThat(course1.hashCode()).isEqualTo(course2.hashCode());
        softly.assertThat(course1.hashCode()).isNotEqualTo(course3.hashCode());
        softly.assertAll();
    }
}