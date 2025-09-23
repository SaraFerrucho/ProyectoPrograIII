package co.edu.uptcsoft.model;

<<<<<<< HEAD
public class Lesson implements Identifiable {
    private String id;
    private String title;
    private String type;
=======
public class Lesson {
    private String id;
    private String title;
    private String type; // video, text, quiz...
>>>>>>> ce4c03f78796e33d91b87ff4455f45f1748f3a7a

    public Lesson(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    @Override
<<<<<<< HEAD
=======
    public String toString() {
        return "Lesson [id=" + id + ", title=" + title + ", type=" + type + "]";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
