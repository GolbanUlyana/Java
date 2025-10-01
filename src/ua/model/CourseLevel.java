package ua.model;

public enum CourseLevel {
    BEGINNER("Початковий"),
    INTERMEDIATE("Середній"), 
    ADVANCED("Просунутий");
    
    private final String ukrainianName;
    
    CourseLevel(String ukrainianName) {
        this.ukrainianName = ukrainianName;
    }
    
    public String getUkrainianName() {
        return ukrainianName;
    }
    
    public int getRecommendedCredits() {
        return switch(this) {
            case BEGINNER -> 3;
            case INTERMEDIATE -> 5;
            case ADVANCED -> 7;
        };
    }
}