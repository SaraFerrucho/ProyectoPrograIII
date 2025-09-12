package co.edu.uptcsoft.model;

public class Lesson {
    private String id;
    private String title;
    private String type; // video, text, quiz...

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", title=" + title + ", type=" + type + "]";
    }

    public Lesson() {
        id = "";
        title = "";
        type = "";
    }

    public Lesson(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
