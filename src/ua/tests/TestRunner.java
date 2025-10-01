package ua.tests;

public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("🚀 ЗАПУСК ТЕСТІВ ПРОГРАМИ\n");
        
        // Запуск тестів викладачів
        TeacherTest.testTeacherCreation();
        TeacherTest.testInvalidTeacherData();
        TeacherTest.testTeacherEqualsAndHashCode();
        
        // Запуск тестів студентів
        StudentTest.testStudentCreation();
        StudentTest.testInvalidStudentData();
        StudentTest.testStudentStudyDuration();
        
        // Запуск тестів курсів
        CourseTest.testCourseCreation();
        CourseTest.testInvalidCourseData();
        CourseTest.testCourseLevelMethods();
        
        // Запуск тестів читання файлів
        FileDataReaderTest.testFileReading();
        FileDataReaderTest.testInvalidFileReading();
        
        System.out.println("\n🎉 ТЕСТУВАННЯ ЗАВЕРШЕНО!");
    }
}