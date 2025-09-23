package co.edu.uptcsoft.model;

<<<<<<< HEAD
public class Module implements Identifiable {
    private String id;
    private String title;
=======
import java.util.ArrayList;
import java.util.List;

public class Module {
    private String id;
    private String title;
    private List<Lesson> lessons;
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

    public Module(String id, String title) {
        this.id = id;
        this.title = title;
<<<<<<< HEAD
    }

    @Override
=======
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return "Module [id=" + id + ", title=" + title + "]";
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
