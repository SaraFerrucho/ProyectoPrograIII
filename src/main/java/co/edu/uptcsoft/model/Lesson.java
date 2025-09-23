package co.edu.uptcsoft.model;

public class Lesson implements Identifiable {
    private String id;
    private String title;
    private String type;

    public Lesson(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
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
