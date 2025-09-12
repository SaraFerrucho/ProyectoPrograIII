package co.edu.uptcsoft.model;

import java.util.List;

public class Course {
    private String id;
    private String title;
    private String description;
    private List<Module> modules;

    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + ", description=" + description + ", modules=" + modules + "]";
    }

    public Course(String id, String title, String description, List<Module> modules) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.modules = modules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
