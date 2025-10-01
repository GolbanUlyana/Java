package ua.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ua.model.Student;
import ua.util.Logger;

public class StudentRepository extends GenericRepository<Student> {
    
    public StudentRepository() {
        super(new StudentIdentityExtractor(), "StudentRepository");
    }
    
    // Пошук за доменом email
    public List<Student> findByEmailDomain(String domain) {
        Logger.info("Пошук студентів з доменом email: '" + domain + "'");
        
        return getAll().stream()
                .filter(student -> student.email().toLowerCase().endsWith("@" + domain.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    // Пошук активних студентів (навчаються менше 2 років)
    public List<Student> findActiveStudents() {
        Logger.info("Пошук активних студентів");
        
        return getAll().stream()
                .filter(Student::isActiveStudent)
                .collect(Collectors.toList());
    }
    
    // Пошук за роком зарахування
    public List<Student> findByEnrollmentYear(int year) {
        Logger.info("Пошук студентів за роком зарахування: " + year);
        
        return getAll().stream()
                .filter(student -> student.enrollmentDate().getYear() == year)
                .collect(Collectors.toList());
    }
    
    // Групування студентів за роком зарахування
    public Map<Integer, List<Student>> groupStudentsByEnrollmentYear() {
        Logger.info("Групування студентів за роком зарахування");
        
        return getAll().stream()
                .collect(Collectors.groupingBy(
                    student -> student.enrollmentDate().getYear()
                ));
    }
    
    // Використання flatMap для розгортання даних
    public List<String> getAllEmailAddresses() {
        Logger.info("Отримання всіх email адрес студентів");
        
        return getAll().stream()
                .map(Student::email)
                .collect(Collectors.toList());
    }
    
    // Статистика студентів
    public void displayStudentStatistics() {
        Logger.info("Генерація статистики студентів");
        
        List<Student> students = getAll();
        
        if (students.isEmpty()) {
            System.out.println("Немає студентів для статистики");
            return;
        }
        
        // Використання reduce для знаходження найстаршого студента
        Optional<Student> oldestStudent = students.stream()
                .reduce((s1, s2) -> s1.birthDate().isBefore(s2.birthDate()) ? s1 : s2);
        
        // Використання min/max
        Optional<LocalDate> earliestEnrollment = students.stream()
                .map(Student::enrollmentDate)
                .min(LocalDate::compareTo);
        
        long activeCount = students.stream()
                .filter(Student::isActiveStudent)
                .count();
        
        System.out.println("📊 Статистика студентів:");
        System.out.println("   - Загальна кількість: " + students.size());
        System.out.println("   - Активних студентів: " + activeCount);
        oldestStudent.ifPresent(s -> 
            System.out.println("   - Найстарший студент: " + s.getFullName() + " (" + s.birthDate() + ")"));
        earliestEnrollment.ifPresent(date -> 
            System.out.println("   - Найраніше зарахування: " + date));
    }
    
    // Методи сортування
    public List<Student> sortByEmail() {
        Logger.info("Сортування студентів за email");
        
        return getAll().stream()
                .sorted((s1, s2) -> s1.email().compareToIgnoreCase(s2.email()))
                .collect(Collectors.toList());
    }
    
    public List<Student> sortByEnrollmentDateDesc() {
        Logger.info("Сортування студентів за датою зарахування (спадання)");
        
        return getAll().stream()
                .sorted((s1, s2) -> s2.enrollmentDate().compareTo(s1.enrollmentDate()))
                .collect(Collectors.toList());
    }
    
    public List<Student> sortByFullName() {
        Logger.info("Сортування студентів за повним іменем");
        
        return getAll().stream()
                .sorted((s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName()))
                .collect(Collectors.toList());
    }
    
    public List<Student> sortByStudyDuration() {
        Logger.info("Сортування студентів за тривалістю навчання");
        
        return getAll().stream()
                .sorted((s1, s2) -> Integer.compare(s2.getStudyDurationMonths(), s1.getStudyDurationMonths()))
                .collect(Collectors.toList());
    }
}