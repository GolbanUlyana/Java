package ua.model;

public record CourseModule(
    String name,
    String content,
    int durationHours
) {
    // Factory метод
    public static CourseModule createModule(String name, String content, int durationHours) {
        validateData(name, content, durationHours);
        return new CourseModule(name, content, durationHours);
    }
    
    // Валідація
    private static void validateData(String name, String content, int durationHours) {
        if (!ua.util.ValidationHelper.isNotEmpty(name)) {
            throw new IllegalArgumentException("Module name cannot be empty");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        if (durationHours <= 0 || durationHours > 100) {
            throw new IllegalArgumentException("Duration hours must be between 1 and 100");
        }
    }
    
    
    public String getDifficultyLevel() {
        return switch(durationHours) {
            case 1, 2, 3, 4, 5 -> "Легкий";
            case 6, 7, 8, 9, 10 -> "Середній";
            case 11, 12, 13, 14, 15 -> "Складний";
            default -> "Дуже складний";
        };
    }
    
    public boolean hasVideoContent() {
        return content.toLowerCase().contains("відео") || content.toLowerCase().contains("video");
    }
    
    @Override
    public String toString() {
        return String.format("Модуль: %s, тривалість: %d год., рівень: %s", 
                           name, durationHours, getDifficultyLevel());
    }
}