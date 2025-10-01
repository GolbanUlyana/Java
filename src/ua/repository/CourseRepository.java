package ua.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ua.model.Course;
import ua.model.CourseLevel;
import ua.util.Logger;

public class CourseRepository extends GenericRepository<Course> {
    
    public CourseRepository() {
        super(new CourseIdentityExtractor(), "CourseRepository");
    }
    
    // Пошук за рівнем складності
    public List<Course> findByLevel(CourseLevel level) {
        Logger.info("Пошук курсів за рівнем: " + level.getUkrainianName());
        
        return getAll().stream()
                .filter(course -> course.level() == level)
                .collect(Collectors.toList());
    }
    
    // Пошук курсів з кредитами в діапазоні
    public List<Course> findByCreditsRange(int minCredits, int maxCredits) {
        Logger.info("Пошук курсів з кредитами від " + minCredits + " до " + maxCredits);
        
        return getAll().stream()
                .filter(course -> course.credits() >= minCredits && course.credits() <= maxCredits)
                .collect(Collectors.toList());
    }
    
    // Пошук курсів за ключовим словом в назві або описі
    public List<Course> findByKeyword(String keyword) {
        Logger.info("Пошук курсів за ключовим словом: '" + keyword + "'");
        
        String lowerKeyword = keyword.toLowerCase();
        return getAll().stream()
                .filter(course -> course.name().toLowerCase().contains(lowerKeyword) ||
                                 course.description().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
    
    // Пошук майбутніх курсів
    public List<Course> findUpcomingCourses() {
        Logger.info("Пошук майбутніх курсів");
        
        return getAll().stream()
                .filter(course -> course.startDate().isAfter(java.time.LocalDate.now()))
                .collect(Collectors.toList());
    }
    
    // Групування курсів за рівнем
    public Map<CourseLevel, List<Course>> groupCoursesByLevel() {
        Logger.info("Групування курсів за рівнем складності");
        
        return getAll().stream()
                .collect(Collectors.groupingBy(Course::level));
    }
    
    // Використання flatMap для створення комбінацій
    public List<String> generateCourseCombinations() {
        Logger.info("Генерація комбінацій курсів");
        
        List<Course> courses = getAll();
        return courses.stream()
                .flatMap(course1 -> courses.stream()
                    .filter(course2 -> !course1.equals(course2))
                    .map(course2 -> course1.name() + " + " + course2.name()))
                .limit(10) // Обмежуємо кількість комбінацій
                .collect(Collectors.toList());
    }
    
    // Статистика курсів
    public void displayCourseStatistics() {
        Logger.info("Генерація статистики курсів");
        
        List<Course> courses = getAll();
        
        // Використання reduce для підрахунку загальних кредитів
        int totalCredits = courses.stream()
                .mapToInt(Course::credits)
                .reduce(0, Integer::sum);
        
        // Використання collectors для статистики
        Map<CourseLevel, Long> countByLevel = courses.stream()
                .collect(Collectors.groupingBy(Course::level, Collectors.counting()));
        
        Optional<Course> mostCreditsCourse = courses.stream()
                .reduce((c1, c2) -> c1.credits() > c2.credits() ? c1 : c2);
        
        System.out.println("📊 Статистика курсів:");
        System.out.println("   - Загальна кількість: " + courses.size());
        System.out.println("   - Сумарні кредити: " + totalCredits);
        System.out.println("   - Середні кредити: " + (totalCredits / (double)courses.size()));
        
        countByLevel.forEach((level, count) -> 
            System.out.println("   - " + level.getUkrainianName() + ": " + count + " курсів"));
        
        mostCreditsCourse.ifPresent(course -> 
            System.out.println("   - Курс з найбільшою кількістю кредитів: " + 
                             course.name() + " (" + course.credits() + " кредитів)"));
    }
    
    // Методи сортування
    public List<Course> sortByCreditsDesc() {
        Logger.info("Сортування курсів за кредитами (спадання)");
        
        return getAll().stream()
                .sorted((c1, c2) -> Integer.compare(c2.credits(), c1.credits()))
                .collect(Collectors.toList());
    }
    
    public List<Course> sortByLevel() {
        Logger.info("Сортування курсів за рівнем складності");
        
        return getAll().stream()
                .sorted((c1, c2) -> c1.level().compareTo(c2.level()))
                .collect(Collectors.toList());
    }
    
    public List<Course> sortByName() {
        Logger.info("Сортування курсів за назвою");
        
        return getAll().stream()
                .sorted((c1, c2) -> c1.name().compareToIgnoreCase(c2.name()))
                .collect(Collectors.toList());
    }
    
    public List<Course> sortByStartDate() {
        Logger.info("Сортування курсів за датою початку");
        
        return getAll().stream()
                .sorted((c1, c2) -> c1.startDate().compareTo(c2.startDate()))
                .collect(Collectors.toList());
    }
    
    // Порівняння звичайного та паралельного потоків
    public void compareStreamPerformance() {
        Logger.info("Порівняння продуктивності stream vs parallelStream");
        
        List<Course> courses = getAll();
        if (courses.size() < 100) {
            System.out.println("Для точного порівняння потрібно більше даних");
            return;
        }
        
        // Звичайний stream
        long startTime = System.nanoTime();
        List<Course> streamResult = courses.stream()
                .filter(c -> c.credits() > 3)
                .filter(c -> c.level() != CourseLevel.BEGINNER)
                .collect(Collectors.toList());
        long streamTime = System.nanoTime() - startTime;
        
        // Паралельний stream
        startTime = System.nanoTime();
        List<Course> parallelResult = courses.parallelStream()
                .filter(c -> c.credits() > 3)
                .filter(c -> c.level() != CourseLevel.BEGINNER)
                .collect(Collectors.toList());
        long parallelTime = System.nanoTime() - startTime;
        
        System.out.println("⚡ Порівняння продуктивності:");
        System.out.println("   - Звичайний stream: " + streamTime / 1000000.0 + " мс");
        System.out.println("   - Паралельний stream: " + parallelTime / 1000000.0 + " мс");
        System.out.println("   - Знайдено курсів: " + streamResult.size());
        System.out.println("   - Результати ідентичні: " + streamResult.equals(parallelResult));
        
        if (parallelTime < streamTime) {
            System.out.println("   ✅ Паралельний stream швидший на " + 
                             (streamTime - parallelTime) / 1000000.0 + " мс");
        } else {
            System.out.println("   ⚠️ Для малих наборів даних звичайний stream може бути швидшим");
        }
    }
}