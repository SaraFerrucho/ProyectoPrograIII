package co.edu.uptcsoft.model;

<<<<<<< HEAD
public class Course implements Identifiable {
    private String id;
    private String title;
=======
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id;
    private String title;
    private List<Module> modules;
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

    public Course(String id, String title) {
        this.id = id;
        this.title = title;
<<<<<<< HEAD
    }

    @Override
=======
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

>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
    public String getId() {
        return id;
    }

<<<<<<< HEAD
=======
    public void setId(String id) {
        this.id = id;
    }

>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
