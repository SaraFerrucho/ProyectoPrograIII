package co.edu.uptcsoft.model;

public class Course {
    private String id;
    private String title;
    
    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + "]";
    }

    public Course(String id, String title) {
        this.id = id;
        this.title = title;
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
