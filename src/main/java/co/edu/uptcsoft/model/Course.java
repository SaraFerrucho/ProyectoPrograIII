package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id;
    private String title;
    private List<Module> modules;

    public Course(String id, String title) {
        this.id = id;
        this.title = title;
        this.modules = new ArrayList<>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + "]";
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
}
