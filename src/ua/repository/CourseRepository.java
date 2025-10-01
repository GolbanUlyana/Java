package ua.repository;

import java.util.Comparator;
import java.util.List;
import ua.model.Course;

public class CourseRepository extends GenericRepository<Course> {
    
    public CourseRepository() {
        super(new CourseIdentityExtractor(), "CourseRepository");
    }
    
    public List<Course> sortByCreditsAsc() {
        Comparator<Course> creditsComparator = Comparator.comparingInt(Course::getCredits);
        return sortByComparator(creditsComparator, "кількістю кредитів (зростання)");
    }
    
    public List<Course> sortByCreditsDesc() {
        Comparator<Course> creditsComparator = Comparator.comparingInt(Course::getCredits).reversed();
        return sortByComparator(creditsComparator, "кількістю кредитів (спадання)");
    }
    
    public List<Course> sortByStartDate() {
        Comparator<Course> dateComparator = Comparator.comparing(Course::getStartDate);
        return sortByComparator(dateComparator, "датою початку");
    }
    
    public List<Course> sortByLevel() {
        Comparator<Course> levelComparator = Comparator.comparing(Course::getLevel);
        return sortByComparator(levelComparator, "рівнем складності");
    }
    
    public List<Course> sortByLevelDesc() {
        Comparator<Course> levelComparator = Comparator.comparing(Course::getLevel).reversed();
        return sortByComparator(levelComparator, "рівнем складності (спадання)");
    }
    
    public List<Course> sortByNameLength() {
        Comparator<Course> lengthComparator = Comparator.comparingInt(c -> c.getName().length());
        return sortByComparator(lengthComparator, "довжиною назви");
    }
    
    public List<Course> sortByDurationToStart() {
        Comparator<Course> durationComparator = Comparator.comparing(c -> 
            java.time.LocalDate.now().until(c.getStartDate()).getDays());
        return sortByComparator(durationComparator, "часом до початку (дні)");
    }
    
    public List<Course> sortByName() {
        Comparator<Course> nameComparator = Comparator.comparing(Course::getName);
        return sortByComparator(nameComparator, "назвою курсу");
    }
}