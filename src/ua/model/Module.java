package ua.model;

public class Module {
    private String name;
    private String content;
    
    public Module(String name, String content) {
        setName(name);
        setContent(content);
    }
    
    public static Module createModule(String name, String content) {
        return new Module(name, content);
    }
    
    public String getName() { return name; }
    public String getContent() { return content; }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Module name cannot be empty");
        }
        this.name = name.trim();
    }
    
    public void setContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        this.content = content;
    }
    
    @Override
    public String toString() {
        return String.format("Модуль: %s, зміст: %s", name, content);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Module module = (Module) obj;
        return name.equals(module.name) && content.equals(module.content);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, content);
    }
}