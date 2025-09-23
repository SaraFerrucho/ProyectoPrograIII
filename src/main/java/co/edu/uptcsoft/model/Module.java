package co.edu.uptcsoft.model;

public class Module implements Identifiable {
    private String id;
    private String title;

    public Module(String id, String title) {
        this.id = id;
        this.title = title;
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
}
