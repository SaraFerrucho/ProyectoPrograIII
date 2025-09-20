package co.edu.uptcsoft.model;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String id;
    private String title;
    private List<Lesson> lessons;

    public Module(String id, String title) {
        this.id = id;
        this.title = title;
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
