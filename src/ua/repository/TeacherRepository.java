package ua.repository;

import ua.model.Teacher;
import java.util.Comparator;
import java.util.List;

public class TeacherRepository extends GenericRepository<Teacher> {
    
    public TeacherRepository() {
        super(new TeacherIdentityExtractor(), "TeacherRepository");
    }
    
    public List<Teacher> sortByExperienceDesc() {
        Comparator<Teacher> experienceComparator = Comparator
            .comparingInt(Teacher::experienceYears)
            .reversed();
        return sortByComparator(experienceComparator, "досвідом (спадання)");
    }
    
    public List<Teacher> sortByExperienceAsc() {
        Comparator<Teacher> experienceComparator = Comparator
            .comparingInt(Teacher::experienceYears);
        return sortByComparator(experienceComparator, "досвідом (зростання)");
    }
    
    public List<Teacher> sortByAgeAsc() {
        Comparator<Teacher> ageComparator = Comparator
            .comparing(Teacher::birthDate)
            .reversed();
        return sortByComparator(ageComparator, "віком (зростання)");
    }
    
    public List<Teacher> sortByAgeDesc() {
        Comparator<Teacher> ageComparator = Comparator
            .comparing(Teacher::birthDate);
        return sortByComparator(ageComparator, "віком (спадання)");
    }
    
    public List<Teacher> sortByFullName() {
        Comparator<Teacher> nameComparator = (t1, t2) -> {
            int lastCompare = t1.lastName().compareTo(t2.lastName());
            if (lastCompare != 0) return lastCompare;
            return t1.firstName().compareTo(t2.firstName());
        };
        return sortByComparator(nameComparator, "прізвищем та ім'ям");
    }
    
    public List<Teacher> sortByFullNameReversed() {
        Comparator<Teacher> nameComparator = (t1, t2) -> {
            int lastCompare = t2.lastName().compareTo(t1.lastName());
            if (lastCompare != 0) return lastCompare;
            return t2.firstName().compareTo(t1.firstName());
        };
        return sortByComparator(nameComparator, "прізвищем та ім'ям (зворотній порядок)");
    }
    
    public List<Teacher> sortByBirthDate() {
        return sortByComparator(Comparator.comparing(Teacher::birthDate), "датою народження");
    }
}