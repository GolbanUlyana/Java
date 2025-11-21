package ua.model;



public enum AssignmentType {
    HOMEWORK("Домашня робота", 20),
    PROJECT("Проект", 50),
    QUIZ("Тест", 15),
    EXAM("Екзамен", 100);
    
    private final String ukrainianName;
    private final int maxScore;
    
    AssignmentType(String ukrainianName, int maxScore) {
        this.ukrainianName = ukrainianName;
        this.maxScore = maxScore;
    }
    
    public String getUkrainianName() {
        return ukrainianName;
    }
    
    public int getMaxScore() {
        return maxScore;
    }
    
    public boolean isGraded() {
        return switch(this) {
            case HOMEWORK, PROJECT, EXAM -> true;
            case QUIZ -> false;
        };
    }
}