package ua.repository;

import ua.model.Student;
import java.util.Comparator;
import java.util.List;

public class StudentRepository extends GenericRepository<Student> {
    
    public StudentRepository() {
        super(new StudentIdentityExtractor(), "StudentRepository");
    }
    
    public List<Student> sortByEnrollmentDateAsc() {
        Comparator<Student> dateComparator = Comparator.comparing(Student::enrollmentDate);
        return sortByComparator(dateComparator, "датою зарахування (зростання)");
    }
    
    public List<Student> sortByEnrollmentDateDesc() {
        Comparator<Student> dateComparator = Comparator.comparing(Student::enrollmentDate).reversed();
        return sortByComparator(dateComparator, "датою зарахування (спадання)");
    }
    
    public List<Student> sortByEmail() {
        Comparator<Student> emailComparator = Comparator.comparing(Student::email);
        return sortByComparator(emailComparator, "email");
    }
    
    public List<Student> sortByStudyDuration() {
        Comparator<Student> durationComparator = Comparator.comparingInt(Student::getStudyDurationMonths);
        return sortByComparator(durationComparator, "тривалістю навчання");
    }
    
    public List<Student> sortByBirthDate() {
        Comparator<Student> birthComparator = Comparator.comparing(Student::birthDate);
        return sortByComparator(birthComparator, "датою народження");
    }
    
    public List<Student> sortByFullName() {
        Comparator<Student> nameComparator = Comparator
            .comparing(Student::lastName)
            .thenComparing(Student::firstName);
        return sortByComparator(nameComparator, "прізвищем та ім'ям");
    }
    
    public List<Student> sortByActiveStatus() {
        Comparator<Student> activeComparator = (s1, s2) -> {
            boolean s1Active = s1.isActiveStudent();
            boolean s2Active = s2.isActiveStudent();
            return Boolean.compare(s2Active, s1Active);
        };
        return sortByComparator(activeComparator, "статусом активності");
    }
}