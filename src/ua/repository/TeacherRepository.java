package ua.repository;

import java.util.List;
import java.util.stream.Collectors;
import ua.model.Teacher;
import ua.util.Logger;

public class TeacherRepository extends GenericRepository<Teacher> {
    
    public TeacherRepository() {
        super(new TeacherIdentityExtractor(), "TeacherRepository");
    }
    
    // Пошук за досвідом (діапазон)
    public List<Teacher> findByExperienceRange(int minYears, int maxYears) {
        Logger.info("Пошук викладачів з досвідом від " + minYears + " до " + maxYears + " років");
        
        return getAll().stream()
                .filter(teacher -> teacher.experienceYears() >= minYears && 
                                  teacher.experienceYears() <= maxYears)
                .collect(Collectors.toList());
    }
    
    // Пошук за прізвищем (частинний збіг)
    public List<Teacher> findByLastNameContains(String substring) {
        Logger.info("Пошук викладачів з прізвищем, що містить: '" + substring + "'");
        
        return getAll().stream()
                .filter(teacher -> teacher.lastName().toLowerCase().contains(substring.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    // Пошук досвідчених викладачів (≥10 років)
    public List<Teacher> findExperiencedTeachers() {
        Logger.info("Пошук досвідчених викладачів (≥10 років)");
        
        return getAll().stream()
                .filter(Teacher::isExperienced)
                .collect(Collectors.toList());
    }
    
    // Статистика досвіду викладачів
    public void displayExperienceStatistics() {
        Logger.info("Генерація статистики досвіду викладачів");
        
        List<Teacher> teachers = getAll();
        
        // Використання reduce для підрахунку загального досвіду
        int totalExperience = teachers.stream()
                .mapToInt(Teacher::experienceYears)
                .reduce(0, Integer::sum);
        
        // Використання collect для групування
        var experienceGroups = teachers.stream()
                .collect(Collectors.groupingBy(
                    teacher -> {
                        int exp = teacher.experienceYears();
                        if (exp <= 5) return "Початківець";
                        else if (exp <= 15) return "Досвідчений";
                        else return "Експерт";
                    }
                ));
        
        System.out.println("📊 Статистика викладачів:");
        System.out.println("   - Загальна кількість: " + teachers.size());
        System.out.println("   - Сумарний досвід: " + totalExperience + " років");
        System.out.println("   - Середній досвід: " + (totalExperience / (double)teachers.size()) + " років");
        
        experienceGroups.forEach((group, list) -> 
            System.out.println("   - " + group + ": " + list.size() + " викладачів"));
    }
    
    // Методи сортування
    public List<Teacher> sortByExperienceDesc() {
        Logger.info("Сортування викладачів за досвідом (спадання)");
        
        return getAll().stream()
                .sorted((t1, t2) -> Integer.compare(t2.experienceYears(), t1.experienceYears()))
                .collect(Collectors.toList());
    }
    
    public List<Teacher> sortByFullName() {
        Logger.info("Сортування викладачів за повним іменем");
        
        return getAll().stream()
                .sorted((t1, t2) -> t1.getFullName().compareToIgnoreCase(t2.getFullName()))
                .collect(Collectors.toList());
    }
    
    public List<Teacher> sortByBirthDate() {
        Logger.info("Сортування викладачів за датою народження");
        
        return getAll().stream()
                .sorted((t1, t2) -> t1.birthDate().compareTo(t2.birthDate()))
                .collect(Collectors.toList());
    }
    
    public List<Teacher> sortByLastName() {
        Logger.info("Сортування викладачів за прізвищем");
        
        return getAll().stream()
                .sorted((t1, t2) -> t1.lastName().compareToIgnoreCase(t2.lastName()))
                .collect(Collectors.toList());
    }
    
    // Паралельний пошук (для великих наборів даних)
    public List<Teacher> findExperiencedTeachersParallel() {
        Logger.info("Паралельний пошук досвідчених викладачів");
        
        return getAll().parallelStream()
                .filter(Teacher::isExperienced)
                .collect(Collectors.toList());
    }
}